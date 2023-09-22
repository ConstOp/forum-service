package telran.java48.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java48.accounting.dao.UserRepository;
import telran.java48.accounting.dto.UserDto;
import telran.java48.accounting.dto.UserRegisterDto;
import telran.java48.accounting.dto.UserRoleDto;
import telran.java48.accounting.dto.UserUpdateDto;
import telran.java48.accounting.dto.exceptions.UserConflictException;
import telran.java48.accounting.dto.exceptions.UserNotFoundException;
import telran.java48.accounting.model.User;

@Service
@RequiredArgsConstructor
public class AccountingServiceImpl implements AccountingService {
	final UserRepository userRepository;
	final ModelMapper modelMapper;

	@Override
	public UserDto register(UserRegisterDto userRegister) {
		if (userRepository.existsById(userRegister.getLogin())) {
			throw new UserConflictException();
		}
		User user = modelMapper.map(userRegister, User.class);
		user.addRole("USER");
		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto login() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDto deleteUser(String login) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		userRepository.delete(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(String login, UserUpdateDto userUpdate) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		if (userUpdate.getFirstName() != null) {
			user.setFirstName(userUpdate.getFirstName());
		}
		if (userUpdate.getLastName() != null) {
			user.setLastName(userUpdate.getLastName());
		}
		userRepository.save(user);
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserRoleDto addRole(String login, String role) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		user.addRole(role);
		return modelMapper.map(user, UserRoleDto.class);
	}

	@Override
	public UserRoleDto deleteRole(String login, String role) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		user.removeRole(role);
		return modelMapper.map(user, UserRoleDto.class);
	}

	@Override
	public void changePassword(String login, String newPassword) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		user.setPasswordString(newPassword);
		userRepository.save(user);
	}

	@Override
	public UserDto getUser(String login) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserRoleDto changeRole(String login, String role, boolean isAddRole) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		boolean res;
		if (isAddRole) {
			res = user.addRole(role);
		} else {
			res = user.removeRole(role);
		}
		if (res) {
			userRepository.save(user);
		}
		return modelMapper.map(user, UserRoleDto.class);
	}

}
