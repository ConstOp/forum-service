package telran.java48.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.java48.accounting.dao.UserAccountRepository;
import telran.java48.post.dao.PostRepository;
import telran.java48.post.model.Post;
import telran.java48.security.model.UserAuth;

@Service("customSecurity")
@RequiredArgsConstructor
public class CustomWebSecurity {
	final PostRepository postRepository;
	final UserAccountRepository userAccountRepository;

	public boolean checkPostAuthor(String postId, String userName) {
		Post post = postRepository.findById(postId).orElse(null);
		return post != null && userName.equalsIgnoreCase(post.getAuthor());
	}

	public boolean checkPasswordExpiratonDate(Authentication authentication) {
		Object principal = authentication.getPrincipal();
//		System.out.println(principal);
		UserAuth userAuth = (UserAuth) principal;
//		System.out.println(userAuth.isPasswordExpiratonDate());
		return userAuth.isPasswordExpiratonDate();
	}
}
