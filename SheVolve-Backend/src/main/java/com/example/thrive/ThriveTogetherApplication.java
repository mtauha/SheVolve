package com.example.thrive;

import com.example.thrive.user.model.request.JoinRequest;
import com.example.thrive.user.model.request.RequestStatus;
import com.example.thrive.user.model.request.RequestType;
import com.example.thrive.user.repo.UserRepository;
import com.example.thrive.user.model.*;
import com.example.thrive.user.repo.request.JoinRequestRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ThriveTogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThriveTogetherApplication.class, args);
	}

	@Bean
	public CommandLineRunner seedUsers(
			UserRepository userModelRepository,
			JoinRequestRepo joinRequestRepo,
			BCryptPasswordEncoder passwordEncoder
	) {
		return args -> {
			// Clear existing data (optional for development)
			userModelRepository.deleteAll();

			// Create sample NGO
			NGOModel ngo1 = NGOModel.builder()
					.firstName("Helping")
					.lastName("Hands")
					.username("helpinghands")
					.email("helpinghands@ngo.org")
					.password(passwordEncoder.encode("password123"))
					.userRole(UserRole.ROLE_NGO)
					.description("A non-profit organization focusing on community development.")
					.build();
			NGOModel ngo2 = NGOModel.builder()
					.firstName("All")
					.lastName("Together")
					.username("alltogether")
					.email("alltogether@ngo.org")
					.password(passwordEncoder.encode("password123"))
					.userRole(UserRole.ROLE_NGO)
					.description("A non-profit organization focusing on community development.")
					.build();

			userModelRepository.saveAll(List.of(ngo1));
			userModelRepository.save(ngo2);

			// Create sample Mentors
			MentorModel mentor1 = MentorModel.builder()
					.firstName("Jane")
					.lastName("Doe")
					.username("janedoe")
					.email("jane.doe@mentor.org")
					.password(passwordEncoder.encode("password123"))
					.userRole(UserRole.ROLE_MENTOR)
					.build();

			MentorModel mentor2 = MentorModel.builder()
					.firstName("John")
					.lastName("Smith")
					.username("johnsmith")
					.email("john.smith@mentor.org")
					.password(passwordEncoder.encode("password123"))
					.userRole(UserRole.ROLE_MENTOR)
					.build();

			userModelRepository.save(mentor1);
			userModelRepository.save(mentor2);

			ngo1.setMentors(Arrays.asList(mentor1, mentor2));

			// Create sample Entrepreneurs
			EntrepreneurModel entrepreneur1 = EntrepreneurModel.builder()
					.firstName("Alice")
					.lastName("Brown")
					.username("alicebrown")
					.email("alice.brown@entrepreneur.org")
					.password(passwordEncoder.encode("password123"))
					.userRole(UserRole.ROLE_ENTREPRENEUR)
					.build();

			EntrepreneurModel entrepreneur2 = EntrepreneurModel.builder()
					.firstName("Bob")
					.lastName("Johnson")
					.username("bobjohnson")
					.email("bob.johnson@entrepreneur.org")
					.password(passwordEncoder.encode("password123"))
					.userRole(UserRole.ROLE_ENTREPRENEUR)
					.build();

			userModelRepository.save(entrepreneur1);
			userModelRepository.save(entrepreneur2);

			// Create sample Admin
			AdminModel admin = AdminModel.builder()
					.firstName("Super")
					.lastName("Admin")
					.username("admin")
					.email("admin@system.org")
					.password(passwordEncoder.encode("adminpassword"))
					.userRole(UserRole.ROLE_ADMIN)
					.build();

			// Save all users
			userModelRepository.save(admin);

			JoinRequest request1 = JoinRequest.builder()
					.requester(mentor1)
					.responder(ngo1)
					.requestType(RequestType.REQUEST_BY_MENTOR_TO_NGO)
					.build();

			JoinRequest request2 = JoinRequest.builder()
					.requester(entrepreneur1)
					.responder(ngo1)
					.requestType(RequestType.REQUEST_BY_ENTREPRENEUR_TO_NGO)
					.build();

			JoinRequest request3 = JoinRequest.builder()
					.requester(mentor2)
					.responder(ngo2)
					.requestType(RequestType.REQUEST_BY_MENTOR_TO_NGO)
					.build();

			JoinRequest request4 = JoinRequest.builder()
					.requester(entrepreneur2)
					.responder(ngo2)
					.requestType(RequestType.REQUEST_BY_ENTREPRENEUR_TO_NGO)
					.build();

			joinRequestRepo.save(request1);
			joinRequestRepo.save(request2);
			joinRequestRepo.save(request3);
			joinRequestRepo.save(request4);

			System.out.println("Database seeded with sample users!");
		};
	}

}
