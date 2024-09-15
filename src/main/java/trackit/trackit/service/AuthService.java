package trackit.trackit.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import trackit.trackit.dto.SignUpDTO;
import trackit.trackit.entity.AppUser;
import trackit.trackit.repository.AppUserRepository;
import trackit.trackit.utils.TokenUtils;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository repository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser signup(SignUpDTO signupDTO) {
        if (repository.existsByUsername(signupDTO.getUsername()) || repository.existsByEmail(signupDTO.getEmail())) {
            throw new IllegalArgumentException("Email or Username already exist.");
        }

        AppUser user = new AppUser();
        user.setUsername(signupDTO.getUsername());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword())); // Hash the password

        // Save user to the database
        return repository.save(user);
    }

    public String generateToken(AppUser appUser) {
        return tokenUtils.generateToken(appUser); // Assume you have a method to generate JWT token
    }
}
