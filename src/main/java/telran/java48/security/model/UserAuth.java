package telran.java48.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class UserAuth extends User {
	private static final long serialVersionUID = 9167377916071826359L;
	boolean isPasswordExpiratonDate;

	public UserAuth(String username, String password, Collection<? extends GrantedAuthority> authorities,
			boolean isPasswordExpiratonDate) {
		super(username, password, authorities);
		this.isPasswordExpiratonDate = isPasswordExpiratonDate;
	}

	@Override
	public String toString() {
		return "UserAuth [isPasswordExpiratonDate=" + isPasswordExpiratonDate + "]";
	}

}
