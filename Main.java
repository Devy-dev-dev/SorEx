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
        System.out.println("b : ");
        b.addDiplomaFromUpdatedBlockchain(copy);
        System.out.println(b);
        System.out.println("\n\n\ncopy : ");
        System.out.println(copy);
        System.out.println("b = copy ? : "+b.verifyAllBlockchain(copy));


        // TODO: rajouter possibilité préfecture de mettre à jour carte d'identité + nom et prénom
        // TODO: ajouter possibilité chiffrer addresse et clef privée --> récupérer adresse étudiant

        // protection de la ressource critique
        final Semaphore mutex = new Semaphore(1);
        try {
            mutex.acquire();
            b.writeBlockchain();
            mutex.release();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        Blockchain bRead = retrieveBlockchainFromFile();

        System.out.println("identique ? : "+b.verifyAllBlockchain(bRead));

    }

    public static Blockchain retrieveBlockchainFromFile(){
        try {
            File file = new File("blockchain.txt");
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            StringBuilder sb = new StringBuilder();    //constructs a string buffer with no characters
            String line;
            String temp = "";   // utilisée pour remplir dans line l'ArrayList des blocs lus

            // on récupère la taille de la bolckchain
            line = br.readLine();
            int taille = Integer.parseInt(line);
            String[] donnees;   // chaque indice contient 1 bloc

            // on lit le reste de la blockchain (sans la taille au début du fichier)
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == "[".charAt(0)) {
                    temp = line;
                    while (!temp.contains("DONE")) {
                        temp = br.readLine();
                        line += temp;
                    }
                }
                sb.append(line);
                sb.append("\n");

            }
            String blockchainText = sb.toString();
            blockchainText = blockchainText.replace("][", "\n");
            blockchainText = blockchainText.replace("[", "");
            blockchainText = blockchainText.replace("]", "\n");
            donnees = blockchainText.split("\n");


            String[] blockchainArray = new String[taille];
            int cpt = 0;
            for(int i = 0; i < donnees.length; i++){
                blockchainArray[cpt] = "";
                while(!donnees[i].equals("DONE")){
                    blockchainArray[cpt] += donnees[i]+"\n";
                    i++;
                }
                cpt++;
            }

            Blockchain bTemp = null;
            for(int i = 0; i < blockchainArray.length; i++){
                String[] data = blockchainArray[i].split("\n");
                int parcoursData = 10;
                StringBuilder inlineformation = new StringBuilder();
                while (parcoursData < data.length){
                    inlineformation.append(data[parcoursData]).append("\n");
                    parcoursData++;
                }
                inlineformation = new StringBuilder(inlineformation.toString().replace(",", ""));
                String[] formation = inlineformation.toString().split("\n");

                // 1. on créer un étudiant depuis les valeurs lues
                Student s = new Student();
                s = s.newStudentFromFile(
                        data[4],
                        data[5],
                        data[6],
                        data[7],
                        data[8],
                        data[9],
                        formation
                );

                // 2. on créer un block ensuite
                MinimalBlock block = new MinimalBlock();
                block = block.minimalBlockFromFile(
                        Integer.parseInt(data[0]),
                        data[1],
                        s,
                        data[2],
                        data[3]
                        );

                // 3. on met a jour la blockchain
                if(i == 0)
                    bTemp = new Blockchain(block);
                else
                    bTemp.addBlock(block);

            }
            return bTemp;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;  // si il y a eu un probleme

    }

}