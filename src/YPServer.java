import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by nw on 16/10/8.
 */
public class YPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);

        while (true) {
            Socket accept = serverSocket.accept();
            System.out.println("accept");
            new MyThread(accept).start();
        }

    }

    public static class MyThread extends Thread {
        private final Socket socket;

        public MyThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[1024];
                int len = -1;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                while ((len = inputStream.read(bytes)) != -1) {
                    bos.write(bytes, 0, len);
                    System.out.println(socket + new String(bos.toByteArray()));
                }
                System.out.println("close");

            } catch (Exception e) {

            }
        }
    }

}
