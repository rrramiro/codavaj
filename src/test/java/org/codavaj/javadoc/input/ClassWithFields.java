package org.codavaj.javadoc.input;

public class ClassWithFields {

    /**
     * Final complex fields
     */
    public static final ArrayReturnClass arc = new ArrayReturnClass();
    protected final BufferCapabilities.FlipContents bcfc = BufferCapabilities.FlipContents.BACKGROUND;
    
    /**
     * A string field.
     */
    public static final String STATICFINAL_FIELD_STRING = "FS";
    public static final int STATICFINAL_FIELD_INT = 1;
    public static final double STATICFINAL_FIELD_DBL = 2.0d;
    public static final float STATICFINAL_FIELD_FLT = 1.0f;
    
    public static final char STATICFINAL_FIELD_CHAR = 'c';
    public static final short STATICFINAL_FIELD_SHORT = 1;
    public static final long STATICFINAL_FIELD_LONG1 = 1l;
    public static final long STATICFINAL_FIELD_LONG2 = 1;
    public static final boolean STATICFINAL_FIELD_BOOL_F = false;
    public static final boolean STATICFINAL_FIELD_BOOL_T = true;
    public static final char STATICFINAL_FIELD_C2 = '\n';
    
    /**
     * A string field.
     */
    public static String STATIC_FIELD_STRING = "FS";
    public static int STATIC_FIELD_INT = 1;
    public static double STATIC_FIELD_DBL = 2.0d;
    public static float STATIC_FIELD_FLT = 1.0f;
    
    /**
     * A string field.
     */
    public final String FINAL_FIELD_STRING = "FS";
    public final int FINAL_FIELD_INT = 1;
    public final double FINAL_FIELD_DBL = 2.0d;
    public final float FINAL_FIELD_FLT = 1.0f;
    
    public DefaultInterface defint_;
    public DefaultInterface[] defintarray_;
    public String strfield_;
    public String[] strarrayfield_;
 
    protected DefaultInterface protdefint_;
    protected DefaultInterface[] protdefintarray_;
    protected String protstrfield_;
    protected String[] protstrarrayfield_;
 
    private DefaultInterface privdefint_;
    private DefaultInterface[] privdefintarray_;
    private String privstrfield_;
    private String[] privstrarrayfield_;
 
    protected String myStringMethod( DefaultInterface[] p1 ) throws Exception {
        return "";
    }
    
    protected void myVoidMethod( String p1 ) {
    }
    
    protected void myVoidExceptionMethod( String p1 ) throws java.lang.Exception{
    }
}
