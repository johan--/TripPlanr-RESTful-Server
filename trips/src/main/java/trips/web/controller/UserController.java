package trips.web.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import trips.model.User;
import trips.model.dao.UserDao;

@Controller
public class UserController {

	@Autowired
	private UserDao userDao;

	@RequestMapping("/users.html")
	public String users( ModelMap models )
	{
		models.put( "users", userDao.getUsers() );
		return "users";
	}
}
