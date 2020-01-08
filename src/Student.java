package src;

import java.time.LocalDate;
import java.util.ArrayList;

public class Student {
    /* possède les attributs suivants :
    - statut : OK, BANNED
    - nom
    - prenom
    - date naissance
    - hash carte d'identité
    - hash JAPD
    - arrayList de diplômes : [cursus diplôme,
                               nom diplôme,
                               date,
                               université]
     */

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    String hashID;
    String hashJAPD;
    String[] diplomesDetail;
    ArrayList<String> diplomes;

}
