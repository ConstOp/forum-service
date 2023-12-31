package telran.java48.accounting.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode
@Document(collection = "users")
public class User {
	@Id
	String login;
	@Setter
	String passwordString;
	@Setter
	String firstName;
	@Setter
	String lastName;
	Set<String> roles;

	public User() {
		roles = new HashSet<>();
	}

	public User(String login, String firstName, String lastName) {
		super();
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		roles.add("USER");
	}

	public boolean addRole(String role) {
		return roles.add(role);
	}

	public boolean removeRole(String role) {
		return roles.remove(role);
	}
}
