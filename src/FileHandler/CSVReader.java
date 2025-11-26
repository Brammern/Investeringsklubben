package FileHandler;

import java.util.ArrayList;

public class CSVReader {
    String filename;

    public CSVReader(String filename){this.filename = formatFilename(filename);}

    public ArrayList<String[]> read() {
        return LineSplitter.lineSplitter(DecimalFormatting.commaToDot(Read.read(filename)));
    }

    private String formatFilename(String filename){
        return "src/Data/" + filename + ".csv";
    }
}