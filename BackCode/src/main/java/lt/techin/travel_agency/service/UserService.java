package lt.techin.travel_agency.service;

import lt.techin.travel_agency.dto.user.UserMapper;
import lt.techin.travel_agency.dto.user.UserRequestDTO;
import lt.techin.travel_agency.model.Role;
import lt.techin.travel_agency.model.User;
import lt.techin.travel_agency.repository.RoleRepository;
import lt.techin.travel_agency.repository.UserRepository;
import lt.techin.travel_agency.validation.exception.EmailInUseException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User addUser(UserRequestDTO dto) {
    if (userRepository.existsByEmail(dto.email())) {
      throw new EmailInUseException("Email '" + dto.email() + "' was not found");
    }
    Role roleUser = roleRepository.findByName("ROLE_USER");
    User newUser = UserMapper.toEntity(dto);

    newUser.setPassword(passwordEncoder.encode(dto.password()));
    newUser.getRoles().add(roleUser);

    return userRepository.save(newUser);
  }
}
