package vn.edu.eaut.unitconverter.util;

import java.net.InetAddress;

public class NetworkUtil {
    public static boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }
}
