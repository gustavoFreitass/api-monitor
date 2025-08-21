package br.com.firmino.api_monitor.user;


import br.com.firmino.api_monitor.profile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User resisterUser(User user){
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        UserProfile userProfile = new UserProfile();
        user.setUserProfile(userProfile);
        userProfile.setUser(user);

        return userRepository.save(user);
    }
}
