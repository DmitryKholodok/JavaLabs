package com.kholodok.lab1.dao;

import com.kholodok.lab1.dao.exception.DAOException;
import com.kholodok.lab1.domain.book.Book;

import java.io.IOException;

public interface BookDAO {

    boolean addBook(Book book) throws DAOException;

    Book[] findBook(String title) throws DAOException;

    boolean deleteBook(int idBook) throws DAOException;

}

