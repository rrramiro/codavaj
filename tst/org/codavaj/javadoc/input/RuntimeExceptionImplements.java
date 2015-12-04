package org.codavaj.javadoc.input;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

/**
 * @author Peter
 */
public class RuntimeExceptionImplements extends RuntimeException implements
        Serializable, Externalizable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public RuntimeExceptionImplements() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public RuntimeExceptionImplements(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     */
    public RuntimeExceptionImplements(Throwable arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param arg0
     * @param arg1
     */
    public RuntimeExceptionImplements(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    public void readExternal(ObjectInput objectinput)
    throws IOException, ClassNotFoundException{}

    public void writeExternal(ObjectOutput objectoutput)
    throws IOException{}
}
