package telran.java48.accounting.service;

import telran.java48.accounting.dto.UserDto;
import telran.java48.accounting.dto.UserRegisterDto;
import telran.java48.accounting.dto.UserRoleDto;
import telran.java48.accounting.dto.UserUpdateDto;

public interface AccountingService {
	UserDto register(UserRegisterDto userRegister);

	UserDto deleteUser(String login);

	UserDto updateUser(String login, UserUpdateDto userUpdate);

	UserRoleDto changeRole(String login, String role, boolean isAddRole);

	UserRoleDto addRole(String login, String role);

	UserRoleDto deleteRole(String login, String role);

	void changePassword(String login, String newPassword);

	UserDto getUser(String login);

}
