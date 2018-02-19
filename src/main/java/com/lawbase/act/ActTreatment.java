package com.lawbase.act;

public enum ActTreatment {
	NYIF("Not Yet In Force"),
	LIF("Law in Force"),
	R("Repealed"),
	PBIS("Pending Bill In Sangsad"),
	AA("Annotations Available"),
	NA("Not Assigned");
	
	String text;
	
	ActTreatment(String text){
		
		this.text=text;
	}
	
	public String getText()
	{
		return text;
	}
}
