package com.lawbase.module.admin.court;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.adhocmaster.controller.MvcUserController;
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;
import com.book.simpleBook.SimpleBook;
import com.book.simpleBook.SimpleMissingBook;
import com.lawbase.court.CourtBook;
import com.lawbase.court.CourtBookService;
import com.lawbase.module.admin.caseBook.AdminCaseController;

@Controller
@RequestMapping("/admin/courtbook")
public class AdminCourtBookController extends MvcUserController {

	private static final Logger logger = LoggerFactory.getLogger(AdminCourtBookController.class);

	private static final String viewRoot = "admin/courtbook-";
	private static final String pathRoot = "/admin/courtbook";
	@Autowired
	private CourtBookService courtBookService;

	@Autowired
	AdminCaseController adminCaseController;

	@Override
	protected void generateControllerPaths() {

		controllerPaths = new HashMap<>();

		controllerPaths.put("index", pathRoot);
		controllerPaths.put("add", pathRoot + "/add");
		controllerPaths.put("edit", pathRoot + "/edit?id=");
		controllerPaths.put("delete", pathRoot + "/delete?id=");
		controllerPaths.put("manage", pathRoot + "/manage");

	}

	/**
	 * 
	 */
	@PostConstruct
	protected void init() {

		controllerPaths.put("editCase", adminCaseController.getControllerPath("edit"));
		// because contructor have no access to autowired elements which are
		// added after construction

	}

	@GetMapping(value = { "", "/", "/index" })
	public String index(

			Model model, HttpSession httpSession, @RequestParam Map<String, String> params

	) {

		// TODO make is paginated

		// List <CourtBook> courtBooks = courtRepository.findAll();
		Page<CourtBook> courtBooks = courtBookService.findAll(new PageRequest(1, 5));

		logger.debug(courtBooks.toString());

		model.addAttribute("books", courtBooks);

		try {

			model.addAttribute("authorities", getUser(httpSession).getRole().name());
			
			addUserInfoAttribute( model, httpSession );

			logger.debug(getUser(httpSession).getRole().name());

		} catch (UserNotFoundInSessionException e) {

			e.printStackTrace(); //TODO forward error view.
			
			
		}

		addCommonModelAttributes(model, "index");

		return viewRoot + "index";
		// return "index";

	}

	@GetMapping("/add")
	public String add(

			HttpSession httpSession,
			Model model

	) throws UserNotFoundInSessionException {

		addUserInfoAttribute( model, httpSession );
		addCommonModelAttributes(model, "add");
		return viewRoot + "index";

	}

	@GetMapping("/edit")
	public String edit(

			HttpSession httpSession,
			Model model, @RequestParam ObjectId id

	) throws UserNotFoundInSessionException {

		SimpleBook courtBook = courtBookService.findOne(id);

		if (null == courtBook)
			courtBook = new SimpleMissingBook();
		
		model.addAttribute("courtBook", courtBook);
		
		addUserInfoAttribute( model, httpSession );
		addCommonModelAttributes(model, "edit");
		
		return viewRoot + "index";

	}

}
