import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import src.Blockchain;
import src.Student;

import java.io.*;
import java.lang.invoke.MutableCallSite;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Blockchain b = new Blockchain();

        // creation nouveau etudiant
        String nom = "John";
        String prenom = "Doe";
        LocalDate l = LocalDate.now();
        String id = "src/documentsStudent/ID.png";
        String japd = "src/documentsStudent/JAPD.png";
        String bac = "src/documentsStudent/BAC.png";
        String[] diplomes = new String[] {"L1", "Geol", "UPMC", "2019", "12.7"};

        b.addBlock(new Student(nom, prenom, l, id, japd, bac, diplomes));

        // ajout d'un nouveau diplôme de l'étudiant
        b.getBlock().get(1).getStudent().addDiplomes(new String[] {"L2", "Geol", "UPMC", "2020", "14.7"});
        b.getBlock().get(1).getStudent().addDiplomes(new String[] {"L3", "Geol", "UPMC", "2020", "14.7"});
        Blockchain copy = b.fork();
        b.addBlock(new Student(nom, prenom, l, id, japd, bac, diplomes));
        copy.addBlock(b.getLastBlock());
        // pour partager l'information, il faut que l'université partage l'infos du Student aux autres pour qu'elles puissent créer le bloc.


        // TODO: rajouter possibilité préfecture de mettre à jour carte d'identité + nom et prénom
        // TODO: ajouter possibilité chiffrer addresse et clef privée --> récupérer adresse étudiant

        final Semaphore mutex = new Semaphore(1);
        try {
            mutex.acquire();
            writeBlockchain(b);
            mutex.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    private static void writeBlockchain(Blockchain b) {
        try {
            FileWriter fileWriter = new FileWriter("blockchain.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for(int i = 0; i < b.getBlock().size(); i++){
                printWriter.printf("%d\n",b.getBlock().get(i).getIndex());
                printWriter.println(b.getBlock().get(i).getTimestamp().toString());
                printWriter.println(b.getBlock().get(i).getPreviousHash());
                printWriter.println(b.getBlock().get(i).getCurrentHash());
                printWriter.println(b.getBlock().get(i).getStudent().getIdStudent());
                printWriter.println(b.getBlock().get(i).getStudent().getNom());
                printWriter.println(b.getBlock().get(i).getStudent().getPrenom());
                printWriter.println(b.getBlock().get(i).getStudent().getHashID());
                printWriter.println(b.getBlock().get(i).getStudent().getHashJAPD());
                printWriter.println(b.getBlock().get(i).getStudent().getHashBAC());
                for(int j = 0; j < b.getBlock().get(i).getStudent().getDiplomes().size(); j++)
                    printWriter.println(Arrays.toString(b.getBlock().get(i).getStudent().getDiplomes().get(j)));

                printWriter.println();
            }
            printWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}