package by.dzikovskiy.usermicroservice.controller;


import by.dzikovskiy.usermicroservice.security.entity.UserAuth;
import by.dzikovskiy.usermicroservice.security.entity.request.AuthRequest;
import by.dzikovskiy.usermicroservice.security.entity.request.RegistrationRequest;
import by.dzikovskiy.usermicroservice.security.entity.response.AuthResponse;
import by.dzikovskiy.usermicroservice.security.service.JwtUtils;
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
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

}