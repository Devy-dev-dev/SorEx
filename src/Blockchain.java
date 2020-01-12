package src;

import jdk.nashorn.internal.ir.Block;

import java.time.LocalDate;
import java.util.ArrayList;

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



    private void getGenesisBlock(){
        this.blocks.add(new MinimalBlock(0, LocalDate.now(), new Student(), "empty"));
    }



    public void addBlock(Blockchain this, Student student){
        this.blocks.add(new MinimalBlock(this.blocks.size(), LocalDate.now(), student, this.blocks.get(this.blocks.size()-1).getCurrentHash()));
    }

    // trouve le block à partir du hash donné
    public int findBlockFromHash(String givenHash){
        for(int i = 0; i < this.getBlock().size(); i++){
            if(this.getBlock().get(i).getCurrentHash().equals(givenHash))
                return i;
        }
        return -1;
    }


//    public void addBlock(Blockchain this, MinimalBlock blocks){
//        if(!blocks.isEmpty())
//            addBlock(blocks.getStudent());
//    }


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
            if(!this.blocks.get(i).getStudent().getDiplomes().equals(blocks.get(i).getStudent().getDiplomes())){
                flag = false;
                System.out.println("Difference of diploma at block "+i);
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
                System.out.println("Difference of name at block "+i);
            }

            if(!this.blocks.get(i).getStudent().getDateNaissance().equals(blocks.get(i).getStudent().getDateNaissance())){
                flag = false;
                System.out.println("Difference of birthday at block "+i);
            }

            if(!this.blocks.get(i).getStudent().getIdStudent().equals(blocks.get(i).getStudent().getIdStudent())){
                flag = false;
                System.out.println("Difference of student ID at block "+i);
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
        if (this.getChainSize() != b2.getChainSize())
            return false;
        return this.verify(b2.getBlock());
    }


    // fork la blockchain (= copie la blockchain)
    public Blockchain fork() {
        return new Blockchain(this);
    }


    public String[] listAllHash(){
        System.out.println("taille blockchain : "+this.blocks.size());
        String[] allHash = new String[this.blocks.size()];
        for(int i = 0; i < this.blocks.size(); i++)
            allHash[i] = this.blocks.get(i).getCurrentHash();
        return allHash;
    }

    public String[] listAllHashStudent(){
        String[] allHash = new String[this.blocks.size()];
        for(int i = 0; i < this.blocks.size(); i++)
            allHash[i] = this.blocks.get(i).getStudent().getIdStudent();
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
}
