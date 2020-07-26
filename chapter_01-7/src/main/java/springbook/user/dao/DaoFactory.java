package springbook.user.dao;

import org.springframework.context.annotation.Bean;

public class DaoFactory {
    @Bean
    public UserDao userDao() {
        // UserDao는 여전히 connectionMaker()에서 생성되는 Object를 DI받는
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(new DConnectionMaker());
        return userDao;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(new realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new DConnectionMaker();
    }
}
