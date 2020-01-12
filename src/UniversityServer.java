package src;

import java.io.IOException;
//import java.net.ServerSocket;
import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.time.LocalDate;

public class UniversityServer {
    static Student s;
    static Blockchain b = new Blockchain();
    public static void main(String[] args) throws Exception {
        
        // Scanner myObj = new Scanner(System.in); 
        // System.out.println("Please enter the domain name:"); //localhost
        // String domainName = myObj.nextLine();
        // System.out.println("Please enter the port number:"); //3024
        // int port = myObj.nextInt();
        // myObj.nextLine();
        ServerSocket serverSocket = new ServerSocket(3024);
        // System.out.println("Waiting for connexions in port " + port);
        System.out.println("   _____            ______    ");
        System.out.println("  / ___/____  _____/ ____/  __");        
        System.out.println("  \\__ \\/ __ \\/ ___/ __/ | |/_/");
        System.out.println(" ___/ / /_/ / /  / /____>  < ");
        System.out.println("/____/\\____/_/  /_____/_/|_|");
        System.out.println("\nWelcome to SorEx! \nThe blockchain for students and universities to handle administration process.");
        SorEx(serverSocket);
    }

    public static void SorEx(ServerSocket server) throws Exception {
        ServerSocket serverSocket = server;
        Socket clientSocket = serverSocket.accept();
        System.out.println("Connection established");
        boolean running = true;

        while(running){

        String message;
        PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader infromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
        outToClient.println("Please enter your first name:");
        System.out.println("Student is typing...");
        String firstName = infromClient.readLine();

        outToClient.println("Please enter your last name:");
        String LastName = infromClient.readLine();

        outToClient.println("Please enter your birth date YYYY-MM-DD:");
        LocalDate birthDate = LocalDate.parse(infromClient.readLine());

        String[] diplomes = new String[] {"L1", "Geol", "UPMC", "2019", "12.7"};

        s = new Student(firstName, 
                        LastName, 
                        birthDate, " ", " ", " ",
                        diplomes);
        b.addBlock(s);
        String blockHash = b.getBlock().get(b.getBlock().size()-1).getCurrentHash();
        System.out.println("Student is waiting...");
        System.out.println("New block hash: "+blockHash);

        outToClient.println("Congratulations! Your hash is: "+blockHash);
        }
        
    }
}
