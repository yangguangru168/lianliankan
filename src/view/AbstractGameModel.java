package view;

import java.util.Random;

public abstract class AbstractGameModel {
    //map存放图片
    public int[][] map;
    //确定折点
    public Node[] turingPoints = new Node[2];

    public void setMap(int[][] map) {
        this.map = map;
    }

    //保存折点信息的方法
    public void setPoint(int i, int j, int index) {
        if (turingPoints[index - 1] == null) {
            turingPoints[index - 1] = new Node();
        }
        turingPoints[index - 1].setI(i);
        turingPoints[index - 1].setJ(j);

    }

    //随机生成图片
    //(元素个数，行数，列数，图片资源个数)
    public int[][] initMapHelper(int elementCount,
                                 int row, int col, int elementIamge) {
        //偶数
        if ((elementCount % 2 != 0) || (elementCount != (row - 2) * (col - 2))) {
            throw new java.lang.IllegalArgumentException("不符合创建条件");
        }
        //生成随机数
        Random random = new Random();
        //定义一维数组
        int[] initElement = new int[elementCount];
        for (int i = 0; i < elementCount; i += 2) {
            int r = Math.abs(random.nextInt()) % elementIamge + 1;
            initElement[i] = r;
            initElement[i + 1] = r;
        }
        //打乱顺序
        initElement = getRandomArrayHelper(initElement);
        //放入二维数组
        int[][] finalMap = new int[row][col];
        int index = 0;
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                finalMap[i][j] = initElement[index];
                index++;
            }
        }
        return finalMap;
    }

    //随机生成一维数组
    public int[] getRandomArrayHelper(int[] srcArray) {
        Random random1 = new Random();
        int resultArray[] = new int[srcArray.length];//定义一个与原始数组相同大小的数组来存放打乱顺序后的结果
        //还剩下的元素个数
        int srcIndex = srcArray.length;//64
        for (int i = 0; i < srcArray.length; i++) {
            int randomIndex = Math.abs(random1.nextInt() % srcIndex);//取余0-63
            //随机取数组下标
            resultArray[i] = srcArray[randomIndex];
            //将最后一个未使用的元素和取出的元素交换
            srcArray[randomIndex] = srcArray[--srcIndex];//63
            //srcArray[randomIndex]赋值之后变成了多余的数据，应当将其替换掉
            //替换后srcIndex减1,random1.nextInt() % srcIndex也随机减一，这样对resultArray赋值时，取到的都是srcArray中索引不重复的数据
        }
        return resultArray;
    }

    //一折算法
    public boolean linkByOneTurn(int itemAI, int itemAJ, int itemBI, int itemBJ) {
        if ((map[itemAI][itemBJ] == 0)
                && linkByLine(itemAI, itemAJ, itemAI, itemBJ)
                && linkByLine(itemBI, itemBJ, itemAI, itemBJ)
                && (map[itemAI][itemAJ] == map[itemBI][itemBJ])){
            setPoint(itemAI, itemBJ, 1);
            return true;
        } else if ((map[itemBI][itemAJ] == 0)
                && linkByLine(itemAI, itemAJ, itemBI, itemAJ)
                && linkByLine(itemBI, itemBJ, itemBI, itemAJ)
                && map[itemAI][itemAJ] == map[itemBI][itemBJ]) {
            setPoint(itemBI, itemAJ, 1);
            return true;
        } else {
            return false;
        }
    }
    //二折调用一折的算法
    public boolean linkByOneTurn1(int itemAI, int itemAJ, int itemBI, int itemBJ) {
        if ((map[itemAI][itemBJ] == 0)
                && linkByLine(itemAI, itemAJ, itemAI, itemBJ)
                && linkByLine(itemBI, itemBJ, itemAI, itemBJ)
                ){
            setPoint(itemAI, itemBJ, 1);
            return true;
        } else if ((map[itemBI][itemAJ] == 0)
                && linkByLine(itemAI, itemAJ, itemBI, itemAJ)
                && linkByLine(itemBI, itemBJ, itemBI, itemAJ)
                ) {
            setPoint(itemBI, itemAJ, 1);
            return true;
        } else {
            return false;
        }
    }

    //两折
    public boolean linkByTwoturn(int itemAI, int itemAJ, int itemBI, int itemBJ) {

        for (int i = 0; i < map.length; i++) {
            if ((map[i][itemAJ] == 0)
                    && linkByLine(itemAI, itemAJ,i, itemAJ)
                    && linkByOneTurn1( itemBI, itemBJ,i, itemAJ)
                    && map[itemAI][itemAJ] == map[itemBI][itemBJ]) {
                setPoint(i, itemAJ, 2);
                return true;
            }
        }
        for (int j = 0; j < map[itemAI].length; j++) {
            if ((map[itemAI][j] == 0)
                    && linkByLine(itemAI, itemAJ,itemAI, j)
                    && linkByOneTurn1(itemBI, itemBJ,itemAI, j)
                    && map[itemAI][itemAJ] == map[itemBI][itemBJ]) {
                setPoint(itemAI, j, 2);
                return true;
            }
        }
        return false;
    }


    //直连条件
    public boolean linkByLine(int itemAI, int itemAJ, int itemBI, int itemBJ) {
        if (itemAI == itemBI) {//两个元素在同一水平线，纵坐标相等
            int minJ = itemAJ < itemBJ ? itemAJ : itemBJ;
            int maxJ = itemAJ > itemBJ ? itemAJ : itemBJ;
            //取出两个元素的列的较大值和较小值，以便后续进行循环
            for (int j = minJ + 1; j < maxJ; j++) {
                if (map[itemAI][j] != 0) {//判断两者之间有没有东西
                    return false;
                }
            }
            if ((map[itemAI][itemAJ] == map[itemBI][itemBJ] || map[itemBI][itemBJ] == 0) && itemAJ != itemBJ) {
                return true;
            }
            return false;
        } else if (itemAJ == itemBJ) {//两个元素在一条垂直线上，横坐标相等
            int minI = itemAI < itemBI ? itemAI : itemBI;
            int maxI = itemAI > itemBI ? itemAI : itemBI;
            for (int i = minI + 1; i < maxI; i++) {
                if (map[i][itemAJ] != 0) {
                    return false;
                }
            }
            if ((map[itemAI][itemAJ] == map[itemBI][itemBJ] || map[itemBI][itemBJ] == 0) && itemAI != itemBI) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public abstract byte isConneted(int itemAI, int itemAJ, int itemBI, int itemBJ);

}





