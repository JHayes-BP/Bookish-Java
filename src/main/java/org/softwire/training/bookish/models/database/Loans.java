package org.softwire.training.bookish.models.database;

import org.jdbi.v3.core.Jdbi;

import java.util.Scanner;

public class Loans {
    public void bookChecking(Scanner sc, Jdbi jdbi){
        String checkChoice = getQuestionAnswer(sc, "Do you want to check a book in (1) or out (2)");
        if (checkChoice.equals("1")){
            checkIn(sc,jdbi);
        }else if (checkChoice.equals("2")){
            checkOut(sc,jdbi);
        }
    }

    private void checkIn(Scanner sc,Jdbi jdbi){}

    private void checkOut(Scanner sc,Jdbi jdbi){}

    private String getQuestionAnswer(Scanner sc,String question){
        System.out.println(question);
        return sc.nextLine();
    }
}
