package trips.model.dao.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import trips.model.Route;
import trips.model.Trip;
import trips.model.dao.RouteDao;

@Repository
public class RouteDaoImpl implements RouteDao {

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public Route getRoute(Integer id) {
        return entityManager.find( Route.class, id );
	}

	@Override
	public List<Route> getRoute(Trip trip) {
        return entityManager.createQuery( "from User where trip=:trip order by id", Route.class )
        		.setParameter("trip",	trip)
                .getResultList();
	}

}
