package techit.util;

public class StringUtils {
	
	private static final String ALPHA_NUMERIC_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	/** Checks of string is null or empty. */
	public static boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}
	
	/** Generates an alphanumeric string of the specified length. */
	public static String random(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int charIndex = (int)(Math.random() * ALPHA_NUMERIC_STRING.length());
			sb.append(ALPHA_NUMERIC_STRING.charAt(charIndex));
		}
		return sb.toString();
	}

}
