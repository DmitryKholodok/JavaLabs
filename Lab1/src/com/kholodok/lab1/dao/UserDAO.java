package com.kholodok.lab1.dao;

import com.kholodok.lab1.dao.exception.DAOException;
import com.kholodok.lab1.domain.user.Type;
import com.kholodok.lab1.domain.user.User;

public interface UserDAO {

    boolean register(User user) throws DAOException;
    Type logination(String login, String password) throws DAOException;
}
