package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.model.AuthProvider;
import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.payload.graphql.request.SignupRequest;
import ir.manoosheh.mylinkedin.payload.graphql.response.SignupResponse;
import ir.manoosheh.mylinkedin.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Autowired
    final UserRepository userRepository;
    @Autowired
    final PasswordEncoder passwordEncoder;
//    final UploadService uploadService;


    public User getUserByID(Long userId) {
        return userRepository.findById(userId).get();//.orElseThrow();
    }

    public User getUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public boolean save(User user) {
        userRepository.save(user);
        return true;
    }


    public String getUsername() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (principal instanceof User) {
//            username = ((User) principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
        return username;
    }

    //public SignupResponse save(SignupRequest signupRequest, DataFetchingEnvironment environment) throws Exception {
    public SignupResponse save(SignupRequest signupRequest) throws Exception {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            log.error(" Someone tried signing up using a taken email.");
//            throw new IsExistException("User is exist!", signupRequest.getEmail(), "save");
            return new SignupResponse(false, "Account already exits", signupRequest.getEmail(), signupRequest.getIsCompany());

        } else {
            User user = new User();
            user.setName(signupRequest.getEmail());
            user.setEmail(signupRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setIsCompany(signupRequest.getIsCompany());
            user.setProvider(AuthProvider.local);
//            user.setAuthorities(new ArrayList<>());
            user.setEnabled(false);

            if (user.getIsCompany())
                user.setUserProfile(null);
//            else
//                user.setCompanyProfile(null);

//            if (environment.getArgument("file")!=null) {
//                String avatar = uploadService.upload(environment);
//                user.setAvatar(avatar);
//            }
            userRepository.save(user);
            log.info("'{}' is added to user repository", user.getUsername());
            return new SignupResponse(true, "Registration was successful", user.getEmail(), user.getIsCompany());
        }
    }

}
