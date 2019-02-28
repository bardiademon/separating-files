package bardiademon;

import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@bardiademon
public class Main
{
    private static final String SPLIT_ALL_TYPE = "1", INPUT_TYPE = "2", SELECTED_DIR = "3";
    private static final String PUT_NEW = "1", PUT_NEW_AND_AUTO_PUT = "2", COMPLETE = "3", CLEAR_ALL_INPUT = "4";

    private static boolean AutoPut;
    private static final String COMPLETE_FOR_AUTO_PUT = ".type.";
    static final String DEFAULT_NAME_DIR = "..DEFAULT..";

    @bardiademon
    public static void main (String[] args)
    {
        JFileChooser chooser = new JFileChooser ();
        chooser.setFileSelectionMode (JFileChooser.DIRECTORIES_ONLY);
        System.out.print ("Select Dir: ");
        if (chooser.showOpenDialog (null) == JFileChooser.OPEN_DIALOG)
        {
            File file = chooser.getSelectedFile ();
            if (file == null)
            {
                main (args);
                return;
            }
            System.out.println ("\rSelected: " + file.getPath ());
            List<String> types = new ArrayList<> ();
            List<String> namesDir = new ArrayList<> ();
            while (true)
            {
                new CreateOption ().create (new String[]{"Split all type" , "Input type" , "Selected dir"} , true);
                System.out.print ("Input: ");
                BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));
                try
                {
                    String input = reader.readLine ();
                    if (input.equals (String.valueOf (CreateOption.OPTION_EXIT))) System.exit (0);
                    switch (input)
                    {
                        case SPLIT_ALL_TYPE:
                            new AllType (file);
                            TryAgain ();
                            return;
                        case INPUT_TYPE:
                            boolean breakWhile = false;
                            while (true)
                            {
                                System.out.print ("Input type: ");
                                input = reader.readLine ();
                                System.out.print ("Input name dir [enter null set to default]: ");
                                String nameDir = reader.readLine ();
                                if (nameDir == null || nameDir.equals ("")) nameDir = DEFAULT_NAME_DIR;
                                if (!AutoPut)
                                {
                                    types.add (input);
                                    namesDir.add (nameDir);
                                    new CreateOption ().create (new String[]{"Put New" , "Put New And Auto Put" , "Complete" , "Clear All Input"} , true);
                                    System.out.print ("Input: ");
                                    String inputForType = reader.readLine ();
                                    switch (inputForType)
                                    {
                                        case PUT_NEW:
                                            break;
                                        case PUT_NEW_AND_AUTO_PUT:
                                            AutoPut = true;
                                            System.out.printf ("\n---Complete Type %s---\n" , COMPLETE_FOR_AUTO_PUT);
                                            break;
                                        case CLEAR_ALL_INPUT:
                                            types.clear ();
                                            namesDir.clear ();
                                            break;
                                        case COMPLETE:
                                        default:
                                            breakWhile = true;
                                            break;
                                    }
                                }
                                else if (input.equals (COMPLETE_FOR_AUTO_PUT)) break;
                                else
                                {
                                    types.add (input);
                                    namesDir.add (nameDir);
                                }
                                if (breakWhile) break;
                            }
                            if (types.size () > 0)
                            {
                                new Types (file , namesDir , types);
                                System.out.println ("Completed " + file.getPath ());
                                TryAgain ();
                                return;
                            }
                            break;
                        default:
                        case SELECTED_DIR:
                            main (args);
                            return;
                    }
                }
                catch (IOException ignored)
                {
                }
            }
        }
    }

    @bardiademon
    private static void TryAgain ()
    {
        new CreateOption ().create (new String[]{"Select Dir"} , true);
        try
        {
            System.out.print ("Input: ");
            if (new BufferedReader (new InputStreamReader (System.in)).readLine ().equals (String.valueOf (1)))
            {
                main (null);
                return;
            }
        }
        catch (IOException ignored)
        {
        }
        System.exit (0);
    }
}
