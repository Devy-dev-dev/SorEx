package src;

import java.time.LocalDate;
import java.util.ArrayList;

public class Blockchain{
    ArrayList<MinimalBlock> blocks = new ArrayList<>();


    public Blockchain(){
        this.getGenesisBlock();
    }

    // pour fork
    // TODO: regarder si ça marche bien
    private Blockchain(MinimalBlock block){
        this.addBlock(block);
    }


    private void getGenesisBlock(){
        this.blocks.add(new MinimalBlock(0, LocalDate.now(), new Student(), "empty"));
    }

    public void addBlock(Blockchain this, Student student){
        this.blocks.add(new MinimalBlock(this.blocks.size(),
                                     LocalDate.now(),
                                     student,
                                     this.blocks.get(this.blocks.size()-1).getCurrentHash())
        );
    }

    public void addBlock(Blockchain this, MinimalBlock blocks){
        if(!blocks.isEmpty())
            addBlock(blocks.getStudent());
    }



    public int getChainSize(){
        return this.blocks.size();
    }

    public boolean verify(MinimalBlock blocks){
        boolean flag = true;
        for(int i = 0; i < blocks.size(); i++){
            if(this.blocks.get(i).getIndex() != i){
                flag = false;
                System.out.println("Wrong block index at block "+i);
            }

            if(!this.blocks.get(i-1).getCurrentHash().equals(blocks.get(i).getPreviousHash())){
                flag = false;
                System.out.println("Wrong previous hash at block "+i);
            }

            if(!this.blocks.get(i).getCurrentHash().equals(this.blocks.get(i).hashingFunction())){
                flag = false;
                System.out.println("Wrong hash at block "+i);
            }

            if(this.blocks.get(i-1).getTimestamp().compareTo(this.blocks.get(i).getTimestamp()) >= 1){
                flag = false;
                System.out.println("Backdating at block "+i);
            }
        }
        return flag;
    }

    // sert à dupliquer la blockchain
    // TODO: Ne marche pas
    /*
//    public ArrayList<MinimalBlock> fork(){
    public Blockchain fork(){
        MinimalBlock[] copyBlock = new MinimalBlock[this.blocks.size()];

        for(int i = 0; i < copyBlock.length; i++){
            copyBlock[i] = new MinimalBlock(this.blocks.get(i).getIndex(),
                                            this.blocks.get(i).getTimestamp(),
                                            this.blocks.get(i).getStudent(),
                                            this.blocks.get(i).getPreviousHash());
        }
        ArrayList<MinimalBlock> blockCopied = new ArrayList<>(Arrays.asList(copyBlock));
        System.out.println("taille blockcopied : "+blockCopied.size());
        Blockchain b = new Blockchain(blockCopied.get(0));
//        b.addBlock(blockCopied.get(0));
        for(int i = 1; i < blockCopied.size(); i++) {
            System.out.println("blockCopied(i) : "+blockCopied.get(i).getStudent().getIdStudent());
            b.addBlock(blockCopied.get(i));
        }

        return b;
    }

     */

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
