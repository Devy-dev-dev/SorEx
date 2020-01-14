package src;

import java.io.*;
import java.time.*;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

class ServerThread extends Thread{  
    Student student;
    String message;
    String line = null;
    Socket socket = null;
    PrintWriter outToClient = null;
    BufferedReader inFromUser = null;
    BufferedReader inFromClient = null;
    Blockchain b = new Blockchain();
    final Semaphore mutex = new Semaphore(1);

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
        String choice;
        Boolean running = true;
        try{
            while(running){
                outToClient.println("Please choose your service: [1]new inscription [2]verify status [3]add diplome [4]quit SorEx");
                System.out.println("Student is typing...");
                choice = inFromClient.readLine();
                if(choice.compareTo(Integer.toString(4))==0){
                    running = false;
                    break;
                }
                if(Integer.parseInt(choice) == 1){ //new inscription
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
                        
                        try {
                            mutex.acquire();
                            b = b.retrieveBlockchainFromFile();
                            b.addBlock(student);
                            String blockHash = b.getBlock().get(b.getBlock().size()-1).getCurrentHash();
                            System.out.println("Student is waiting...");
                            System.out.println("New block hash: "+blockHash);
                            outToClient.println("Congratulations! Blocked created. Your hash is: "+blockHash);
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
                }else if(Integer.parseInt(choice) == 2){ //verify status
                    try {
                        outToClient.println("Please provide me the hashcode of your block to verify your status:");
                        System.out.println("Student is typing...");
                        String hashOfBlock = inFromClient.readLine();
                        b = b.retrieveBlockchainFromFile();
                        int indexOfBlock = b.findBlockFromHash(hashOfBlock);
                        Boolean status = b.getBlock().get(indexOfBlock).getStudent().isStatutValide();
                        if(status == true){
                            outToClient.println("Your status is valid.");
                            System.out.println("The status of student is valid");
                        }else{
                            outToClient.println("Your status is invalid.");
                            System.out.println("The status of student is invalid");
                        }
                    } catch (IOException e) {
                        line = this.getName(); //reused String line for getting thread name
                        System.out.println("IO Error/ Client "+line+" terminated abruptly");
                    }
                }else{ //add diplome
                    try {
                        outToClient.println("Please provide me the hashcode of your block to add your diplome:");
                        System.out.println("Student is typing...");
                        String hashOfBlock = inFromClient.readLine();
                        mutex.acquire();
                        b = b.retrieveBlockchainFromFile();
                        int indexOfBlock = b.findBlockFromHash(hashOfBlock);
                        String[] diplomes = {"L3", "DANT", "Sorbonne", "2020", "14.7"};
                        b.getBlock().get(indexOfBlock).getStudent().addDiplomes(diplomes);
                        outToClient.println("Diplome added: " + Arrays.toString(diplomes));
                        System.out.println("Diplome added: " + Arrays.toString(diplomes));
                        b.writeBlockchain();
                        mutex.release();
                    } catch (IOException e) {
                        line = this.getName(); //reused String line for getting thread name
                        System.out.println("IO Error/ Client "+line+" terminated abruptly");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("after while");
        }catch (IOException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client "+line+" terminated abruptly");
        } catch(NullPointerException e){
            line = this.getName(); //reused String line for getting thread name
            System.out.println("Client "+ line +" Closed");
        }finally{    
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