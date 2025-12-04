package FileHandler;

import java.util.ArrayList;

/**
 * En hjælpeklasse til håndtering af transactions.
 * Klassen har statiske metoder, der arbejder sammen med transactions files.
 * Klassen hjælper med at finde det næste ledige transcation ID i transactions filen.
 */

public class TransactionHelper {

    /**
     * Den finder næste ledige ID nummer ud fra de eksisterende data i transaction filen.
     * Metoden læser alle eksisterende transcations voa {@link CSVReader} og beregner
     * det højeste ID der allerede findes. Derefter returneres værdien + 1, så hvert id får et unikt nummer.
     * Hvis filen er tom eller ingen ID indes, returneres 1 som startværdi.
     * @return næste ledige transcation ID som et heltal.
     * @throws NumberFormatException hvis en eksisterende ID værdi i fil ikke kan laves til en int.
     */
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
