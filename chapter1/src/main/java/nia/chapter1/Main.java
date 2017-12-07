package nia.chapter1;

import java.io.IOException;


/**
 * Created by jinxiaofei.
 * Time 2017/12/7 上午10:14
 * Desc 文件描述
 */
public class Main {

    public static void main(String[] args) {
        new Thread(()->{
            try {
                new BlockingIoExample().serve(9999);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(ConnectExample::connect).start();
    }
}
