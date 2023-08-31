package org.adbms.usermanagement.service;

import lombok.RequiredArgsConstructor;
import org.adbms.usermanagement.dto.NewUserDTO;
import org.adbms.usermanagement.model.User;
import org.adbms.usermanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public void createUser(NewUserDTO newUserDTO) {
        User user = User.builder()
                .name(newUserDTO.getName())
                .email(newUserDTO.getEmail())
                .gender(newUserDTO.getGender())
                .telephone(newUserDTO.getTelephone())
                .address(newUserDTO.getAddress())
                .build();

        userRepository.save(user);
    }
}
