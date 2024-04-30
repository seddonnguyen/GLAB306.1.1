package junit.test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PasswordComply {

    static final String DB_URL = "jdbc:mysql://localhost:3306/PERSCHOLAS";
    static final String USER = "root";
    static final String PASS = "";
    static final String QUERY = "{call getEmpName (?, ?)}";

    private final int minPasswordLength = 8;
    private final int maxPasswordLength = 12;
    private String passwordString;

    PasswordComply(String verifyPassword) {
        passwordString = verifyPassword;
    }

    /*
    CREATE TABLE PERSCHOLAS.Employee(
      id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255),
        password VARCHAR(255)
    );

    INSERT INTO PERSCHOLAS.Employee (name, password) VALUES ('John', 'abcd1234');
    INSERT INTO PERSCHOLAS.Employee (name, password) VALUES ('Jane', '5678');

    DELIMITER $$

    DROP PROCEDURE IF EXISTS `PERSCHOLAS`.`getEmpName` $$
    CREATE PROCEDURE `PERSCHOLAS`.`getEmpName`
    (IN EMP_EXTENSION VARCHAR(255), OUT EMP_FIRST VARCHAR(255))
    BEGIN
        SELECT name INTO EMP_FIRST
        FROM PERSCHOLAS.Employee
        WHERE password = EMP_EXTENSION;
    END $$

    DELIMITER ;
    */

    // This is a dummy method and needs to implement the real code to validate      // password against database entries.
    public boolean doesNotAlreadyExist() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        CallableStatement stmt = conn.prepareCall(QUERY);

        stmt.setString(1, passwordString);
        stmt.registerOutParameter(2, java.sql.Types.VARCHAR);

        System.out.println("Executing stored procedure...");
        stmt.execute();

        //Retrieve password
        String existingPassword = stmt.getString(2);
        if (existingPassword != null && !existingPassword.isEmpty()) {
            System.out.println("Password already exist" + existingPassword);
            return false;
        }
        System.out.println("Password does not exist");
        return true;
    }

    public void setPassword(String givenPassword) {
        passwordString = givenPassword;
    }

    public boolean doesPasswordComply() {
        return verifyPasswordLength() && verifyAlphaNumeric() && hasAllowedSpecialCharacters() && hasNoSpecialCharacters();
    }

    private boolean verifyPasswordLength() {
        if (!passwordString.isEmpty()) {
            return passwordString.length() >= minPasswordLength && passwordString.length() <= maxPasswordLength;
        }
        return false;
    }

    private boolean verifyAlphaNumeric() {
        return true;
    }

    private boolean hasAllowedSpecialCharacters() {
        return true;
    }

    private boolean hasNoSpecialCharacters() {
        return true;
    }
}