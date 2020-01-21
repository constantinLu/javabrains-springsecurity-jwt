package com.springframework.springsecurityjwt;

import com.springframework.springsecurityjwt.models.AuthentificationRequest;
import com.springframework.springsecurityjwt.models.AuthentificationResponse;
import com.springframework.springsecurityjwt.services.MyUserDetailsService;
import com.springframework.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    MyUserDetailsService userDetailsService;


    JwtUtil jwtTokenUtil;

    public HelloResource(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping("/hello")
    public String home() {
        return "hello";
    }

    @PostMapping
    @RequestMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthentificationRequest authentificationRequest)
            throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken
                            (authentificationRequest.getUsername(), authentificationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect user name or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authentificationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthentificationResponse(jwt));
    }
}
