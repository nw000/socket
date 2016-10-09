import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nw on 16/10/8.
 */
public class MyServer {
    public static final int port = 8888;

    public static void main(String[] args) {
        System.out.println("Server start ...");
        new MyServer().init();
    }

    private void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket accept = serverSocket.accept();
                new MyHandlerThread(accept);
            }


        } catch (Exception e) {
            System.out.println("服务器异常"+e.getMessage());
        }
    }

    public static class  MyHandlerThread implements Runnable{

        private  Socket socket;

        public MyHandlerThread(Socket socket) {
            this.socket = socket;
            new Thread(this).start();
        }

        @Override
        public void run() {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String clientInputStr = input.readLine();
                //处理客户端的数据
                System.out.println("客户端写过来的数据"+clientInputStr);
                PrintStream out = new PrintStream(socket.getOutputStream());
                //向客户端写数据
                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
                System.out.print("请输入:\t");
                out.println(s);

                out.close();
                input.close();
            } catch (Exception e) {
                System.out.println("服务端run错误"+ e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("服务端 finally 异常:" + e.getMessage());
                    }
                }
            }
        }
    }



}
