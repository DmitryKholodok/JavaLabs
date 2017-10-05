package com.kholodok.lab1.dao.impl;

import com.kholodok.lab1.dao.BookDAO;
import com.kholodok.lab1.dao.exception.DAOBookNotFoundException;
import com.kholodok.lab1.dao.exception.DAOException;
import com.kholodok.lab1.domain.book.Book;
import com.kholodok.lab1.domain.book.Type;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class FileBookDAO implements BookDAO {

    private static String fileBooks =
            "/Users/dmitrykholodok/java/university/Lab1/src/com/kholodok/lab1/files/Books";

    @Override
    public boolean addBook(Book book) throws DAOException {
        try {
            if (bookExists(book.getmName()))
                return false;
            BufferedWriter out = new BufferedWriter(new FileWriter(fileBooks, true));
            try {
                writeBookInfo(out, book);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new DAOException("", e);
        }
        return true;
    }

    @Override
    public Book[] findBook(String title) throws DAOException {
        Book[] books;
        try {
            books = getBooks(title);
        } catch (IOException e) {
            throw new DAOException(e);
        }
        return books;
    }

    @Override
    public boolean deleteBook(int idBook) throws DAOException {
        try {
            StringBuilder sb = getRemainingBooks(idBook);
            writeToFile(sb.toString());
        } catch (DAOBookNotFoundException e) {
            return false;
        } catch (IOException e) {
            throw new DAOException(e);
        }
        return true;
    }

    private StringBuilder getRemainingBooks(int id) throws IOException, DAOBookNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader(fileBooks));
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        try {
            String str;
            while ((str = in.readLine()) != null) {
                String[] arr = str.split(" ");
                if (id == Integer.valueOf(arr[0])) {
                    found = true;
                    continue;
                }
                sb.append(str + "\n");
            }
        } finally {
            in.close();
        }

        if (!found)
            throw new DAOBookNotFoundException("Book is not found");
        return sb;
    }

    private void writeToFile(String text) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(fileBooks, false));
        try {
            out.write(text);
        } finally {
            out.close();
        }
    }

    private boolean bookExists(String name) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileBooks));
        String str;
        try {
            while((str = in.readLine()) != null) {
                String[] arr = str.split(" ");
                if (name.equals(arr[1]))
                    return true;
            }
            return false;
        } finally {
            in.close();
        }
    }

    private void writeBookInfo(BufferedWriter out, Book book) throws IOException {
        out.write(String.valueOf(book.getId()));
        out.write(" ");
        out.write(book.getmName());
        out.write(" ");
        out.write(book.getMtype().toString());
        out.write("\n");
    }

    private Book[] getBooks(String title) throws IOException {

        List<Book> bookList = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(fileBooks));
        try {
            String str;
            while ((str = in.readLine()) != null) {
                String[] arr = str.split(" ");
                if (title.equals(arr[1])) {
                    Book book = formBook(arr);
                    bookList.add(book);
                }
            }
        } finally {
            in.close();
        }

        if (bookList.isEmpty()) return null;

        Book[] books = new Book[bookList.size()];
        return bookList.toArray(books);

    }

    private Book formBook(String[] bookParts) {
        Book book = new Book();
        book.setId(Integer.valueOf(bookParts[0]));
        book.setmName(bookParts[1]);
        book.setMtype(Type.valueOf(bookParts[2]));
        return book;
    }


}
