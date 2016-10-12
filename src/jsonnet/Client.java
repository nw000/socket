package jsonnet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

import static net.Client.host;

/**
 * Created by dxjf on 16/10/11.
 */
public class Client {
    public final static String HOST = "localhost";  //要连接的服务端IP地址
    public static void main(String args[]) throws Exception {

        int port = 8899;   //要连接的服务端对应的监听端口
        //与服务端建立连接
        Socket client = new Socket(host, port);
        //建立连接后就可以往服务端写数据了
        Writer writer = new OutputStreamWriter(client.getOutputStream());
        writer.write("{\"cmd\" : 1}");
        writer.write("\n");
        writer.flush();

        writer.write("{\"cmd\" : 2}");
        writer.write("\n");
        writer.flush();

        //写完以后进行读操作
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String temp;
        int i = 0;
        while ((temp = br.readLine()) != null) {
           System.out.println("from temp: " + temp);
            //writer.write("{\"cmd\" : 1}");
            //writer.write("\n");
            //writer.flush();
            //i++;
        }


        writer.close();
        br.close();
        client.close();
    }
}
