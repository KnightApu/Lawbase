package util;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class TranslationUtil {

	static Logger logger = Logger.getLogger(TranslationUtil.class);

	private static final char[] banglaDigits = { '০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯' };
	private static final char[] englishDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static HashMap<Character, Character> mapBanglaToEn = new HashMap<Character, Character>();

	static {
		int len = Math.min(banglaDigits.length, englishDigits.length);
		for (int i = 0; i < len; i++) {
			mapBanglaToEn.put(banglaDigits[i], englishDigits[i]);
		}
	}

	public static final String getDigitsInEnglishFromBangla(String number) {
		StringBuilder builder = new StringBuilder();
		try {
			for (int i = 0; i < number.length(); i++) {
				if (mapBanglaToEn.containsKey(number.charAt(i))) {
					builder.append(mapBanglaToEn.get(number.charAt(i)));
				} else {
					builder.append(number.charAt(i));
				}
			}
		} catch (Exception e) {
			logger.fatal("Exception at translating : " + number);
		}
		logger.debug("Original : " + number + " Translated to: " + builder.toString());
		return builder.toString();
	}

	public static final String getDigitBanglaFromEnglish(String number) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < number.length(); i++) {
			if (Character.isDigit(number.charAt(i))) {
				if (((int) (number.charAt(i)) - 48) <= 9) {
					builder.append(banglaDigits[(int) (number.charAt(i)) - 48]);
				} else {
					builder.append(number.charAt(i));
				}
			} else {
				builder.append(number.charAt(i));
			}
		}
		return builder.toString();
	}

}
