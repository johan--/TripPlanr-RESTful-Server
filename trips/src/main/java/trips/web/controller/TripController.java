package trips.web.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Date;
import java.util.ArrayList;
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

import trips.model.Location;
import trips.model.Route;
import trips.model.Trip;
import trips.model.dao.LocationDao;
import trips.model.dao.RouteDao;
import trips.model.dao.TripDao;
import trips.model.dao.UserDao;

@Controller
public class TripController {

	@Autowired
	private TripDao tripDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private LocationDao locationDao;

	@Autowired
	private RouteDao routeDao;

	@RequestMapping("/trip/list.json")
	public void list(HttpServletResponse response, HttpServletRequest request)
			throws JSONException, IOException {
		JSONObject reqJSON = null;
		List<Trip> trips = null;
		
		int userId = 0;
		try {
			reqJSON = new JSONObject(URLDecoder.decode(request.getQueryString(), "UTF-8"));
			userId = reqJSON.getInt("userId");
		} catch (JSONException e) {
			throw new JSONException("jason error: " + e.getMessage() + " string: " + URLDecoder.decode(request.getQueryString(), "UTF-8"));

		}
		if (userId > 0) {
			trips = tripDao.getTrips(userDao.getUser(userId));
		}
		ObjectMapper mapper = new ObjectMapper();
		String result = "{\"trips\" : " + mapper.writeValueAsString( trips ) + "}";
		response.setContentType("application/json");
		response.getWriter().write(result );
	}

	@RequestMapping("/trip/delete.json")
	public void delete(HttpServletResponse response, HttpServletRequest request)
			throws JSONException, IOException {
		JSONObject reqJSON = null;
		int userId = 0;
		String tripName = "";
		String result = "";
		try {
			reqJSON = new JSONObject(request.getQueryString());
			userId = reqJSON.getInt("userId");
			tripName = reqJSON.getString("tripName");

		} catch (JSONException e) {
			result = "System error";
			throw new JSONException("jason error: " + request.getQueryString() + " Json: " + reqJSON.toString());
		}

		if (tripName != "" && userId > 0) {
			Trip trip = tripDao.getTrip(userDao.getUser(userId), tripName);
			tripDao.deleteTrip(trip);
			result = "Trip successfuly removed";
		} else {
			result = "Input error";
		}
		result = "{\"result\" : \"" + result + "\"}";
		response.setContentType("application/json");
		response.getWriter().write(result );

	}

	@RequestMapping("/trip/create.json")
	public void create(HttpServletResponse response, HttpServletRequest request)
			throws JSONException, IOException {
		JSONObject reqJSON = null;
		JSONObject tripJSON = null;
		Trip trip = new Trip();
		String err = "";
		String result = "";

		int userId = 0;
		try {
			reqJSON = new JSONObject(request.getQueryString());
			userId = reqJSON.getInt("userId");
			tripJSON = reqJSON.getJSONObject("trip");

		} catch (JSONException e) {
			err = "Invalid input";
			throw new JSONException("jason error: " + request.getQueryString() + " Json: " + reqJSON.toString());
		}

		if (userId > 0 && tripJSON != null) {
			trip.setUser(userDao.getUser(userId));
			try {
				List<Location> locations = new ArrayList<Location>();
				JSONArray routeJs = tripJSON.getJSONArray("routes");
				Location lastLocation = null;
				trip.setName(tripJSON.getString("name"));
				trip.setStartTime(Date.valueOf(tripJSON.getString("startTime")));
				trip.setDefaultTravelMethod(tripJSON.getString("defaultTravelMethod"));
				trip = tripDao.saveTrip(trip);

				for(int i=0; i< routeJs.length(); i++) {
					JSONObject routeJ = routeJs.getJSONObject(0);
					JSONObject locationJ = routeJ.getJSONObject("fromLocation");
					Route route = new Route();
					route.setTrip(trip);
					route.setTravelMethod(routeJ.getString("travelMethod"));

					Location location = new Location();
					location.setName(locationJ.getString("name"));
					location.setAddress(locationJ.getString("address"));
					location.setType(locationJ.getString("type"));
					location.setLat(locationJ.getDouble("lat"));
					location.setLon(locationJ.getDouble("lon"));
					location = locationDao.saveLocation(location);

					route.setFromLocation(location);

					if (lastLocation != null && !lastLocation.equals(location)) {
						locations.add(location);
					}

					locationJ = routeJ.getJSONObject("toLocation");
					location = new Location();
					location.setName(locationJ.getString("name"));
					location.setAddress(locationJ.getString("address"));
					location.setType(locationJ.getString("type"));
					location.setLat(locationJ.getDouble("lat"));
					location.setLon(locationJ.getDouble("lon"));
					location = locationDao.saveLocation(location);

					route.setToLocation(location);
					locations.add(location);

					lastLocation.copy(location);

					route = routeDao.saveRoute(route);
				}

				trip.setLocations(locations);
				trip = tripDao.saveTrip(trip);
			} catch (JSONException e) {
				err = "Missing parameter";
			}
		}
		if (err == "")
			result = "{\"result\" : \"Trip created\"}";
		else
			result = "{\"result\" : \"Error\", \"message\" : \"" + err + "\"}";
		response.setContentType("application/json");
		response.getWriter().write(result );
	}
}
