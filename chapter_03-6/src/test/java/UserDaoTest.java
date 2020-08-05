import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

// 스프링의 테스트 컨텍스트 프레임워크의 JUnit 확장기능 지정
@RunWith(SpringJUnit4ClassRunner.class)
// 테스트 컨텍스트가 자동으로 만들어줄 애플리케이션 컨텍스트의 위치 지정
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoTest {  // 스프링 테스트 컨텍스트를 적용한 UserDaoTest

    @Autowired
    private ApplicationContext context;
    // 테스트 오브젝트가 만들어지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 값이 주입됨
    // setUp() 메소드에서 오브젝트를 테스트 메소드에서 사용할 수 있도록 인스턴스 변수로 선언

    UserDao dao;    // UserDao 타입 빈을 직접 DI 받음

    private User user1; // User 픽스처를 적용
    private User user2;
    private User user3;

    // @Test 메소드가 실행되기 전에 먼저 실행돼야 하는 메소드(@Before)를 정의
    @Before
    public void setUp() {
        this.dao = context.getBean("userDao", UserDao.class);
        this.user1 = new User("b2", "홍길동", "1234");
        this.user2 = new User("c2", "전우치", "1234");
        this.user3 = new User("a2", "김범진", "1234");

    }

    @Test
    // get() 테스트 기능을 보완한 addAndGet() 테스트
    public void addAndGet() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        dao.add(user3);
        assertThat(dao.getCount(), is(3));

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));

        User userget3 = dao.get(user3.getId());
        assertThat(userget3.getName(), is(user3.getName()));
        assertThat(userget3.getPassword(), is(user3.getPassword()));
    }

    @Test
    // getCount() 테스트
    public void count() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        assertThat(dao.getCount(), is(1));

        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        dao.add(user3);
        assertThat(dao.getCount(), is(3));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    // get() 메소드의 예외상황에 대한 테스트
    public void getUserFailure() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.get("unknown_id");
        // 이 메소드 실행 중 예외 발생, 그렇지 않으면 테스트 실패
    }

    @Test
    public void getAll() throws SQLException {
        dao.deleteAll();

        // 데이터가 없는 경우에 대한 검증 코드가 추가된 getAll() 테스트
        List<User> users0 = dao.getAll();
        assertThat(users0.size(), is(0));
//        System.out.println("users0:"+users0.size());  // 0

        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));

        checkSameUser(user3, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user2, users3.get(2));
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), 		is(user2.getId()));
        assertThat(user1.getName(), 	is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
    }


    public static void main(String[] args) {
        JUnitCore.main("UserDaoTest");
    }
}