package com.guolei.asm_plugin;

/**
 * Created by Android Studio.
 * User: guolei
 * Email: 1120832563@qq.com
 * Date: 2019/4/8
 * Time: 10:36 PM
 * Desc:
 */
public class ByteCodeDemo {

    ByteCodeDemo(){
        System.err.print("hello world");
    }

    public void method(){
        System.err.print("hello world");
        System.err.println(System.currentTimeMillis());
    }

}
