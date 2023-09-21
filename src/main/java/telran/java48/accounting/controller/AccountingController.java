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

	@DeleteMapping("/user/{user}")
	public UserDto deleteUser(@PathVariable String login) {
		return accountingService.deleteUser(login);
	}

	@PutMapping("/user/{user}")
	public UserDto updateUser(@PathVariable String login, @RequestBody UserUpdateDto userUpdate) {
		return accountingService.updateUser(login, userUpdate);
	}

	@PutMapping("/user/{user}/role/{role}")
	public UserRoleDto addRole(@PathVariable String login, @PathVariable String role) {
		return accountingService.addRole(login, role);
	}

	@DeleteMapping("/user/{user}/role/{role}")
	public UserRoleDto deleteRole(@PathVariable String login, @PathVariable String role) {
		accountingService.deleteRole(login, role);
		return null;
	}

	@PutMapping("/password")
	public void changePassword() {
		accountingService.changePassword();
	}

	@GetMapping(("/user/{user}"))
	public UserDto getUser(@PathVariable String login) {
		return accountingService.getUser(login);
	}

}
