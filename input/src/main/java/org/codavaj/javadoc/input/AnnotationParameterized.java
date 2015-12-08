/**
 * 
 */
package org.codavaj.javadoc.input;

/**
 * @author Peter
 *
 */
public @interface AnnotationParameterized {

	/**
	 * The attribteStr javadoc comment
	 */
	public String attributeStr = "hello";
	
	/**
	 * The parameter number 1
	 */
	public String param1 = "conflicts";
	
	public String param1() default "DEF-PARAM1";
	
	/**
	 * The param2def javadoc comment
	 * @return param2Def javadoc return comment.
	 */
	public String param2Def() default "DEF-PARAM2";
	
	public String[] paramStrArr();
	
	/**
	 * The paramStrArrDef javadoc comment
	 * @return paramStrArrDef javadoc return comment.
	 */
	public String[] paramStrArrDef() default {"hello","world"};

	public int paramIntDefault() default 1;
	
	public int paramInt();
	
	public AnnotationDefault annotParam();

	public enum Param {
		ONE, TWO, THREE;
	}
	
	public Param paramEnumDefault() default Param.ONE;
	
	public Param paramEnum();
}
