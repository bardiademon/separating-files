package bardiademon;

import java.util.List;

abstract class Lst
{
    static class Search
    {
        static boolean Search (List List , Object Value)
        {
            for (Object obj : List) if (obj.equals (Value)) return true;
            return false;
        }

        private static int Search2Count;

        static boolean SearchIs2 (List List , Object Value)
        {
            Search2Count = 0;
            for (Object obj : List)
            {
                if (obj.equals (Value)) ++Search2Count;
                if (Search2Count >= 2) return true;
            }
            return false;
        }

        static int SearchCount (List List , Object Value)
        {
            int searchCount = 0;
            for (Object obj : List) if (obj.equals (Value)) ++Search2Count;
            return searchCount;
        }

        public static int GetSearch2Count ()
        {
            return Search2Count;
        }
    }

    static void DuplicateRemoval (List _List)
    {
        for (int i = 0, len = _List.size (); i < len; i++)
        {
            for (int j = i + 1; j < len; j++) if (_List.get (i).equals (_List.get (j))) _List.remove (i);
        }
    }
}
