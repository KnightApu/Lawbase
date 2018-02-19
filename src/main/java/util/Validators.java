package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
	/**
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isSqlSafe(String val){
		if( val.contains(";") || val.contains("\"") || val.contains("'") || val.contains("//")||val.contains("||")){
			return false;
		}else{
			return true;
		}
		
	}
	
	
	  private static Pattern pattern;
	  private static Matcher matcher;
 
	  private static final String PASSWORD_PATTERN = 
              "((?=.*\\d)(?=.*[a-zA-Z]).{4,20})";
 
	  static {
		  pattern = Pattern.compile(PASSWORD_PATTERN);
	  }
	
 
	  /**
	   * Validate password with regular expression
	   * @param password password for validation
	   * @return true valid password, false invalid password
	   */
	  public static boolean validate(final String password){
 
		//  return true;
		  matcher = pattern.matcher(password);
		  return matcher.matches();
 
	  }

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAlphaNumeric(String str){
		if( str.matches("^[a-zA-Z0-9]*$") ){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Checks whether all elements of an array are unique
	 * @param arr
	 * @return
	 */
	public static boolean areAllUnique(Object arr[]){
		for( int i=0; i < arr.length; i++ ){
			for( int j=i+1; j < arr.length; j++ ){
				if( arr[i].equals( arr[j] ) ){
					return false;
				}
			}
		}
		return true;
	}
	
	
}
