package trips.web.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import trips.model.User;
import trips.model.dao.UserDao;

@Controller
public class UserRestController {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	UserDao userDao;
	
	// get all users
	@RequestMapping(value = "/user/", method = RequestMethod.GET , produces="application/json")
	public @ResponseBody List<User> getUsers() {
		
		return entityManager.createQuery( "from User order by id", User.class )
	            .getResultList();
	}
	
	//get user by ID
	@RequestMapping(value = "/user/{userID}", method = RequestMethod.GET , produces="application/json")
	public @ResponseBody User getUser(@PathVariable String userID) {
		
		return entityManager.createQuery("from User where id="+userID , User.class ).getSingleResult();
	}
	
	//create new user
	
	//update user
	
	//delete user
}












