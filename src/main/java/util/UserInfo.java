package util;


public class UserInfo {
	private long userID; //or officerId
	private String username;
	private String fullName;
	private long majorOfficerOfficeDesignationID;
	private long majorOfficeID;
	private long majorOfficeDesignationID;
	private long loginTime;
	
	private long [] officerOfficeDesignationIDs; //non-major
	private long [] officeIDs; //non-major
	private long [] officeDesignationIDs; // non-major
	
	public long getUserID() {
		return userID;
	}
	public void setUserID(long p_userID) {
		userID = p_userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String p_username) {
		username = p_username;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String p_fullName) {
		fullName = p_fullName;
	}
	public long getMajorOfficerOfficeDesignationID() {
		return majorOfficerOfficeDesignationID;
	}
	public void setMajorOfficerOfficeDesignationID(
			long p_majorOfficerOfficeDesignationID) {
		majorOfficerOfficeDesignationID = p_majorOfficerOfficeDesignationID;
	}
	public long getMajorOfficeID() {
		return majorOfficeID;
	}
	public void setMajorOfficeID(long p_majorOfficeID) {
		majorOfficeID = p_majorOfficeID;
	}
	public long getMajorOfficeDesignationID() {
		return majorOfficeDesignationID;
	}
	public void setMajorOfficeDesignationID(long p_majorOfficeDesignationID) {
		majorOfficeDesignationID = p_majorOfficeDesignationID;
	}
	public long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(long p_loginTime) {
		loginTime = p_loginTime;
	}
	public long[] getOfficerOfficeDesignationIDs() {
		return officerOfficeDesignationIDs;
	}
	public void setOfficerOfficeDesignationIDs(long[] p_officerOfficeDesignationIDs) {
		officerOfficeDesignationIDs = p_officerOfficeDesignationIDs;
	}
	public long[] getOfficeIDs() {
		return officeIDs;
	}
	public void setOfficeIDs(long[] p_officeIDs) {
		officeIDs = p_officeIDs;
	}
	public long[] getOfficeDesignationIDs() {
		return officeDesignationIDs;
	}
	public void setOfficeDesignationIDs(long[] p_officeDesignationIDs) {
		officeDesignationIDs = p_officeDesignationIDs;
	}
	
	

}
