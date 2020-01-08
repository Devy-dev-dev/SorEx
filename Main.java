import src.Student;

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

        System.out.println("Test");
    }
}