package org.codavaj.process.wget;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllWgetTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllWgetTests.suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.codavaj.process.wget");
        //$JUnit-BEGIN$
        suite.addTestSuite(LinkUtilsTest.class);
        //$JUnit-END$
        return suite;
    }

}
