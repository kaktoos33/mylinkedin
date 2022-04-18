package ir.manoosheh.mylinkedin.controller;

import ir.manoosheh.mylinkedin.exception.ResourceNotFoundException;
import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.repository.UserRepository;
import ir.manoosheh.mylinkedin.security.CurrentUser;
import ir.manoosheh.mylinkedin.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}

