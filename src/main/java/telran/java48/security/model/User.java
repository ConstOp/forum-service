package telran.java48.security.model;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class User implements Principal {
	String userName;
	Set<Role> roles;

	@Override
	public String getName() {
		return userName;
	}

	public User(String userName, Set<String> roles) {
		super();
		this.userName = userName;
		this.roles =  roles.stream().map(r-> Role.valueOf(r)).collect(Collectors.toSet());
	}
	
	public Set<String> getRoles(){
		return roles.stream().map(r-> r.name()).collect(Collectors.toSet());
	};

}
