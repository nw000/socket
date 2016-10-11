import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dxjf on 16/10/11.
 */
public class Server {
    public static void main(String[] args) {
        System.out.println("server start....");
        new Server().init();
    }

    private void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(Client.port);
            while (true) {
                Socket alientSocket = serverSocket.accept();
                new Thread(new MyHandlerThread(alientSocket)).start();

            }
        } catch (IOException e) {
            System.out.println("服务器异常 catch" + e.getMessage());
        }
    }

    public static class MyHandlerThread implements Runnable {

        private Socket socket;

        public MyHandlerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //获取客户端的数据
            BufferedReader reader = null;
            PrintStream writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //向客户端写数据
                writer = new PrintStream(socket.getOutputStream());
                System.out.println("请输入：\t");
                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                writer.println(str);
                String clientStr = reader.readLine();
                System.out.println("客户端发送的数据为" + clientStr);
            } catch (IOException e) {
                System.out.println("服务端错误" + e.getMessage());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        reader = null;
                        System.out.println("服务端错误 + finally catch" + e.getMessage());
                    }
                }
                if (writer != null) {
                    writer.close();
                    writer = null;
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        socket = null;
                        System.out.println("服务端错误 + final catch" + e.getMessage());
                    }
                }
            }
        }
    }

}
