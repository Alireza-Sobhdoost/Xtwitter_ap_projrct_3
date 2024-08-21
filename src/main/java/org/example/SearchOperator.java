package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class SearchOperator {

    private SearchCallBack callBack ;
    public Long user ;
    public SearchOperator (String searchTerm,Long User , SearchCallBack call){
        callBack = call ;
        user = User;
        try (Socket socket = new Socket("localhost", 5000);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            out.writeInt(5);
            out.writeLong(user);
            out.writeUTF(searchTerm.substring(1)); // Send username to server
            if (searchTerm.charAt(0) == '@') {
                out.writeUTF("User");
            } else if (searchTerm.charAt(0) == '#') {
                out.writeUTF("Tag");
            }
            boolean Successsearch = in.readBoolean();
            callBack.onSearchResult(Successsearch);




            out.close();
            in.close();
            socket.close();

        } catch (IOException u) {
            u.printStackTrace();
        }
    }
}
