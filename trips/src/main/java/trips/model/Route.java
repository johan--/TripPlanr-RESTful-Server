package trips.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

public class Route {
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Trip trip; // many to one
	
	@OneToMany(mappedBy = "location")
	@OrderBy("id")
	private Location fromLocation;

	@OneToMany(mappedBy = "location")
	@OrderBy("id")
	private Location toLocation;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String travelMethod;

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Location getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(Location fromLocation) {
		this.fromLocation = fromLocation;
	}

	public Location getToLocation() {
		return toLocation;
	}

	public void setToLocation(Location toLocation) {
		this.toLocation = toLocation;
	}

	public String getTravelMethod() {
		return travelMethod;
	}

	public void setTravelMethod(String travelMethod) {
		this.travelMethod = travelMethod;
	}

}