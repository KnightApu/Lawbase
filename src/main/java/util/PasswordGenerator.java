package util;

import java.util.Random;

public class PasswordGenerator {
	private static final String charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String getRandomPassword(int length) {
	    Random rand = new Random(System.currentTimeMillis());
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < length; i++) {
	        int pos = rand.nextInt(charset.length());
	        sb.append(charset.charAt(pos));
	    }
	    return sb.toString();
	}

}
