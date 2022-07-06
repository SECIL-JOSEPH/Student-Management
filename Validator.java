//$Id$
package utility;

public class Validator {

    public static boolean isMark(int value)
    {        
    	boolean ismark = true;
        if (value < 0 || value > 100)
        {
            ismark = false;
        }
        return ismark;
    }
    
    public static boolean isName(String value)
    {
        boolean name =false;
        for(int i=0;i<value.length();i++)
        {
            if((value.charAt(i)>=65 &&  value.charAt(i)<=90) || (value.charAt(i)>=97 &&  value.charAt(i)<=122))
            {
                name = true;
            }
            else
            {
                name = false;
                break;
            }
        }
        return name;
    }
}
