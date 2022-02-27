package main.java.Utils;

import java.util.Random;

public class Helper {

    public Helper() {
        // TODO Auto-generated constructor stub
    }

    public static String getRandomNumberInRange(long min, long max) {

        Random r = new Random();
        return String.valueOf(r.longs(min, (max + 1)).limit(1).findFirst().getAsLong());

    }
    public enum OS {
        WINDOWS, LINUX, MAC, SOLARIS
    };// Operating systems.

    private static OS os = null;

      public static OS getOS() {
        if (os == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
                os = OS.WINDOWS;
            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                os = OS.LINUX;
            } else if (operSys.contains("mac")) {
                os = OS.MAC;
            } else if (operSys.contains("sunos")) {
                os = OS.SOLARIS;
            }
        }
        return os;
    }
}
