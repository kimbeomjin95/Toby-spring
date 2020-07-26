package springbook.user.dao;

import org.springframework.context.annotation.Bean;

public class DaoFactory {

    @Bean /* 오브젝트 생성을 담당하는 IoC용 메서드라는 표시 */
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(connectionMaker());
        return userDao;
    }
}
