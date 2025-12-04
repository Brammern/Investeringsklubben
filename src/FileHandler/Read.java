package FileHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Read is a method used for reading data from a CSV file
 * Has Methods: <br>
 * {@link #read(String)}
 */
class Read {
    /**
     * reads all data at filename using {@link BufferedReader}
     * @param filename filename to read from
     * @return data read from file
     */
    static ArrayList<String> read(String filename){
        ArrayList<String> output = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            bufferedReader.readLine();
            String line;
            while((line = bufferedReader.readLine()) != null){
                output.add(line);
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }

        return output;
    }
}
