package lt.techin.travel_agency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/token")
public class TokenController {

  private final JwtEncoder jwtEncoder;

  @Autowired
  public TokenController(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  @PostMapping
  public String token(Authentication authentication) {
    Instant currentTime = Instant.now();
    long plusSeconds = 360000L;

    String scope = authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(" "));

    JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(currentTime)
            .expiresAt(currentTime.plusSeconds(plusSeconds))
            .subject(authentication.getName())
            .claim("scope", scope)
            .build();

    return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
  }

}
