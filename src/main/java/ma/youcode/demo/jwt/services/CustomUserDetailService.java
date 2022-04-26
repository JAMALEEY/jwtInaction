package ma.youcode.demo.jwt.services;

import ma.youcode.demo.jwt.entity.UserEntity;
import ma.youcode.demo.jwt.model.UserModel;
import ma.youcode.demo.jwt.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserModel register(UserModel userModel){
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userModel, userEntity);
        userEntity =  userRepository.save(userEntity);
        BeanUtils.copyProperties(userEntity, userModel);
        return userModel;
    }

    // this method do the validation to see if the user exists
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(
                userName
        );


        if(userEntity == null){ // we can call the db using repository so that we can check the availability of the user inthe db
            // UsernameNotFoundException prebuilt method
            throw new UsernameNotFoundException("User doesn't exist !");
        } else {
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(
                    userEntity, userModel
            );
            return userModel;
//            return new User("yasser", "pass", new ArrayList<>());
        }
    }
}
