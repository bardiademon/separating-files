package bardiademon;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class Copy
{
    Copy (FileInputStream fileInputStream , FileOutputStream fileOutputStream) throws IOException
    {
        final byte[] buffer = new byte[1024];
        int len;
        while ((len = fileInputStream.read (buffer)) > 0) fileOutputStream.write (buffer , 0 , len);
        fileOutputStream.flush ();
        fileOutputStream.close ();
        fileInputStream.close ();
    }
}
