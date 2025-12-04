package FileHandler;

import java.util.ArrayList;

/**
 *
 * Has Attributes: <br>
 *
 * Has Methods: <br>
 */
public class TransactionHelper {

    public static int getNextTransactionId() {
        CSVReader transactionReader = new CSVReader("Transactions");
        ArrayList<String[]> transactions = transactionReader.read();
        int maxId = 0;
        for (String[] t : transactions) {
            int id = Integer.parseInt(t[0]);
            if (id > maxId) maxId = id;
        }
        return maxId + 1;
    }
}
