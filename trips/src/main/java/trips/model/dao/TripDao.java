package trips.model.dao;

import java.util.List;

import trips.model.User;

import trips.model.Trip;

public interface TripDao {

    Trip getTrip( Integer id );

    List<Trip> getTrips(User user);

}
