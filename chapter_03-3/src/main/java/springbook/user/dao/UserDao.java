package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 메소드 파라미터로 이전한 익명 내부 클래스
    public void add(final User user) throws SQLException {

        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        });
    }


    public User get(String id) throws  SQLException {

        Connection c = this.dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) throw new EmptyResultDataAccessException(1);

        return user;
    }

   // 메소드로 분리한 try/catch/finally 컨텍스트 코드
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        // StatementStrategy stmt : 클라이언트가 컨텍스트를 호출할 때 넘겨줄 전략 파라미터
        Connection c = null;
        PreparedStatement ps = null;

        try {     // 예외가 발생할 가능성이 있는 코드를 모두 try블록으로 묶어준다.
            c = dataSource.getConnection();

           ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (SQLException e) {  // 예외가 발생했을 때 부가적인 작업을 해줄 수 있도록 catch 블록을 둔다.
            throw e;                // 아직은 예외를 다시 메소드 밖으로 던지는 것 밖에 없다.
        } finally { // try 블록에서 예외가 발생했을 때나 안했을 때나 모두 실행된다.
            if (ps != null) { try { ps.close(); } catch (SQLException e) {} }
            if (c != null) { try { c.close();  } catch (SQLException e) {} }    // Connection 반환
        }
    }

    // 익명 내부 클래스를 적용한 deleteAll() 메소드
    public void deleteAll() throws SQLException {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
             public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                 return c.prepareStatement("delete from users");
             }
         });
    }

    // JDBC 예외처리를 적용한 getCount() 메소드
    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");

            rs = ps.executeQuery(); // ResultSet도 예외가 발생할 수 있는 코드이므로 try블록 안에 둬야 함
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}