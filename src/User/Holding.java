package User;

import FileHandler.CSVReader;

import java.util.ArrayList;

public class Holding {
    private int userId;
    private String date;
    private String ticker;
    private double price;
    private String currency;
    private String orderType;
    private int quantity;
    private int id;
    private double currentValue;

    public Holding(String[] data){
        this.id = Integer.parseInt(data[0]);
        this.userId = Integer.parseInt(data[1]);
        this.date = data[2];
        this.ticker = data[3];
        this.price = Double.parseDouble(data[4]);
        this.currency = data[5];
        this.orderType = data[6];
        this.quantity = Integer.parseInt(data[7]);
        this.currentValue = calculateCurrentValue();
    }

    public Holding (int userId, String date, String ticker, double price, String currency, String ordertype, int quantity) {
        this.userId = userId;
        this.date = date;
        this.ticker = ticker;
        this.price = price;
        this.currency = currency;
        this.orderType = ordertype;
        this.quantity = quantity;
        this.currentValue = calculateCurrentValue();
    }

    public int getUserId(){
        return userId;
    }

    public String getDate(){
        return date;
    }

    public String getTicker(){
        return ticker;
    }

    public double getPrice(){
        return price;
    }

    public String getCurrency(){
        return currency;
    }

    public String getOrderType(){
        return orderType;
    }

    public int getQuantity(){
        return quantity;
    }

    public double getCurrentValue(){return this.currentValue;}

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    private double calculateCurrentValue() {
        CSVReader stockMarketReader = new CSVReader("stockMarket");
        CSVReader currenciesReader = new CSVReader("currency");
        ArrayList<String[]> stocks = stockMarketReader.read();
        ArrayList<String[]> currencies = currenciesReader.read();

        double stockPrice = 0;
        if (stocks != null) {
            for(String[] stock: stocks){
                if (this.ticker.equals(stock[0])){
                    stockPrice = Double.parseDouble(stock[3]);
                }
            }
        }
        double rate = getRate(currencies);
        return stockPrice*rate*this.quantity;
    }

    private double getRate(ArrayList<String[]> currencies) {
        String curr = getCurrency();
        double rate = 0;
        String code = curr.toUpperCase();
        if ("DKK".equals(code)) return 1.0;
        if (currencies != null) {
            for (String[] row : currencies) {
                if (row[0].equalsIgnoreCase("base_currency")) continue;
                if (row[0].equalsIgnoreCase(code)) {
                    try {
                        rate = Double.parseDouble(row[2]);
                    } catch (NumberFormatException e) {
                        System.out.println("Fejl ved parsing af valutakurs for " + code + ": " + e.getMessage());
                        rate = 1.0;
                    }
                }
            }
        }
        return rate;
    }

    @Override
    public String toString() {
        String formattedValue = String.format("%.2f", getCurrentValue());
        return ticker + " x" + quantity + " (Nuværende værdi: " + formattedValue + " DKK" + ")";
    }
}
