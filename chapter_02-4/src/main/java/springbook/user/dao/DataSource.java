package springbook.user.dao;

import javax.sql.CommonDataSource;
import java.sql.Wrapper;

public interface DataSource extends CommonDataSource, Wrapper {
}
