package org.codavaj.process.docparser;

import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.codavaj.type.EnumConst;
import org.codavaj.type.Field;
import org.codavaj.type.Method;
import org.codavaj.type.Modifiable;
import org.codavaj.type.Parameter;
import org.codavaj.type.Type;
import org.codavaj.type.TypeFactory;
import org.codavaj.type.reflection.ReflectionUtils;

public class ClassVsTypeVerifier {

    public void checkClassAgainstJavadoc( String classname, ClassLoader cl, TypeFactory tf, int modifiers  ) {
        try {
            Class c = cl.loadClass(classname);
            Type clazz = ReflectionUtils.getType(c);
            
            Type t = tf.lookupType(classname);
            
            if ( t != null && c != null ) {
                compareTypeToClass(t, clazz, tf, modifiers);
            } else {
                System.out.println("Type " + classname + " not found in javadoc.");
            }
        } catch ( IllegalAccessException iae ) {
            System.out.println("not found: " + iae.getMessage());
        } catch ( ClassNotFoundException e ) {
            System.out.println("not found: " + e.getMessage());
        } catch ( NoClassDefFoundError ncdfe ) {
            System.out.println("no def found: " + ncdfe.getMessage());
        }
    }
    
    public void checkJarAgainstJavadoc( JarFile jar, ClassLoader cl, TypeFactory tf, int modifiers  ) {
        Enumeration jarentries = jar.entries();
        while ( jarentries.hasMoreElements() ) {
            ZipEntry entry = (ZipEntry)jarentries.nextElement();
            String filename = entry.getName();
            
            String classname = filename.replace('/','.');
            if ( classname.indexOf("$") != -1 ) {
                continue; //inner class
            }
            if ( classname.indexOf(".class") == -1 ) {
                continue; // not a .class file
            }
            
            try {
                classname = classname.substring(0, classname.indexOf(".class"));
                Class c = cl.loadClass(classname);
                Type clazz = ReflectionUtils.getType(c);
                
                Type t = tf.lookupType(classname);
                
                if ( t != null && c != null ) {
                    compareTypeToClass(t, clazz, tf, modifiers);
                } else {
                    System.out.println("Type " + classname + " not found in javadoc.");
                }
            } catch ( IllegalAccessException iae ) {
                System.out.println("not found: " + iae.getMessage());
            } catch ( ClassNotFoundException e ) {
                System.out.println("not found: " + e.getMessage());
            } catch ( NoClassDefFoundError ncdfe ) {
                System.out.println("no def found: " + ncdfe.getMessage());
            }
        }
    }
    
    private void compareTypeToClass( Type t, Type c, TypeFactory tf, int modifiers ) throws IllegalAccessException {
    	// From the .class files a @interface annotation is not differentiabable to an interface
        System.out.println("Compare " + t.getTypeName() + " to " + c.getTypeName());
        if ( t.isInterface() != c.isInterface()) {
            System.err.println("Type : " + t.getTypeName() + " mismatch of interface. Type: " + t.isInterface() + " Class: " + c.isInterface());
        } 
        if ( t.isAnnotation() != c.isAnnotation() ) {
            System.err.println("Type : " + t.getTypeName() + " mismatch of annotation. Type: " + t.isAnnotation() + " Class: " + c.isAnnotation());
        }
        if ( t.isEnum() != c.isEnum() ) {
            System.err.println("Type : " + t.getTypeName() + " mismatch of enum. Type: " + t.isEnum() + " Class: " + c.isEnum());
        }
        compareFields( t, c, tf, modifiers );
        compareEnums( t, c, tf, modifiers );
        compareMethods(t, c, tf, modifiers );
        compareConstructors(t, c, tf, modifiers );
        compareSuperclass(t, c, tf, modifiers );
        compareImplementsList(t, c, tf, modifiers );
        compareInnerTypes(t, c, tf, modifiers );
    }
    
