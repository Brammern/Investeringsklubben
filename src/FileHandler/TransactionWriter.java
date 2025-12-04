package FileHandler;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * Has Attributes: <br>
 *
 * Has Methods: <br>
 */
public class TransactionWriter {

    private final String filename;

    public TransactionWriter(String filename) {
        this.filename = "src/Data/" + filename + ".csv";
    }

    public void writeTransaction(int id, int userId, String date, String ticker, double price, String currency, String orderType, int quantity) {
        //Laver pris til string med komma som decimal
        String priceStr = String.valueOf(price).replace(".", ",");

        String line = id + ";" + userId + ";" + date + ";" + ticker + ";" + price + ";" + currency + ";" + orderType + ";" + quantity;
        line = line.replaceAll("\\.", ",");
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(line + "\n");
        } catch (IOException e) {
            System.out.println("Fejl" + e.getMessage());
        }
    }

}
