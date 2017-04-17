package com.roi.config;


import com.roi.entity.Admin;
import com.roi.entity.Student;
import com.roi.entity.Teacher;
import com.roi.repository.AdminRepository;
import com.roi.repository.StudentRepository;
import com.roi.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        for(Admin a: adminRepository.findAll()) {
            auth.inMemoryAuthentication().withUser("ad"+a.getLogin().toString()).password(a.getPassword()).roles("ADMIN");

        }

        for(Student s: studentRepository.findAll()) {
            auth.inMemoryAuthentication().withUser("st"+s.getLogin().toString()).password(s.getPassword()).roles("STUDENT");
        }

        for(Teacher t: teacherRepository.findAll()) {
            auth.inMemoryAuthentication().withUser("te"+t.getLogin().toString()).password(t.getPassword()).roles("TEACHER");
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/studentName/**").access("hasRole('ROLE_STUDENT')")
                .antMatchers("/teacher/**").access("hasRole('ROLE_TEACHER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
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
}