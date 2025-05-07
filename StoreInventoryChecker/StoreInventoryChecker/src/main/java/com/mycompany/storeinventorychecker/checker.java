package com.mycompany.storeinventorychecker;
public class checker {
    
    public static boolean isDouble(String str) {
        if (str == null || str.isEmpty()) return false;

        boolean hasDecimal = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i == 0 && c == '-') continue; // allow leading minus

            if (c == '.') {
                if (hasDecimal) return false; // only one decimal allowed
                hasDecimal = true;
            } else if (!Character.isDigit(c)) {
                return false;
            }
        }

        // make sure it's not just "-" or "-." or "."
        return !(str.equals("-") || str.equals(".") || str.equals("-.")); 
    }


    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) return false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i == 0 && c == '-') continue; // allow leading minus
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    
    
}
