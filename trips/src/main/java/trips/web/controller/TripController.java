package trips.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import trips.model.Trip;
import trips.model.dao.TripDao;
import trips.model.dao.UserDao;

@Controller
public class TripController {

	@Autowired
	private TripDao tripDao;
	
	@Autowired
	private UserDao userDao;

	@RequestMapping("/trip/list.json")
	public void list(HttpServletResponse response, HttpServletRequest request)
			throws JSONException, IOException {
		JSONArray jsonArray = new JSONArray();
		JSONObject reqJSON = null;
		List<Trip> trips = null;
		
		int userId = 0;
		try {
			reqJSON = new JSONObject(request.getQueryString());
			userId = reqJSON.getInt("userId");
		} catch (JSONException e) {
			throw new JSONException("jason error: " + request.getQueryString() + " Json: " + reqJSON.toString());

		}
		if (userId > 0) {
			trips = tripDao.getTrips(userDao.getUser(userId));
		}
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		String result = "{\"trips\" : " + mapper.writeValueAsString( trips ) + "}";
		response.getWriter().write(result );
	}

}
