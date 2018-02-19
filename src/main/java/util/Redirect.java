package util;

import javax.servlet.http.HttpServletRequest;

/**
 * A tool to handle generic redirects. This class is httpservlet specific
 * @author muktadir
 *
 */
public class Redirect {
	
	public static void to( String redirectUrl) {
		
	}
	
	public static String getRedirectUrl( HttpServletRequest request ) {
		
		String redirectTo = request.getParameter("redirectTo");
		if ( redirectTo !=null && ! redirectTo.equals("")) { 
			
			return redirectTo;
			
		}
		return null;
		
	}

}
