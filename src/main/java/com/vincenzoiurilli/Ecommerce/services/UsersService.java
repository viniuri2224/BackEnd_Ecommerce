package com.vincenzoiurilli.Ecommerce.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vincenzoiurilli.Ecommerce.dto.users.NewUserDTO;
import com.vincenzoiurilli.Ecommerce.dto.users.NewUserDTOResponse;
import com.vincenzoiurilli.Ecommerce.dto.users.UpdatedUserResponseDTO;
import com.vincenzoiurilli.Ecommerce.entities.Role;
import com.vincenzoiurilli.Ecommerce.entities.Status;
import com.vincenzoiurilli.Ecommerce.entities.Users;
import com.vincenzoiurilli.Ecommerce.exceptions.NotFoundException;
import com.vincenzoiurilli.Ecommerce.repositories.UserRepository;

import com.vincenzoiurilli.Ecommerce.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private MailgunSender mailgunSender;

    @Autowired
    private Cloudinary imageUploader;

    @Autowired
    private CartsService cartsService;

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    public NewUserDTOResponse save(NewUserDTO body) {

        Role role = Role.valueOf(body.role().toUpperCase());

        Users newUser = new Users(body.name(), body.surname(), body.email(), bcrypt.encode(body.password()), LocalDateTime.now(), Status.ACTIVE, role);

        Users savedUser = userRepository.save(newUser);

        this.createUserCart(savedUser);

        this.mailgunSender.sendRegistrationEmail(savedUser);

        return new NewUserDTOResponse(savedUser.getId());
    }

    public Users findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con email " + email + " non è stato trovato"));
    }

    public Users findById(UUID userId) {
        return this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    private void createUserCart(Users user){
        this.cartsService.newCarts(user);
    }

    public UpdatedUserResponseDTO findByIdAndUpdateUser(UUID userId, NewUserDTO body) {
        Users user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));

        user.setName(body.name());
        user.setSurname(body.surname());
        user.setEmail(body.email());
        user.setPassword(bcrypt.encode(body.password()));
        user.setRole(Role.valueOf(body.role().toUpperCase()));

        this.userRepository.save(user);

        return new UpdatedUserResponseDTO(user.getName(), user.getSurname(), user.getEmail(), user.getStatus().name(), user.getRole().name(), user.getProfileImageUrl());

    }

    public void findByIdAndDeleteUser(UUID userId) {
        Users user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
        this.userRepository.delete(user);
    }

    public String uploadProfilePicture(MultipartFile file, UUID ownerId) {

        Map options = ObjectUtils.asMap("folder", "eiot/avatars");

        try {

            Map result = imageUploader.uploader().upload(file.getBytes(), options);
            String imageUrl = (String) result.get("url");
            // ... qua salvo l'URL dentro il record dello user di riferimento
            Users owner = this.findById(ownerId);
            owner.setProfileImageUrl(imageUrl);
            this.userRepository.save(owner);
            return imageUrl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
