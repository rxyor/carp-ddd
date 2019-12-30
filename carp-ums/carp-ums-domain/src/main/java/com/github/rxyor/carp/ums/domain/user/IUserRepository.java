package com.github.rxyor.carp.ums.domain.user;

import java.util.List;

public interface IUserRepository {

    User find(String username);

    List<User> findAll();

    User save(User user);
}