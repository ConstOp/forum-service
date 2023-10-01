package telran.java48.security.filter;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java48.accounting.dao.UserAccountRepository;
import telran.java48.accounting.model.UserAccount;
import telran.java48.post.dao.PostRepository;
import telran.java48.post.model.Post;

@Component
@RequiredArgsConstructor
@Order(55)
public class ModeratorManagingPostsFilter implements Filter {

	final PostRepository postRepository;
	final UserAccountRepository userAccountRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			Principal principal = request.getUserPrincipal();
			Post post = postRepository.findById(getLastStringInPath(request)).orElse(null);
			if (post == null) {
				response.sendError(404);
				return;
			}
			UserAccount userAccount = userAccountRepository.findById(principal.getName()).get();
			if (!post.getAuthor().equalsIgnoreCase(principal.getName()) && !userAccount.getRoles().contains("MODERATOR")) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return HttpMethod.DELETE.matches(method) && path.matches("/forum/post/\\w+/?");
	}
	
	private String getLastStringInPath(HttpServletRequest request) {
		String[] arr = request.getServletPath().split("/");
		return arr[arr.length - 1];
	}
}
