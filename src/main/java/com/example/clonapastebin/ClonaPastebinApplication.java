package com.example.clonapastebin;

import com.example.clonapastebin.entity.Content;
import com.example.clonapastebin.entity.User;
import com.example.clonapastebin.repository.ContentRepository;
import com.example.clonapastebin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"service", "com.example.clonapastebin"})
public class ClonaPastebinApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContentRepository contentRepository;

	public static void main(String[] args) {

		SpringApplication.run(ClonaPastebinApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User user = new User();
		user.setPassword("test2");
		user.setUsername("George2");

		Content content = new Content();
		content.setContent("I must finish my project2");
		user.getContentList().add(content);

		contentRepository.save(content);
		userRepository.save(user);
	}
}