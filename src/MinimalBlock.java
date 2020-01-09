package src;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;

// sert à créer la blockchain
// créer un block minimal, et va servir à créer le 1er block
public class MinimalBlock extends ArrayList<MinimalBlock> {
    private int index;
    private LocalDate timestamp;
    private Student student;
    private String previousHash;
    private String currentHash;

    public MinimalBlock(int index, LocalDate timestamp, Student s, String previousHash){
//        System.out.println("index        : "+index);
//        System.out.println("timestamp    : "+timestamp);
//        System.out.println("student      : "+s.toString());
//        System.out.println("previousHash : "+previousHash);
        this.index = index;
        this.timestamp = timestamp;
        this.student = s;
        this.previousHash = previousHash;
        this.currentHash = hashingFunction();
    }



    public String hashingFunction(){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            String data = index + timestamp.toString() + student.getIdStudent() + previousHash;
            byte[] b = data.getBytes(StandardCharsets.UTF_8);
            byte[] messageDigest = md.digest(b);

            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32)
                hashtext.insert(0, "0");

            return hashtext.toString();

        } catch (NoSuchAlgorithmException e){
            System.out.println("Erreur dans hashing function : class Blockchain");
            e.printStackTrace();
        }
        return null;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getCurrentHash(){
        return currentHash;
    }

    public int getIndex(){
        return index;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString(){
        String infoBlock ="";
        infoBlock += index +" "+timestamp+" ";
        infoBlock += student.toString();
        return infoBlock;
    }
}
