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


//        System.out.println("Test copy !");
//        Blockchain copy = b.fork();
//        System.out.println("Copy == origin : "+copy.verifyAllBlockchain(b));
//
//        copy.getBlock().get(1).getStudent().addDiplomes(new String[] {"L3", "Geol", "UPMC", "2020", "17.5"});
//        System.out.println("Copy == origin : "+copy.verifyAllBlockchain(b));
//
//
//        copy.addBlock(new Student(nom, prenom, l, id, japd, bac, diplomes));
//        System.out.println("Copy == origin : "+copy.verifyAllBlockchain(b));
//
//        System.out.println();
//        System.out.println("Copy : ");
//        System.out.println(copy.toString());
//        System.out.println("Original : ");
        System.out.println(b.toString());

        String toFind = "b6c3fff3755946f3c3d67e40becb55f4c7f919d2c70fc1c4bfc61d7d6d23da5a22366a7b695e642a0d2a30cd4987e581016a93a5ddeed839ae1126a7964b9e6";
        System.out.print("index de "+toFind+" : ");
        System.out.println(b.findBlockFromHash(toFind));







    }
}