package com.imedical.mobiledoctor.util;

public class Validator {

    public static boolean isBlank(String str) {
        boolean is = true;
        if (str == null || str.trim().equals("")) {
            is = true;
        } else {
            is = false;
        }

        return is;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
