package telran.java48.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
	String login;
	String password;
	String firstName;
	String lastName;
}
