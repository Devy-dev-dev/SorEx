package src;

import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class StudentClient {
    public static void main(String[] args) throws Exception {
        // Scanner myObj = new Scanner(System.in); 
        // System.out.println("Please enter the domain name:"); //localhost
        // String domainName = myObj.nextLine();
        // System.out.println("Please enter the port number:"); //3024
        // int port = myObj.nextInt();
        // myObj.nextLine();
        Socket clientSocket = new Socket("localhost", 3024);
        System.out.println("Connection established");
        SorEx();
    }

    public static void SorEx() throws Exception {
        String fromServer, message;
        Socket clientSocket = new Socket();
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("   _____            ______    ");
        System.out.println("  / ___/____  _____/ ____/  __");        
        System.out.println("  \\__ \\/ __ \\/ ___/ __/ | |/_/");
        System.out.println(" ___/ / /_/ / /  / /____>  < ");
        System.out.println("/____/\\____/_/  /_____/_/|_|");
        System.out.println("\nWelcome to SorEx! \nThe blockchain for students and universities to handle your administration process.");
        
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
        
    }

}
