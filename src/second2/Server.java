package second2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dxjf on 16/10/12.
 */
public class Server {
    public final static int PORT = 7779;
    public static Timer time = new Timer();

    public static void main(String[] args) {

        System.out.println("server start ....");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("true:");
                Socket accept = serverSocket.accept();
                new Thread(new MyHandler(accept)).start();
            }
        } catch (IOException e) {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e1) {
                serverSocket = null;
                e1.printStackTrace();
            }
        }
    }


    private static class MyHandler implements Runnable {
        private Socket socket;

        public MyHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            DataInputStream bufferedReader = null;
            DataOutputStream bufferedWriter = null;
            try {
                //读取
                bufferedReader = new DataInputStream(socket.getInputStream());
                //向客户端写数据
                bufferedWriter = new DataOutputStream(socket.getOutputStream());
                //readInt readInt readString(${len})

                while (true) {
                    int cmd = bufferedReader.readInt();
                    int len = bufferedReader.readInt();
                    byte[] buffer = new byte[len];
                    bufferedReader.read(buffer);
                    handlerRequest(bufferedWriter, cmd, new String(buffer));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void handlerRequest(DataOutputStream bufferedWriter, int cmd, String json) {
            System.out.println("json:" + json);
            try {
                if (cmd == 1) {
                    bufferedWriter.writeInt(cmd);
                    bufferedWriter.writeInt("{\"price\":56}".getBytes().length);
                    bufferedWriter.write("{\"price\":56}".getBytes());
                    bufferedWriter.flush();
                } else if (cmd == 3) {
                    time.schedule(new MyTimerTasker(bufferedWriter, cmd), 500, 500);
                } else {
                    bufferedWriter.writeInt(cmd);
                    bufferedWriter.writeInt("{\"code\":00,\"msg\":\"no such cmd\"}".getBytes().length);
                    bufferedWriter.write("{\"code\":00,\"msg\":\"no such cmd\"}".getBytes());
                    bufferedWriter.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static class MyTimerTasker extends TimerTask {

        private DataOutputStream dataOutputStream;
        private int cmd;

        public MyTimerTasker(DataOutputStream dataOutputStream, int cmd) {
            this.dataOutputStream = dataOutputStream;
            this.cmd = cmd;
        }

        @Override
        public void run() {
            try {
                dataOutputStream.writeInt(cmd);
                int price = new Random().nextInt(10000) + 50000;
                String priceContent = "{\"goldprice\"," + price + "}";
                dataOutputStream.writeInt(priceContent.getBytes().length);
                dataOutputStream.write(priceContent.getBytes());
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
