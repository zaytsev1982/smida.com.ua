package ua.com.smida.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.com.smida.security.details.CustomUserDetailsService;
import ua.com.smida.security.jwt.JwtConfigurer;
import ua.com.smida.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${url.path.access.private}")
    private String PATH_PRIVATE;
    @Value("${url.path.access.public}")
    private String PATH_PUBLIC;

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public CustomSecurityConfig(JwtTokenProvider jwtTokenProvider,
        CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPassword());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/v1/login", "/api/v1/registration").permitAll()
            .antMatchers(PATH_PUBLIC).permitAll()
            .antMatchers(HttpMethod.POST, PATH_PRIVATE).hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, PATH_PRIVATE).hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, PATH_PRIVATE).hasAnyRole("ADMIN", "USER")
            .anyRequest().authenticated()
            .and()
            .apply(new JwtConfigurer(jwtTokenProvider));
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPassword() {
        return new BCryptPasswordEncoder(11);
    }

}
