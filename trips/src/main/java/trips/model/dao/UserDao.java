package trips.model.dao;

import java.util.List;

import trips.model.User;

public interface UserDao {

	User getUser( Integer id );

	List<User> getUsers();

	User saveUser(User user);

}
