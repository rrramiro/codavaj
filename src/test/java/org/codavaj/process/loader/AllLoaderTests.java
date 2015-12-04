package org.codavaj.process.loader;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllLoaderTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllLoaderTests.suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.codavaj.process.loader");
        //$JUnit-BEGIN$
        suite.addTestSuite(TestJarLoader.class);
        //$JUnit-END$
        return suite;
    }

}
