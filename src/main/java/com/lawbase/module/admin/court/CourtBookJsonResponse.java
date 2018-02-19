package com.lawbase.module.admin.court;

import java.util.List;

import com.lawbase.court.CourtBook;


public class CourtBookJsonResponse {
	
	public int iTotalRecords;
	public int iTotalDisplayRecords;
	public int sEcho;
	public List <CourtBook> aaData;
	public CourtBookJsonResponse(int iTotalRecords, int iTotalDisplayRecords, int sEcho) {
		super();
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.sEcho = sEcho;
	}

}
