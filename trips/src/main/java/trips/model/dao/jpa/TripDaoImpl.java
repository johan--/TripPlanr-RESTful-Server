package trips.model.dao.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import trips.model.User;
import trips.model.Trip;
import trips.model.dao.TripDao;

@Repository
public class TripDaoImpl implements TripDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Trip getTrip( Integer id )
    {
        return entityManager.find( Trip.class, id );
    }

    @Override
    public List<Trip> getTrips(User user)
    {
        return entityManager.createQuery( "from Trip where user=:user order by id", Trip.class )
        		.setParameter("user", user)
            .getResultList();
    }

}
