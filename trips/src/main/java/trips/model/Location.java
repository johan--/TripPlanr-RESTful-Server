package trips.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Location {
	@JsonIgnore
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false, length=32)
	private String name;
	
	@Column(nullable = false, length=8, name="location_type")
	private String type;
	
	@Column(nullable = false, length=64)
	private String notes;

	private double lon;
	private double lat;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public boolean equals (Location location) {
		if (this.name == location.getName()
				&& this.address == location.getAddress()
				&& this.lat == location.getLat()
				&& this.lon == location.getLon()
				&& this.type == location.getType()
				&& this.notes == location.getNotes()
				)
			return true;
		else
			return false;
	}

	public void copy(Location location) {
		this.name = location.getName();
		this.address = location.getAddress();
		this.lat = location.getLat();
		this.lon = location.getLon();
		this.type = location.getType();
		this.notes = location.getNotes();
	}

}