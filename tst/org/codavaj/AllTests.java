package org.codavaj;

import org.codavaj.process.docparser.AllDocParserTests;
import org.codavaj.process.loader.AllLoaderTests;
import org.codavaj.process.search.AllSearchTests;
import org.codavaj.process.wget.AllWgetTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static void main(String[] args) {
        junit.textui.TestRunner.run(AllTests.suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.codavaj");
        //$JUnit-BEGIN$
        suite.addTest( AllDocParserTests.suite() );
        suite.addTest( AllWgetTests.suite() );
        suite.addTest( AllLoaderTests.suite() );
        suite.addTest( AllSearchTests.suite() );
        //$JUnit-END$
        return suite;
    }

}