    private void compareImplementsList( Type t, Type c, TypeFactory tf, int modifiers ) throws IllegalAccessException {
        // go through each Type interface and check its in the Class
        
        List classList = c.getImplementsList();
        List typeList = t.getImplementsList();
        
        if ( classList.size() != typeList.size() ) {
            System.err.println("Type : " + t.getTypeName() + " mismatch of implements list size. Type: " + t.getImplementsList().size() + " Class: " + c.getImplementsList().size());
            return;
        }
        // for every implements of the reflection class - find the javadoc one
        for( int i = 0; i < classList.size(); i++ ) {
            String cl = (String)classList.get(i);
            boolean found = false;
            for( int j = 0; j < typeList.size(); j++ ) {
                String ty = removeGenerics((String)typeList.get(j));
                if ( ty.equals(cl)) {
                    found = true;
                    break;
                }
            }
            if ( !found ) {
                System.err.println("Type " + t.getTypeName() + " mismatch implements list["+i+"] Class: " + cl);
            }
        }
        // for every implements of the javadoc class - find the reflection one
        for( int i = 0; i < typeList.size(); i++ ) {
            String ty = (String)typeList.get(i);
            boolean found = false;
            for( int j = 0; j < classList.size(); j++ ) {
                String cl = (String)classList.get(j);
                if ( ty.equals(cl)) {
                    found = true;
                    break;
                }
            }
            if ( !found ) {
                System.err.println("Type " + t.getTypeName() + " mismatch implements list["+i+"] Type: " + ty);
            }
        }
    }
    
    private void compareSuperclass( Type t, Type c, TypeFactory tf, int modifiers ) throws IllegalAccessException {
        if ( t.getSuperType() == null && c.getSuperType() == null ) {
            return; //OK
        }
        if ( t.getSuperType() != null && c.getSuperType() == null ) {
            System.err.println("Superclass mismatch. Type: " + t.getSuperType() + " Class: " + c.getSuperType());
            return;
        }
        if ( t.getSuperType() == null && c.getSuperType() != null ) {
            System.err.println("Superclass mismatch. Type: " + t.getSuperType() + " Class: " + c.getSuperType());
            return;
        }
        if ( !removeGenerics(t.getSuperType()).equals( c.getSuperType()) ) {
            System.err.println("Superclass mismatch. Type: " + t.getSuperType() + " Class: " + c.getSuperType());
        }
    }    
    
    private String removeGenerics( String input ) {
    	int idx = input.indexOf("<");
    	if ( idx != -1 ) {
    		return input.substring(0, idx);
    	}
    	return input;//TODO remove trailing <...> in input
    }
    
    private void compareInnerTypes( Type t, Type c, TypeFactory tf, int modifiers ) throws IllegalAccessException {
        
        List classList = c.getInnerTypeList();
        List typeList = t.getInnerTypeList();
        
        // for every implements of the javadoc class - find the reflection one
        // the reverse is not true because we dont filter the modifiers of the
        // classes inner types
        for( int i = 0; i < typeList.size(); i++ ) {
            Type ty = (Type)typeList.get(i);
            boolean found = false;
            for( int j = 0; j < classList.size(); j++ ) {
                Type cl = (Type)classList.get(j);
                if ( ty.getTypeName().equals(cl.getTypeName())) {
                    found = true;
                    compareTypeToClass(ty, cl, tf, modifiers);
                    break;
                }
            }
            if ( !found ) {
                System.err.println("Type " + t.getTypeName() + " not found innertype ["+i+"] Type: " + ty.getTypeName());
            }
        }
    }
    
