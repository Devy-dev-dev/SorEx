package src;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Student {
    static int nbStudent = 0;   // incrémente à chaque nouvel objet Etudiant créé


    /* possède les attributs suivants :
    - statutValide : true = OK, false = BANNED
    - id unique (idStudent)
    - nom
    - prenom
    - date naissance
    - hash carte d'identité
    - hash JAPD
    - hash BAC
    - arrayList de diplômes : [cursus diplôme,
                               nom diplôme,
                               date,
                               université,          <-- détails du diplôme
                               moyenne]
     */

    // ce qui n'est pas hashed :
    private boolean statutValide;
    private String[] diplomeDetail;  // sert à construire diplomes
    private ArrayList<String[]> diplomes;  // enregistre les diplômes

    // Ce qui est hashed :
    private final String idStudent;  // unique ID -> final
    private String nom;
    private String prenom;
//    private LocalDate dateNaissance;  // https://www.baeldung.com/java-8-date-time-intro
    private String dateNaissance;  // https://www.baeldung.com/java-8-date-time-intro
    private String hashID;
    private String hashJAPD;
    private String hashBAC;     // hash tu baccalauréat


    // test que ça marche bien avec des valeurs exemples
    public Student() {
        nbStudent++;

        this.diplomes = new ArrayList<>();
        this.statutValide = true;
        this.nom = hashingFunction("Dupont".getBytes(StandardCharsets.UTF_8));
        this.prenom = hashingFunction("Albert".getBytes(StandardCharsets.UTF_8));
        this.dateNaissance = hashingFunction(LocalDate.of(1994, 2, 20).toString().getBytes(StandardCharsets.UTF_8));
        this.hashID = imageHash("src/documentsStudent/ID.png"); // genere le SHA de l'ID
        this.hashJAPD = imageHash("src/documentsStudent/JAPD.png"); // genere le SHA de la JAPD
        this.hashBAC = imageHash("src/documentsStudent/BAC.png"); // genere le SHA de la JAPD
        this.diplomeDetail = new String[]{"L1", "Géologie", "2017", "UPMC", "13.4"};
        this.diplomes.add(diplomeDetail);

        this.diplomeDetail = new String[]{"L2", "Géologie", "2018", "UPMC", "15.2"};
        this.diplomes.add(diplomeDetail);

        this.idStudent = uniqueHash();


    }

    private String imageHash(String file) {
        File input = new File(file);    // image
        try {
            // try to load the image
            BufferedImage buffImg = ImageIO.read(input);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(buffImg, "png", outputStream);
            byte[] IDdata = outputStream.toByteArray();

            return hashingFunction(IDdata);  // try to convert hash the IDdata from image
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return null;  // si cela echoue on retourne null
    }

    private String hashingFunction(byte[] data) {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input png
            byte[] messageDigest = md.digest(data);

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashtext = new StringBuilder(no.toString(16));

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32)
                hashtext.insert(0, "0");

            return hashtext.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String uniqueHash(){
        String constantData = nbStudent+hashJAPD+hashBAC+dateNaissance;  // on utilise des données qui ne changerons jamais
        byte[] b = constantData.getBytes(StandardCharsets.UTF_8); // on les transforme en byte array
        return hashingFunction(b);
    }



    // affiche les diplômes de la personne
    @Override
    public String toString(){
        StringBuilder affichage = new StringBuilder();
        for (String[] formation : diplomes) {
            for (String value : formation)
                affichage.append(value).append(" ");
            affichage.append("\n");
        }
        return affichage.toString();
    }





    // ------------------------------------------------------------------------------- //
    // ------------------------------ GETTERS & SETTERS ------------------------------ //
    // ------------------------------------------------------------------------------- //
    public int getNbStudent(){
        return nbStudent;
    }

    public String getIdStudent(){
        return idStudent;
    }

    public boolean isStatutValide() {
        return statutValide;
    }

    public void setStatutValide(boolean statutValide) {
        this.statutValide = statutValide;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getHashID() {
        return hashID;
    }

    public String getHashJAPD() {
        return hashJAPD;
    }

    public String getHashBAC() {
        return hashBAC;
    }

    public String[] getDiplomeDetail() {
        return diplomeDetail;
    }

    public void setDiplomeDetail(String[] diplomeDetail) {
        this.diplomeDetail = diplomeDetail;
    }

    public ArrayList<String[]> getDiplomes() {
        return diplomes;
    }

    public void setDiplomes(ArrayList<String[]> diplomes) {
        this.diplomes = diplomes;
    }
}

