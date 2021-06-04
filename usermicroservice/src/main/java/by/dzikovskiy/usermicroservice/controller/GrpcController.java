package by.dzikovskiy.usermicroservice.controller;

import by.dzikovskiy.usermicroservice.security.annotation.UserAndAdminAuthorization;
import by.dzikovskiy.usermicroservice.service.GreeterGrpcService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class GrpcController {
    private final GreeterGrpcService greeterGrpcService;

    @GetMapping("/hello")
    @UserAndAdminAuthorization
    public String getHello(@RequestParam("name") final String name) {
        return greeterGrpcService.sendMessage(name);
    }
}
