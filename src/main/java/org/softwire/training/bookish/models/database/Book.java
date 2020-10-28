package org.softwire.training.bookish.models.database;

import org.jdbi.v3.core.Jdbi;

import java.util.Scanner;

public class Book {
    public void deleteBook(Scanner sc, Jdbi jdbi){
        System.out.println("What is the name of the book?: ");
        String bookName = sc.nextLine();
        System.out.println("And what is the books author?; ");
        String author = sc.nextLine();
        jdbi.withHandle(handle -> handle.execute("DELETE FROM books WHERE(bookName = ? && bookAuthor = ?)",bookName,author));
    }

    public void addBook(Scanner sc,Jdbi jdbi){
        System.out.println("What is the name of the book?: ");
        String bookName = sc.nextLine();
        System.out.println("And what is the books author?; ");
        String author = sc.nextLine();
        jdbi.withHandle(handle -> handle.execute("INSERT INTO books(bookName,bookAuthor) VALUES (?,?)",bookName,author));
    }

}
