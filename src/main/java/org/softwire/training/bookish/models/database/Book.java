package org.softwire.training.bookish.models.database;

import org.jdbi.v3.core.Jdbi;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Book {
    public void deleteBook(Scanner sc, Jdbi jdbi){
        String bookName = getQuestionAnswer(sc,"What is the name of the book?");
        String author = getQuestionAnswer(sc,"And what is the books author?");
        String deleteType = getQuestionAnswer(sc,"Delete copies (1) or Delete entire book (2) ?");
        if (deleteType.equals("1")){
            String copies = getQuestionAnswer(sc,"How many copies?");
            jdbi.withHandle(handle->
                    handle.execute("UPDATE books SET bookCopies = bookCopies - ? WHERE (bookName = ? && bookAuthor = ?)",
                            copies,bookName,author ));
        }else if (deleteType.equals("2")){
            jdbi.withHandle(handle ->
                    handle.execute("DELETE FROM books WHERE(bookName = ? && bookAuthor = ?)",
                            bookName,author));
        }
    }

    public void addBook(Scanner sc,Jdbi jdbi){
        String addType = getQuestionAnswer(sc,"Do you to add a copy (1) or a new book (2) ?");
        if (addType.equals("1")){
            addCopy(sc, jdbi);
        }else if (addType.equals("2")){
            addNewBook(sc, jdbi, "INSERT INTO books(bookName,bookAuthor) VALUES (?,?)");
        }
    }

    private void addNewBook(Scanner sc, Jdbi jdbi, String s) {
        String bookName = getQuestionAnswer(sc, "What is the name of the book?");
        String author = getQuestionAnswer(sc,"And What is the books author?");
        jdbi.withHandle(handle -> handle.execute(s, bookName, author));
    }

    private void addCopy(Scanner sc, Jdbi jdbi) {
        String bookId = getBookId(sc, jdbi);
        String copies = getQuestionAnswer(sc,"How many copies do you want to add?");
        jdbi.withHandle(handle ->
                handle.execute("UPDATE books SET bookCopies = bookCopies + ? WHERE (bookId = ?)",
                        Integer.valueOf(copies),Integer.valueOf(bookId)));
    }


    public void editBook(Scanner sc, Jdbi jdbi){
        String bookId = getBookId(sc, jdbi);
        String bookName = getQuestionAnswer(sc,"What is the name of the book?");
        String author = getQuestionAnswer(sc,"And what is the books author?");
        jdbi.withHandle(handle ->
                handle.execute("UPDATE books SET bookName = ?,bookAuthor = ? WHERE (bookId = ?)",
                        bookName,author,Integer.valueOf(bookId)));
    }

    private String getBookId(Scanner sc, Jdbi jdbi) {
        String bookId = getQuestionAnswer(sc,"What is the ID of the book you to edit?");
        List<Map<String,Object>> bookField = jdbi.withHandle(handle ->
                handle.select("select * from books WHERE (bookId = ?)", Integer.valueOf(bookId))
                .mapToMap()
                .list());
        System.out.println(Arrays.toString(bookField.toArray()));
        return bookId;
    }

    public void viewBooks(Scanner sc, Jdbi jdbi){
        List<Map<String,Object>> allBooks = jdbi.withHandle(handle -> handle.select("select * from books")
                .mapToMap()
                .list());
        for (int i = 0; i <allBooks.size();i++){
            System.out.println(allBooks.get(i));
        }
    }

    private String getQuestionAnswer(Scanner sc,String question){
        System.out.println(question);
        return sc.nextLine();
    }


}
