package com.itec.application.objects;

import java.net.HttpURLConnection;
import java.net.URL;

public class Verifier {
    public static boolean checkString(String s, boolean allowAlphaNumerical) {
        if (s == null) {
            return false;
        }

        if(s.isEmpty()) {
            return false;
        }

        int len = s.length();
        for (int i = 0; i < len; i++) {
            if(!allowAlphaNumerical) {
                if ((!Character.isLetter(s.charAt(i)))) {
                    return false;
                }
            }else {
                if (!Character.isLetter(s.charAt(i)) && !Character.isWhitespace(s.charAt(i)) && !Character.isAlphabetic(s.charAt(i)) && !"!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".contains(s.charAt(i) + "")) {
                    return false;
                }
            }
        }

      return true;
    }

    public static boolean checkImgurLink(String s) {
        if(s.startsWith("https://i.imgur.com/") && s.endsWith(".png")) {
            return pingHttpAddress(s);
        }else {
            return false;
        }
    }

    public static boolean pingHttpAddress(String s) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(s).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if(responseCode != 200) {
                return false;
            }else {
                return true;
            }
        }catch (Exception exception) {
            return false;
        }
    }

    public static int getResponseCodeFromHTTPAddress(String s) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(s).openConnection();
            connection.setRequestMethod("HEAD");
            return connection.getResponseCode();
        }catch (Exception exception) {
            return 404;
        }
    }

    public static boolean checkNameConvention(String s) {
        return checkString(s, false) && !s.isEmpty() && s.length() <= 16;
    }

    public static boolean checkDescriptionConvention(String s) {
        return checkString(s, true) && !s.isEmpty() && s.length() >= 32 && s.length() <= 128;
    }
}
