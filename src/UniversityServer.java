package src;

import java.io.*;
import java.net.*;


public class UniversityServer {

    public static void main(String[] args){
        Socket s = null;
        ServerSocket s1 = null;
        System.out.println("Server Listening......");

        try{
            int port = Integer.parseInt(args[0]);
            s1 = new ServerSocket(port);
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Server error");
        }
        
        while(true){
            try{
                s = s1.accept();
                System.out.println("connection Established");
                ServerThread st = new ServerThread(s);
                st.start();
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("Connection Error");
            }
        }
    }
}
