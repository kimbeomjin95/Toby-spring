package springbook.user.dao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 스프링 빈 팩토리가 사용할 설정정보를 담은 DaoFactory 클래스
@Configuration  // 어플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
public class CountingDaoFactory {
    @Bean
    // 오브젝트 생성을 담당하는 Ioc용 메소드라는 표시
    // 모든 DAO는 여전히 ConnectionMaker()에서 만들어지는 오브젝트 DI를 받음
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }
    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    <bean id ="connectionMaker" class="springbook DConnectionMaker" />
    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new DConnectionMaker();
    }
}
