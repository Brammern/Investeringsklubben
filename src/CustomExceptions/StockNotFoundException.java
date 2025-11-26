package CustomExceptions;

// Exception til når/hvis der søges efter en specifik aktie ticker ud fra bondMarket.csv og stockMarket.csv
// til hvis der ikke findes et gyldigt svar.

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String ticker) {
        super("Stock med ticker '" + ticker + "' blev ikke fundet.");
    }
}
