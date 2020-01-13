package src;

import java.io.*;
import java.time.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class UniversityServer {
<<<<<<< HEAD
=======
    private final Object mutex = new Object();

>>>>>>> f7813f30366f88fe6b84b9daa49e35e8ac43f822
    public static void main(String[] args){
        Socket s = null;
        ServerSocket s1 = null;
        System.out.println("Server Listening......");

        try{
            s1 = new ServerSocket(8080);
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
