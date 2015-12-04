/**
 * 
 */
package org.codavaj.javadoc.input;

/**
 * @author Peter
 *
 */
public @interface AnnotationClass {

	Class annotationClassDef() default BufferCapabilities.class;
	
	Class annotationClass();
	
	Class<? extends java.util.List> listClass();
	
	
	public static final int INT = 1;
	public String STR="Hello World";
}
