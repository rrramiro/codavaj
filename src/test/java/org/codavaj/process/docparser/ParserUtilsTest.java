package org.codavaj.process.docparser;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.codavaj.process.docparser.ParserUtils;
import org.codavaj.process.wget.LinkUtilsTest;
import org.codavaj.type.Type;
import org.dom4j.Document;

public class ParserUtilsTest extends TestCase {

    private ParserUtils parserUtil = new ParserUtils();
    
    /*
      */
    public void testImplementsListClass() throws Exception {
        
        String html = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
            "<HTML>TaskSchedulerImpl (Jumpi v1.2.0 Standard Edition Library)\n"+
            "<DL> \n"+
             "<DT>public class TaskSchedulerImpl</DT>\n"+
             "<DT>extends java.lang.Object</DT>\n"+
             "<DT>implements java.lang.Runnable, \n"+
              "<A href=\"../../../../org/jumpi/spi/component/TaskScheduler.html\">TaskScheduler</A>,\n"+
              "java.test.NotStripped"+
             "</DT>\n"+
            "</DL>\n"+
            "</HTML>";

        
        Document xml = parserUtil.loadHtmlAsDom(html); 
        Type t = new Type();
        t.setInterface(false);
        parserUtil.determineImplementsList(t, xml,null);
        List types = t.getImplementsList();
        assertTrue( types.size() == 3);
        assertTrue( types.contains("org.jumpi.spi.component.TaskScheduler"));
        assertTrue( types.contains("java.lang.Runnable"));
        assertTrue( types.contains("java.test.NotStripped"));
    }

    public void testImplementsListInterface() throws Exception {
        
        String html = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
            "<HTML>TaskSchedulerImpl (Jumpi v1.2.0 Standard Edition Library)\n"+
            "<HR/>"+  
            "<DL> "+
              "<DT>public interface Component</DT>"+
              "<DT>extends "+
                "<A href=\"../../../org/jumpi/spi/Configurable.html\">Configurable</A>,"+ 
                "<A href=\"../../../org/jumpi/spi/Manageable.html\">Manageable</A>"+
              "</DT>"+
            "</DL>"+  
            "</HTML>";

        
        Document xml = parserUtil.loadHtmlAsDom(html); 
        Type t = new Type();
        t.setInterface(true);
        parserUtil.determineImplementsList(t, xml, null);
        List types = t.getImplementsList();
        assertTrue( types.size() == 2);
        assertTrue( types.contains("org.jumpi.spi.Configurable"));
        assertTrue( types.contains("org.jumpi.spi.Manageable"));
    }

    public void testImplementsListInterface2() throws Exception {
        
        String html = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
            "<HTML>TaskSchedulerImpl (Jumpi v1.2.0 Standard Edition Library)\n"+
            "<HR/>"+  
            "<DL> "+
            "<DT>public interface Task</DT>"+
            "<DT>extends java.lang.Runnable</DT>"+
            "</DL>"+  
            "</HTML>";

        
        Document xml = parserUtil.loadHtmlAsDom(html); 
        Type t = new Type();
        t.setInterface(true);
        parserUtil.determineImplementsList(t, xml, null);
        List types = t.getImplementsList();
        assertTrue( types.size() == 1);
        assertTrue( types.contains("java.lang.Runnable"));
    }

    public void testJavadocLinkToTypenameEmpty() throws Exception {
        List extlinks = new ArrayList();
        extlinks.add("http://longlink.com/api/");
        extlinks.add("http://shortlink.com/api");
        
        assertEquals("org.jumpi.spi.Configurable", parserUtil.javadocLinkToTypename("../../../org/jumpi/spi/Configurable.html", null));
        assertEquals("org.jumpi.spi.Configurable", parserUtil.javadocLinkToTypename("../../../org/jumpi/spi/Configurable.html#indexinside", null));
        // standard apis
        assertEquals("java.awt.CompositeContext", parserUtil.javadocLinkToTypename("http://java.sun.com/j2se/1.5.0/docs/api/java/awt/CompositeContext.html", null ));
        assertEquals("java.awt.AWTPermission", parserUtil.javadocLinkToTypename("http://java.sun.com/j2se/1.4.2/docs/api/java/awt/AWTPermission.html", null ));
        
        //external custom links
        assertEquals("java.awt.AWTPermission", parserUtil.javadocLinkToTypename("http://longlink.com/api/java/awt/AWTPermission.html", extlinks ));
        assertEquals("java.awt.AWTPermission", parserUtil.javadocLinkToTypename("http://shortlink.com/api/java/awt/AWTPermission.html", extlinks ));
        
        boolean threw = false;
        try {
            parserUtil.javadocLinkToTypename("http://absolutelink.com/api/java/awt/AWTPermission.html", extlinks );
        } catch ( UnresolvedExternalLinkException e ) {
            threw = true;
        }
        assertTrue("Not unresolved exception", threw);
    }
}
