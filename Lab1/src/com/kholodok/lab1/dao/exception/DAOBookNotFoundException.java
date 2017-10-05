package com.kholodok.lab1.dao.exception;

public class DAOBookNotFoundException extends Exception {

    private static final long serialVersionUID = 2L;

    public DAOBookNotFoundException(String message) { super(message); }

    public DAOBookNotFoundException(String message, Exception e) { super(message, e); }

    public DAOBookNotFoundException(Exception e) { super(e); }
}
