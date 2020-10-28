package org.softwire.training.bookish;

import com.mysql.jdbc.JDBC4ClientInfoProvider;
import org.jdbi.v3.core.Jdbi;
import org.softwire.training.bookish.models.database.Accounts;
import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.Loans;

import java.sql.*;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws SQLException {
        String hostname = "localhost";
        String database = "database";
        String user = "root";
        String password = "admin";
        String connectionString = "jdbc:mysql://" + hostname + "/" + database + "?user=" + user + "&password=" + password + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false";
        Jdbi jdbi = Jdbi.create(connectionString);
//        jdbcMethod(connectionString);
//        jdbiMethod(connectionString);
        Scanner sc = new Scanner(System.in);
        Book bookHandling = new Book();
        Accounts accountHandling = new Accounts();
        Loans loanHandling = new Loans();

        while (true) {
            System.out.println("Add book (1) Delete book (2) Edit book (3) View Books (4) View accounts (5)" +
                    " Add Account (6) Edit Account (7) Check in/out a book (8) ");
            String input = sc.nextLine();
            if (input.equals("1")) {
                bookHandling.addBook(sc, jdbi);
            } else if (input.equals("2")) {
                bookHandling.deleteBook(sc, jdbi);
            } else if (input.equals("3")) {
                bookHandling.editBook(sc, jdbi);
            } else if (input.equals("4")) {
                bookHandling.viewBooks(sc, jdbi);
            } else if (input.equals("5")) {
                accountHandling.printAccounts(sc, jdbi);
            } else if (input.equals("6")) {
                accountHandling.addAccount(sc, jdbi);
            }else if (input.equals("7")) {
                accountHandling.editAccount(sc, jdbi);
            }else if (input.equals("8")){
                loanHandling.bookChecking(sc,jdbi);
            } else {
                break;
            }

        }
        sc.close();
    }

}
