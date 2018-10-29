package view;

public class AbstractGameTwoNode extends AbstractGameModel{
    @Override
    public byte isConneted(int itemAI, int itemAJ, int itemBI, int itemBJ) {
        if (linkByLine(itemAI,itemAJ,itemBI,itemBJ)){
            return 1;
        }if(linkByOneTurn(itemAI,itemAJ,itemBI,itemBJ)){
            return 2;
        }if(linkByTwoturn(itemAI,itemAJ,itemBI,itemBJ)){
            return 3;
        }
        return 0;
    }
}
