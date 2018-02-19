package com.lawbase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.adhocmaster.controller.MvcUserController;
import com.lawbase.court.CourtBook;
import com.lawbase.court.CourtBookFactory;
import com.lawbase.court.CourtBookRepository;

public class FrontCommonController extends MvcUserController {

	@Autowired
	CourtBookRepository courtBookRepository;
	
	@Override
	protected void generateControllerPaths() {
		// TODO Auto-generated method stub
		
	} 

	/*
	 * adds the courtbook names in the top menu
	 */
	protected void addCommonFrontMenuAttributes( Model model ){
		
		CourtBookFactory courtBookService = new CourtBookFactory( courtBookRepository );
    	
    	List<CourtBook> courtBooks = courtBookService.getCourtBooks();
    	
    	model.addAttribute( "courtBooks", courtBooks);
	}
	


}
