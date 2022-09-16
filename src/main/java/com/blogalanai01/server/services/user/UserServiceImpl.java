package com.blogalanai01.server.services.user;
import org.springframework.stereotype.Service;

import com.blogalanai01.server.dtos.user.RegisterDTO;
import com.blogalanai01.server.models.User;
import com.blogalanai01.server.repositories.UserRepository;

import org.modelmapper.ModelMapper;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private ModelMapper modelmapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelmapper = modelMapper;
    }


    @Override
    public User addUser(RegisterDTO user){
        User existedUser = findUserByEmail(user.getEmail());
        if (existedUser != null){
            return null;
        }
        User newUser = this.modelmapper.map(user, User.class);
        this.userRepository.save(newUser);
        return newUser;
    }

    @Override
    public User findUserByEmail(String email){
        try{
            User user = this.userRepository.getUserByEmmail(email);
            return user;
        }
        catch(Error err){
            return null;
        }
    }

    @Override
    public User getUserById(String id){
        return this.userRepository.getUserById(id);
    }
    
}
