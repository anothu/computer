package indi.me.computer.service;

import indi.me.computer.dao.LoginMapper;
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/11 4:10 下午
 */
public class LoginService {


  private LoginMapper loginMapper;
  private SqlSession session;

  public LoginService() {
    String resource = "mybatis-config.xml";
    InputStream inputStream = null;
    try {
      inputStream = Resources.getResourceAsStream(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    session = sqlSessionFactory.openSession();
    loginMapper = session.getMapper(LoginMapper.class);
  }

  public Integer Login(String user, String password) {
    return loginMapper.login(user, password);
  }
}
