package third;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dxjf on 16/10/12.
 */
@SuppressWarnings("ALL")
public class Client {
    public final static String HOST = "localhost";
    public  Socket socket = null;
    public  DataOutputStream bufferedWriter = null;
    public  DataInputStream bufferedReader = null;
    public  final AtomicInteger atomicInteger = new AtomicInteger();
    //public   static Client client;
    private Map<Integer,Map<Integer,CallBackSocket>> map = new ConcurrentHashMap<>();

    public static class ClientHelper{
            public static final Client client = new Client();

    }

    public static Client getInstance() {
        return ClientHelper.client;
    }

    private Client() {
        try {
            connectService();
           startReaderService();
        } catch (Exception e) {
            try {
                bufferedReader.close();
                bufferedWriter.close();
                socket.close();
            } catch (Exception g) {
                g.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private  void startReaderService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        int code = bufferedReader.readInt();
                        int atomic = bufferedReader.readInt();
                        int length = bufferedReader.readInt();
                        System.out.println("code******" + code + "atomic****"+ atomic);
                        for (Integer key : map.keySet()) {
                            if (key == atomic && map.get(key).containsKey(code)) {
                                byte[] bytes = new byte[length];
                                bufferedReader.read(bytes);
                                CallBackSocket callBackSocket = map.get(key).get(code);
                                callBackSocket.onSuccess(code,"OK",new String(bytes));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void clientWriteService(int cmd, JSONObject jsonObject,CallBackSocket callBackSocket) {
        atomicInteger.getAndAdd(1);
        Map<Integer, CallBackSocket> callMap = new ConcurrentHashMap<>();
        callMap.put(cmd,callBackSocket);
        map.put(atomicInteger.get(),callMap);
        new Thread(new WriterRunnable(bufferedWriter, jsonObject.toString(),atomicInteger,cmd)).start();
    }


    public void connectService() throws Exception {
        socket = new Socket(HOST, Server.PORT);
        //向服务器写数据
        bufferedWriter = new DataOutputStream(socket.getOutputStream());
        bufferedReader = new DataInputStream(socket.getInputStream());
    }

    public static class WriterRunnable implements Runnable {

        private final DataOutputStream bufferedWriter;
        private final String json;
        private final int cmd;
        private AtomicInteger atomicInteger;

        public WriterRunnable(DataOutputStream bufferedWriter, String json, AtomicInteger atomicInteger, int cmd) {
            this.bufferedWriter = bufferedWriter;
            this.json = json;
            this.atomicInteger = atomicInteger;
            this.cmd = cmd;
        }

        @Override
        public void run() {
            try {
                bufferedWriter.writeInt(cmd);
                bufferedWriter.writeInt(atomicInteger.get());
                bufferedWriter.writeInt(json.getBytes().length);
                bufferedWriter.write(json.getBytes());
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
