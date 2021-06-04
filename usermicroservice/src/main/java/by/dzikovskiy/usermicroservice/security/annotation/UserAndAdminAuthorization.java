package by.dzikovskiy.usermicroservice.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasAnyRole(T(by.dzikovskiy.usermicroservice.security.entity.Role).ROLE_ADMIN," +
        "T(by.dzikovskiy.usermicroservice.security.entity.Role).ROLE_USER)")
public @interface UserAndAdminAuthorization {
}