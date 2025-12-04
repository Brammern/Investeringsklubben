package FileHandler;

import java.util.ArrayList;

/**
 *DecimalFormatting is a Class used for converting ',' to '.' or vice versa
 * Has Methods: <br>
 * {@link #commaToDot(ArrayList)} <br>
 * {@link #dotToComma(ArrayList)}
 */
class DecimalFormatting {
    /**
     * commaToDot is a static method that converts ',' to '.'
     * @param toFormat the ArrayList&ltString[]&gt to format
     * @return the formatted ArrayList&ltString[]&gt
     */
    static ArrayList<String> commaToDot(ArrayList<String> toFormat){
        ArrayList<String> returnString = new ArrayList<>();
        for(String string: toFormat){
            String formattedString = string.replaceAll(",", ".");
            returnString.add(formattedString);
        }
        return returnString;
    }
    /**
     * dotToComma is a static method that converts '.' to ','
     * @param toFormat the ArrayList&ltString[]&gt to format
     * @return the formatted ArrayList&ltString[]&gt
     */
    static ArrayList<String> dotToComma(ArrayList<String> toFormat){
        ArrayList<String> returnString = new ArrayList<>();
        for(String string: toFormat){
            String formattedString = string.replaceAll("\\.", ",");
            returnString.add(formattedString);
        }
        return returnString;
    }
}
