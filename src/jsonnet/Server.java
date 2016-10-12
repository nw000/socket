package jsonnet;

import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dxjf on 16/10/11.
 */
public class Server {
    public static void main(String args[]) throws IOException {
        //为了简单起见，所有的异常信息都往外抛
        int port = 8899;
        //定义一个ServerSocket监听在端口8899上
        ServerSocket server = new ServerSocket(port);
        while (true) {
            //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
            Socket socket = server.accept();
            //每接收到一个Socket就建立一个新的线程来处理它
            new Thread(new Task(socket)).start();
        }
    }

    /**
     * 用来处理Socket请求的
     */
    static class Task implements Runnable {

        private Socket socket;

        public Task(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                handleSocket();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 跟客户端Socket进行通信
         * @throws Exception
         */
        private void handleSocket() throws Exception {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String temp;

            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            while ((temp = br.readLine()) != null) {
                handleRequest(writer,temp);
            }
            br.close();
            socket.close();
        }

        private void handleRequest(Writer writer, String temp) throws Exception {
            System.out.println(temp);
            JSONObject obj = new JSONObject(temp);
            int cmd = obj.optInt("cmd");
            if (cmd == 1) {
                writer.write("{\"price\" : 4100}");
                writer.write("\n");
                writer.flush();
            }
            else {
                //TODO
                writer.write("{\"code\" : -1,\"msg\" : \"无法识别的cmd\"}");
                writer.write("\n");
                writer.flush();
            }
            //读完后写一句
        }
    }
}


