package lt.techin.travel_agency.util;


import lt.techin.travel_agency.model.Role;
import lt.techin.travel_agency.model.User;
import lt.techin.travel_agency.repository.RoleRepository;
import lt.techin.travel_agency.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitRolesAndAdmin {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public InitRolesAndAdmin(UserRepository userRepository, RoleRepository repository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public CommandLineRunner initRolesAndAd() {
    return args -> {

      if (roleRepository.count() == 0) {
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);
      }
      if (!userRepository.existsByEmail("admin@admin.com")) {
        User user = new User();
        user.setName("admin");
        user.setEmail("admin@admin.com");
        user.setPassword(passwordEncoder.encode("Something9!"));

        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        Role roleUser = roleRepository.findByName("ROLE_USER");

        user.getRoles().add(roleAdmin);
        user.getRoles().add(roleUser);

        userRepository.save(user);
      }


    };
  }
}
