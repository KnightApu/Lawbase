package com.adhocmaster.login;

import com.adhocmaster.mongo.user.User;

import login.LoginDTO;

public interface LoginFactory {
    
    LoginAdapter getLoginAdapter();
    LoginDTO getLoginDTO();
    User getUser();

}
