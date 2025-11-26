package User;

public class Holding {
    private String stocks;
    private int quantity;

    public Holding (String stocks, int quantity) {
        this.stocks = stocks;
        this.quantity=quantity;
    }

    public String getStocks(){
        return stocks;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity=quantity;
    }

    @Override
    public String toString(){
        return stocks + " x " + quantity;
    }
}
