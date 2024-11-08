package KKCH.StoreEverything;

import KKCH.StoreEverything.Role.UserRoleService;
import KKCH.StoreEverything.Security.CustomUserDetailsService;
import KKCH.StoreEverything.Security.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan
@ComponentScan("KKCH")
@EnableJpaRepositories
@Configuration
public class StoreEverythingApplication {
	public static void main(String[] args) {
		SpringApplication.run(StoreEverythingApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper (){
		return new ModelMapper();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Primary
	@Bean
	public CustomUserDetailsService UserDetailsServiceImpl() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public UserRoleService userRoleService(){ return new UserRoleService(); }
}