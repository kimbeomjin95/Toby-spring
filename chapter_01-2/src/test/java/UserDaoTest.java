import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao dao = new UserDao() {
            @Override
            public Connection getConnection() throws ClassNotFoundException, SQLException {
                return null;
            }
        };

        User user = new User();
        user.setId("test12");
        user.setName("이건");
        user.setPassword("1234");

        dao.add(user);

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");

    }

}
