package second2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by dxjf on 16/10/12.
 */
public class Client {
    public final static String HOST = "localhost";
    public static Socket socket = null;
    public static DataOutputStream bufferedWriter = null;
    public static DataInputStream bufferedReader = null;

    public static void main(String[] args) {
        try {
            connectService();
            new Thread(new ReaderThread(bufferedReader)).start();
            bufferedWriter.writeInt(1);
            String body = "{\"body\":{\"name\":\"临近\"}}";
            bufferedWriter.writeInt(body.getBytes().length);
            bufferedWriter.write(body.getBytes());
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static void connectService() throws Exception {
        socket = new Socket(HOST, Server.PORT);
        //向服务器写数据
        bufferedWriter = new DataOutputStream(socket.getOutputStream());
        bufferedReader = new DataInputStream(socket.getInputStream());
    }
    public static class ReaderThread implements Runnable {
        private DataInputStream bufferedReader;

        public ReaderThread(DataInputStream bufferedReader) {
            this.bufferedReader = bufferedReader;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int i = bufferedReader.readInt();
                    int length = bufferedReader.readInt();
                    byte[] bytes = new byte[length];
                    bufferedReader.read(bytes);
                    System.out.println("cmd ：" + i + "********lenght ：" + length + "*******content ：" + new String(bytes));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
