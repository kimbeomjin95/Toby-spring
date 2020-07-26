package springbook.user.dao;


/*
// DAO 생성 메소드의 추가로 인해 발생하는 중복
// ConnectionMaker 구현 클래스를 선정하고 생성하는 코드의 중복
public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(new DConnectionMaker());
    }
    public UserDao accountDao() {
        return new UserDao(new DConnectionMaker());
    }
    public UserDao messageDao() {
        return new UserDao(new DConnectionMaker());
    }
}
 */

// 생성 오브젝트 코드 수정
public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }
    public UserDao accountDao() {
        return new UserDao(connectionMaker());
    }
    public UserDao messageDao() {
        return new UserDao(connectionMaker());
    }
    // 분리해서 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
