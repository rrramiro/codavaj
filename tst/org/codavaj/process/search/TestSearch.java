package org.codavaj.process.search;

import org.codavaj.dump.Dumper;
import org.codavaj.process.ProgressEvent;
import org.codavaj.process.ProgressListener;
import org.codavaj.process.docparser.DocParser;
import org.codavaj.process.loader.JarLoader;
import org.codavaj.type.TypeFactory;

import junit.framework.TestCase;

public class TestSearch extends TestCase implements ProgressListener {

    public void notify( ProgressEvent event ) {
        System.out.println(event);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestSearch.class);
    }
    /*
    public void test_codavaj_identity() throws Exception {

        JarLoader loader1 = new JarLoader();
        loader1.setJarFileName("tmp/codavaj/codavaj.jar");
        loader1.addProgressListener(this);
        loader1.process();
        TypeFactory typeFactory1 = loader1.getTypeFactory();
        
        JarLoader loader2 = new JarLoader();
        loader2.setJarFileName("tmp/codavaj/codavaj.jar");
        loader2.addProgressListener(this);
        loader2.process();
        TypeFactory typeFactory2 = loader2.getTypeFactory();
        
        Search search = new Search();
        search.setJavadocTypeFactory(typeFactory1);
        search.setJarTypeFactory(typeFactory2);
        search.process();
        
        SearchContext ctx = search.getSearchResult();
        Dumper.dump(ctx);
        
        assertTrue( "no match!", ctx.getMap(typeFactory1.getDefaultPackage()) != null );
    }
    */
    /*
    public void test_codavaj_javadoc() throws Exception {

        JarLoader loader1 = new JarLoader();
        loader1.setJarFileName("tmp/codavaj/codavaj.jar");
        loader1.addProgressListener(this);
        loader1.process();
        TypeFactory typeFactory1 = loader1.getTypeFactory();
        
        DocParser parser = new DocParser();
        parser.setJavadocDirName("tmp/codavaj/javadoc");
        parser.process();
        TypeFactory typeFactory2 = parser.getTypeFactory();
        
        Search search = new Search();
        search.setJavadocTypeFactory(typeFactory1);
        search.setJarTypeFactory(typeFactory2);
        search.process();
        
        SearchContext ctx = search.getSearchResult();
        Dumper.dump(ctx);
        
        assertTrue( "no match!", ctx.getMap(typeFactory1.getDefaultPackage()) != null );
    }
    */
    
    public void test_codavaj_miss() throws Exception {

        JarLoader loader1 = new JarLoader();
        loader1.setJarFileName("tmp/codavaj/codavaj.jar");
        loader1.addProgressListener(this);
        loader1.process();
        TypeFactory typeFactory1 = loader1.getTypeFactory();
        
        JarLoader loader2 = new JarLoader();
        loader2.setJarFileName("lib/dom4j-1.6.1.jar");
        loader2.addProgressListener(this);
        loader2.process();
        TypeFactory typeFactory2 = loader2.getTypeFactory();
        
        Search search = new Search();
        search.setJavadocTypeFactory(typeFactory1);
        search.setJarTypeFactory(typeFactory2);
        search.process();
        
        SearchContext ctx = search.getSearchResult();
        Dumper.dump(ctx);
        
        assertTrue( "matched!", ctx.getMap(typeFactory1.getDefaultPackage()) == null );
    }


}
