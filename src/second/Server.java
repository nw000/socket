//package second;
//
//import org.json.JSONObject;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
///**
// * Created by dxjf on 16/10/12.
// */
//public class Server {
//    public final static int PORT = 7777;
//
//    public static void main(String[] args) {
//        System.out.println("server start ....");
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(PORT);
//            while (true) {
//                Socket accept = serverSocket.accept();
//                new Thread(new MyHandler(accept)).start();
//            }
//        } catch (IOException e) {
//            try {
//                serverSocket.close();
//            } catch (IOException e1) {
//                serverSocket = null;
//                e1.printStackTrace();
//            }
//        }
//    }
//
//
//    private static class MyHandler implements Runnable {
//        private Socket socket;
//
//        public MyHandler(Socket socket) {
//            this.socket = socket;
//        }
//
//        @Override
//        public void run() {
//            BufferedReader bufferedReader = null;
//            BufferedWriter bufferedWriter = null;
//            try {
//                //读取客户端的数据
//                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                //向客户端写数据
//                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//                String temp;
//                while ((temp = bufferedReader.readLine()) != null) {
//                    handlerRequest(bufferedWriter, temp);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (bufferedReader != null) {
//                    try {
//                        bufferedReader.close();
//                    } catch (Exception e) {
//                        bufferedReader = null;
//                        e.printStackTrace();
//                    }
//                }
//
//                if (bufferedWriter != null) {
//                    try {
//                        bufferedWriter.close();
//                    } catch (IOException e) {
//                        bufferedWriter = null;
//                        e.printStackTrace();
//                    }
//                }
//
//                if (socket != null) {
//                    try {
//                        socket.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        private void handlerRequest(BufferedWriter bufferedWriter, String temp) {
//
//            try {
//                JSONObject jsonObject = new JSONObject(temp);
//                int cmd = jsonObject.optInt("cmd");
//                if (cmd == 1) {
//                    bufferedWriter.write("{\"price\":56}");
//                    bufferedWriter.newLine();
//                    bufferedWriter.flush();
//                } else {
//                    bufferedWriter.write("{\"code\":00,\"msg\":\"no such cmd\"}");
//                    bufferedWriter.newLine();
//                    bufferedWriter.flush();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
