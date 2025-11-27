package User;

public class Holding {
    private int userId;
    private String date;
    private String ticker;
    private double price;
    private String currency;
    private String orderType;
    private int quantity;
    private int id;

    public Holding(String[] data){
        this.id = Integer.parseInt(data[0]);
        this.userId = Integer.parseInt(data[1]);
        this.date = data[2];
        this.ticker = data[3];
        this.price = Double.parseDouble(data[4]);
        this.currency = data[5];
        this.orderType = data[6];
        this.quantity = Integer.parseInt(data[7]);
    }

    public Holding (int userId, String date, String ticker, double price, String currency, String ordertype, int quantity) {
        this.userId=userId;
        this.date=date;
        this.ticker = ticker;
        this.price=price;
        this.currency=currency;
        this.orderType=ordertype;
        this.quantity=quantity;
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

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }


    @Override
    public String toString(){
        return ticker + " x " + quantity;
    }
}
