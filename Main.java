import src.Blockchain;
import src.Student;

import java.time.LocalDate;

// TODO: creer une classe EnrolledStudent qui possède une copie du hash de ses données depuis le block
//  car pour l'instant les étudiants ne peuvent pas utiliser la technologie
public class Main {
    public static void main(String[] args) {
//        Student s = new Student();
//        Student s1 = new Student();
//        System.out.println("id s  : "+s.getIdStudent());
//        System.out.println("id s1 : "+s1.getIdStudent());
//        System.out.println(s.getNom());
//        System.out.println(s.getPrenom());
//        System.out.println(s.getDateNaissance());
//        System.out.println(s.getHashID());
//        System.out.println(s.getHashJAPD());
//        System.out.println(s.getHashBAC());
//        if (s.isStatutValide())
//            System.out.println("Est un étudiant valide");
//        else
//            System.out.println("A été banni du système");
//
//
////        ArrayList<String[]> formations = s.getDiplomes();
//        System.out.println(s.toString());


        Blockchain b = new Blockchain();

        // creation nouveau etudiant
        String nom = "John";
        String prenom = "Doe";
        LocalDate l = LocalDate.now();
        String id = " ";
        String japd = " ";
        String bac = " ";
        String[] diplomes = new String[] {"L1", "Geol", "UPMC", "2019", "12.7"};

        b.addBlock(new Student(nom,
                                prenom,
                                l,
                                id,
                                japd,
                                bac,
                                diplomes));
        // ajout d'un nouveau diplôme de l'étudiant
        b.getBlock().get(1).getStudent().addDiplomes(new String[] {"L2", "Geol", "UPMC", "2020", "14.7"});

//        String[] tousLesHash = b.listAllHash();
//        for(String hashS: tousLesHash)
//            System.out.println(hashS);
//
//        String[] tousLesHashEtudiant = b.listAllHashStudent();
//        for(String hashS: tousLesHashEtudiant)
//            System.out.println(hashS);

        /*
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("Test block a le bon hash étudiant");
        for(int i = 0; i < b.getBlock().size(); i++){
            System.out.println("hash block         : "+b.getBlock().get(i).getCurrentHash());
            System.out.println("hash etudiant      : "+b.getBlock().get(i).getStudent().getIdStudent());
            System.out.println("formation etudiant : "+b.getBlock().get(i).getStudent().toString());
            System.out.println();
        }

        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("Test toString() blockchain");
        System.out.println(b.toString());
         */


        // MARCHE PAS ENCORE
        System.out.println("Test copy !");
        Blockchain copy = b.fork();
        copy.getBlock().get(1).getStudent().addDiplomes(new String[] {"L3", "Geol", "UPMC", "2020", "17.5"});

        System.out.println("Copy : ");
        System.out.println(copy.toString());
        System.out.println("Original : ");
        System.out.println(b.toString());





    }
}