package br.com.firmino.api_monitor.user;


import br.com.firmino.api_monitor.dto.UserDTO;
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

    public UserDTO resisterUser(User user){
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        UserProfile userProfile = new UserProfile();
        user.setUserProfile(userProfile);
        userProfile.setUser(user);

        User savedUser = userRepository.save(user);

        return convertToDto(savedUser);
    }

    private UserDTO convertToDto(User user){
        UserDTO dto =new UserDTO();
        dto.setId(user.getId());
        dto.setUsername((user.getUsername()));
        dto.setEmail(user.getEmail());

        if (user.getUserProfile() != null){
            dto.setDisplayName(user.getUserProfile().getDisplayName());
        }
        return dto;
    }
}
