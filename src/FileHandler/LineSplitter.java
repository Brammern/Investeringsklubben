package FileHandler;

import java.util.ArrayList;

/**
 * LineSplitter is used to split a String into a String array using the regex ';'
 * Has Methods: <br>
 * {@link #lineSplitter(ArrayList)}
 */
class LineSplitter {
    /**
     * lineSplitter is a static method that runs through a ArrayList&ltString&gt splitting all Strings into String arrays using regex ';'
     * @param toFormat ArrayList&ltString&gt to format
     * @return ArrayList&ltString[]&gt of formatted data
     */
    static ArrayList<String[]> lineSplitter(ArrayList<String> toFormat){
        ArrayList<String[]> formatted = new ArrayList<>();
        for (String line: toFormat){
            String[] temp = line.split(";");
            formatted.add(temp);
        }
        return formatted;
    }
}
