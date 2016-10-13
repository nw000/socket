package first;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by dxjf on 16/10/12.
 */
public class Client {
    public static final String HOST = "localhost";

    public static void main(String[] args) {
        System.out.println("client start .....");
        Socket client = null;
        BufferedReader bfr = null;
        PrintStream printStream = null;
        while (true) {
            try {
                client = new Socket(HOST, Server.PORT);
                //获取读入流
                bfr = new BufferedReader(new InputStreamReader(client.getInputStream()));

                //获取输出流
                printStream = new PrintStream(client.getOutputStream());
                System.out.println("请输入：\t");
                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                printStream.println(str);

                String serverStr = bfr.readLine();
                System.out.println("服务端的数据为" + serverStr);
                if ("OK".equals(serverStr)) {
                    System.out.println("连接即将结束");
                    Thread.sleep(500);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bfr != null) {
                    try {
                        bfr.close();
                        bfr = null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (printStream != null) {
                    printStream.close();
                }

                if (client != null) {
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }
    }
}
