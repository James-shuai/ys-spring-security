package com.ys.ysspringsecurity.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ys
 * @date 2020/4/21 13:59
 */
public class MapToBean {

  public static byte[] ser(Object o) {

    ObjectOutput oos = null;
    ByteArrayOutputStream baos = null;
    try {
      //创建一个byte的数组的流
      baos = new ByteArrayOutputStream();
      //创建对象流将对象写入到byte数组里
      oos = new ObjectOutputStream(baos);
      //进行写入操作
      oos.writeObject(o);
      //将byte数组的对象进行转换为byte的数组
      return baos.toByteArray();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }


  /**
   * 反序列化
   */
  public static Object unser(byte[]bs)
  {
    ByteArrayInputStream bais=null;
    //创建一个byte的数组的读入流对其byte数组
    bais =new ByteArrayInputStream(bs);
    try {
      //对象的输入流，用于读取对象
      ObjectInputStream objectInputStream=new ObjectInputStream(bais);
      //将byte格式的转化为对象
      return objectInputStream.readObject();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return bs;

  }


}
