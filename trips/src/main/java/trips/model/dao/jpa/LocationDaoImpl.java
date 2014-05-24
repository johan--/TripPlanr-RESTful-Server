package trips.model.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import trips.model.Location;
import trips.model.dao.LocationDao;

@Repository
public class LocationDaoImpl implements LocationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Location getLocation( Integer id )
    {
        return entityManager.find( Location.class, id );
    }
}
