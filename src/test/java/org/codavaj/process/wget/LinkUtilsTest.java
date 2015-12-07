package org.codavaj.process.wget;

import org.codavaj.process.wget.LinkUtils;

import junit.framework.TestCase;

public class LinkUtilsTest extends TestCase {

    private LinkUtils linkUtil = new LinkUtils();
    

   /*
     * Test method for 'org.codavaj.process.wget.LinkUtils.normalizeUrl(String)'
     */
    public void testNormalizeUrl() {
        assertTrue( "http://a:8080/b/c/".equals(linkUtil.normalizeUrl("http://a:8080/b/c")));
    }

    /*
     * Test method for 'org.codavaj.process.wget.LinkUtils.relativeUrl(String, String, String)'
     */
    public void testRelativeUrl() {
        // ignore empty links
        assertNull( linkUtil.relativeUrl("http://root/", "http://root/a/b/c/", ""));
        assertNull( linkUtil.relativeUrl("http://root/", "http://root/a/b/c/", "  "));

        // ignore absolute paths
        assertNull( linkUtil.relativeUrl("http://root/", "http://root/a.html", "/")); 
        assertNull( linkUtil.relativeUrl("http://root/", "http://root/a.html", "/b.html")); 
        
        // straight forward relative path
        assertTrue( "b.html".equals(linkUtil.relativeUrl("http://root/", "http://root/a.html", "b.html"))); 
        assertTrue( "a/b/d.html".equals(linkUtil.relativeUrl("http://root/", "http://root/a/b/c.html", "d.html"))); 
        
        // stripping the current directory
        assertTrue( "b.html".equals(linkUtil.relativeUrl("http://root/", "http://root/a.html", "./b.html"))); 
        assertTrue( "b.html".equals(linkUtil.relativeUrl("http://root/", "http://root/a.html", "././b.html"))); 

        // absolute paths below root directory
        assertTrue( "b.html".equals(linkUtil.relativeUrl("http://root/", "http://root/a.html", "http://root/b.html"))); 
        
        // stripping the parent directory
        assertNull( linkUtil.relativeUrl("http://root/", "http://root/a/b/c.html", "../d.html")); 

    }

    public void testRelativeDirectoryOfLink() {
        assertTrue( "".equals(linkUtil.relativeDirectoryOfLink("notadir")));
        
        assertTrue( "adir/".equals(linkUtil.relativeDirectoryOfLink("adir/")));
        assertTrue( "adir/".equals(linkUtil.relativeDirectoryOfLink("adir/a")));
        assertTrue( "adir/".equals(linkUtil.relativeDirectoryOfLink("adir/bc")));

        assertTrue( "adir/bdir/".equals(linkUtil.relativeDirectoryOfLink("adir/bdir/cfile")));
    }
    
    public void testBasenameOfLink() {
        assertTrue( "file".equals(linkUtil.basenameOfLink("http://a/b/c/file")));
        assertTrue( "file.html".equals(linkUtil.basenameOfLink("http://a/file.html")));
    }

}
