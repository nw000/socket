//package second2;
//
//import org.json.JSONObject;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//
///**
// * Created by dxjf on 16/10/12.
// */
//@SuppressWarnings("ALL")
//public class Client {
//    public final static String HOST = "localhost";
//    public static Socket socket = null;
//    public static DataOutputStream bufferedWriter = null;
//    public static DataInputStream bufferedReader = null;
//
//    public static void main(String[] args) {
//        try {
//            //socket = new Socket(HOST, Server.PORT);
//            ////向服务器写数据
//            //bufferedWriter = new DataOutputStream(socket.getOutputStream());
//            //bufferedReader = new DataInputStream(socket.getInputStream());
//            connectService();
//            //clientWriteService();
//            //bufferedWriter.writeInt(1);
//            //String body = "{\"body\":{\"name\":\"临近\"}}";
//            //bufferedWriter.writeInt(body.getBytes().length);
//            //bufferedWriter.write(body.getBytes());
//            //bufferedWriter.flush();
//            //
//            //bufferedWriter.writeInt(34);
//            //String content = "{\"content\":{\"age\":18}}";
//            //bufferedWriter.writeInt(content.getBytes().length);
//            //bufferedWriter.write(content.getBytes());
//            //bufferedWriter.flush();
//            //
//            //bufferedWriter.writeInt(3);
//            //String contentPrice = "{\"token\":2345}";
//            //bufferedWriter.writeInt(contentPrice.getBytes().length);
//            //bufferedWriter.write(contentPrice.getBytes());
//            //bufferedWriter.flush();
//
//
//            while (true) {
//                //int i = bufferedReader.readInt();
//                //int length = bufferedReader.readInt();
//                //byte[] bytes = new byte[length];
//                //bufferedReader.read(bytes);
//                //System.out.println("cmd" + i + "********lenght" + length + "*******content" + new String(bytes));
//
//                new Thread(new ReaderThread(bufferedReader)).start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                bufferedReader.close();
//                bufferedWriter.close();
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//
//    private static void clientWriteService(int cmd, JSONObject jsonObject) {
//        new Thread(new WriterThread(bufferedWriter)).start();
//    }
//
//    public static void connectService() throws Exception {
//        socket = new Socket(HOST, Server.PORT);
//        //向服务器写数据
//        bufferedWriter = new DataOutputStream(socket.getOutputStream());
//        bufferedReader = new DataInputStream(socket.getInputStream());
//    }
//
//    public static class WriterThread implements Runnable {
//
//        private final DataOutputStream bufferedWriter;
//
//        public WriterThread(DataOutputStream bufferedWriter) {
//            this.bufferedWriter = bufferedWriter;
//        }
//
//        @Override
//        public void run() {
//
//        }
//    }
//
//    public static class ReaderThread implements Runnable {
//
//
//        private DataInputStream bufferedReader;
//
//        public ReaderThread(DataInputStream bufferedReader) {
//            this.bufferedReader = bufferedReader;
//        }
//
//        @Override
//        public void run() {
//
//        }
//    }
//}
