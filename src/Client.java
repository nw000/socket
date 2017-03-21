import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by dxjf on 16/10/11.
 */
class Client {
    private static final String HOST = "localhost";
    public static final int port = 8989;

    public static void main(String[] args) {
        Socket socket = null;
        while (true) {
            try {
                socket = new Socket(HOST, port);
                //从服务器读取数据
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //向服务器发送数据
                String line = input.readLine();
                System.out.println("服务段发送的是" + line );
                PrintStream out = new PrintStream(socket.getOutputStream());
                System.out.println("请输入：\t");
                //读取键盘的数据
                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.println(str);
                if ("OK".equals(line)) {
                    System.out.println("客户端即将关闭");
                    Thread.sleep(500);
                    break;
                }
                input.close();
                out.close();

            } catch (Exception e) {
                System.out.println("客户端错误"+e.getMessage());

            } finally {

            }
        }
    }
}
