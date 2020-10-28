package org.softwire.training.bookish.models.database;

import org.jdbi.v3.core.Jdbi;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Accounts {

    public void printAccounts(Scanner sc, Jdbi jdbi){
        List<Map<String,Object>> allAccounts = jdbi.withHandle(handle -> handle.createQuery("select * from accounts")
                .mapToMap()
                .list());
        for (int i = 0; i <allAccounts.size();i++){
            System.out.println(allAccounts.get(i));
        }
    }

    public void addAccount(Scanner sc, Jdbi jdbi){
        String name = getQuestionAnswer(sc,"What do you want to be the account name?:");
        String age = getQuestionAnswer(sc,"What is the persons age? ");
        jdbi.withHandle(handle -> handle.execute("INSERT INTO accounts(accountName,age) VALUES(?,?) ",name,Integer.valueOf(age)));;
    }

    public void editAccount(Scanner sc, Jdbi jdbi){
        String accountId = getQuestionAnswer(sc,"What is the account ID? ");
        List<Map<String,Object>> accountField = jdbi.withHandle(handle ->
                handle.select("select * from accounts WHERE (idAccounts = ?)", Integer.valueOf(accountId))
                        .mapToMap()
                        .list());
        System.out.println(Arrays.toString(accountField.toArray()));
        String name = getQuestionAnswer(sc,"What is the account name? ");
        String age = getQuestionAnswer(sc, "What is the persons age");
        jdbi.withHandle(handle -> handle.execute("UPDATE accounts SET accountName = ?,age = ? WHERE idAccounts = ? ",
                name,Integer.valueOf(age),Integer.valueOf(accountId)));;

    }

    private String getQuestionAnswer(Scanner sc,String question){
        System.out.println(question);
        return sc.nextLine();
    }

}
