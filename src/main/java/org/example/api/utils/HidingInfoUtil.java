package org.example.api.utils;

public class HidingInfoUtil {

    public static String emailHide(String email) {
        Integer avg = null;
        String[] splitted = null;
        String email1 = null;
        String email2 = null;
        splitted = email.split("@");

        email1 = splitted[0];
        avg = email1.length() / 2;
        email1 = email1.substring(0, email1.length() - avg);
        email2 = splitted[1];
        return email1 + "***@" + email2; // 输出为81226***@qq.com
    }
}
