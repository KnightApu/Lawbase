package com.lawbase.cases;

public enum CaseCategory {
	CR("Criminal Case"),
	CV("Civil Case"),
	NA("Not Assigned");
	
	String text;
	
	CaseCategory(String text){
		
		this.text=text;
	}
	
	public String getText()
	{
		return text;
	}
}
