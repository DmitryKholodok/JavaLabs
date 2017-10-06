package com.kholodok.lab1.dao.impl;

import com.kholodok.lab1.dao.UserDAO;
import com.kholodok.lab1.dao.exception.DAOException;
import com.kholodok.lab1.dao.exception.DAOLoginExistsException;
import com.kholodok.lab1.dao.exception.DAOLoginNotFoundException;
import com.kholodok.lab1.domain.user.Type;
import com.kholodok.lab1.domain.user.User;

import java.io.*;

public class FileUserDAO implements UserDAO {

    private static String fileUsers =
            "/Users/dmitrykholodok/java/university/Lab1/src/com/kholodok/lab1/files/Users";

    @Override
    public boolean register(User user) throws DAOException {
        try {
            if (userLoginExists(user.getName()))
                throw new DAOLoginExistsException("Login " + user.getName() + " exists yet!");
            addUserToFile(user);
        } catch (IOException | DAOLoginExistsException e) {
            throw new DAOException(e);
        }
        return true;
    }

    private void addUserToFile(User user) throws IOException {
        StringBuilder sb = getUserWorkString(user);
        writeToFile(sb.toString());
    }

    private StringBuilder getUserWorkString(User user) {
        return new StringBuilder()
                .append(user.getName()).append(" ")
                .append(user.getPassword()).append(" ")
                .append(user.getType().toString()).append("\n");
    }

    private void writeToFile(String s) throws IOException {
        BufferedWriter bf = new BufferedWriter(new FileWriter(fileUsers, true));;
        try {
            bf.write(s);
        } finally {
            bf.close();
        }
    }

    private boolean userLoginExists(String login) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileUsers));
        String str;
        boolean exists = false;
        try {
            while ((str = bf.readLine()) != null) {
                String[] arr = str.split(" ");
                if (arr[0].equals(login)) {
                    exists = true;
                    break;
                }
            }
        } finally {
            bf.close();
        }
        if (exists) return true;
        return false;
    }

    private String[] getUserWorkArr(String login) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileUsers));
        String str;
        boolean exists = false;
        try {
            while ((str = bf.readLine()) != null) {
                String[] arr = str.split(" ");
                if (arr[0].equals(login)) {
                    return arr;
                }
            }
        } finally {
            bf.close();
        }
        return null;
    }

    @Override
    public Type logination(String login, String password) throws DAOException {
        String[] userWorkArr;
        try {
            userWorkArr = getUserWorkArr(login);
            if (userWorkArr == null)
                throw new DAOLoginNotFoundException("Login " + login + " does not exists!");
        } catch (IOException | DAOLoginNotFoundException e) {
            throw new DAOException(e);
        }

        if (userWorkArr[1].equals(password))
            return Type.valueOf(userWorkArr[2]);
        throw new DAOException("Password is not valid!");
    }
}
