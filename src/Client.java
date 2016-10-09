import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by nw on 16/10/8.
 */
public class Client {
    public static int port = 8888;
    public static String host = "localhost";


    public static void main(String[] args) {
        System.out.println("Clent start.....");
        Socket socket = null;
        try {
            while (true) {
                socket = new Socket(host, port);
                //从服务端读取数据
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //向服务端写数据
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                System.out.print("请输入:\t");
                out.println(str);

                String ret = input.readLine();
                System.out.println("服务器返回的返回码是"+ ret);
                if ("ok".equals(ret)) {
                    System.out.println("客户端将关闭连接");
                    Thread.sleep(500);
                    break;
                }
                out.close();
                input.close();
            }
        } catch (Exception e) {
            System.out.println("客户端异常"+e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    socket = null;
                    System.out.println("客户端异常"+e.getMessage());
                }
            }
        }
    }
}
