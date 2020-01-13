package src;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class Blockchain{
    ArrayList<MinimalBlock> blocks = new ArrayList<>();


    public Blockchain(){
        this.getGenesisBlock();
    }

    // pour copier la blockchain
    private Blockchain(Blockchain origin){
        int size = 0;
        for(int i = 0; i < origin.getBlock().size(); i++){
            Student tempStudent = origin.getBlock().get(i).getStudent().fork();
            if (i == 0)
                this.blocks.add(new MinimalBlock(size, origin.getBlock().get(i).getTimestamp(), tempStudent, "empty"));
            else
                this.blocks.add(new MinimalBlock(size, origin.getBlock().get(i).getTimestamp(), tempStudent, this.blocks.get(this.blocks.size() - 1).getCurrentHash()));
            size++;
        }

    }

    // pour créer blockchain depuis un fichier
    public Blockchain(MinimalBlock b){
        this.blocks.add(b);
    }



    private void getGenesisBlock(){
        this.blocks.add(new MinimalBlock(0, LocalDate.now().toString(), new Student(), "empty"));
    }



    public void addBlock(Blockchain this, Student student){
        this.blocks.add(new MinimalBlock(this.blocks.size(), LocalDate.now().toString(), student, this.blocks.get(this.blocks.size() - 1).getCurrentHash()));
    }

    public void addBlock(MinimalBlock block){
        this.blocks.add(new MinimalBlock(this.blocks.size(), block.getTimestamp(), block.getStudent(), this.blocks.get(this.blocks.size() - 1).getCurrentHash()));
    }

    // trouve le block à partir du hash donné
    public int findBlockFromHash(String givenHash){
        for(int i = 0; i < this.getBlock().size(); i++){
            if(this.getBlock().get(i).getCurrentHash().equals(givenHash))
                return i;
        }
        return -1;
    }


    public int getChainSize(){
        return this.blocks.size();
    }

    public boolean verify(ArrayList<MinimalBlock> blocks){
        boolean flag = true;
        for(int i = 0; i < blocks.size(); i++){
            if(this.blocks.get(i).getIndex() != i){
                flag = false;
                System.out.println("Wrong block index at block "+i);
            }


            if(!this.blocks.get(i).getCurrentHash().equals(this.blocks.get(i).hashingFunction())){
                flag = false;
                System.out.println("Wrong hash at block "+i);
            }


            // check que les objets étudiants sont les mêmes
            for(int diploma = 0; diploma < this.blocks.get(i).getStudent().getDiplomes().size(); diploma++){
                String diploma1 = Arrays.toString(this.blocks.get(i).getStudent().getDiplomes().get(diploma));
                String diploma2 = Arrays.toString(blocks.get(i).getStudent().getDiplomes().get(diploma));
                if(this.blocks.get(i).getStudent().getDiplomes().size() != blocks.get(i).getStudent().getDiplomes().size()){
                    flag = false;
                    System.out.println("Difference of number of diploma at block "+i);
                    break;
                }
                if(!diploma1.equals(diploma2)){
                    flag = false;
                    System.out.println("Difference of diploma at block "+i);
                    break;
                }
            }


            if(this.blocks.get(i).getStudent().isStatutValide() != blocks.get(i).getStudent().isStatutValide()){
                flag = false;
                System.out.println("Difference of validity at block "+i);
            }

            if(!this.blocks.get(i).getStudent().getNom().equals(blocks.get(i).getStudent().getNom())){
                flag = false;
                System.out.println("Difference of name at block "+i);
            }

            if(!this.blocks.get(i).getStudent().getPrenom().equals(blocks.get(i).getStudent().getPrenom())){
                flag = false;
                System.out.println("Difference of firstname at block "+i);
            }

            if(!this.blocks.get(i).getStudent().getDateNaissance().equals(blocks.get(i).getStudent().getDateNaissance())){
                flag = false;
                System.out.println("Difference of birthday at block "+i);
            }


            if(!this.blocks.get(i).getStudent().getHashID().equals(blocks.get(i).getStudent().getHashID())){
                flag = false;
                System.out.println("Difference of ID at block "+i);
            }

            if(!this.blocks.get(i).getStudent().getHashJAPD().equals(blocks.get(i).getStudent().getHashJAPD())){
                flag = false;
                System.out.println("Difference of JAPD at block "+i);
            }

            if(!this.blocks.get(i).getStudent().getHashBAC().equals(blocks.get(i).getStudent().getHashBAC())){
                flag = false;
                System.out.println("Difference of BAC at block "+i);
            }


            if (i > 0) {
                if (!this.blocks.get(i - 1).getCurrentHash().equals(blocks.get(i).getPreviousHash())) {
                    flag = false;
                    System.out.println("Wrong previous hash at block " + i);
                }

                if (this.blocks.get(i - 1).getTimestamp().compareTo(this.blocks.get(i).getTimestamp()) >= 1) {
                    flag = false;
                    System.out.println("Backdating at block " + i);
                }
            }
        }
        return flag;
    }

    public boolean verifyAllBlockchain(Blockchain b2){
        if (this.getChainSize() != b2.getChainSize()) {
            System.out.println("Different length");
            return false;
        }
        return this.verify(b2.getBlock());
    }


    // fork la blockchain (= copie la blockchain)
    public Blockchain fork() {
        return new Blockchain(this);
    }


    // liste tous les hash des blocks
    public String[] listAllHash(){
        System.out.println("taille blockchain : "+this.blocks.size());
        String[] allHash = new String[this.blocks.size()];
        for(int i = 0; i < this.blocks.size(); i++)
            allHash[i] = this.blocks.get(i).getCurrentHash();
        return allHash;
    }


    public ArrayList<MinimalBlock> getBlock(){
        return blocks;
    }

    @Override
    public String toString() {
        StringBuilder infoBlockChain = new StringBuilder();
        for (MinimalBlock block : blocks) {
            infoBlockChain.append(block.toString());
            infoBlockChain.append("\n");
        }
        return infoBlockChain.toString();
    }

    public String displayLastBlockInfo(){
        String message = "";
        message += "index         : "+ this.blocks.get(this.blocks.size()-1).getIndex()+"\n";
        message += "timestamp     : "+this.blocks.get(this.blocks.size()-1).getTimestamp()+"\n";
        message += "previous hash : "+this.blocks.get(this.blocks.size()-1).getPreviousHash()+"\n";
        message += "current hash  : "+this.blocks.get(this.blocks.size()-1).getCurrentHash()+"\n";
        return message;
    }

    public MinimalBlock getLastBlock(){
        return this.blocks.get(this.blocks.size()-1);
    }

    public void writeBlockchain() {
        try {
//            // Décommenter pour ajouter possibilité d'append
//            File tempFile = new File("blockchain.txt");
//            boolean exists = tempFile.exists();
//            //utiliser condition quand on voudra implémenter le append

            FileWriter fileWriter = new FileWriter("blockchain.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(this.blocks.size());
            for (MinimalBlock block : this.blocks) {
                printWriter.printf("%d\n", block.getIndex());  // 0
                printWriter.println(block.getTimestamp());  // 1
                printWriter.println(block.getPreviousHash());  // 2
                printWriter.println(block.getCurrentHash());  // 3
                printWriter.println(block.getStudent().getNom());  // 4
                printWriter.println(block.getStudent().getPrenom());  // 5
                printWriter.println(block.getStudent().getDateNaissance());   // 6
                printWriter.println(block.getStudent().getHashID()); // 7
                printWriter.println(block.getStudent().getHashJAPD());  // 8
                printWriter.println(block.getStudent().getHashBAC());  // 9
                for (int j = 0; j < block.getStudent().getDiplomes().size(); j++)
                    printWriter.println(Arrays.toString(block.getStudent().getDiplomes().get(j)));

                printWriter.println("DONE");
            }
            printWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
