package FileHandler;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Håndterer skrivning af transaktioner til CSV-fil.
 *
 * Klassen bliver brugt til at gemme køb og salg af aktier til en
 * transaktion fil i CSV format. Filen gemmes automatisk og transaktion bliver tilføjet i bunden af fil.
 */
public class TransactionWriter {

    private final String filename;

    /**
     * Opretter en ny transactionWriter, som skriver til en specifik CSV-fil.
     * @param filename
     */

    public TransactionWriter(String filename) {
        this.filename = "src/Data/" + filename + ".csv";
    }

    /**
     * Skriver en transaktion til CSV-filen.
     * Decimaltal omdannes til komma for at matche dansk format.
     * filen åbnes i append mode, så de eksisterende transaktioner bevares.
     * Linjen formateres som semikolon-separeret data i følgende rækkefølge:
     * @param id        Unikt transcation ID.
     * @param userId    ID for brugeren, som udførte transaction.
     * @param date      Dato og tid for transaction.
     * @param ticker    Aktiens ticker symbol.
     * @param price     Prisen for aktien ved transaktion.
     * @param currency  Valutaprisen.
     * @param orderType Typen af ordrer (Køb/sælg):
     * @param quantity  Antallet af aktier der bliver solgt/købt.
     */

    public void writeTransaction(int id, int userId, String date, String ticker, double price, String currency, String orderType, int quantity) {
        //Laver pris til string med komma som decimal
        String priceStr = String.valueOf(price).replace(".", ",");

        String line = id + ";" + userId + ";" + date + ";" + ticker + ";" + price + ";" + currency + ";" + orderType + ";" + quantity;
        // konverterer amerikansk notation til dansk (komma som decimal)
        line = line.replaceAll("\\.", ",");
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(line + "\n");
        } catch (IOException e) {
            System.out.println("Fejl" + e.getMessage());
        }
    }

}
