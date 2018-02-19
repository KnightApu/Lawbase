package com.lawbase.module.admin.category;

import java.util.List;

import com.lawbase.taxonomy.Category;

public class CategoryBookJsonResponse {
	public int iTotalRecords;
	public int iTotalDisplayRecords;
	public int sEcho;
	public List <Category> aaData;
	
	public CategoryBookJsonResponse(int iTotalRecords, int iTotalDisplayRecords, int sEcho) {
		super();
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.sEcho = sEcho;
	}
}
