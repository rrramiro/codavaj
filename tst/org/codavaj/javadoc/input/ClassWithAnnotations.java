package org.codavaj.javadoc.input;

@AnnotationDefault
@AnnotationParameterized(param1="class", paramStrArr={"c","e"},paramInt=2,annotParam=@AnnotationDefault,paramEnum=AnnotationParameterized.Param.ONE)
public class ClassWithAnnotations {

	@AnnotationDefault public String strAttribute1;
	
	@AnnotationParameterized(param1="attribute", paramStrArr={"c","e"},paramInt=2,annotParam=@AnnotationDefault,paramEnum=AnnotationParameterized.Param.TWO)
	public String strAttribute2;
	
	@AnnotationClass(annotationClass=java.lang.Object.class,listClass=java.util.ArrayList.class)
	public String strAttribute3;
	
    public ClassWithAnnotations() {
    }
    
    public ClassWithAnnotations( String p1 ) {
    }
    
    @AnnotationDefault
    public ClassWithAnnotations( ArrayReturnClass p1 ) {
        
    }
    
    @AnnotationParameterized(param1="method", paramStrArr={"b","a"},paramInt=2,annotParam=@AnnotationDefault,paramEnum=AnnotationParameterized.Param.THREE)
    protected ClassWithAnnotations( boolean p1 ) {
        
    }
    
    private ClassWithAnnotations( long p1 ) {
        
    }
    
    @AnnotationDefault
    protected String myStringMethod( DefaultInterface[] p1 ) throws Exception {
        return "";
    }
    
    @AnnotationClass(annotationClass=java.util.ArrayList.class, listClass=java.util.LinkedList.class)
    protected void myVoidMethod( String p1 ) {
    }
    
    protected void myVoidExceptionMethod( String p1 ) throws java.lang.Exception{
    }
}
