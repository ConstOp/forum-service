package telran.java48.security;

import java.time.LocalDate;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java48.accounting.dao.UserAccountRepository;
import telran.java48.accounting.model.UserAccount;

@Service
@RequiredArgsConstructor
public class UserDelailsServiceImpl implements UserDetailsService {
	final UserAccountRepository userAccountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount userAccount = userAccountRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		String[] roles = userAccount.getRoles()
				.stream()
				.map(r -> "ROLE_" + r.toUpperCase())
				.toArray(String[]::new);
//		return new User(username, userAccount.getPassword(), AuthorityUtils.createAuthorityList(roles));
		boolean credentialsNonExpired = true;
		if(LocalDate.now().isAfter(userAccount.getPasswordChangeDate())) {
			credentialsNonExpired = false;
		}
		return new User(username, userAccount.getPassword(), true, true, credentialsNonExpired, true, AuthorityUtils.createAuthorityList(roles));
	}

}
