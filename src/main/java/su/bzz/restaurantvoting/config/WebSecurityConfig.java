package su.bzz.restaurantvoting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import su.bzz.restaurantvoting.AuthUser;
import su.bzz.restaurantvoting.model.Role;
import su.bzz.restaurantvoting.model.User;
import su.bzz.restaurantvoting.repository.UserRepository;
import su.bzz.restaurantvoting.util.JsonUtil;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    void setMapper() {
        JsonUtil.setObjectMapper(objectMapper);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.debug("Authenticating '{}'", email);
            Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
            return new AuthUser(optionalUser.orElseThrow(
                    () -> new UsernameNotFoundException("User '" + email + "' was not found")));
        };
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(PASSWORD_ENCODER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/api/**").anonymous()
                .antMatchers(HttpMethod.POST, "/api/vote/**").hasRole(Role.USER.name())
                .antMatchers(HttpMethod.DELETE, "/api/vote/**").hasRole(Role.USER.name())
                .antMatchers(HttpMethod.PUT, "/api/vote/**").hasRole(Role.USER.name())
                .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/api/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role.ADMIN.name())
                .antMatchers("/api/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }
}