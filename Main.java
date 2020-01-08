import src.Student;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println("Hello World");
        System.out.println("Marche avec Intellij");

        Student s = new Student();
        System.out.println(s.getNom());
        System.out.println(s.getDateNaissance());
        System.out.println(s.getHashID());
    }
}