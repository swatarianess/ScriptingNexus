package curtains.Utils;

/**
 * Created by Reall_blue on 02/01/2015.
 */
public class StringUtil {

    /**
     * Join a string array using no separator, the array will be joined from the
     * start of the array until the end.
     *
     * NUll is returned If a problem is encountered
     *
     */
    public static String join(final String[] array){
        if(array == null){
            return null;
        }
        String newString = "";
        for (final String string : array){
            newString = newString.concat(string);
        }
        return newString;
    }
}
