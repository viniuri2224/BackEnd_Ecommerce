package com.vincenzoiurilli.Ecommerce.services;

import com.vincenzoiurilli.Ecommerce.dto.auth.LoginDTO;
import com.vincenzoiurilli.Ecommerce.dto.auth.LoginResponseDTO;
import com.vincenzoiurilli.Ecommerce.dto.users.NewUserDTO;
import com.vincenzoiurilli.Ecommerce.dto.users.NewUserDTOResponse;
import com.vincenzoiurilli.Ecommerce.entities.Role;
import com.vincenzoiurilli.Ecommerce.entities.Status;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.exceptions.UnauthorizedException;
import com.vincenzoiurilli.Ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.vincenzoiurilli.Ecommerce.security.JWTTools;
import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTTools jwtTools;

    public NewUserDTOResponse createNewUser(NewUserDTO body) {
        return this.usersService.save(body);
    }

    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        Users user = usersService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), user.getPassword())) {
            return new LoginResponseDTO(jwtTools.createToken(user));
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
