package myfirst;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nw on 17/3/20.
 */
public class Server {

    public static final int PORT = 123456;

    public static void main(String[] args) throws Exception {
        System.out.println("server start ......");
        ServerSocket socket = new ServerSocket(PORT);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        while (true) {
            Socket accept = socket.accept();
            executorService.execute(new MyHandler(accept));
        }
    }


    public static class MyHandler implements Runnable {

        private Socket accept;

        public MyHandler(Socket accept) {
            this.accept = accept;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                    String content = reader.readLine();
                    System.out.println("客户端的字段 ：" + content);
                    System.out.println("请输入");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
