package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Blockchain{
    ArrayList<MinimalBlock> blocks = new ArrayList<>();


    public Blockchain(){
        this.getGenesisBlock();
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

    public ArrayList<MinimalBlock> fork(){
        MinimalBlock[] copyBlock = new MinimalBlock[this.blocks.size()];

        for(int i = 0; i < copyBlock.length; i++){
            copyBlock[i] = new MinimalBlock(this.blocks.get(i).getIndex(),
                                            this.blocks.get(i).getTimestamp(),
                                            this.blocks.get(i).getStudent(),
                                            this.blocks.get(i).getPreviousHash());
        }
        return new ArrayList<>(Arrays.asList(copyBlock));
    }

    public String[] listAllHash(){
//        System.out.println("taille blockchain : "+this.blocks.size());
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


}
