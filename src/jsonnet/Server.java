package jsonnet;

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
            Reader reader = new InputStreamReader(socket.getInputStream());
            char chars[] = new char[64];
            int len;
            StringBuilder sb = new StringBuilder();
            String temp;
            int index;
            while ((len=reader.read(chars)) != -1) {
                temp = new String(chars, 0, len);
                if ((index = temp.indexOf("eof")) != -1) {//遇到eof时就结束接收
                    sb.append(temp.substring(0, index));
                    break;
                }
                sb.append(temp);
            }
            System.out.println("from client: " + sb);
            //读完后写一句
            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write("Hello Client.");
            writer.flush();
            writer.close();
            reader.close();
            socket.close();
        }

    }

}


