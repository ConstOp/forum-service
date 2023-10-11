package telran.java48.accounting.service;

import telran.java48.accounting.dto.UserDto;
import telran.java48.accounting.dto.UserRegisterDto;
import telran.java48.accounting.dto.UserRoleDto;
import telran.java48.accounting.dto.UserUpdateDto;
import telran.java48.security.model.Role;

public interface UserAccountService {
	UserDto register(UserRegisterDto userRegister);

	UserDto deleteUser(String login);

	UserDto updateUser(String login, UserUpdateDto userUpdate);

	UserRoleDto changeRoleList(String login, String role, boolean isAddRole);

	void changePassword(String login, String newPassword);

	UserDto getUser(String login);

}
