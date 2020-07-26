import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

// JUnit을 적용한 UserDaoTest
public class UserDaoTest {

    // setUp() 메소드에서 오브젝트를 테스트 메소드에서 사용할 수 있도록 인스턴스 변수로 선언
    private UserDao dao;
    private User user1; // User 픽스처를 적용
    private User user2;
    private User user3;

    // @Test 메소드가 실행되기 전에 먼저 실행돼야 하는 메소드(@Before)를 정의
    @Before
    public void setUp() {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.dao = context.getBean("userDao", UserDao.class);
        this.user1 = new User("test10", "홍길동", "1234");
        this.user2 = new User("abcd20", "전우치", "1234");
        this.user3 = new User("apple30", "김범진", "1234");
    }

    @Test
    // get() 테스트 기능을 보완한 addAndGet() 테스트
    public void addAndGet() throws SQLException {

        dao.deleteAll();
        assertThat(dao.getCount(), is(0));

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount(), is(2));

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName(), is(user1.getName()));
        assertThat(userget1.getPassword(), is(user1.getPassword()));

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName(), is(user2.getName()));
        assertThat(userget2.getPassword(), is(user2.getPassword()));
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


    public static void main(String[] args) {
        JUnitCore.main("UserDaoTest");
    }
}