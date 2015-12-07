package org.codavaj.process.docparser;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.jar.JarFile;

import junit.framework.TestCase;

import org.codavaj.dump.Dumper;
import org.codavaj.process.ProgressEvent;
import org.codavaj.process.ProgressListener;
import org.codavaj.process.srcwriter.SrcWriter;
import org.codavaj.type.TypeFactory;
import org.codavaj.type.reflection.SingleJarClassLoader;

public class SingleClassJavadocComparisonTests extends TestCase implements ProgressListener {

    public void notify( ProgressEvent event ) {
        System.out.println(event);
    }

    public void test_SingleCodavajClass() throws Exception {
        System.out.print(new File(".").getAbsolutePath());
        JarFile jar = new JarFile("input/target/scala-2.11/input_2.11-1.0.0.jar" );
        SingleJarClassLoader cl = new SingleJarClassLoader(jar);
        DocParser dp = new DocParser();
        dp.setJavadocDirName("input/target/scala-2.11/api");
        dp.setJavadocClassName("org.codavaj.javadoc.input.GenericClass");
        dp.setDebugFlag(true);
        dp.addProgressListener(this);
        dp.process();
        TypeFactory typeFactory = dp.getTypeFactory();
        ClassVsTypeVerifier verifier = new ClassVsTypeVerifier();
        verifier.checkClassAgainstJavadoc(dp.getJavadocClassName(), cl, typeFactory, Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE );
        Dumper.dump(typeFactory);
        
        
        SrcWriter sw = new SrcWriter();
        sw.setSrcDirName("target/codavaj/input/src");
        // link the previously parsed javadocs with the writer
        sw.setTypeFactory(dp.getTypeFactory());
        sw.addProgressListener(this);
        sw.process();
        
    }
/*


    public void test_SingleJ2seClass() throws Exception {
        JarFile jar = new JarFile( "tmp/j2se_1_6_16/rt.jar" );

        SingleJarClassLoader cl = new SingleJarClassLoader(jar);

        DocParser dp = new DocParser();
        dp.setJavadocDirName("tmp/j2se_1_6_16/docs/api");
        dp.setJavadocClassName("java.rmi.MarshalledObject");
        dp.setDebugFlag(true);
        dp.addProgressListener(this);
        dp.process();
        TypeFactory typeFactory = dp.getTypeFactory();
        ClassVsTypeVerifier verifier = new ClassVsTypeVerifier();
        verifier.checkClassAgainstJavadoc(dp.getJavadocClassName(), cl, typeFactory, Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE );
        Dumper.dump(typeFactory);
        
        
        SrcWriter sw = new SrcWriter();
        sw.setSrcDirName("tmp/j2se_1_6_16/src");
        // link the previously parsed javadocs with the writer
        sw.setTypeFactory(dp.getTypeFactory());
        sw.addProgressListener(this);
        sw.process();
        
    }
    */
}
