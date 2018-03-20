package com.lawbase.module.front.user;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adhocmaster.controller.MongoRestController;
import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;
import com.adhocmaster.mongo.user.UserService;
import com.adhocmaster.service.RepositoryService;
import com.utility.form.FormValidationException;

import javassist.NotFoundException;
import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;

@RestController
@RequestMapping("/public/rest/user/")
public class UserProfileRestController extends MongoRestController<User> {

	@Autowired
	UserService userService;

	@Override
	protected RepositoryService<User> getService() {
		// TODO Auto-generated method stub
		return userService;
	}

	@PostMapping("edit/{id}")
	public User update(

			@PathVariable ObjectId id, HttpSession httpSession, @RequestParam Map<String, String> params

	) throws RestBadDataException, RestInternalServerException {

		try {

			System.out.println("user profile edit/id te call asche");

			return userService.updateBasicInformation( id, params);

		} catch (FormValidationException e) {

			throw new RestBadDataException(e);

		} catch (Exception e) {

			throw new RestInternalServerException(e);

		}

	}
	

}
