package com.example.bank.global.config;

<<<<<<< HEAD
import com.example.bank.global.dto.CommonResponse;
import com.example.bank.global.dto.ResponseDto;
import com.example.bank.user.domain.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
=======
import com.example.bank.user.domain.UserRole;
>>>>>>> origin/master
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        log.debug("디버그 :BCryptPasswordEncoder() 빈 등록됨");
        return new BCryptPasswordEncoder();
    }

    @Bean
<<<<<<< HEAD
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그 : filterChain 빈 등록");
        http.headers().frameOptions().disable(); // iframe 허용 안함
        http.csrf().disable(); //post 위조일 때, 다른 개발 툴을 사용했을 때 작동안해서
        http.cors().configurationSource(configurationSource()); //자바스크립트 요청 거부
=======
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.headers().frameOptions().disable(); // iframe 허용 안함
        http.csrf().disable(); //post 위조일 때, 다른 개발 툴을 사용했을 때 작동안해서
        http.cors().configurationSource(null); //자바스크립트 요청 거부
>>>>>>> origin/master

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable();
<<<<<<< HEAD

        http.exceptionHandling().authenticationEntryPoint(((request, response, authException) ->
                CommonResponse.unAuthentication(response, "로그인 하세요.")));
        http.authorizeRequests()
                //.antMatchers("/api/**").authenticated()
=======
        http.authorizeRequests()
                .antMatchers("/api/**").authenticated()
>>>>>>> origin/master
                .antMatchers("api/admin/**").hasRole(""+UserRole.ADMIN)
                .anyRequest().permitAll();
        return http.build();
    }

<<<<<<< HEAD
    public CorsConfigurationSource configurationSource(){
        log.debug("디버그 : SecurityFilterChain cors 등록");
=======
    @Bean
    public CorsConfigurationSource configurationSource(){
>>>>>>> origin/master
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOriginPattern("*"); // IP 주소(프론트 앤드 IP만 허용)// 앱은 cors에 안걸림
        corsConfiguration.setAllowCredentials(true); //클라이언트 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
