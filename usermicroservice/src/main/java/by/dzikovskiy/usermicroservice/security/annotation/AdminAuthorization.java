package by.dzikovskiy.usermicroservice.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasRole(T(by.dzikovskiy.usermicroservice.security.entity.Role).ROLE_ADMIN)")
public @interface AdminAuthorization {
}