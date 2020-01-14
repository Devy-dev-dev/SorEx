package src;

import java.io.*;
import java.net.*;
import java.util.concurrent.Semaphore;

public class PrefectureServer {
    public static void main(String[] args) throws Exception {
        int port;
        port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Prefecture Server is listening");
        SorEx(serverSocket);
    }

    public static void SorEx(ServerSocket server) throws Exception {
        Blockchain b = new Blockchain();
        ServerSocket serverSocket = server;
        Socket clientSocket = serverSocket.accept();
        PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader infromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("   _____            ______    ");
        System.out.println("  / ___/____  _____/ ____/  __");        
        System.out.println("  \\__ \\/ __ \\/ ___/ __/ | |/_/");
        System.out.println(" ___/ / /_/ / /  / /____>  < ");
        System.out.println("/____/\\____/_/  /_____/_/|_|");
        System.out.println("\nWelcome to SorEx! \nThe blockchain for students and universities to handle your administration process.");
        
        final Semaphore mutex = new Semaphore(1);
        try {
            outToClient.println("Please give me the hash of your block in order to update your new ID:");
            System.out.println("Student is typing...");
            mutex.acquire();
            b = b.retrieveBlockchainFromFile();
            String hashOfBlock = infromClient.readLine();
            int indexOfBlock = b.findBlockFromHash(hashOfBlock);
            String hashOfNewID = b.getBlock().get(indexOfBlock).getStudent().imageHash("src/documentsStudent/ID_nouvelle.png");
            b.getBlock().get(indexOfBlock).getStudent().setHashID(hashOfNewID);
            System.out.println("New ID hash: "+hashOfNewID);
            outToClient.println("Congratulations! Your new ID hash is: "+hashOfNewID);
            b.writeBlockchain();
            mutex.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        
    }
}