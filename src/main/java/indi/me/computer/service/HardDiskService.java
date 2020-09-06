package indi.me.computer.service;

import indi.me.computer.dao.HardDiskMapper;
import indi.me.computer.pojo.HardDisk;
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
public class HardDiskService {

  private HardDiskMapper hardDiskMapper;
  private SqlSession session;

  public HardDiskService() {
    String resource = "mybatis-config.xml";
    InputStream inputStream = null;
    try {
      inputStream = Resources.getResourceAsStream(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    session = sqlSessionFactory.openSession();
    hardDiskMapper = session.getMapper(HardDiskMapper.class);
  }

  public List<HardDisk> selectAll() {
    return hardDiskMapper.selectAll();
  }

  public List<HardDisk> selectByTime(Long time) {
    return hardDiskMapper.selectByTime(time);
  }

  public void insertOne(HardDisk hardDisk) {
    hardDiskMapper.insertOne(hardDisk);
    session.commit();
  }
}
