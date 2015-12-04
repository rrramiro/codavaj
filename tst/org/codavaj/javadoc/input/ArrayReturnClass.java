package org.codavaj.javadoc.input;

public class ArrayReturnClass {

    public ArrayReturnClass() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String[] stringarray( Object[] p1, DefaultInterface[] p2 ) {
        return new String[] { "just", "a", "test" };
    }
    
    public PublicInterface[] interfarray( String[] p1, Object[] p2) {
        return new PublicInterface[0];
    }


    public static PublicInterface[] interfarray( DefaultInterface[] p1, DefaultInterface p2, Object[] p3) {
        return new PublicInterface[0];
    }
    
    public final void finalmethod() {
    }
}
