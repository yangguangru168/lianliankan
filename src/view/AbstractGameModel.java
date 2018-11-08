package view;

import java.util.Random;

public abstract class AbstractGameModel {
    //map存放图片
    public int[][] map;
    //保存拐点
    public Point[] points = new Point[100];
    //

    public void setMap(int[][] map) {
        this.map = map;
    }
    //保存拐点的方法
    public void setPoint1(int x, int y,int index) {
        if (points[index - 1] == null) {
            points[index - 1] = new Point();
        }
        points[index - 1].setX(x);
        points[index - 1].setY(y);
    }

    //(元素个数，行数，列数，图片资源个数)
    public int[][] initMapHelper(int elementCount,
                                 int row, int col, int elementIamge) {
        if ((elementCount % 2 != 0) || (elementCount != (row - 2) * (col - 2))) {
            throw new java.lang.IllegalArgumentException("不符合创建条件");
        }
        Random random = new Random();
        int[] initElement = new int[elementCount];
        for (int i = 0; i < elementCount; i += 2) {
            int r = Math.abs(random.nextInt()) % elementIamge + 1;//随机获取图片编号
            initElement[i] = r;
            initElement[i + 1] = r;
        }
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
        int resultArray[] = new int[srcArray.length];
        //还剩下的元素个数
        int srcIndex = srcArray.length;//64
        for (int i = 0; i < srcArray.length; i++) {
            int randomIndex = Math.abs(random1.nextInt() % srcIndex);//取余0-63
            //随机取数组下标
            resultArray[i] = srcArray[randomIndex];
            //将最后一个未使用的元素和取出的元素交换
            srcArray[randomIndex] = srcArray[--srcIndex];//63
        }
        return resultArray;
    }

    //一折算法
    public boolean linkByOneTurn(int itemAI, int itemAJ, int itemBI, int itemBJ) {
        if ((map[itemAI][itemBJ] == 0)
                && linkByLine(itemAI, itemAJ, itemAI, itemBJ)
                && linkByLine(itemBI, itemBJ, itemAI, itemBJ)
                && (map[itemAI][itemAJ] == map[itemBI][itemBJ])) {
            setPoint1(itemAI, itemBJ, 1);
            return true;
        } else if ((map[itemBI][itemAJ] == 0)
                && linkByLine(itemAI, itemAJ, itemBI, itemAJ)
                && linkByLine(itemBI, itemBJ, itemBI, itemAJ)
                && map[itemAI][itemAJ] == map[itemBI][itemBJ]) {
            setPoint1(itemBI, itemAJ, 1);
            return true;
        } else {
            return false;
        }
    }

    //二折调用一折的算法
    public Point linkByOneTurn1(int itemAI, int itemAJ, int itemBI, int itemBJ) {
        if ((map[itemAI][itemBJ] == 0)
                && linkByLine(itemAI, itemAJ, itemAI, itemBJ)
                && linkByLine(itemBI, itemBJ, itemAI, itemBJ)
        ) {
            Point point = new Point(itemAI, itemBJ);
            return point;
        } else if ((map[itemBI][itemAJ] == 0)
                && linkByLine(itemAI, itemAJ, itemBI, itemAJ)
                && linkByLine(itemBI, itemBJ, itemBI, itemAJ)
        ) {
            Point point = new Point(itemBI, itemAJ);
            return point;
        } else {
            return null;
        }
    }

    //计算两点的距离
    public int distance1(int AX, int AY, int BX, int BY) {
        if (AX != BX && AY != BY) {
            try {
                throw new Exception("两点不在一条直线");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int dis = 0;
        if (AX == BX) {
            dis = Math.abs(AY - BY);
        } else {
            dis = Math.abs(AX - BX);
        }
        return dis;
    }

    //两点折现的距离 //git
    public int distance2(int AX, int AY, int BX, int BY) {
        Point point = linkByOneTurn1(AX, AY, BX, BY);
        return distance1(AX, AY, point.getX(), point.getY())
                + distance1(point.getX(), point.getY(), BX, BY);
    }

    //两折选最优路径
    public boolean linkByTwoturn(int itemAI, int itemAJ, int itemBI, int itemBJ) {
        int count = 0;
        for (int i = 0; i < map.length; i++) {
            if (map[i][itemAJ] == 0) {//遍历行
                if (linkByLine(itemAI, itemAJ, i, itemAJ)) {
                    if (map[itemAI][itemAJ] == map[itemBI][itemBJ]) {
                        Point point = linkByOneTurn1(itemBI, itemBJ, i, itemAJ);
                        if (point != null) {
                            System.out.println("p1:"+point.toString());
                            points[count] = point;
                            count++;
                            points[count] = new Point(i, itemAJ);
                            System.out.println("1:"+points[count].getX()+" , "+points[count].getY());
                            count++;

                        }
                    }
                }
            }
        }
        for (int j = 0; j < map.length; j++) {
            if ((map[itemAI][j] == 0)) {//遍历列
                if (linkByLine(itemAI, itemAJ, itemAI, j)) {
                    if (map[itemAI][itemAJ] == map[itemBI][itemBJ]) {
                        Point point =linkByOneTurn1(itemBI, itemBJ, itemAI, j);
                        if ( point != null) {
                            System.out.println("p2:"+point.toString());
                            points[count] = point;
                            count++;
                            points[count] = new Point(itemAI, j);
                            System.out.println("2:"+points[count].getX()+" , "+points[count].getY());
                            count++;

                        }
                    }
                }
            }
        }
        if (count != 0) {
            Point p0 = points[0];
            Point p1 = points[1];
                int dis = distance1(itemAI, itemAJ, points[1].getX(), points[1].getY()) +
                        distance2(points[1].getX(), points[1].getY(), itemBI, itemBJ);
            System.out.println("第一个的长度: "+dis);
                for (int i = 2; i < count; i = i+2) {
                    System.out.println(points[i+1].getX()+" , "+points[i+1].getY());
                    int length = distance1(itemAI, itemAJ, points[i+1].getX(), points[i+1].getY())
                            + distance2(points[i+1].getX(), points[i+1].getY(), itemBI, itemBJ);
                    System.out.println("第二个长度："+length);
                    if (length < dis) {
                        dis = length;

                        p0 = points[i];
                        p1 = points[i+1];
                        System.out.println("pi: "+p0.toString());
                    }
                System.out.println("最优路径: "+dis);
            }
                setPoint1(p0.getX(), p0.getY(), 1);
                setPoint1(p1.getX(), p1.getY(), 2);
            return true;
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





