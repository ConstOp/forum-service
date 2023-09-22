package telran.java48.accounting.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java48.accounting.dto.UserDto;
import telran.java48.accounting.dto.UserRegisterDto;
import telran.java48.accounting.dto.UserRoleDto;
import telran.java48.accounting.dto.UserUpdateDto;
import telran.java48.accounting.service.AccountingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountingController {

	final AccountingService accountingService;

	@PostMapping("/register")
	public UserDto register(@RequestBody UserRegisterDto userRegister) {
		return accountingService.register(userRegister);
	}

	@PostMapping("/login")
	public UserDto login() {
		return accountingService.login();
	}

	@DeleteMapping("/user/{login}")
	public UserDto deleteUser(@PathVariable String login) {
		return accountingService.deleteUser(login);
	}

	@PutMapping("/user/{login}")
	public UserDto updateUser(@PathVariable String login, @RequestBody UserUpdateDto userUpdate) {
		return accountingService.updateUser(login, userUpdate);
	}

	@PutMapping("/user/{login}/role/{role}")
	public UserRoleDto addRole(@PathVariable String login, @PathVariable String role) {
		return accountingService.changeRole(login, role, true);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public UserRoleDto deleteRole(@PathVariable String login, @PathVariable String role) {
		return accountingService.changeRole(login, role, false);
	}

	@PutMapping("/password")
	public void changePassword() {
		
	}

	@GetMapping(("/user/{login}"))
	public UserDto getUser(@PathVariable String login) {
		return accountingService.getUser(login);
	}

}
