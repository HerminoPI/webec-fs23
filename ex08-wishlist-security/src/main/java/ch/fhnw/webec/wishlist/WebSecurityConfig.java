package ch.fhnw.webec.wishlist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/css/**").permitAll() //damit weitere css Datein die später kommen auch erlaubt sind
            .antMatchers("/img/background.jpg").permitAll()
            .antMatchers("/category").permitAll()
            .regexMatchers("/wishlist/\\d+").authenticated()
            .anyRequest().hasRole("EDITOR")
            .and()
            .formLogin(Customizer.withDefaults());
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        var builder = User.withDefaultPasswordEncoder();

        var user = builder
            .username("user")
            .password("user")
            .roles().build();

        var editor = builder
            .username("editor")
            .password("editor")
            .roles("EDITOR").build();

        return new InMemoryUserDetailsManager(user, editor);
    }

}
