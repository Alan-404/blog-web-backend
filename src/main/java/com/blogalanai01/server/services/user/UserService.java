package com.blogalanai01.server.services.user;

import com.blogalanai01.server.dtos.user.RegisterDTO;
import com.blogalanai01.server.models.User;

public interface UserService {
    public User addUser(RegisterDTO  user);
    public User findUserByEmail(String email);
    public User getUserById(String id);
}
