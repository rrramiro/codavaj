package org.codavaj.process.docparser;

import java.lang.reflect.Modifier;
import java.util.jar.JarFile;

import junit.framework.TestCase;

import org.codavaj.dump.Dumper;
import org.codavaj.process.ProgressEvent;
import org.codavaj.process.ProgressListener;
import org.codavaj.type.TypeFactory;
import org.codavaj.type.reflection.SingleJarClassLoader;

public class JarJavadocComparisonTests extends TestCase implements ProgressListener {

    public void notify( ProgressEvent event ) {
        System.out.println(event);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(JarJavadocComparisonTests.class);
    }

    
    public void test_jumpi() throws Exception {
        JarFile jar = new JarFile( "tmp/jumpi/jumpi.jar" );

        SingleJarClassLoader cl = new SingleJarClassLoader(jar);

        DocParser dp = new DocParser();
        dp.setJavadocDirName("tmp/jumpi/javadoc");
        dp.addProgressListener(this);
        dp.process();
        TypeFactory typeFactory = dp.getTypeFactory();
        ClassVsTypeVerifier verifier = new ClassVsTypeVerifier();
        verifier.checkJarAgainstJavadoc(jar, cl, typeFactory, Modifier.PUBLIC );
        Dumper.dump(typeFactory);
    }

    public void test_codavaj() throws Exception {
        JarFile jar = new JarFile( "tmp/codavaj/codavaj.jar" );

        SingleJarClassLoader cl = new SingleJarClassLoader(jar);

        DocParser dp = new DocParser();
        dp.setJavadocDirName("tmp/codavaj/javadoc");
        dp.addProgressListener(this);
        dp.process();
        TypeFactory typeFactory = dp.getTypeFactory();
        ClassVsTypeVerifier verifier = new ClassVsTypeVerifier();
        verifier.checkJarAgainstJavadoc(jar, cl, typeFactory, Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE );
        Dumper.dump(typeFactory);
    }

    public void test_j2se() throws Exception {
        JarFile jar = new JarFile( "tmp/j2se_1_6_16/rt.jar" );

        SingleJarClassLoader cl = new SingleJarClassLoader(jar);

        DocParser dp = new DocParser();
        dp.setJavadocDirName("tmp/j2se_1_6_16/docs/api");
        dp.addProgressListener(this);
        dp.process();
        TypeFactory typeFactory = dp.getTypeFactory();
        ClassVsTypeVerifier verifier = new ClassVsTypeVerifier();
        verifier.checkJarAgainstJavadoc(jar, cl, typeFactory, Modifier.PUBLIC );
        Dumper.dump(typeFactory);
    }
    
}
