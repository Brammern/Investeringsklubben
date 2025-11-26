package User;

public class Holding {
    private String ticker;
    private int quantity;


    public Holding (String ticker, int quantity) {
        this.ticker = ticker;
        this.quantity=quantity;
    }

    public String getTicker(){
        return ticker;
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
