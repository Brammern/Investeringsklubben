package FileHandler;

import java.util.ArrayList;

public class TransactionReader implements CSVReader {
    String filename = "src/Data/transactions.csv";

    public ArrayList<String[]> read() {
        return LineSplitter.lineSplitter(DecimalFormatting.commaToDot(Read.read(filename)));
    }
}