package telran.java48.accounting.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.java48.security.model.Role;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection = "users")
public class UserAccount {
	@Id
	String login;
	@Setter
	String password;
	@Setter
	String firstName;
	@Setter
	String lastName;
	Set<Role> roles = new HashSet<>();

//	public UserAccount() { //It doesn't work correctly for me. Why?
//		roles = new HashSet<>();
//	}

	public UserAccount(String login, String password, String firstName, String lastName) {
		super();
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public boolean addRole(Role role) {
		return roles.add(role);
	}

	public boolean removeRole(Role role) {
		return roles.remove(role);
	}
}
