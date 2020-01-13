package src;

import java.io.*;
import java.time.*;
import java.util.*;
import java.net.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.concurrent.Semaphore;


class ServerThread extends Thread{  
    Student student;
    Blockchain b = new Blockchain();

    String line = null;
    String message;
    BufferedReader inFromClient = null;
    PrintWriter outToClient = null;
    BufferedReader inFromUser = null;
    Socket socket = null;

    public ServerThread(Socket s){  
        this.socket = s;
    }

    public void run() {
        try{
            System.out.println("   _____            ______    ");
            System.out.println("  / ___/____  _____/ ____/  __");        
            System.out.println("  \\__ \\/ __ \\/ ___/ __/ | |/_/");
            System.out.println(" ___/ / /_/ / /  / /____>  < ");
            System.out.println("/____/\\____/_/  /_____/_/|_|");
            System.out.println("\nWelcome to SorEx! \nThe blockchain for students and universities to handle administration process.");
            outToClient = new PrintWriter(socket.getOutputStream(),true);
            inFromUser = new BufferedReader(new InputStreamReader(System.in));
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch(IOException e){
            System.out.println("IO error in server thread");
        }

        try {
            outToClient.println("Please enter your first name:");
            System.out.println("Student is typing...");
            String firstName = inFromClient.readLine();
    
            outToClient.println("Please enter your last name:");
            String LastName = inFromClient.readLine();
    
            outToClient.println("Please enter your birth date YYYY-MM-DD:");
            LocalDate birthDate = LocalDate.parse(inFromClient.readLine());
            
            String[] diplomes = new String[] {"L3", "Info", "UPMC", "2019", "14.7"};

            student = new Student(firstName, 
                            LastName, 
                            birthDate, 
                            "src/documentsStudent/ID.png", 
                            "src/documentsStudent/JAPD.png", 
                            "src/documentsStudent/BAC.png",
                            diplomes);
            //student = new Student();
            b.addBlock(student);

            String blockHash = b.getBlock().get(b.getBlock().size()-1).getCurrentHash();
            System.out.println("Student is waiting...");
            System.out.println("New block hash: "+blockHash);
            outToClient.println("Congratulations! Your hash is: "+blockHash);

            final Semaphore mutex = new Semaphore(1);
            try {
                mutex.acquire();
                b.writeBlockchain();
                mutex.release();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        } catch (IOException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        } catch(NullPointerException e){
            line = this.getName(); //reused String line for getting thread name
            System.out.println("Client "+ line +" Closed");
        }

        finally{    
            try{
                System.out.println("Connection Closing..");
                if (inFromClient!=null){
                    inFromClient.close(); 
                    System.out.println("Socket Input Stream Closed");
                }

                if(outToClient!=null){
                    outToClient.close();
                    System.out.println("Socket Out Closed");
                }
                if (socket!=null){
                    socket.close();
                    System.out.println("Socket Closed");
                }
            }catch(IOException ie){
                System.out.println("Socket Close Error");
            }
        }//end finally
    }
}