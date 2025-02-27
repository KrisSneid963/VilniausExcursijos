package lt.techin.travel_agency.security;


import lt.techin.travel_agency.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImplementation implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email) //o postmana
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
