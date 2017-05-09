package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ErlangUtils {

    /**
     * @param answer string value from erlang process.
     * @return collection of double values.
     */
    public List<Double> getListDoubleValueFromAnswer(String answer) {
        String str = answer.replaceAll("[^-?0-9]+", " ");
        String[] strings = str.trim().split(" ");
        System.out.println(Arrays.toString(strings));
        List<Double> collection = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            collection.set(i, Double.valueOf(strings[i]));
        }
        return collection;
    }

    /**
     * @param answer string value from erlang process.
     * @return double value.
     */
    public Double getDoubleValueFromAnswer(String answer) {
        String str = answer.replaceAll("[^-?0-9]+", " ");
        String[] strings = str.trim().split(" ");
        System.out.println(Arrays.toString(strings));
        return Double.valueOf(strings[0]);
    }
}
