package <%= PACKAGE %>.config

import <%= PACKAGE %>.security.jwt.{JWTAuthenticationFilter, JWTLoginFilter, TokenAuthenticationService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.{HttpSecurity, WebSecurity}
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig @Autowired()(tokenAuthenticationService: TokenAuthenticationService) extends WebSecurityConfigurerAdapter {


  override def configure(auth: AuthenticationManagerBuilder): Unit = {
    auth.inMemoryAuthentication()
      .withUser("admin")
      .password("password")
      .roles("ADMIN")
  }

  override def configure(web: WebSecurity): Unit = {
    web.ignoring()
      .antMatchers(HttpMethod.OPTIONS, "/api/**")
  }

  override def configure(http: HttpSecurity): Unit = {
    http.csrf().disable()
      .authorizeRequests()
      .antMatchers("/api/**").authenticated()
      .and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), tokenAuthenticationService),
      classOf[UsernamePasswordAuthenticationFilter])
      .addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService),
        classOf[UsernamePasswordAuthenticationFilter])

  }
}
