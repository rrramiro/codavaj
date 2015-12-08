package org.codavaj.javadoc.input;

public enum EnumClass {

	HELLO, THERE, YOU, 
	
	// just some text
	MUST, 
	
	/**
	 * DIG-parameter comment
	 */
	DIG, 
	
	
	/**
	 * THIS-parameter comment
	 */
	THIS;

	//TODO enum with constructors
	
	public String attributeString = "hello";
	
	public EnumClass methodInEnumClass( String param1 ) {
		return THIS;
	}
	
	public static EnumClass staticMethodInEnumClass( String param1 ) {
		return THIS;
	}
	
	static {
		System.out.println("Init of EnumClass");
	}
}
