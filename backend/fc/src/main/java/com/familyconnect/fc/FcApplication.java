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
			Role parentRole = roleRepository.save(new Role("PARENT"));
			Role childRole = roleRepository.save(new Role("CHILD"));


			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			ApplicationUser admin = new ApplicationUser("admin@admin.com","admin", passwordEncode.encode("123"), roles);

			userRepository.save(admin);

			initParents(roleRepository, userRepository, passwordEncode,parentRole);
			initChildren(roleRepository, userRepository, passwordEncode,childRole);

		};
	}


	private void initParents(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder ,Role parentRole) {


		Set<Role> roles = new HashSet<>();
		roles.add(parentRole);

		String[] names = {"halil", "hasan", "sulo","ibo"};
		String[] mails = {"tokibokit@gmail.com", "hasan@gmail.com", "slymnyilmaz67@gmail.com","ibo@gmail.com"};
		
		for(int i = 0; i < names.length; i++){
			ApplicationUser user = new ApplicationUser(mails[i], names[i], passwordEncoder.encode("123"), roles);
			userRepository.save(user);
		}

	}

	private void initChildren(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder ,Role childRole) {

		Set<Role> roles = new HashSet<>();
		roles.add(childRole);

		String[] names = {"cocuk1", "cocuk2", "cocuk3","cocuk4"};
		String[] mails = {"cocuk1@gmail.com" , "cocuk2@gmail.com","cocuk3@gmail.com" ,"cocuk4@gmail.com"};

		for(int i = 0; i < names.length; i++){
			ApplicationUser user = new ApplicationUser(mails[i], names[i], passwordEncoder.encode("123"), roles);
			userRepository.save(user);
		}

	}

	



}
