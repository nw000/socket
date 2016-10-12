package first;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dxjf on 16/10/12.
 */
public class Server {
    public  static ExecutorService threadPoolExecutor;
    public  final static int PORT = 12345;
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(PORT);
        threadPoolExecutor = Executors.newFixedThreadPool(5);
        while (true) {
            Socket client = serverSocket.accept();
            threadPoolExecutor.execute(new Server.MyHandlerThread(client));
        }
    }


    public static class MyHandlerThread implements Runnable {
        private Socket socket;

        public MyHandlerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            //第一步是处理服务器传递过来的数据
            try {
                BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String clientStr= bfr.readLine();
                System.out.println("客户端传过来的数据是" + clientStr);
                System.out.println("请输入:\t" );
                //读取键盘的输入然后写到客户端
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                printStream.println(str);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}