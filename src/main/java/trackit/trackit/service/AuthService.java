package trackit.trackit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import trackit.trackit.dto.SignUpDTO;
import trackit.trackit.entity.AppUser;
import trackit.trackit.repository.AppUserRepository;
import trackit.trackit.utils.TokenUtils;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser signup(SignUpDTO signupDTO) {
        if (userRepository.existsByUsername(signupDTO.getUsername())) {
            // throw new IllegalArgumentException("Email or Username already exist.");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        if (userRepository.existsByEmail(signupDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(signupDTO.getUsername());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword())); // Hash the password

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            var userObj = user.get();
            return User.builder().username(userObj.getUsername()).password(userObj.getPassword()).build();
        } else {
            throw new UsernameNotFoundException(email);
        }
    }

    public String generateToken(AppUser appUser) {
        return tokenUtils.generateToken(appUser);
    }
}
