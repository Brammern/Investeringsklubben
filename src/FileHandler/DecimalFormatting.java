package FileHandler;

import java.util.ArrayList;

class DecimalFormatting {
    static ArrayList<String> commaToDot(ArrayList<String> toFormat){
        ArrayList<String> returnString = new ArrayList<>();
        for(String string: toFormat){
            String formattedString = string.replaceAll(",", ".");
            returnString.add(formattedString);
        }
        return returnString;
    }
    static ArrayList<String> dotToComma(ArrayList<String> toFormat){
        ArrayList<String> returnString = new ArrayList<>();
        for(String string: toFormat){
            String formattedString = string.replaceAll("\\.", ",");
            returnString.add(formattedString);
        }
        return returnString;
    }
}
