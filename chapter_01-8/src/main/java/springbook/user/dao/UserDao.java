package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

// 인스턴스 변수를 사용하도록 수정한 UserDao
public class UserDao {
    /* 초기에 설정하면 사용 중에는 바뀌지 않는 읽기 전용 인스턴스 변수 */
    private DataSource dataSource;
    /* 매번 새로운 값으로 바뀌는 정보를 담은 인스턴스 변수 */

    public void setDataSource(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost/~/test", "sa","");

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

