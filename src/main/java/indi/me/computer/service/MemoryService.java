package indi.me.computer.service;

import indi.me.computer.dao.MemoryMapper;
import indi.me.computer.pojo.Memory;
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
 * @date 2020/7/5 6:39 下午
 */
public class MemoryService {

  private MemoryMapper MemoryMapper;
  private SqlSession session;

  public MemoryService() {
    String resource = "mybatis-config.xml";
    InputStream inputStream = null;
    try {
      inputStream = Resources.getResourceAsStream(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    session = sqlSessionFactory.openSession();
    MemoryMapper = session.getMapper(MemoryMapper.class);
  }

  public List<Memory> selectAll() {
    return MemoryMapper.selectAll();
  }

  public List<Memory> selectByTime(Long time) {
    return MemoryMapper.selectByTime(time);
  }

  public void insertOne(Memory Memory) {
    MemoryMapper.insertOne(Memory);
    session.commit();
  }
}
