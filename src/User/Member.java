package User;

public class Member implements User{
    int userId;
    String fullName;
    String email;
    String birthDate;
    int initialCash;

    public Member(int userId, String fullName, String email, String birthDate, int initialCash){
        this.userId=userId;
        this.fullName=fullName;
        this.email=email;
        this.birthDate=birthDate;
        this.initialCash=initialCash;
        //ask for username and initialize fields
    }

    public int getInitialCash() {
        return initialCash;
    }

    @Override
    public void display(){

    }

    public void showStockMarket(){

    }
    public void showCurrency(){

    }
    public void registerSale(){

    }
    public void registerPurchase(){

    }
    public void showPortfolio(){

    }
    public void showTransactionHistory(){

    }
}
