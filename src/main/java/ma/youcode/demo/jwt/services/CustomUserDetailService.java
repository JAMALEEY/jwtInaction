package ma.youcode.demo.jwt.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {

    // this method do the validation to see if the user exists
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals(("yasser"))){ // we can call the db using repository so that we can check the availability of the user inthe db
            return new User("yasser", "pass", new ArrayList<>());
        } else {
            // UsernameNotFoundException prebuilt method
            throw new UsernameNotFoundException("User doesn't exist !");
        }
    }
}
