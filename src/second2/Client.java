package second2;

import java.io.*;
import java.net.Socket;

/**
 * Created by dxjf on 16/10/12.
 */
@SuppressWarnings("ALL")
public class Client {
    public final static String HOST = "localhost";

    public static void main(String[] args) {
        Socket socket = null;
        DataOutputStream bufferedWriter = null;
        DataInputStream bufferedReader = null;
        try {
            socket = new Socket(HOST, Server.PORT);
            //向服务器写数据
            bufferedWriter = new DataOutputStream(socket.getOutputStream());
            bufferedWriter.writeInt(1);

            String body = "{\"body\":{\"name\":\"临近\"}}";
            bufferedWriter.writeInt(body.getBytes().length);
            bufferedWriter.write(body.getBytes());
            bufferedWriter.flush();

            //bufferedWriter.write("{\"cmd\":1,\"body\":{\"name\":\"临近\"}}");
            //bufferedWriter.newLine();
            //bufferedWriter.flush();

            //bufferedWriter.write("{\"cmd\":2}");
            //bufferedWriter.newLine();
            //bufferedWriter.flush();

            bufferedWriter.writeInt(34);
            String content = "{\"content\":{\"age\":18}}";
            bufferedWriter.writeInt(content.getBytes().length);
            bufferedWriter.write(content.getBytes());
            bufferedWriter.flush();

            bufferedWriter.writeInt(3);
            String contentPrice = "{\"token\":2345}";
            bufferedWriter.writeInt(contentPrice.getBytes().length);
            bufferedWriter.write(contentPrice.getBytes());
            bufferedWriter.flush();

            bufferedReader = new DataInputStream(socket.getInputStream());
            String temp;
            while (true) {
                int i = bufferedReader.readInt();
                int length = bufferedReader.readInt();
                byte[] bytes = new byte[length];
                bufferedReader.read(bytes);
                System.out.println("cmd" + i + "lenght" + length + "content" + new String(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                bufferedWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
