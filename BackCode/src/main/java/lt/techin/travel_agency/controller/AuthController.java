package lt.techin.travel_agency.controller;

import lt.techin.travel_agency.dto.travel.LoginResponseDTO;
import lt.techin.travel_agency.dto.user.UserRequestDTO;
import lt.techin.travel_agency.model.Role;
import lt.techin.travel_agency.model.User;
import lt.techin.travel_agency.repository.RoleRepository;
import lt.techin.travel_agency.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
// @CrossOrigin(origins = "*") // Nes jau yra parasyta WebConfig
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        String roleName = (request.role() != null && request.role().equalsIgnoreCase("ROLE_ADMIN"))
                ? "ROLE_ADMIN"
                : "ROLE_USER";

        Optional<Role> roleOpt = Optional.ofNullable(roleRepository.findByName(roleName));

        if (roleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Role " + roleName + " not found.");
        }

        user.setRoles(Collections.singletonList(roleOpt.get()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/login")
    public ResponseEntity<User> loginUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok().body(user);
    }

}

