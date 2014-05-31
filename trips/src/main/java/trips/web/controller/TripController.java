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
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping("/trip/list/{userId}")
	public void list(@PathVariable Integer userId, HttpServletResponse response, HttpServletRequest request)
			throws JSONException, IOException {
		List<Trip> trips = null;

		if (userId > 0) {
			trips = tripDao.getTrips(userDao.getUser(userId));
		}
		ObjectMapper mapper = new ObjectMapper();
		String result = "{\"trips\" : " + mapper.writeValueAsString( trips ) + "}";
		response.setContentType("application/json");
		response.getWriter().write(result );
	}

	private String delete(Integer userId, String tripName) {
		String result = "";
		if (tripName != "" && userId > 0) {
			Trip trip = tripDao.getTrip(userDao.getUser(userId), tripName);
			if (trip != null) {
				List<Route> routes = trip.getRoutes();
				for(int i=0; i < routes.size(); i++) {
					routeDao.deleteRoute(routes.get(i));
				}
				tripDao.deleteTrip(trip);
				result = "Trip successfuly removed";
			} else {
				result = "Trip not found";
			}
		} else {
			result = "Input error";
		}
		return "{\"result\" : \"" + result + "\"}";

	}

	@RequestMapping("/trip/delete/{userId}/{tripName}")
	public void delete(@PathVariable Integer userId, @PathVariable String tripName, HttpServletResponse response, HttpServletRequest request)
			throws JSONException, IOException {

		response.setContentType("application/json");
		response.getWriter().write(delete(userId, URLDecoder.decode(tripName, "UTF-8")));

	}

	private String create(Integer userId, JSONObject tripJSON) {
		String result = ""; 
		Trip trip = new Trip();
		String err = "";

		if (userId > 0 && tripJSON != null) {
			trip.setUser(userDao.getUser(userId));
			try {
				List<Location> locations = new ArrayList<Location>();
				JSONArray routeJs = tripJSON.getJSONArray("routes");
				Location lastLocation = new Location();
				trip.setName(tripJSON.getString("name"));
				trip.setStartTime(Date.valueOf(tripJSON.getString("startTime")));
				trip.setDefaultTravelMethod(tripJSON.getString("defaultTravelMethod"));
				trip = tripDao.saveTrip(trip);

				for(int i=0; i< routeJs.length(); i++) {
					JSONObject routeJ = routeJs.getJSONObject(i);
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
					location.setNotes(locationJ.getString("notes"));
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
					location.setNotes(locationJ.getString("notes"));

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
			} catch (Exception ex) {
				err = "others:" + ex.getMessage();
			}
		}
		if (err == "")
			result = "{\"result\" : \"Trip created\"}";
		else
			result = "{\"result\" : \"Error\", \"message\" : \"" + err + "\"}";

		return result;
	}

	@RequestMapping("/trip/create")
	public void create(HttpServletResponse response, HttpServletRequest request)
			throws JSONException, IOException {
		JSONObject reqJSON = null;
		String result;

		try {
			reqJSON = new JSONObject(URLDecoder.decode(request.getQueryString(), "UTF-8"));
			result = create(reqJSON.getInt("userId"),reqJSON.getJSONObject("trip"));

		} catch (JSONException e) {
			result = "{\"result\" : \"Invalid input\"}";
			throw new JSONException("jason error: " + e.getMessage() + " string: " + request.getQueryString());
		}

		response.setContentType("application/json");
		response.getWriter().write(result );
	}

	@RequestMapping("/trip/update")
	public void update(HttpServletResponse response, HttpServletRequest request)
			throws JSONException, IOException {
		JSONObject reqJSON = null;
		JSONObject tripJSON = null;
		String result, tripName;
		Integer userId;

		try {
			reqJSON = new JSONObject(URLDecoder.decode(request.getQueryString(), "UTF-8"));
			tripJSON = reqJSON.getJSONObject("trip");
			userId = reqJSON.getInt("userId");
			tripName = reqJSON.getString("tripName");
			result = delete(userId, tripName);
			if (result != "{\"result\" : \"Input error\"}" && result != "{\"result\" : \"Trip not found\"}")
				result = create(userId,tripJSON);
		} catch (JSONException e) {
			result = "{\"result\" : \"Invalid input\"}";
			throw new JSONException("jason error: " + e.getMessage() + " string: " + request.getQueryString());
		}

		response.setContentType("application/json");
		response.getWriter().write(result );
	}

}
