import src.Blockchain;
import src.Student;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Blockchain b = new Blockchain();

        // creation nouveau etudiant
        String nom = "John";
        String prenom = "Doe";
        LocalDate l = LocalDate.now();
        String id = " ";
        String japd = " ";
        String bac = " ";
        String[] diplomes = new String[] {"L1", "Geol", "UPMC", "2019", "12.7"};

        b.addBlock(new Student(nom, prenom, l, id, japd, bac, diplomes));

        // ajout d'un nouveau diplôme de l'étudiant
        b.getBlock().get(1).getStudent().addDiplomes(new String[] {"L2", "Geol", "UPMC", "2020", "14.7"});


        System.out.println("Test copy !");
        Blockchain copy = b.fork();
        copy.getBlock().get(1).getStudent().addDiplomes(new String[] {"L3", "Geol", "UPMC", "2020", "17.5"});
        copy.addBlock(new Student(nom, prenom, l, id, japd, bac, diplomes));

        System.out.println("Copy : ");
        System.out.println(copy.toString());
        System.out.println("Original : ");
        System.out.println(b.toString());







    }
}