package bardiademon;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@bardiademon
class AllType
{
    @bardiademon
    AllType (File _File)
    {
        try
        {
            BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));
            new CreateOption ().create (new String[]{"All files and all files in folders" , "All files in this folder"});
            System.out.print ("Input: ");
            String input = reader.readLine ();
            if (input.equals ("1"))
            {
                FindAllFile.Ready.FD.FindD (_File , (l , list) ->
                {
                    list.add (_File);
                    for (File file : list) new ToSplit (file );
                });
            }
            else if (input.equals ("2")) new ToSplit (_File);
            else System.out.println ("Input Error");
        }
        catch (IOException ignored)
        {
        }
    }

}
