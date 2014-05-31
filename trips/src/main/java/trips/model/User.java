package trips.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ROLE_USER")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = true, name = "email")
	private String email;

	@Column(nullable = true, name = "password")
	private String password;

	@Column(nullable = false)
	private boolean enabled;

	@Column(nullable = false, name = "device_id")
	private String deviceId;

	@Column(nullable = false, name = "client_id")
	private String clientId;

	@Column(nullable = false, name = "client_secret")
	private String clientSecret;

	public User() {
	}

	public User(Integer id, String email, String password, boolean enabled,
			String deviceId, String clientId, String clientSecret) {

		this.id = id;
		this.email = email;
		this.password = password;
		this.enabled = enabled;
		this.deviceId = deviceId;
		this.clientId = clientId;
		this.clientSecret = clientSecret;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
