/*
 * Created on Nov 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package util;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

public class DAOResult 
{
	public final static int DONE = 0;
	public final static int VALIDATION_ERROR = 1;
	public final static int DB_EXCEPTION = 2;

	
	private String  message; 
	private boolean successful; 
	private int type; 
	
	public DAOResult()
	{ 
		successful = false;
		message = "";
	}

	public DAOResult(String p_message,boolean p_successful)
	{ 
		successful = p_successful;
		message = p_message;
	}

	public DAOResult(String p_message,boolean p_successful,int p_type)
	{ 
		successful = p_successful;
		message = p_message;
		type = p_type;
	}

	public void setResult(String p_message,boolean p_successful,int p_type)
	{ 
		successful = p_successful;
		message = p_message;
		type = p_type;
	}
	
	public void setMessage(String p_message)
	{
		message = p_message;
	}

	public String getMessage()
	{
		return message;
	}

	public boolean isSuccessful()
	{
		return successful;
	}

	public void setSuccessful(boolean p_successful)
	{
		successful = p_successful;
	}

	public void setType(int p_type)
	{
		type = p_type;
	}

	public int getType()
	{
		return type;
	}

	public String toString() 
	{
		String s = "";
		String errorDesc = null;
		
		switch(type) {
		case DONE :
		errorDesc = "DONE";
		break;

		case VALIDATION_ERROR :
		errorDesc = "VALIDATION_ERROR";
		break;

		case DB_EXCEPTION:
		errorDesc = "DB_EXCEPTION";
		break;
		}

		s += "";		
		s += "\nmessage = " + message;
		s += "\nsuccessful = " + successful;
		s += "\ntype = " + errorDesc;
		
		return s; 
	}
}
