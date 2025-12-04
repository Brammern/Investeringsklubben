package FileHandler;

import java.util.ArrayList;

/**
 *
 * Has Attributes: <br>
 *
 * Has Methods: <br>
 */
class LineSplitter {
    static ArrayList<String[]> lineSplitter(ArrayList<String> toFormat){
        ArrayList<String[]> formatted = new ArrayList<>();
        for (String line: toFormat){
            String[] temp = line.split(";");
            formatted.add(temp);
        }
        return formatted;
    }
}
