package org.softwire.training.bookish.models.database;

import org.jdbi.v3.core.Jdbi;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Loans {
    public void bookChecking(Scanner sc, Jdbi jdbi,Book bookHandling){
        String accountId = getQuestionAnswer(sc, "What is your account ID?");
        String bookId = getQuestionAnswer(sc, "What is the book ID?");
        String checkChoice = getQuestionAnswer(sc, "Do you want to check the book in (1) or out (2)");
        if (checkChoice.equals("1")){
            checkIn(sc,jdbi,bookHandling,Integer.valueOf(accountId),Integer.valueOf(bookId));
        }else if (checkChoice.equals("2")){
            checkOut(sc,jdbi,bookHandling,Integer.valueOf(accountId),Integer.valueOf(bookId));
        }
    }

    public void displayLoans(Scanner sc, Jdbi jdbi){
        List<Map<String,Object>> allLoans = jdbi.withHandle(handle -> handle.createQuery("select * from loans")
                .mapToMap()
                .list());
        for (int i = 0; i <allLoans.size();i++){
            System.out.println(allLoans.get(i));
        }
    }

    private void checkIn(Scanner sc,Jdbi jdbi,Book bookHandling,int accountId,int bookId){ //bringing back a book (add copy back, remove loan)
        jdbi.withHandle(handle-> handle.execute("DELETE FROM loans WHERE (idLoans = ? && bookId = ?)",accountId,bookId));
        jdbi.withHandle(handle -> handle.execute( " UPDATE books SET bookCopies = bookCopies + 1  WHERE bookId = ?",bookId));
    }

    private void checkOut(Scanner sc,Jdbi jdbi,Book bookHandling,int accountId,int bookId){ //taking out a book
        jdbi.withHandle(handle -> handle.execute("INSERT INTO loans (idloans,bookId,dateLoaned) VALUES (?,?,?)",accountId,bookId,new Date()));
        jdbi.withHandle(handle -> handle.execute( " UPDATE books SET bookCopies = bookCopies - 1  WHERE bookId = ?",bookId));
    }

    private String getQuestionAnswer(Scanner sc,String question){
        System.out.println(question);
        return sc.nextLine();
    }
}
