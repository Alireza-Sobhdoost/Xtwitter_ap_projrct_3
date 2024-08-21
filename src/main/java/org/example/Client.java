package org.example;

import java.io.*;
import java.net.*;

public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(socket.getInputStream());

            // sends output to the socket
            out = new DataOutputStream(
                    socket.getOutputStream());
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            return;
        }


        Appframe appframe = new Appframe() ;


        // close the connection
        try {
            if (false) {
                input.close();
                out.close();
                socket.close();
            }
        }
        catch (IOException i) {
            System.out.println(i);
        }
    }

    public DataOutputStream getOut(){
        return out;
    }
    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 5000);
    }
}