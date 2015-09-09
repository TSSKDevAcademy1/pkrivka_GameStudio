package entity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Named
@SessionScoped
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private String name;
	// @Size(min = 5, max = 10)
	// @Pattern(regexp = ".*\\d.*")
	private String passwd;

	public User() {
	}

	public User(String name, String passwd) {
		this.name = name;
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String password) {
		this.passwd = password;
	}

}
