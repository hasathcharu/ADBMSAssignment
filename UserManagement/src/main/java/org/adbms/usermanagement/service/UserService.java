package org.adbms.usermanagement.service;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import lombok.RequiredArgsConstructor;
import org.adbms.usermanagement.dto.NewUserDTO;
import org.adbms.usermanagement.dto.UserDetailsDTO;
import org.adbms.usermanagement.dto.UsersResponse;
import org.adbms.usermanagement.exception.RestException;
import org.adbms.usermanagement.model.User;
import org.adbms.usermanagement.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    public void createUser(NewUserDTO newUserDTO) {

        User checkUser = userRepository.findByEmail(newUserDTO.getEmail()).orElse(null);
        if(checkUser!=null){
            throw new RestException(HttpStatus.CONFLICT, "User exists");
        }
        User user = User.builder()
                .name(newUserDTO.getName())
                .email(newUserDTO.getEmail())
                .gender(newUserDTO.getGender())
                .telephone(newUserDTO.getTelephone())
                .address(newUserDTO.getAddress())
                .build();

        userRepository.save(user);
    }

    public List<UsersResponse> getAllUsers() {
        List<User>users=userRepository.findAll();

        return users.stream().map(this::mapToUsersResponse).toList();
    }

    private UsersResponse mapToUsersResponse(User user) {
        return UsersResponse.builder()
                .id(user.getId().toString())
                .name(user.getName())
                .address(user.getAddress())
                .email(user.getEmail())
                .gender(user.getGender())
                .telephone(user.getTelephone())
                .build();
    }


    public UserDetailsDTO getUserDetails(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user==null){
            throw new RestException(HttpStatus.NOT_FOUND, "User not found");
        }
            return mapToUserDetailsDTO(user);
    }


    private UserDetailsDTO mapToUserDetailsDTO(User user){
        return UserDetailsDTO.builder()
                .name(user.getName())
                .address(user.getAddress())
                .email(user.getEmail())
                .telephone(user.getTelephone())
                .build();
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user==null){
            throw new RestException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteUserByEmail(email);
    }


    public void updateUsers(NewUserDTO newUserDTO) {
        User user = userRepository.findByEmail(newUserDTO.getEmail()).orElse(null);
        if(user==null){
            throw new RestException(HttpStatus.NOT_FOUND, "User not found");
        }
            user.setName(newUserDTO.getName());
            user.setEmail(newUserDTO.getEmail());
            user.setAddress(newUserDTO.getAddress());
            user.setTelephone(newUserDTO.getTelephone());
            user.setGender(newUserDTO.getGender());
            userRepository.save(user);
    }
}
