package org.codavaj.process.docparser;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllDocParserTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllDocParserTests.suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.codavaj.process.docparser");
        //$JUnit-BEGIN$
        suite.addTestSuite(SingleClassJavadocComparisonTests.class);
        suite.addTestSuite(ParserUtilsTest.class);
        suite.addTestSuite(JarJavadocComparisonTests.class);
        //$JUnit-END$
        return suite;
    }

}
