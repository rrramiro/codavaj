package org.codavaj.dump;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codavaj.process.search.SearchContext;
import org.codavaj.type.Package;
import org.codavaj.type.TypeFactory;

public class Dumper {

    public static void dump( TypeFactory tf ) {
        List packages = tf.getPackages();
        System.out.println("########## ALL PACKAGES ############");
        for( int i=0; i < packages.size(); i++) {
            Package pkg = (Package)packages.get(i);
            System.out.println("["+i+"]" + pkg.getName());
        }
        System.out.println("########## PACKAGE HEIRARCHY ############");
        Package pckg = tf.getDefaultPackage();
        dump( pckg, 0 );
    }
    
    public static void dump(Package pckg, int indentation ) {
        dump(indentation);
        System.out.println(pckg.getName());
        List subpackages = pckg.getPackages();
        for( int i = 0; i < subpackages.size(); i++ ) {
            Package subpackage = (Package)subpackages.get(i);
            dump( subpackage, indentation + 4);
        }
    }

    public static void dump( int indentation ) {
        String ind = "";
        for( int i = 0; i < indentation; i++ ) {
            ind += " ";
        }
        System.out.print(ind);
    }
    
    
    public static void dump( SearchContext ctx ) {
    	Map packageMap = ctx.getPackageMap();
    	
    	System.out.println("########## PACKAGE MAP ###############");
    	Iterator it = packageMap.keySet().iterator();
    	while ( it.hasNext() ) {
    		String packageName = (String)it.next();
    		
    		Package javadocPackage = (Package)packageMap.get(packageName);
    		
    		System.out.println("Package: " + packageName + " -> " + javadocPackage.getName());
    	}
    }
}
