package telran.java48.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java48.post.dao.PostRepository;
import telran.java48.post.model.Post;

@Component
@RequiredArgsConstructor
public class DeletePostFilter implements Filter {

	final PostRepository postRepository;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			User user = (User) request.getUserPrincipal();
			String[] arr = request.getServletPath().split("/");
			String postId = arr[arr.length - 1];
			Post post = postRepository.findById(postId).orElse(null);
			if (post == null) {
				response.sendError(404);
				return;
			}
			if (!(user.getUsername().equals(post.getAuthor()) || user.getAuthorities().contains("ROLE_MODERATOR"))) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
		return;
	}

	private boolean checkEndPoint(String method, String path) {
		return HttpMethod.DELETE.matches(method) && path.matches("/forum/post/\\w+/?");

	}

}
