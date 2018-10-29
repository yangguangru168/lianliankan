package view;

public class AbstractGameNode extends AbstractGameModel{
    @Override
    public byte isConneted(int itemAI, int itemAJ, int itemBI, int itemBJ) {
        if (linkByLine(itemAI,itemAJ,itemBI,itemBJ)){
            return 1;
        }if(linkByOneTurn(itemAI,itemAJ,itemBI,itemBJ)){
            return 2;
        }
        return 0;
    }
}
