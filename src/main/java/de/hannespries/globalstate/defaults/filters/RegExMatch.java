package de.hannespries.globalstate.defaults.filters;

import de.hannespries.globalstate.FilterOperator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExMatch extends FilterOperator {

    private String pattern = "";

    public RegExMatch(String pattern){
        this.pattern = pattern;
    }

    public static boolean match(String pattern, String source) {
        Pattern patternObj = Pattern.compile(pattern, Pattern.MULTILINE);
        Matcher matcher = patternObj.matcher(source);
        return matcher.matches();
    }

//    public static boolean matchFind(String pattern, String source) {
//        Pattern patternObj = Pattern.compile(pattern, Pattern.MULTILINE);
//        Matcher matcher = patternObj.matcher(source);
//        return matcher.find();
//    }

    @Override
    public boolean check(Object toCheckValue) {
        return match(this.pattern, toCheckValue.toString());
    }
}
