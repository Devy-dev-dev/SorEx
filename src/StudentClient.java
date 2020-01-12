package src;

import java.io.*;
import java.time.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class StudentClient {
    public static void main(String[] args) throws IOException{
        InetAddress address = InetAddress.getLocalHost();
        Socket s = null;
        String line = null;
        BufferedReader inFromUser = null;
        BufferedReader inFromServer = null;
        DataOutputStream outToServer = null;
        try {
            System.out.println("Connection established");
            System.out.println("Client Address : "+address+"\n");
            System.out.println("   _____            ______    ");
            System.out.println("  / ___/____  _____/ ____/  __");        
            System.out.println("  \\__ \\/ __ \\/ ___/ __/ | |/_/");
            System.out.println(" ___/ / /_/ / /  / /____>  < ");
            System.out.println("/____/\\____/_/  /_____/_/|_|");
            System.out.println("\nWelcome to SorEx! \nThe blockchain for students and universities to handle administration process.");
            
            s = new Socket(address, 8080);
            inFromUser = new BufferedReader(new InputStreamReader(System.in));
            inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            outToServer = new DataOutputStream(s.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        String fromServer, message;
        try{
            fromServer = inFromServer.readLine();
            System.out.println("From Server: " + fromServer);// first name question
            message = inFromUser.readLine();
            outToServer.writeBytes(message + '\n');// write answer

            fromServer = inFromServer.readLine();
            System.out.println("From Server: " + fromServer);// last name question
            message = inFromUser.readLine();
            outToServer.writeBytes(message + '\n');// write answer

            fromServer = inFromServer.readLine();
            System.out.println("From Server: " + fromServer);// birth date question
            message = inFromUser.readLine();
            outToServer.writeBytes(message + '\n');// write answer

            System.out.println("SorEx is processing...");
            fromServer = inFromServer.readLine();
            System.out.println("From Server: " + fromServer);// return hash code
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Socket read Error");
        }
        finally{
            inFromServer.close();
            outToServer.close();
            inFromUser.close();
            s.close();
            System.out.println("Connection Closed");
        }
    }
}
