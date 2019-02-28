package bardiademon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@bardiademon
class Types
{

    private List<File> fileList = new ArrayList<> ();

    @bardiademon
    Types (File _File , List<String> Names , List<String> Types)
    {
        BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));
        new CreateOption ().create (new String[]{"All files and all files in folders" , "All files in this folder"});
        try
        {
            System.out.print ("Input: ");
            getFile (_File , reader.readLine ());
            if (fileList == null) throw new IOException ();
            for (File file : fileList)
            {
                for (int i = 0; i < Types.size (); i++)
                {
                    String type = Types.get (i);
                    System.out.println ("Completing ." + type);
                    ToSplit toSplit = new ToSplit (file , Names.get (i) , type);
                    while (toSplit.testStartThreadCopy ())
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
        catch (IOException ignored)
        {
        }
    }

    @bardiademon
    private void getFile (File file , String input)
    {
        switch (input)
        {
            case "1":
                FindAllFile.Ready.FD.FindD (file , (l , list) -> fileList = list);
            case "2":
            default:
                fileList.add (file);
                break;
        }
    }
}
