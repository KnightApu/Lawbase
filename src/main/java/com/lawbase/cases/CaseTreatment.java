package com.lawbase.cases;

public enum CaseTreatment {
	NT("Negative Treatment"),
	CT("Cautionary Treatment"),
	PT("Positive Treatment"),
	NHOT("Neutral History or Treatment"),
	CI("Citator Information"),
	NA("Not Assigned");
	
	String text;
	
	CaseTreatment(String text){
		
		this.text=text;
	}
	
	public String getText()
	{
		return text;
	}

}
