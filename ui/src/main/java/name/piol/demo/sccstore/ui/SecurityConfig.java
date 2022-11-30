package name.piol.demo.sccstore.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig { //extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authProvider;
    
    @Autowired
    private CustomAuthenticationFailureHandler failureHandler;

    
    
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                //.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                //.antMatchers("/sccstore/store").authenticated()
                .antMatchers("/sccstore/shopping-cart").authenticated()
                .antMatchers("/**").permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .failureHandler(failureHandler)
                .defaultSuccessUrl("/sccstore")
                .permitAll();
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests( authorize -> authorize
                .requestMatchers("/sccstore/shopping-cart").authenticated()
                .requestMatchers("/**").permitAll()                                  
                //.anyRequest().denyAll() 
                )
            .formLogin()
                .loginPage("/login")
                .failureHandler(failureHandler)
                .defaultSuccessUrl("/sccstore")
                .permitAll();

        return http.build();
    }
    
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(authProvider);
    }*/

    /*@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
    
        return authProvider;
    }*/

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
