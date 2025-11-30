package FileHandler;

import User.Member;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class WriteUser implements CSVWriter{
    private final Member member;
    private final String fileName = "src/Data/users.csv";

    public WriteUser(Member member){
        this.member = member;
    }

    public void writer(){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            bufferedWriter.write(format());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private String getDate(){
        LocalDate today = LocalDate.now();
        return today.getDayOfMonth() + "-" +
                today.getMonthValue() + "-" +
                today.getYear();
    }

}
