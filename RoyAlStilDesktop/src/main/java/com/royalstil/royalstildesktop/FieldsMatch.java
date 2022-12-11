package com.royalstil.royalstildesktop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldsMatch {


    public static boolean emailCheck(String email){
        Pattern pattern = Pattern.compile("^(.+)@(.+)\\.(.+)$");
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }

    public static boolean phoneNumberCheck(String phoneNumber){
        Pattern pattern = Pattern.compile
        ("^\\+?[0-9\\-\\s][(]?[0-9\\-\\s]{3}[)]?\\s?[0-9\\-\\s]{3}\\s?[0-9\\-\\s]{2}[\\-]?[0-9\\-\\s]{2}$");
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }
}
