package org.codavaj.process.search;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllSearchTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllSearchTests.suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.codavaj.process.search");
        //$JUnit-BEGIN$
        suite.addTestSuite(TestSearch.class);
        //$JUnit-END$
        return suite;
    }

}
