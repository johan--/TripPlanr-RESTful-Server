package trips.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;

@Entity
public class Trip {
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private User user;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Date startTime;
	
	@OneToMany(mappedBy = "trip")
    @OrderBy("id")
	private List<Route> routes; // one to many
	
	@ManyToMany
	@JoinTable(name = "trip_locations",
    joinColumns = @JoinColumn(name = "trip_id"),
    inverseJoinColumns = @JoinColumn(name = "location_id"))
	@OrderColumn(name = "step")
	private List<Location> locations; // many to many
	
	@Column(length=10)
	private String defaultTravelMethod;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public String getDefaultTravelMethod() {
		return defaultTravelMethod;
	}

	public void setDefaultTravelMethod(String defaultTravelMethod) {
		this.defaultTravelMethod = defaultTravelMethod;
	}

}
