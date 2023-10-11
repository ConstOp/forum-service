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

import telran.java48.accounting.model.UserAccount;

@Component
@Order(30)
public class UpdateByOwnerFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			Principal principal = request.getUserPrincipal();
			if (!principal.getName().equalsIgnoreCase(getLastStringInPath(request))) {
				response.sendError(403);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return (HttpMethod.PUT.matches(method) && path.matches("/account/user/\\w+/?"))
				|| (HttpMethod.POST.matches(method) && path.matches("/forum/post/\\w+/?"))
				|| (HttpMethod.PUT.matches(method) && path.matches("/forum/post/\\w+/comment/\\w+/?"));

	}

	private String getLastStringInPath(HttpServletRequest request) {
		String[] arr = request.getServletPath().split("/");
		return arr[arr.length - 1];
	}
}
