package telran.java48.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
		http.authorizeRequests((authorize) -> authorize.mvcMatchers("/account/register", "/forum/posts/**").permitAll()
				.mvcMatchers("/account/user/{login}/role/{role}").hasRole("ADMINISTRATOR")
				.mvcMatchers(HttpMethod.PUT, "/account/user/{login}").access("#login == authentication.name")
				.mvcMatchers(HttpMethod.DELETE, "/account/user/{login}")
				.access("#login == authentication.name or hasRole('ADMINISTRATOR')")
				.mvcMatchers("/forum/post/{author}", "/forum/post/{id}/comment/{author}")
				.access("#author == authentication.name").anyRequest().authenticated());
//				.addFilterBefore(new DeletePostFilter(), AuthorizationFilter.class)
//				.addFilterBefore(new UpdatePostFilter(), AuthorizationFilter.class);
		return http.build();
	}

}
