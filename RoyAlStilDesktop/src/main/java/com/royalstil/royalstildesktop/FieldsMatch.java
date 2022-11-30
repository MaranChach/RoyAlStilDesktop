package com.royalstil.royalstildesktop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldsMatch {


    public static boolean emailCheck(String email){
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }
}
