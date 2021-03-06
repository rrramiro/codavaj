/*
 *   Copyright 2005 Peter Klauser
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.codavaj.type;

import org.codavaj.AbstractLogger;
import org.codavaj.type.reflection.ReflectionUtils;
import org.codavaj.type.reflection.SingleJarClassLoader;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;


/**
 * A container for Types.
 */
public class TypeFactory extends AbstractLogger {
    
    private Map types = new HashMap();
    private Map packages = new HashMap();
    
    /**
     * Creates a new TypeFactory object.
     */
    public TypeFactory() {
        packages.put(Package.defaultPackageName, new Package(Package.defaultPackageName));
    }

    private void linkPackage( Package pckg ) {
        // link packages to themselves heirarchicaly
        while( !Package.defaultPackageName.equals(pckg.getName())) {
            String parentName = pckg.getParentPackageName();
            
            Package parentPckg = (Package)packages.get(parentName);
            if ( parentPckg == null ) {
                parentPckg = new Package( parentName );
                packages.put(parentPckg.getName(), parentPckg);
            }
            parentPckg.addPackage(pckg);
            pckg.setParentPackage(parentPckg);
            
            // go up the heirarchy to the default package - adding it.
            pckg = parentPckg;
        }
    }
    private Package linkPackage( Type type ) {
        // link the type to it's package and vice versa
        Package pckg = (Package)packages.get(type.getPackageName());
        if ( pckg == null ) {
            pckg = new Package( type.getPackageName());
            packages.put(pckg.getName(), pckg);
        }
        type.setPackage(pckg);
        pckg.addType(type);
        
        linkPackage( pckg );
        return pckg;
    }
    
    /**
     * Create a Type given it's name in the TypeFactory. The Type name
     * should use '.' for the package separator and '$' for inner class
     * separation. The Type is automatically linked to it's containing
     * Package.
     *
     * @param typename fully qualified type name - a.b.c.D$E
     *
     * @return a Type with the given fully qualified name.
     * 
     * @see Type#getTypeName()
     */
    public synchronized Type createType(String typename) {
        Type type = (Type) types.get(typename);

        if (type == null) {
            type = new Type();
            type.setTypeName(typename);
            types.put(typename, type);
            
            linkPackage( type );
        }

        return type;
    }

    protected synchronized void addType( Type t ) {
        if ( t != null ) {
            types.put(t.getTypeName(), t);
            linkPackage(t);
        }
    }
    
    /**
     * Lookup a Type given it's fully qualified Type name.
     *
     * @param typeName the fully qualified Type name.
     *
     * @return a Type with the given fully qualified name, or null if not found.
     * 
     * @see Type#getTypeName()
     */
    public Type lookupType(String typeName) {
        return (Type) types.get(typeName);
    }

    /**
     * Return the list of all Types in the container. The Types are not sorted in
     * any way.
     *
     * @return the list of all Types.
     */
    public List getTypes() {
        return new ArrayList(types.values());
    }

    /**
     * Return the list of all Packages in the container. The Packages are not
     * sorted in any way.
     * 
     * @return the list of all Packages.
     */
    public List getPackages() {
        return new ArrayList(packages.values());
    }
    
    /**
     * Return the default Package ( Package.getName returns "" )
     * @return the default Package
     */
    public Package getDefaultPackage() {
        return (Package)packages.get(Package.defaultPackageName);
    }
    
    /**
     * Link inner types to their enclosing type. Set extends type to
     * java.lang.Object if there isn't one.
     */
    public void link() {
        // need to go through each type, determine if it is an inner type and
        // if it is, add it to it's outer type
        List allTypes = getTypes();

        for (int i = 0; (allTypes != null) && (i < allTypes.size()); i++) {
            Type type = (Type) allTypes.get(i);

            if (type.getEnclosingType() != null) {
                // lookup the enclosing type
                Type enclosingType = (Type) types.get(type.getEnclosingType());

                if (enclosingType != null) {
                    enclosingType.addInnerType(type);
                } else {
                    warning("enclosing type " + type.getEnclosingType()
                        + " was not found in type " + type.getTypeName());
                }
            }

            if (!type.isInterface()
                    && !"java.lang.Object".equals(type.getTypeName())
                    && (type.getSuperType() == null)) {
                type.setSuperType("java.lang.Object");
            }
        }
    }
    
    
    /**
     * Load the class contents of a Jar file into a TypeFactory.
     * 
     * @param jar the Jar file to load.
     * @return the class contents of a Jar file as a TypeFactory.
     */
    public static TypeFactory getInstance( JarFile jar ) {
        TypeFactory tf = new TypeFactory();
        
        SingleJarClassLoader cl = new SingleJarClassLoader(jar);
        
        Enumeration jarentries = jar.entries();
        while ( jarentries.hasMoreElements() ) {
            ZipEntry entry = (ZipEntry)jarentries.nextElement();
            String filename = entry.getName();
            
            String classname = filename.replace('/','.');
            if ( classname.indexOf(".class") == -1 ) {
                continue; // not a .class file
            }
            
            try {
                classname = classname.substring(0, classname.indexOf(".class"));
                Class c = cl.loadClass(classname);
                if ( c.getDeclaringClass() != null ) {
                    // inner class - skip
                    continue;
                }
                Type clazz = ReflectionUtils.getType(c);
                tf.addType(clazz);
                
            } catch ( ClassNotFoundException e ) {
                tf.warning("TypeFactory class not found!",e);
            } catch ( NoClassDefFoundError ncdfe ) {
                tf.warning("TypeFactory no class definition found!", ncdfe);
            }
        }
        tf.link();
        return tf;
    }
    
}
