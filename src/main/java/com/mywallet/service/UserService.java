package com.mywallet.service;

import com.mywallet.model.User;

public interface UserService {
    User registerUser(String username, String password);
    User loginUser(String username, String password);
}
