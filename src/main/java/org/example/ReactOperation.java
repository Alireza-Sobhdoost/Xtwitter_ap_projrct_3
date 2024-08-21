package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ReactOperation{
    private  FrameCallBack callBack ;
    public ReactOperation (String type , Post post , Long userId , FrameCallBack callBack) {
        this.callBack = callBack;
        try (Socket socket = new Socket("localhost", 5000);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            out.writeInt(4);
            out.writeUTF(type);
            out.writeLong(post.getId());
            out.writeLong(userId);
            boolean reacted = in.readBoolean();
            callBack.onLikeResult(reacted);
            out.close();
            in.close();
            socket.close();


        } catch (IOException u) {
            u.printStackTrace();
        }
    }
}
