import src.Blockchain;
import src.MinimalBlock;
import src.Student;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.Semaphore;

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

        // tester mairie qui modifie le hash de l'étudiant X
        String blocDeEtudiant = "72c961a2868c095c2344ccc49e5efe8e50dafa20d5a9ed60154b6a3f183ddada582aaaa8d43ac1fed2725a526c47db32189d33a334fae4ce52ff709bb9dff221";


        // Robin n'est pas concerné
        Blockchain copy = b.fork();
        b.addBlock(new Student(nom, prenom, l, id, japd, bac, diplomes));
        copy.addBlock(b.getLastBlock());
        copy.getBlock().get(1).getStudent().addDiplomes(diplomes);
        copy.getBlock().get(1).getStudent().addDiplomes(diplomes);
        copy.getBlock().get(1).getStudent().addDiplomes(diplomes);
        b.addDiplomaFromUpdatedBlockchain(copy);


    }


}