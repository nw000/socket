//package second;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.Socket;
//
///**
// * Created by dxjf on 16/10/12.
// */
//@SuppressWarnings("ALL")
//public class Client {
//    public final static String HOST = "localhost";
//
//    public static void main(String[] args) throws Exception{
//        Socket socket = new Socket(HOST, Server.PORT);
//        //向服务器写数据
//        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//        bufferedWriter.write("{\"cmd\":1}");
//        bufferedWriter.newLine();
//        bufferedWriter.flush();
//
//        bufferedWriter.write("{\"cmd\":2}");
//        bufferedWriter.newLine();
//        bufferedWriter.flush();
//
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        String temp ;
//        while ((temp = bufferedReader.readLine()) != null) {
//            System.out.println(temp);
//        }
//        bufferedReader.close();
//        bufferedWriter.close();
//        socket.close();
//
//    }
//}
