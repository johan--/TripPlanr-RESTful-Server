package trips.model.dao;

import java.util.List;

import trips.model.Route;
import trips.model.Trip;

public interface RouteDao {

	Route getRoute( Integer id );

	List<Route> getRoute(Trip trip);

	Route saveRoute(Route route);

	void deleteRoute(Route route);

}
