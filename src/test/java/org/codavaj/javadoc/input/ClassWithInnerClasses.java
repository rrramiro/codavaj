package org.codavaj.javadoc.input;

public class ClassWithInnerClasses {

    public ClassWithInnerClasses() {
    }
    
    interface intfce2 {
        public void m1();
    }
    
    class ClassWithOuterClass1 implements intfce2{
        ClassWithOuterClass1() {}
        public void m1(){
            
        }
    }
    protected class ClassWithOuterClass2 {
        ClassWithOuterClass2() {}
        
        public String mcl2( String p1 ) throws Exception {
            return "";
        }
    }
    private class ClassWithOuterClass3 {
        ClassWithOuterClass3() {}
    }
    final class ClassWithOuterClass4 extends ClassWithOuterClass3{
        ClassWithOuterClass4() {}
    }
    
    interface intfce {
        public void m1();
        void m2();
        
    }
    
}
