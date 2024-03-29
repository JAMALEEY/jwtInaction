package ma.youcode.demo.jwt.controller;

import ma.youcode.demo.jwt.model.JwtRequest;
import ma.youcode.demo.jwt.model.JwtResponse;
import ma.youcode.demo.jwt.model.UserModel;
import ma.youcode.demo.jwt.services.CustomUserDetailService;
import ma.youcode.demo.jwt.util.JwtUtil;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody
                                              UserModel userModel
    ) {
        UserModel userModel1 = customUserDetailService.register(userModel);
        ResponseEntity<UserModel> responseEntity = new ResponseEntity<>(userModel1, HttpStatus.CREATED);
    return responseEntity;
    }

// similar to theo ne in jwtConfig that is public to all users !s
    @PostMapping("/login")
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

    @GetMapping("/currentUser")
    public UserModel  getCurrentUser(Principal principal) {
              UserDetails userDetails =  this.customUserDetailService.loadUserByUsername(
                      principal.getName());
              return (UserModel) userDetails;
    }
}
