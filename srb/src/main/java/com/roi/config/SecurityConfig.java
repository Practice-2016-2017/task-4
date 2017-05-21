package com.roi.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
                "select concat('ad',Login) as username, Password as password, true as enabled from Admin where concat('ad',Login) = ?")
                .authoritiesByUsernameQuery("select concat('ad',Login) as username, 'ROLE_ADMIN' from Admin where concat('ad',Login) = ?");


        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
                "select concat('te',Login) as username, Password as password, true as enabled from Teacher where concat('te',Login) = ?")
                .authoritiesByUsernameQuery("select concat('te',Login) as username, 'ROLE_TEACHER' from Teacher where concat('te',Login) = ?");

        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
                "select concat('st',Login) as username, Password as password, true as enabled from Student where concat('st',Login) = ?")
                .authoritiesByUsernameQuery("select concat('st',Login) as username, 'ROLE_STUDENT' from Student where concat('st',Login) = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/studentName/**").access("hasRole('ROLE_STUDENT')")
                .antMatchers("/teacher/**").access("hasRole('ROLE_TEACHER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated();
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/**").permitAll()
                .anyRequest().permitAll()
                .and();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();

        http.sessionManagement()
                .maximumSessions(100)
                .expiredUrl("/login")
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry());


        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true);


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SessionAuthenticationStrategy getSessionAuthStrategy(SessionRegistry sessionRegistry) {
        return  new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
    }
    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

}