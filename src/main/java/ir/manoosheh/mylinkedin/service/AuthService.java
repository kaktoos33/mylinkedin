package ir.manoosheh.mylinkedin.service;

import ir.manoosheh.mylinkedin.model.User;
import ir.manoosheh.mylinkedin.payload.graphql.request.LoginRequest;
import ir.manoosheh.mylinkedin.payload.graphql.response.LoginResponse;
import ir.manoosheh.mylinkedin.payload.graphql.response.UserResponse;
import ir.manoosheh.mylinkedin.repository.UserRepository;
import ir.manoosheh.mylinkedin.security.TokenProvider;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    @Autowired
    final AuthenticationManager authenticationManager;

    @Autowired
    final private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = tokenProvider.createToken(authentication);

            User user = userRepository.findByEmail(authentication.getName()).orElse(null);


            assert user != null;
            if (!user.getIsCompany() && !(user.getUserProfile() == null)) {
                return new LoginResponse(true, "Tokens have been generated",
                        accessToken, "refreshToken",
                        new UserResponse(user.getId().toString(), user.getIsCompany(),
                                user.getUserProfile().getFirstName() + " " + user.getUserProfile().getLastName(),
                                user.getUserProfile().getDescription(), true));
            } else
                return new LoginResponse(true, "Tokens have been generated",
                        accessToken, "refreshToken",
                        new UserResponse(user.getId().toString(), user.getIsCompany(), false));

        } catch (Exception e) {
            log.error("'{}' has failed to logged in.", loginRequest.getEmail());
            return new LoginResponse(false, e.getMessage(),
                    "FAILED", "FAILED",
                    new UserResponse("0", null, false));
        }


//        try {
//            Authentication authenticate = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//            user = (User) authenticate.getPrincipal();
//            String accessToken = jwtTokenUtil.generateToken(user);
//            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
//            log.info("'{}' has logged in.", user.getUsername());
//            if (user.getIsCompany() && !(user.getCompanyProfile() == null)) {
//                return new LoginResponse(true, "Tokens have been generated",
//                        accessToken, refreshToken.getToken(),
//                        new UserResponse(user.getId().toString(), user.getIsCompany(),
//                                user.getCompanyProfile().getName(), user.getCompanyProfile().getDescription(), true));
//            } else if (!user.getIsCompany() && !(user.getUserProfile() == null)) {
//                return new LoginResponse(true, "Tokens have been generated",
//                        accessToken, refreshToken.getToken(),
//                        new UserResponse(user.getId().toString(), user.getIsCompany(),
//                                user.getUserProfile().getFirstName() + " " + user.getUserProfile().getLastName(),
//                                user.getUserProfile().getDescription(), true));
//            } else
//                return new LoginResponse(true, "Tokens have been generated",
//                        accessToken, refreshToken.getToken(),
//                        new UserResponse(user.getId().toString(), user.getIsCompany(), false));
//        } catch (Exception e) {
//            log.error("'{}' has failed to logged in.", loginRequest.getEmail());
//            return new LoginResponse(false, e.getMessage(),
//                    "FAILED", "FAILED",
//                    new UserResponse("0", null, false));
//        }
    }


}