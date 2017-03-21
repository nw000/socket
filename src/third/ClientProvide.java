package third;

import org.json.JSONObject;

/**
 * Created by dxjf on 16/10/14.
 */
public class ClientProvide {
    public static void main(String[] args) throws Exception{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","lijie");
        Client.getInstance().clientWriteService(3, jsonObject, new CallBackSocket() {
            @Override
            public void onSuccess(int cmd, String msg, String content) {
                System.out.println("code" + cmd + "*****msg"+ msg + "*****content" + content);
            }
        });
    }

}
