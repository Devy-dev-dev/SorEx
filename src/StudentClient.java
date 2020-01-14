package src;

import java.io.*;
import java.net.*;

public class StudentClient {
    public static void main(String[] args) throws IOException{
        InetAddress address = InetAddress.getLocalHost();
        Socket s = null;
        BufferedReader inFromUser = null;
        BufferedReader inFromServer = null;
        DataOutputStream outToServer = null;
        int port;
        port = Integer.parseInt(args[0]);
        String fromServer, message;
        try {
            System.out.println("Connection established");
            System.out.println("Client Address : "+address+"\n");
            System.out.println("   _____            ______    ");
            System.out.println("  / ___/____  _____/ ____/  __");        
            System.out.println("  \\__ \\/ __ \\/ ___/ __/ | |/_/");
            System.out.println(" ___/ / /_/ / /  / /____>  < ");
            System.out.println("/____/\\____/_/  /_____/_/|_|");
            System.out.println("\nWelcome to SorEx! \nThe blockchain for students and universities to handle administration process.");
            s = new Socket(address, port);
            inFromUser = new BufferedReader(new InputStreamReader(System.in));
            inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
            outToServer = new DataOutputStream(s.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
            System.err.print("IO Exception");
        }

        if(port == 6060){ // 6060 for university, 8080 for prefecture
            try{
                fromServer = inFromServer.readLine();
                System.out.println("From Server: " + fromServer);// choose service
                message = inFromUser.readLine();
                outToServer.writeBytes(message + '\n');// write 1 or 2 or 3
                if(Integer.parseInt(message) == 1){
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
                }else if(Integer.parseInt(message) == 2){
                    try{
                        fromServer = inFromServer.readLine();
                        System.out.println("From Server: " + fromServer);// server asks for block's hash code
                        message = inFromUser.readLine();
                        outToServer.writeBytes(message + '\n');// write hash code
            
                        fromServer = inFromServer.readLine();
                        System.out.println("From Server: " + fromServer);// return answer valid/invalid
                    }catch(IOException e){
                        e.printStackTrace();
                        System.out.println("Socket read Error");
                    }
                }else{
                    try{
                        fromServer = inFromServer.readLine();
                        System.out.println("From Server: " + fromServer);// server asks for block's hash code
                        message = inFromUser.readLine();
                        outToServer.writeBytes(message + '\n');// write hash code
            
                        fromServer = inFromServer.readLine();
                        System.out.println("From Server: " + fromServer);// return diplome added
                    }catch(IOException e){
                        e.printStackTrace();
                        System.out.println("Socket read Error");
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
                System.out.println("Socket read Error");
            }finally{
                inFromServer.close();
                outToServer.close();
                inFromUser.close();
                s.close();
                System.out.println("Connection Closed");
            }
        }else{ // for prefecture
            try{
                fromServer = inFromServer.readLine();
                System.out.println("From Server: " + fromServer);// hash of block question
                message = inFromUser.readLine();
                outToServer.writeBytes(message + '\n');// write hash of block
    
                System.out.println("SorEx is processing...");
                fromServer = inFromServer.readLine();
                System.out.println("From Server: " + fromServer);// get new ID hash from prefecture
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
}
