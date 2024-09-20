package trackit.trackit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trackit.trackit.dto.LoginDO;
import trackit.trackit.dto.SignUpDTO;
import trackit.trackit.entity.AppUser;
import trackit.trackit.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpDTO signUpDTO) {
        System.out.println("I am a chosen one, amen ================================> ");
        AppUser newUser = authService.signup(signUpDTO);
        String token = authService.generateToken(newUser);
        return ResponseEntity.ok(new AuthResponse(newUser, token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> postMethodName(@RequestBody LoginDO loginDO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDO.getEmail(), loginDO.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails authenticatedUser = (UserDetails) authentication.getPrincipal();
        AppUser appUser = new AppUser();
        appUser.setUsername(authenticatedUser.getUsername());
        appUser.setEmail(loginDO.getEmail());

        String token = authService.generateToken(appUser);
        return ResponseEntity.ok(new AuthResponse(appUser, token));
    }

    // Custom response class for signup
    public static class AuthResponse {
        private AppUser appUser;
        private String token;

        public AppUser getAppUser() {
            return appUser;
        }

        public void setAppUser(AppUser appUser) {
            this.appUser = appUser;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public AuthResponse(AppUser appUser, String token) {
            this.appUser = appUser;
            this.token = token;
        }
    }

}