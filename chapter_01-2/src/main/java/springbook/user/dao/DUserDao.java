package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// UserDao 클래스를 상속하는 서브 클래스
public class DUserDao extends UserDao {

    @Override
    public Connection getConnection() throws  ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/test", "sa","");
        return c;
    }
}
