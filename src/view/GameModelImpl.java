package view;

public class GameModelImpl extends AbstractGameModel{
    @Override
    public byte isConneted(int itemAI, int itemAJ, int itemBI, int itemBJ) {
        if (linkByLine(itemAI,itemAJ,itemBI,itemBJ)){
            return 1;
        }else {
            return 0;
        }
    }
}
