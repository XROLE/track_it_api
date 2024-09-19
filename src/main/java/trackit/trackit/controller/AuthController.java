package trackit.trackit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<?> signup(@RequestBody SignUpDTO signUpDTO){
        System.out.println("I am a chosen one, amen ================================> ");
        AppUser newUser = authService.signup(signUpDTO);
        String token = authService.generateToken(newUser);
        return ResponseEntity.ok(new SignupResponse(newUser, token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDO> postMethodName(@RequestBody LoginDO loginDO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDO.getEmail(), loginDO.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok().build();
    }
    
   
     // Custom response class for signup
     public static class SignupResponse {
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

        public SignupResponse(AppUser appUser, String token) {
            this.appUser = appUser;
            this.token = token;
        }
    }
    
}