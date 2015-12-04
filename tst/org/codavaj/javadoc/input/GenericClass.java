package org.codavaj.javadoc.input;

import java.io.Serializable;

public class GenericClass<E extends Enum<E>> implements Serializable, Comparable<E> {

	/**
	 * Generic field E
	 */
	public E fieldOfE;
	
	/**
	 * A comparable generic field.
	 */
	public Comparable<E> comparableE;
	
	/**
	 * Array two-dimensions of E
	 */
	public E[][] arrayFieldOfE;
	
	/**
	 * A comparable generic field.
	 */
	public Comparable<E>[] arrayOfomparableE;
	
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(E o) {
		return 0;
	}
	
	/**
	 * Constructor e
	 * @param e
	 */
	public GenericClass(E e ) {
		fieldOfE = e;
	}
	
	/**
	 * Constructor e+f
	 * @param e
	 * @param f
	 * @param arrayE
	 * @param arrayComparableE
	 */
	public GenericClass(E e, Comparable<E> f, E[] arrayE, Comparable<E>[][] arrayComparableE ) {
		fieldOfE = e;
		comparableE = f;
	}
	
	/**
	 * Man this one is a tricky one!
	 * @param <T>
	 * @param enumType
	 * @param name
	 * @return
	 */
	static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
		return null;
	}
	
	/**
	 * A generic method
	 * @param input
	 * @return the input
	 */
	public E thisMethod( E input ) {
		return input;
	}

	/**
	 * A generic method
	 * @param input
	 * @return the input
	 */
	public Comparable<E> thisComparableMethod( Comparable<E> input ) {
		return comparableE;
	}
}
