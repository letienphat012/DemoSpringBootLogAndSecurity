package tma.tft.phat.ss.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.social.security.SpringSocialConfigurer;

import tma.tft.phat.ss.security.jwt.JwtConfigurer;
import tma.tft.phat.ss.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/register", "/login", "/logout","/auth/signin").permitAll().antMatchers("/")
                .hasRole("MEMBER").antMatchers("/admin").hasRole("ADMIN").antMatchers("/student").hasRole("MEMBER")
                .antMatchers("/student/**").hasRole("ADMIN").antMatchers("/course/**").hasRole("ADMIN").and()
                .formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password")
                .defaultSuccessUrl("/").failureUrl("/login?error").and().logout().logoutSuccessUrl("/").and()
                .exceptionHandling().accessDeniedPage("/403");
        http.apply(new SpringSocialConfigurer());

        http.apply(new JwtConfigurer(jwtTokenProvider));
    }

}
