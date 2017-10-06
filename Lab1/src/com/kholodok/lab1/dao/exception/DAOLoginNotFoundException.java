package com.kholodok.lab1.dao.exception;

public class DAOLoginNotFoundException extends Exception {

    private static final long serialVersionUID = 4L;

    public DAOLoginNotFoundException(String message) {
        super(message);
    }

    public DAOLoginNotFoundException(String message, Exception e) {
        super(message, e);
    }

    public DAOLoginNotFoundException(Exception e) { super(e); }
}
