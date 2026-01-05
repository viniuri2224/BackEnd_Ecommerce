package com.vincenzoiurilli.Ecommerce.controllers;

import com.vincenzoiurilli.Ecommerce.dto.auth.LoginDTO;
import com.vincenzoiurilli.Ecommerce.dto.auth.LoginResponseDTO;
import com.vincenzoiurilli.Ecommerce.dto.users.NewUserDTO;
import com.vincenzoiurilli.Ecommerce.dto.users.NewUserDTOResponse;
import com.vincenzoiurilli.Ecommerce.exceptions.ValidationException;
import com.vincenzoiurilli.Ecommerce.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserDTOResponse createNewUser(@RequestBody @Validated NewUserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }

        return this.authService.createNewUser(body);
    }

    @GetMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        return this.authService.login(body);

    }

}
