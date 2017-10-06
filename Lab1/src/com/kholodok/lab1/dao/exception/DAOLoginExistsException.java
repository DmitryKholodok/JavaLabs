package com.kholodok.lab1.dao.exception;

public class DAOLoginExistsException extends Exception{

    private static final long serialVersionUID = 3L;

    public DAOLoginExistsException(String message) {
        super(message);
    }

    public DAOLoginExistsException(String message, Exception e) {
        super(message, e);
    }

    public DAOLoginExistsException(Exception e) { super(e); }

}
