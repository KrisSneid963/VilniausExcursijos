package lt.techin.travel_agency.controller;


import jakarta.validation.Valid;
import lt.techin.travel_agency.dto.user.UserMapper;
import lt.techin.travel_agency.dto.user.UserRequestDTO;
import lt.techin.travel_agency.dto.user.UserResponseDTO;
import lt.techin.travel_agency.model.User;
import lt.techin.travel_agency.service.UserService;
import lt.techin.travel_agency.util.WebUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/register")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRequestDTO dto) {
    User newUser = userService.addUser(dto);
    UserResponseDTO responseDTO = UserMapper.toDTO(newUser);

    URI location = WebUtil.createLocation("/{id}", newUser.getId());

    return ResponseEntity.created(location).body(responseDTO);
  }
}
