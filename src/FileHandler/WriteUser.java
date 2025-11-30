package FileHandler;

import User.Member;

import java.time.LocalDate;

public class WriteUser implements CSVWriter{
    private final Member member;

    public WriteUser(Member member){
        this.member = member;
    }

    public void write(){

    }

    private String format(){
        return member.getUserId() + "," +
                member.getFullName() + "," +
                member.getEmail() + "," +
                member.getBirthDate() + "," +
                member.getInitialCash() + "," +
                getDate() + "," +
                getDate();

    }

    public String getDate(){
        LocalDate today = LocalDate.now();
        return today.getDayOfMonth() + "-" +
                today.getMonthValue() + "-" +
                today.getYear();
    }

}
