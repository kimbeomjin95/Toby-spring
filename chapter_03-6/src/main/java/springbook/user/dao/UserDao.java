package springbook.user.dao;


import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// JdbcTemplate을 적용한 UserDao 클래
public class UserDao {
    public void setDataSource(DataSource dataSource) {  // 수정자 메소드이면서 JdbcContext에 대한 생성. DI 작업을 동시에 수행한다.
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private JdbcTemplate jdbcTemplate;

    // 재사용 가능하도록 독립시킨 RowMapper
    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;

        }
    };

    // JdbcTemplate을 이용한 add() 메소드
    public void add(final User user) throws SQLException {
        this.jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?)",
                user.getId(), user.getName(),user.getPassword());
    }

    // queryForObject()와 RowMapper를 적용한 get() 메소드
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[] {id}, this.userMapper);
    }

    // 내장 콜백을 사용하는 update()로 변경한 deleteAll() 메소드
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    // query()를 이용해 만든 getAll() 메소드
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
    }

    // queryForInt() 메소드 삭제로 dlsgo queryForObject()로 대체
    public int getCount() {
       return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }
}