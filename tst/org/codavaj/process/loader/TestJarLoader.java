package org.codavaj.process.loader;

import java.util.jar.JarFile;

import org.codavaj.dump.Dumper;
import org.codavaj.process.ProgressEvent;
import org.codavaj.process.ProgressListener;
import org.codavaj.process.docparser.DocParser;
import org.codavaj.type.TypeFactory;
import org.codavaj.type.reflection.SingleJarClassLoader;

import junit.framework.TestCase;

public class TestJarLoader extends TestCase implements ProgressListener {

    public void notify( ProgressEvent event ) {
        System.out.println(event);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJarLoader.class);
    }
    
    public void test_jumpi() throws Exception {

        JarLoader loader = new JarLoader();
        loader.setJarFileName("tmp/jumpi/jumpi.jar");
        loader.addProgressListener(this);
        loader.process();
        TypeFactory typeFactory = loader.getTypeFactory();
        Dumper.dump(typeFactory);
    }

    public void test_j2se() throws Exception {
        JarLoader loader = new JarLoader();
        loader.setJarFileName("tmp/j2se_1_6_16/rt.jar");
        loader.addProgressListener(this);
        loader.process();
        TypeFactory typeFactory = loader.getTypeFactory();
        Dumper.dump(typeFactory);
    }

    public void test_codavaj() throws Exception {

        JarLoader loader = new JarLoader();
        loader.setJarFileName("tmp/codavaj/codavaj.jar");
        loader.addProgressListener(this);
        loader.process();
        TypeFactory typeFactory = loader.getTypeFactory();
        Dumper.dump(typeFactory);
    }


}
