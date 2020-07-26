import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {

       public static void main(String[] args) throws SQLException, ClassNotFoundException {
            UserDao dao = new UserDao();

            User user = new User();
            user.setId("jin");
            user.setName("김봉진");
            user.setPassword("1234");

            dao.add(user);

            User user2 = dao.get(user.getId());
            System.out.println(user2.getName());
            System.out.println(user2.getPassword());

            System.out.println(user2.getId() + " 조회 성공");

        }
}
