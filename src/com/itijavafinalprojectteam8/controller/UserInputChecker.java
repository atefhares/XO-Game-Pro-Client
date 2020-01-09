package com.itijavafinalprojectteam8.controller;

/**
 * @author ahares
 */
public class UserInputChecker {

    private static final int PASSWORD_LENGTH = 8;

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    private static boolean isLetter(char ch) {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }

    private static boolean isNumeric(char ch) {

        return (ch >= '0' && ch <= '9');
    }

    public static boolean isValidPassword(String password) {
        System.out.println(password.length());
        if (password.length() < PASSWORD_LENGTH) {
            return false;
        } else {
            int charCounter = 0;
            int numCounter = 0;

            for (int i = 0; i < password.length(); i++) {

                char ch = password.charAt(i);

                if (isNumeric(ch)) {
                    numCounter++;
                } else if (isLetter(ch)) {
                    charCounter++;
                } else {
                    return false;
                }
            }

            return (charCounter >= 2 && numCounter >= 2);
        }
    }
}
