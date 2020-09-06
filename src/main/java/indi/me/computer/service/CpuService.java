package indi.me.computer.service;

import indi.me.computer.dao.CpuMapper;
import indi.me.computer.pojo.Cpu;
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
 * @date 2020/7/5 6:33 下午
 */
public class CpuService {

  private CpuMapper cpuMapper;
  private SqlSession session;

  public CpuService() {
    String resource = "mybatis-config.xml";
    InputStream inputStream = null;
    try {
      inputStream = Resources.getResourceAsStream(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    session = sqlSessionFactory.openSession();
    cpuMapper = session.getMapper(CpuMapper.class);
  }

  public List<Cpu> selectAll() {
    return cpuMapper.selectAll();
  }

  public List<Cpu> selectByTime(Long time) {
    return cpuMapper.selectByTime(time);
  }

  public void insertOne(Cpu cpu) {
    cpuMapper.insertOne(cpu);
    session.commit();
  }
}
