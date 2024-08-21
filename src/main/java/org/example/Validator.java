package org.example;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Validator {

    public static boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePhone(String number) {
        String regex = "^(0|\\+98|0098)9[0-9]{9}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static void main(String[] args) {
        String email = "example@example.com";
        String phone = "+989123456789";

        System.out.println("Email Validation: " + validateEmail(email));
        System.out.println("Phone Validation: " + validatePhone(phone));
    }
}