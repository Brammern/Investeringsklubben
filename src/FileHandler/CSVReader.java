package FileHandler;

import java.util.ArrayList;

/**
 * CSVReader is a Class used to get data from CSV files and format the data appropriately
 * Has Attributes: <br>
 *{@link #filename} <br>
 * Has Methods: <br>
 * {@link #CSVReader(String)} <br>
 * {@link #read()}<br>
 */
public class CSVReader {
    /**
     * name of the file to read from
     */
    String filename;

    /**
     * constructor, gets and formats a filename using {@link #formatFilename(String)} then sets {@link #filename}
     * @param filename String of the file to read from
     */
    public CSVReader(String filename){this.filename = formatFilename(filename);}

    /**
     * reads csv file {@link #filename} using {@link Read} then formats it using {@link DecimalFormatting} and {@link LineSplitter}
     * @return ArrayList&ltString[]&gt containing data read from file
     */
    public ArrayList<String[]> read() {
        return LineSplitter.lineSplitter(DecimalFormatting.commaToDot(Read.read(filename)));
    }

    private String formatFilename(String filename){
        return "src/Data/" + filename + ".csv";
    }
}