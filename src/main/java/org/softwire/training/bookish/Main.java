package org.softwire.training.bookish;

import com.mysql.jdbc.JDBC4ClientInfoProvider;
import org.jdbi.v3.core.Jdbi;
import org.softwire.training.bookish.models.database.Book;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

        System.out.println("Hi Librarian, what would you like to do today?  Add book (1) Delete book (2) View accounts (3): ");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        Book bookHandling = new Book();
        if (input.equals("1")){
            bookHandling.addBook(sc,jdbi);
        }else if (input.equals("2")){
            bookHandling.deleteBook(sc,jdbi);
        }else if (input.equals("3")){
            printAccounts(sc,jdbi);
        }

    }

    private static void printAccounts(Scanner sc,Jdbi jdbi){
        List<Map<String,Object>> allAccounts = jdbi.withHandle(handle -> handle.createQuery("select * from accounts")
                    .mapToMap()
                    .list());
        for (int i = 0; i <allAccounts.size();i++){
            System.out.println(allAccounts.get(i));
        }
    }


    private static void jdbcMethod(String connectionString) throws SQLException {
        System.out.println("JDBC method...");

        // TODO: print out the details of all the books (using JDBC)
        // See this page for details: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html

        Connection con = DriverManager.getConnection(connectionString);

        String query = "select * from accounts";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String accountName = rs.getString("accountName");
                System.out.print(accountName);
            }
        } catch (SQLException e) {
            System.out.print(e);
        }

    }

    private static void jdbiMethod(String connectionString) {
        System.out.println("\nJDBI method...");

        // TODO: print out the details of all the books (using JDBI)
        // See this page for details: http://jdbi.org
        // Use the "Book" class that we've created for you (in the models.database folder)

        Jdbi jdbi = Jdbi.create(connectionString);
        List<String> names = jdbi.withHandle(handle -> {
                    handle.createQuery("select accountName from accounts")
                            .mapTo(String.class)
                            .list();
                    handle.execute("INSERT INTO accounts(accountName,age) VALUES (?, ?)", "mick", 9);
                    return null;
        });

        //System.out.print(Arrays.toString(names.toArray()));



    }
}
