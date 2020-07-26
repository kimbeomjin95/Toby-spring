package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

// 독립된 SimpleConnectionMaker를 사용하게 만든 UserDao
public class UserDao {
    private ConnectionMaker connectionMaker;
    // 인터페이스를 통해 오브젝트에 접근하므로 구체적인 클래스 정보를 알 필요 없음

    // 어떤 클래스의 오브젝트를 사용할지 생성자의 코드는 제거되지 않고 남아 있다.
    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values (?,?,?)"
        );
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}

