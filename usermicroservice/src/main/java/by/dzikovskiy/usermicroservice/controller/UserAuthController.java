package by.dzikovskiy.usermicroservice.controller;


import by.dzikovskiy.usermicroservice.security.entity.RefreshToken;
import by.dzikovskiy.usermicroservice.security.entity.UserAuth;
import by.dzikovskiy.usermicroservice.security.entity.request.AuthRequest;
import by.dzikovskiy.usermicroservice.security.entity.request.RegistrationRequest;
import by.dzikovskiy.usermicroservice.security.entity.request.TokenRefreshRequest;
import by.dzikovskiy.usermicroservice.security.entity.response.AuthResponse;
import by.dzikovskiy.usermicroservice.security.exception.TokenRefreshException;
import by.dzikovskiy.usermicroservice.security.service.JwtUtils;
import by.dzikovskiy.usermicroservice.security.service.RefreshTokenService;
import by.dzikovskiy.usermicroservice.security.service.UserDetailsImpl;
import by.dzikovskiy.usermicroservice.security.service.UserDetailsServiceImpl;
import by.dzikovskiy.usermicroservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserAuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserAuth user = new UserAuth();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        user.addRole(registrationRequest.getRole());
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(loginRequest.getLogin());
        String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new AuthResponse(jwt, refreshToken.getToken()));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateJwtToken(user.getLogin());
                    return ResponseEntity.ok(new AuthResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

}