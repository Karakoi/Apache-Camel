package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberParser {

    public int findNumberAsInteger(String str) {
        Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        Matcher matcher = pat.matcher(str);
        String res = "";
        while (matcher.find()) {
            res = matcher.group();
        }
        return Integer.valueOf(res);
    }

    public String findNumberAsString(String str) {
        Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        Matcher matcher = pat.matcher(str);
        String res = "";
        while (matcher.find()) {
            res = matcher.group();
        }
        return res;
    }

}
