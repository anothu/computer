package indi.me.computer;

import indi.me.computer.service.NetworkService;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO Description
 *
 * @author yulinxiao
 * @version 1.0
 * @date 2020/7/5 4:46 下午
 */
public class Test {

  public static void main(String[] args)
      throws ParseException, IllegalAccessException, InvocationTargetException, InstantiationException {
    NetworkService networkService = new NetworkService();
    Class<? extends NetworkService> aClass = networkService.getClass();
    //构造器
    Constructor<?>[] constructors = aClass.getConstructors();
    NetworkService o = (NetworkService) constructors[0].newInstance();
    //字段
    Field[] fields = aClass.getDeclaredFields();
    List<Field> fields1 = Arrays.asList(fields);
    testt();
    List list = new LinkedList();
    list.add(1);
    list.add("hello");
    System.out.println(list.get(0).getClass());
    System.out.println(list.get(1).getClass());
  }

  public static void testt() {

  }

}
