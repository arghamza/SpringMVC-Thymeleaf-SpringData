package ma.emsi.patientweb.Security;

import ma.emsi.patientweb.Security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

         /* String encodedPWD=passwordEncoder.encode("1234");
        auth.inMemoryAuthentication()
                .withUser("user1").password(encodedPWD).roles("USER");
        auth.inMemoryAuthentication()
                .withUser("user2").password(passwordEncoder.encode("12345")).roles("USER");

        auth.inMemoryAuthentication() .withUser("admin").password(passwordEncoder.encode("2546")).roles("USER","ADMIN");*/
      /*
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username as principal,password as credentials,active from users where username=?")
                .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username=?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder);
    */
    auth.userDetailsService(userDetailsService);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.formLogin();
            http.authorizeRequests()
                .antMatchers("/webjars/**").permitAll();
            http.authorizeRequests().antMatchers("/").permitAll();
            http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
            http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER");
            http.authorizeRequests().anyRequest().authenticated();
            http.exceptionHandling().accessDeniedPage("/403");
    }
}
