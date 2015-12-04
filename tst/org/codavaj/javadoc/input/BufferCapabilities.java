package org.codavaj.javadoc.input;

public class BufferCapabilities
    implements Cloneable
{
    public static final class FlipContents extends ArrayReturnClass
    {

        private static int I_UNDEFINED;
        private static int I_BACKGROUND;
        private static int I_PRIOR;
        private static int I_COPIED;
        private static final String NAMES[] = {
            "undefined", "background", "prior", "copied"
        };
        public static final FlipContents UNDEFINED;
        public static final FlipContents BACKGROUND;
        public static final FlipContents PRIOR;
        public static final FlipContents COPIED;

        static 
        {
            I_UNDEFINED = 0;
            I_BACKGROUND = 1;
            I_PRIOR = 2;
            I_COPIED = 3;
            UNDEFINED = new FlipContents(I_UNDEFINED);
            BACKGROUND = new FlipContents(I_BACKGROUND);
            PRIOR = new FlipContents(I_PRIOR);
            COPIED = new FlipContents(I_COPIED);
        }

        private FlipContents(int i)
        {
            super();
        }
    }


    public boolean isFullScreenRequired()
    {
        return false;
    }

    public boolean isMultiBufferAvailable()
    {
        return false;
    }

    public boolean isPageFlipping()
    {
        return getFlipContents() != null;
    }

    public FlipContents getFlipContents()
    {
        return flipContents;
    }

    public BufferCapabilities( FlipContents flipcontents)
    {
        flipContents = flipcontents;
    }

    private FlipContents flipContents;
}
