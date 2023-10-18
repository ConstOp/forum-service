package telran.java48.security;

import javax.websocket.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import lombok.RequiredArgsConstructor;
import telran.java48.post.dao.PostRepository;

@Configuration
public class AuthorizationConfiguration {

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.httpBasic(Customizer.withDefaults());
		http.csrf(csrf -> csrf.disable());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeRequests(authorize -> authorize
				.mvcMatchers("/account/register", "/forum/posts/**")
					.permitAll()
				.mvcMatchers("/account/user/{login}/role/{role}")
					.hasRole("ADMINISTRATOR")
				.mvcMatchers(HttpMethod.PUT, "/account/user/{login}")
					.access("#login == authentication.name")
				.mvcMatchers(HttpMethod.DELETE, "/account/user/{login}")
					.access("#login == authentication.name or hasRole('ADMINISTRATOR')")
				.mvcMatchers(HttpMethod.POST, "/forum/post/{author}")
        			.access("#author == authentication.name")
        		.mvcMatchers(HttpMethod.PUT, "/forum/post/{id}/comment/{author}")
        			.access("#author == authentication.name")
        		.mvcMatchers(HttpMethod.PUT, "/forum/post/{id}")
        			.access("@customSecurity.checkPostAuthor(#id, authentication.name)")
        		.mvcMatchers(HttpMethod.DELETE, "/forum/post/{id}")
        			.access("@customSecurity.checkPostAuthor(#id, authentication.name) or hasRole('MODERATOR')")
        		.mvcMatchers("/account/user/**", "/forum/posts/**")
        			.access("authentication.getDetails().credentialsNonExpired == true")//?????????
				.anyRequest()
					.authenticated()
					
		);
		return http.build();
	}
}
