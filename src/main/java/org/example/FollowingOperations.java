package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FollowingOperations {

    private static ProfileCallBack callBack ;

    public FollowingOperations (User A , User B , String operation ,ProfileCallBack call){
        callBack = call ;
        try (Socket socket = new Socket("localhost", 5000);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            if (operation.equals("Follow")){
                out.writeInt(6);
            }
            else if (operation.equals("Unfollow")){
                out.writeInt(7);
            }

            out.writeLong(A.getId()); // Send username to server
            out.writeLong(B.getId()); // Send password to server
            boolean isLoggedIn = in.readBoolean();
//                System.out.println(isLoggedIn);
            callBack.onProfileResult(isLoggedIn);
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
