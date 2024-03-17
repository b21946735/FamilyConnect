package com.familyconnect.fc;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.familyconnect.fc.models.ApplicationUser;
import com.familyconnect.fc.models.Role;
import com.familyconnect.fc.repository.RoleRepository;
import com.familyconnect.fc.repository.UserRepository;

@SpringBootApplication
public class FcApplication {
	public static void main(String[] args) {
		SpringApplication.run(FcApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			Role userRole = roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			ApplicationUser admin = new ApplicationUser("admin@admin.com","admin", passwordEncode.encode("123"), roles);

			userRepository.save(admin);

			initUsers(roleRepository, userRepository, passwordEncode,userRole);
		};
	}


	private void initUsers(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder ,Role userRole) {


		Set<Role> roles = new HashSet<>();
		roles.add(userRole);

		String[] names = {"halil", "hasan", "sulo","ibo"};
		String[] mails = {"tokibokit@gmail.com", "hasan@gmail.com", "slymnyilmaz67@gmail.com","ibo@gmail.com"};
		
		for(int i = 0; i < names.length; i++){
			ApplicationUser user = new ApplicationUser(mails[i], names[i], passwordEncoder.encode("123"), roles);
			userRepository.save(user);
		}

	}
}
