package telran.java48.accounting.service;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java48.accounting.dao.UserAccountRepository;
import telran.java48.accounting.dto.UserDto;
import telran.java48.accounting.dto.UserRegisterDto;
import telran.java48.accounting.dto.UserRoleDto;
import telran.java48.accounting.dto.UserUpdateDto;
import telran.java48.accounting.dto.exceptions.UserExistsException;
import telran.java48.accounting.dto.exceptions.UserNotFoundException;
import telran.java48.accounting.dto.exceptions.UserRoleException;
import telran.java48.accounting.model.UserAccount;
import telran.java48.security.model.Role;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService, CommandLineRunner {
	final UserAccountRepository userAccountRepository;
	final ModelMapper modelMapper;

	@Override
	public UserDto register(UserRegisterDto userRegister) {
		if (userAccountRepository.existsById(userRegister.getLogin())) {
			throw new UserExistsException();
		}
		UserAccount userAccount = modelMapper.map(userRegister, UserAccount.class);
		userAccount.addRole("USER");
		String password = BCrypt.hashpw(userRegister.getPassword(), BCrypt.gensalt());
		userAccount.setPassword(password);
		userAccountRepository.save(userAccount);
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public UserDto deleteUser(String login) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		userAccountRepository.delete(userAccount);
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public UserDto updateUser(String login, UserUpdateDto userUpdate) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		if (userUpdate.getFirstName() != null) {
			userAccount.setFirstName(userUpdate.getFirstName());
		}
		if (userUpdate.getLastName() != null) {
			userAccount.setLastName(userUpdate.getLastName());
		}
		userAccountRepository.save(userAccount);
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public void changePassword(String login, String newPassword) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		String password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
		userAccount.setPassword(password);
		userAccountRepository.save(userAccount);
	}

	@Override
	public UserDto getUser(String login) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(userAccount, UserDto.class);
	}

	@Override
	public UserRoleDto changeRoleList(String login, String role, boolean isAddRole) {
		UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
		boolean res;
		if (isAddRole) {
			res = userAccount.addRole(role.toUpperCase());
		} else {
			res = userAccount.removeRole(role.toUpperCase());
		}
		if(res) {
			userAccountRepository.save(userAccount);
		}
		return modelMapper.map(userAccount, UserRoleDto.class);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!userAccountRepository.existsById("admin")) {
			String password = BCrypt.hashpw("admin", BCrypt.gensalt());
			UserAccount userAccount = new UserAccount("admin", password, "", "");
			userAccount.addRole("USER");
			userAccount.addRole("MODERATOR");
			userAccount.addRole("ADMINISTRATOR");
			userAccountRepository.save(userAccount);
		}
	}
}
