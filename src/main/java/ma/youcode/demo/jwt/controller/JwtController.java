package ma.youcode.demo.jwt.controller;

import ma.youcode.demo.jwt.model.JwtRequest;
import ma.youcode.demo.jwt.model.JwtResponse;
import ma.youcode.demo.jwt.services.CustomUserDetailService;
import ma.youcode.demo.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailService customUserDetailService;


// similar to theo ne in jwtConfig that is public to all users !s
    @PostMapping("/generateToken")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest){
        // model
//        authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtRequest.getUserName(),
                        jwtRequest.getPassword()
                )
        );
        UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequest.getUserName());

//        we call the jwtUtil.generate token this last its taking the userDetails service
        String jwtToken = jwtUtil.generateToken(userDetails); // this returns a token / so we sote it in a variable jwtoken

        JwtResponse jwtResponse = new JwtResponse(jwtToken);
//        return ResponseEntity.ok(jwtResponse);
        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
    }
}
