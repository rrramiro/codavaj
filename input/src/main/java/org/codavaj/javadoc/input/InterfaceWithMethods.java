/**
 * 
 */
package org.codavaj.javadoc.input;

/**
 * @author Peter
 *
 */
public interface InterfaceWithMethods {

	/**
	 * Method without public modifier ( which is redundant )
	 * 
	 * @param hello parameter hello
	 */
	void methodVoid( String hello );
	
	/**
	 * Interface with explicit redundant public modifier.
	 * 
	 * @param hello2 parameter hello2
	 */
	public void methodPublicVoid( String hello2 );
	
	/**
	 * Abstract keyword redundant in an interface
	 */
	abstract void abstractMethod();
	
}
