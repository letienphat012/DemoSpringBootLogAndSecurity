package tma.tft.phat.ss.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import tma.tft.phat.ss.dao.UserDAO;
import tma.tft.phat.ss.security.jwt.AuthenticationRequest;
import tma.tft.phat.ss.security.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDAO userDAO;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody AuthenticationRequest data) {
        
        try {
            String username = data.getEmail();
            logger.info("User sign in with email {}",username);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));

            String token = jwtTokenProvider.createToken(username, userDAO.findUserByEmail(username).getRoleStrings());
            Map<Object, Object> model = new HashMap<>();

            logger.info("Created Token: {}",token);
            model.put("username", username);
            model.put("token", token);

            return ok(model);
        } catch (AuthenticationException e) {
            logger.debug(e.getMessage(), e);
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}
