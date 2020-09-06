package indi.me.computer.service;

import indi.me.computer.dao.NetworkMapper;
import indi.me.computer.pojo.Network;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/5 6:40 下午
 */
public class NetworkService {

  private indi.me.computer.dao.NetworkMapper NetworkMapper;
  private SqlSession session;

  public NetworkService() {
    String resource = "mybatis-config.xml";
    InputStream inputStream = null;
    try {
      inputStream = Resources.getResourceAsStream(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    session = sqlSessionFactory.openSession();
    NetworkMapper = session.getMapper(NetworkMapper.class);
  }

  public List<Network> selectAll() {
    return NetworkMapper.selectAll();
  }

  public List<Network> selectByTime(Long time) {
    return NetworkMapper.selectByTime(time);
  }

  public void insertOne(Network Network) {
    NetworkMapper.insertOne(Network);
    session.commit();
  }
}
