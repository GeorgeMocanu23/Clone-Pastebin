package com.example.clonapastebin.controller;

import com.example.clonapastebin.entity.Content;
import com.example.clonapastebin.entity.User;
import com.example.clonapastebin.repository.ContentRepository;
import com.example.clonapastebin.repository.UserRepository;
import com.example.clonapastebin.request.AddContentRequest;
import com.example.clonapastebin.request.AddUserRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pastebin")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public UserController(UserRepository userRepository, ContentRepository contentRepository) {
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable ObjectId userId) {
        return userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }

    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AddUserRequest userRequest) {
        User user = userService.findByUsername(userRequest.getUsername());
        if (user == null || !user.getPassword().equals(userRequest.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username");
        }
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AddUserRequest userRequest) {
        User existingUser = userRepository.findByUsername(userRequest.getUsername());
        if(existingUser != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<String> getUserIdByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok(user.getId().toString());
    }

    @PostMapping("/{userId}/content")
    public ResponseEntity<String> addContent(@PathVariable ObjectId userId, @RequestBody AddContentRequest contentRequest) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Content content = new Content();
        content.setContent(contentRequest.getContent());
        user.getContentList().add(content);
        contentRepository.save(content);
        userRepository.save(user);
        return ResponseEntity.ok(content.getId().toString());
    }

    @DeleteMapping("{userId}/content/{contentId}")
    public void deleteContent(@PathVariable ObjectId userId, @PathVariable ObjectId contentId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Content content = contentRepository.findById(contentId).orElseThrow(NoSuchElementException::new);
        user.getContentList().remove(content);
        contentRepository.delete(content);
        user.getContentList().removeIf(c -> c.getId().equals(contentId));
        userRepository.save(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable ObjectId userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Content> contentList = user.getContentList();
        for (Content content : contentList) {
            contentRepository.delete(content);
        }
        userRepository.delete(user);
    }

    @GetMapping("/{userId}/content")
    public List<Content> getUserContentById(@PathVariable ObjectId userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return user.getContentList();
    }

    @GetMapping("/{userId}/contents")
    public List<String> getContentIdsByUser(@PathVariable ObjectId userId) {
        List<Content> userContents = userRepository.findById(userId).orElseThrow(NoSuchElementException::new).getContentList();
        return userContents.stream().map(Content::getId).map(ObjectId::toString).collect(Collectors.toList());
    }
}