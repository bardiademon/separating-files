package bardiademon;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@bardiademon
class ToSplit
{
    private static final int MAX_START_THREAD = 5;
    private String nameDir, type;
    private File file;
    private long totalFile;
    private List<String> types;
    private int max = 0;
    private int threadStart = 0;
    private long sizeComplete = 0;
    private List<ThreadCopy> listThread;

    @bardiademon
    ToSplit (File _File)
    {
        this.file = _File;
        toSplitAllType ();
    }

    @bardiademon
    ToSplit (File _File , String NameDir , String NewType)
    {
        this.file = _File;
        this.nameDir = NameDir;
        this.type = NewType;
        toSplit ();
    }

    @bardiademon
    private void toSplitAllType ()
    {
        foundAllType ();
        if (types != null && types.size () > 1)
        {
            List<File> fileList;
            listThread = new ArrayList<> ();
            for (String type : types)
            {
                if ((fileList = foundFileType (type)) != null && fileList.size () > 1)
                {
                    nameDir = "." + type;
                    File newDir = new File (file.getPath () + File.separator + nameDir);
                    totalFile = fileList.size ();
                    if (!newDir.exists () && newDir.mkdir ())
                    {
                        System.out.println ("Completing ." + type);
                        System.out.print (" 0%");
                        for (File file : fileList) copy (file , newDir);
                    }
                    while (testStartThreadCopy ())
                    {
                        try
                        {
                            Thread.sleep (1000);
                        }
                        catch (InterruptedException ignored)
                        {
                        }
                    }
                    System.out.println ("\rCompleted ." + type);
                }
            }
        }
    }

    @bardiademon
    boolean testStartThreadCopy ()
    {
        if (listThread == null) return false;
        for (ThreadCopy threadCopy : listThread) if (threadCopy.isStart ()) return true;
        return false;
    }

    @bardiademon
    private int progress (int max , long sizeComplete)
    {
        float min = ((float) (sizeComplete * totalFile)) / 100;
        StringBuilder progress;

        boolean ok;
        if (min < 0) ok = (max < (int) (min * 100.0));
        else ok = (max < (int) min);
        if (ok)
        {
            if (min < 0) max = (int) (min * 100.0);
            else max = (int) min;
            progress = new StringBuilder ();
            for (int i = 0; i < max; i++) progress.append ("=");
            System.out.printf ("\r%s | %s%%" , progress.toString () , max);
        }
        return max;
    }

    @bardiademon
    private void copy (File file , File newDir)
    {
        while ((threadStart >= MAX_START_THREAD))
        {
            try
            {
                Thread.sleep (1000);
            }
            catch (InterruptedException ignored)
            {
            }
        }
        listThread.add (new ThreadCopy (file , newDir));
        ++threadStart;
    }

    @bardiademon
    class ThreadCopy extends Thread
    {
        private boolean start;

        private File newDir;
        private File file;

        @bardiademon
        ThreadCopy (File file , File newDir)
        {
            this.file = file;
            this.newDir = newDir;
            start ();
        }

        @Override
        public void run ()
        {
            try
            {
                start = true;
                File newFile = new File (newDir.getPath () + File.separator + FilenameUtils.getName (file.getPath ()));
                new Copy (new FileInputStream (file) , new FileOutputStream (newFile));
                file.delete ();
                max = progress (max , ++sizeComplete);
                start = false;
            }
            catch (IOException ignored)
            {
            }
            --threadStart;
        }

        @bardiademon
        boolean isStart ()
        {
            return start;
        }
    }

    @bardiademon
    private void foundAllType ()
    {
        String type;
        File[] files = this.file.listFiles ();
        if (files != null && files.length > 0)
        {
            types = new ArrayList<> ();
            for (File file : files)
            {
                if (file.isFile () && !Lst.Search.Search (types , (type = FilenameUtils.getExtension (file.getPath ()))))
                    types.add (type);
            }
        }
    }

    @bardiademon
    private List<File> foundFileType (String type)
    {
        File[] files = file.listFiles ();
        if (files != null && files.length > 0)
        {
            List<File> fileList = new ArrayList<> ();
            for (File file : files)
                if (FilenameUtils.getExtension (file.getPath ()).equals (type) && moreType1 (file)) fileList.add (file);
            return fileList;
        }
        return null;
    }

    @bardiademon
    private boolean moreType1 (File file)
    {
        String extension = FilenameUtils.getExtension (file.getPath ());
        File[] files = this.file.listFiles ();
        if (files == null) return false;
        int counter = 0;
        for (File fFromFiles : files)
        {
            if (extension.equals (FilenameUtils.getExtension (fFromFiles.getPath ()))) ++counter;
            if (counter >= 2) return true;
        }
        return false;
    }

    @bardiademon
    private void toSplit ()
    {
        List<File> files = foundFileType (type);

        if (files != null && files.size () > 1)
        {
            listThread = new ArrayList<> ();
            totalFile = files.size ();
            if (this.nameDir.equals (Main.DEFAULT_NAME_DIR)) this.nameDir = "." + type;
            File newDir = new File (file.getPath () + File.separator + this.nameDir);
            if (!newDir.exists () && newDir.mkdir ())
                for (File fFiles : files) if (fFiles.isFile ()) copy (fFiles , newDir);
        }
    }
}