    private void compareConstructors( Type t, Type c, TypeFactory tf, int modifiers ) throws IllegalAccessException {
        
        for ( int i =0; c.getConstructorList() != null && i < c.getConstructorList().size(); i++ ) {
            Method reflectionMethod = (Method)c.getConstructorList().get(i);
            if ( 0 == ( reflectionMethod.getModifiers() & modifiers )) {
                System.out.println( "skipping due to modifier " + reflectionMethod.getName() );
                continue;
            }
            
            Method javadocMethod = t.lookupConstructor(reflectionMethod.getParameterList() );
            if ( javadocMethod == null ) {
            	if ( t.isEnum() && reflectionMethod.getParameterList().size() == 2 ) { //Enum default constructors have 2 parameters
            		System.out.println( "Ignoring missing Enum Constructor " + t.getTypeName() + " not found.");
            	} else {
            		System.err.println( "Constructor " + t.getTypeName() + " not found.");
            	}
            } else {
                if ( javadocMethod.getName().equals(reflectionMethod.getName())) {
                    System.err.println("Constructor "+ t.getTypeName() + " mismatch name Type: " + javadocMethod.getName() + " Class: " + reflectionMethod.getName());
                }
                compareModifiers(javadocMethod.getName(), javadocMethod, reflectionMethod);
                compareThrowsList(javadocMethod.getName(), javadocMethod.getThrowsList(), reflectionMethod.getThrowsList());
                //TODO parameter list?
            }
        }
    }
    
    private void compareMethods( Type t, Type c, TypeFactory tf, int modifiers ) throws IllegalAccessException {
        
        for ( int i =0; c.getMethodList() != null && i < c.getMethodList().size(); i++ ) {
            Method reflectionMethod = (Method)c.getMethodList().get(i);
            if ( 0 == ( reflectionMethod.getModifiers() & modifiers )) {
                System.out.println( "skipping due to modifier " + reflectionMethod.getName() );
                continue;
            }
            
            Method javadocMethod = t.lookupMethodByName(reflectionMethod.getName(), reflectionMethod.getParameterList() );
            if ( javadocMethod == null ) {
                System.err.println( "Method " + reflectionMethod.getName() + " not found.");
            } else {
                compareReturnParameter( javadocMethod.getName(), javadocMethod.getReturnParameter(), reflectionMethod.getReturnParameter());
                if ( !t.isInterface() ) { // just public/abstract methods
                	compareModifiers(javadocMethod.getName(), javadocMethod, reflectionMethod);
                }
                compareThrowsList(javadocMethod.getName(), javadocMethod.getThrowsList(), reflectionMethod.getThrowsList());
                //TODO method parameters???
            }
        }
    }
    
    private void compareThrowsList( String name, List javadocThrows, List reflectionList ) {
        if ( javadocThrows != null && reflectionList == null ) {
            System.err.println( "Throws (reflection) " + name + " is missing.");
            return;
        }
        if ( javadocThrows == null && reflectionList != null ) {
            System.err.println( "Throws " + name + " is missing in javadoc.");
            return;
        }
        if ( javadocThrows == null && reflectionList == null ) {
            return;
        }
        if ( javadocThrows.size() != reflectionList.size()) {
            System.err.println( "Throws " + name + " size mismatch. Type: " + javadocThrows.size() + " Class : " + reflectionList.size());
            return;
        }
        for( int i = 0; i < javadocThrows.size(); i++ ) {
            if ( !javadocThrows.get(i).equals( reflectionList.get(i))) {
                System.err.println( "Throws " + name + " parameter"+i+" mismatch. Type: " + javadocThrows.get(i) + " Class : " + reflectionList.get(i));
            }
        }
    }
    private void compareReturnParameter( String name, Parameter javadocParameter, Parameter reflectionParameter ) {
        // cannot check the parameter names
        if ( javadocParameter != null && reflectionParameter == null ) {
            System.err.println( "ReturnParameter (reflection) " + name + " is missing.");
            return;
        }
        if ( javadocParameter == null && reflectionParameter != null ) {
            System.err.println( "ReturnParameter " + name + " is missing in javadoc.");
            return;
        }
        if ( javadocParameter.getType() == null ) {
            System.err.println( "ReturnParameter " + name + " is missing Type.");
            return;
        }
        
        if ( javadocParameter.getDegree() != reflectionParameter.getDegree()) {
            System.err.println( "ReturnParameter " + name + " degree mismatch. Type: " + javadocParameter.getDegree() + " Class : " + reflectionParameter.getDegree());
        }
        if ( !removeGenerics(javadocParameter.getType()).equals(reflectionParameter.getType()) ) {
            System.err.println( "ReturnParameter " + name + " type mismatch. Type: " + javadocParameter.getType() + " Class : " + reflectionParameter.getType());
        }
        if ( javadocParameter.isArray() != reflectionParameter.isArray() ) {
            System.err.println( "ReturnParameter " + name + " array mismatch. Type: " + javadocParameter.isArray() + " Class : " + reflectionParameter.isArray());
        }
    }
    
