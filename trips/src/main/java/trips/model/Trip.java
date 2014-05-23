package trips.model;

import java.sql.Date;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

public class Trip {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Date startTime;
	
	@OneToMany(mappedBy = "route")
    @OrderBy("id")
	private ArrayList<Route> routes; // one to many
	
	@OneToMany(mappedBy = "location")
    @OrderBy("id")
	private ArrayList<Location> locations; // many to many
	
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

	public ArrayList<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(ArrayList<Route> routes) {
		this.routes = routes;
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public String getDefaultTravelMethod() {
		return defaultTravelMethod;
	}

	public void setDefaultTravelMethod(String defaultTravelMethod) {
		this.defaultTravelMethod = defaultTravelMethod;
	}

}
