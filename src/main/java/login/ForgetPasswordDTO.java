package login;

public class ForgetPasswordDTO {
	private String token;
	private long accountID;
	private long messageSentTime;
	private boolean isUser;
	private String mailServer;
	private String mailAddress;
	private String userName;
/*	private String m_phoneNumber;
	
    public void setPhoneNumber(String p_phoneNumber)
    {
            m_phoneNumber = p_phoneNumber;
    }
    public String getPhoneNumber()
    {
            return m_phoneNumber;
    }
*/
	public void setToken(String p_token) {
		token = p_token;
	}
	public String getToken() {
		return token;
	}
	public void setAccountID(long p_accountID) {
		accountID = p_accountID;
	}
	public long getAccountID() {
		return accountID;
	}
	public void setMessageSentTime(long p_messageSentTime) {
		messageSentTime = p_messageSentTime;
	}
	public long getMessageSentTime() {
		return messageSentTime;
	}
	public void setIsUser(boolean p_isUser) {
		isUser = p_isUser;
	}
	public boolean getIsUser() {
		return isUser;
	}
	public void setMailServer(String p_mailServer) {
		mailServer = p_mailServer;
	}
	public String getMailServer() {
		return mailServer;
	}
	public void setMailAddress(String p_mailAddress) {
		mailAddress = p_mailAddress;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setUserName(String p_userName) {
		userName = p_userName;
	}
	public String getUserName() {
		return userName;
	}

}