    private void compareFields( Type t, Type c, TypeFactory tf, int modifiers ) throws IllegalAccessException {
        for ( int i = 0; c.getFieldList() != null && i < c.getFieldList().size(); i++ ) {
            Field reflectionField = (Field)c.getFieldList().get(i);
            if ( 0 == ( reflectionField.getModifiers() & modifiers )) {
                System.out.println( "skipping due to modifier " + reflectionField.getName());
                continue;
            }
            Field javadocField = t.lookupFieldByName(reflectionField.getName());
            if ( javadocField == null ) {
            	if ( t.isEnum() && reflectionField.getName().equals("ENUM$VALUES")) {
            		System.out.println("Ignoring missing field "+ reflectionField.getName());
            	} else {
            		System.err.println( "Field " + reflectionField.getName() + " not found.");
            	}
            } else {
            	compareField(t.isInterface(), javadocField, reflectionField);
            }
        }
    }
    
    private void compareEnums( Type t, Type c, TypeFactory tf, int modifiers ) throws IllegalAccessException {
        for ( int i = 0; c.getEnumConstList() != null && i < c.getEnumConstList().size(); i++ ) {
            EnumConst reflectionEnumField = (EnumConst)c.getEnumConstList().get(i);
            EnumConst javadocEnumField = t.lookupEnumConstByName(reflectionEnumField.getName());
            if ( javadocEnumField == null ) {
        		System.err.println( "EnumConst " + reflectionEnumField.getName() + " not found.");
            }
        }
    }
    
    private void compareField( boolean isInterface, Field javadocField, Field reflectionField ) {
        if ( javadocField.getDegree() != reflectionField.getDegree() ) {
            System.err.println( "Field " + reflectionField.getName() + " degree mismatch : Type " + javadocField.getDegree() + " Class : " + reflectionField.getDegree());
        }
        if ( javadocField.getValue() != null && reflectionField.getValue() != null  ) {
            if ( !javadocField.getValue().equals(reflectionField.getValue())) {
                System.err.println("Field " + javadocField.getName() + " value mismatch : Type " + javadocField.getValue() + " Class : " + reflectionField.getValue());
            }
        }
        if ( !isInterface ) {
        	compareModifiers( javadocField.getName(), javadocField, reflectionField );
        }
    }
    
    private void compareModifiers( String name, Modifiable javadocModifiable, Modifiable reflectModifiable ) {
        if ( javadocModifiable.isAbstract() != reflectModifiable.isAbstract() ) {
            System.err.println("Modifiable " + name + " abstract modifier mismatch.");            
        }
        if ( javadocModifiable.isFinal() != reflectModifiable.isFinal() ) {
            System.err.println("Modifiable " + name + " final modifier mismatch.");            
        }
        if ( javadocModifiable.isPrivate() != reflectModifiable.isPrivate() ) {
            System.err.println("Modifiable " + name + " private modifier mismatch.");            
        }
        if ( javadocModifiable.isProtected() != reflectModifiable.isProtected() ) {
            System.err.println("Modifiable " + name + " protected modifier mismatch.");            
        }
        if ( javadocModifiable.isPublic() != reflectModifiable.isPublic() ) {
            System.err.println("Modifiable " + name + " public modifier mismatch.");            
        }
        if ( javadocModifiable.isStatic() != reflectModifiable.isStatic() ) {
            System.err.println("Modifiable " + name + " static modifier mismatch.");            
        }
        if ( javadocModifiable.isStrictFp() != reflectModifiable.isStrictFp() ) {
            System.err.println("Modifiable " + name + " strictfp modifier mismatch.");            
        }
    }
}
