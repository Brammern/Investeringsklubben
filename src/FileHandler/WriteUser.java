package FileHandler;

import User.Member;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * WriteUser is a Class used to write new Users to the users.csv file
 * Has Attributes: <br>
 * {@link #member}
 * {@link #fileName} <br>
 * Has Methods: <br>
 * {@link #WriteUser(Member)} <br>
 * {@link #writer()}
 */
public class WriteUser implements CSVWriter {
    /**
     * {@link Member} containing the data to write
     */
    private final Member member;
    /**
     * String of the file name
     */
    private final String fileName = "src/Data/users.csv";

    /**
     * constructor that sets the {@link #member} attribute
     * @param member the member to write to CSV
     */
    public WriteUser(Member member) {
        this.member = member;
    }

    /**
     * writer formats the data from {@link #member} then formats it using {@link #format()}
     * before writing it to {@link #fileName} using {@link BufferedWriter}
     */
    public void writer() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
            bufferedWriter.write(format());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * formats the data from {@link #member} as is appropriate for the users.csv file using {@link #getDate()} for current date
     * @return String of formatted data
     */
    private String format() {
        return member.getUserId() + "," +
                member.getFullName() + "," +
                member.getEmail() + "," +
                member.getBirthDate() + "," +
                member.getInitialCash() + "," +
                getDate() + "," +
                getDate();

    }

    /**
     * getDate gets the current date using {@link LocalDate}
     * @return String of current date as dd-MM-yyyy
     */
    private String getDate() {
        LocalDate today = LocalDate.now();
        return today.getDayOfMonth() + "-" +
                today.getMonthValue() + "-" +
                today.getYear();
    }

}
