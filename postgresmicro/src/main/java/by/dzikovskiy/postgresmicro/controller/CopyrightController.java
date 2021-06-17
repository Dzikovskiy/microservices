package by.dzikovskiy.postgresmicro.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class CopyrightController {
    @Value("${text.copyright}")
    private String copyright;

    @GetMapping("/copyright")
    public String getCopyright(){
        return copyright;
    }
}
