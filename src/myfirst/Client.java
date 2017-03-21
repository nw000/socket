package myfirst;

import java.io.*;
import java.net.Socket;

/**
 * Created by nw on 17/3/20.
 */
public class Client {

    public static final String HOST = "localhost";


    public static void main(String[] args) throws Exception {
        Socket client = new Socket(HOST, Server.PORT);
        while (true) {
            try {
                //获取输出流
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String content = bufferedReader.readLine();
                System.out.println("服务端的数据为 ：" + content);

                //写入服务端
                System.out.println("请输入 ： \t");
                PrintStream printStream = new PrintStream(client.getOutputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String readContent = reader.readLine();
                printStream.print(readContent);

                if ("ok".equals(content)) {
                    System.out.println("连接结束");
                    Thread.sleep(500);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }

    }

}
