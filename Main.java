import src.Student;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Student s = new Student();
        System.out.println(s.getNom());
        System.out.println(s.getPrenom());
        System.out.println(s.getDateNaissance());
        System.out.println(s.getHashID());
        System.out.println(s.getHashJAPD());
        System.out.println(s.getHashBAC());
        if (s.isStatutValide())
            System.out.println("Est un étudiant valide");
        else
            System.out.println("A été banni du système");


        ArrayList<String[]> formations = s.getDiplomes();


        for (int i = 0; i < formations.size(); i++){
            String[] formation = formations.get(i);
            System.out.print(i+") ");
            for (String value : formation) {
                System.out.print(value + " ");
            }
            System.out.println();
        }

    }
}