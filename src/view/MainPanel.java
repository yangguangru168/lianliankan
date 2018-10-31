package view;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel implements Runnable{
    //缓冲图片对象
    private BufferedImage bImage;
    //图像对象
    private Graphics bGraphics;
    //保存图片
    private Image image;
    //小图标的编号
    private int[][] map;
    //重置数组
    int[] restart ;
    //重新开始、下一局的重置
    int[] reinit;
    //
    //
   // GameModelImpl gameImpl = new GameModelImpl();
    //AbstractGameNode gameImpl = new AbstractGameNode();
     AbstractGameTwoNode gameImpl = new AbstractGameTwoNode();
    //折点
    Point point[];
    //表示状态
    public int screenState=1;
    public final static int SCREEN_STATE_PLAY = 1;//运行时
    public final static int SCREEN_STATE_DRAWLINE = 2;//两个图片可以连接画线
    public final static int SCREEN_STATE_SELECT_ONE = 3;//选中一个
    public final static int SCREEN_STATE_OVER = 4;//游戏结束
    public final static int SCREEN_STATE_SUCCESS = 5;//闯关成功进入下一关
    //图片坐标
    int selectAI;//行，y轴
    int selectAJ;//列，x轴
    int selectBI;
    int selectBJ;
    //鼠标坐标
    int mouseI;
    int mouseJ;
    //状态值是否能连接
    int code;
    //鼠标点击图标大小
    public final int cellWith=49;
    public final int cellHeight=43;
    //时间条
    int timeWR = 390;
    int time = 0;
    public MainPanel(){
        reset();
        image=Toolkit.getDefaultToolkit().createImage("image/image.png");
        //创建缓冲区，存放图标的区域
        bImage = new BufferedImage(700,650,BufferedImage.TYPE_INT_BGR);
        bGraphics=bImage.getGraphics();
        this.setSize(700,650);
        MouseInputAdapter mAdapter = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelMouseClicked(e);
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                pannelMous(e);
            }
        };
        this.addMouseListener(mAdapter);
        this.addMouseMotionListener(mAdapter);
    }
    //初始化方法
    public void reset(){
        map = gameImpl.initMapHelper(64,10,10,10);
        gameImpl.setMap(map);
        restart = this.reinit();
    }
    //重置方法
    public void refresh(){
        map = this.restart();
        gameImpl.setMap(map);
    }
    public void paint(Graphics g){
        //白色区域
        bGraphics.setColor(Color.LIGHT_GRAY);
        bGraphics.fillRect(0,0,700,650);


        bGraphics.setColor(Color.green);
        bGraphics.fillRoundRect(51,1,timeWR,18,10,10);
        //for遍历图片位置
        for (int i = 1; i <9; i++) {//行
            for (int j = 1; j < 9; j++) {//列
                if (map[i][j] != 0) {
                   bGraphics.setClip(j * 49, i * 43, 49, 43);
                    //将图片放入指定的框里
                    bGraphics.drawImage(image, j * 49 -(map[i][j]-1)*49, i * 43, this);
                }
            }
        }
        bGraphics.setClip(0,0,700,650);
        if (mouseI>0&&mouseI<9 && mouseJ>0&&mouseJ<9&&map[mouseI][mouseJ]!=0) {
            bGraphics.setColor(Color.green);
            bGraphics.drawRect(mouseJ*cellWith,mouseI*cellHeight,cellWith,cellHeight);
        }
        switch (screenState){
            case SCREEN_STATE_SELECT_ONE:
                bGraphics.setColor(Color.RED);
                bGraphics.drawRect(selectAJ*cellWith,selectAI*cellHeight,cellWith,cellHeight);
                break;
            case SCREEN_STATE_DRAWLINE:
                bGraphics.setColor(Color.RED );
                if (code == 1) {
                    bGraphics.drawLine(selectAJ*cellWith+cellWith/2,selectAI*cellHeight+cellHeight/2,selectBJ*cellWith+cellWith/2,selectBI*cellHeight+cellHeight/2);
                }
                if(code==2){
                    point = gameImpl.points;
                    Point node = point[0];
                    bGraphics.drawLine(selectAJ*cellWith+cellWith/2,selectAI*cellHeight+cellHeight/2,node.getY()*cellWith+cellWith/2,node.getX()*cellHeight+cellHeight/2);
                    bGraphics.drawLine(selectBJ*cellWith+cellWith/2,selectBI*cellHeight+cellHeight/2,node.getY()*cellWith+cellWith/2,node.getX()*cellHeight+cellHeight/2);
                }
                if (code == 3){
                    point = gameImpl.points;
                    Point nNode = point[0];
                    Point tNode = point[1];
                    System.out.println("nNode: "+nNode.toString());
                    System.out.println("tNode: "+tNode.toString());
                    bGraphics.drawLine(selectAJ*cellWith+cellWith/2,selectAI*cellHeight+cellHeight/2,
                            tNode.getY()*cellWith+cellWith/2,tNode.getX()*cellHeight+cellHeight/2);
                    bGraphics.drawLine(selectBJ*cellWith+cellWith/2,selectBI*cellHeight+cellHeight/2,
                            nNode.getY()*cellWith+cellWith/2,nNode.getX()*cellHeight+cellHeight/2);
                    bGraphics.drawLine(tNode.getY()*cellWith+cellWith/2,tNode.getX()*cellHeight+cellHeight/2,
                            nNode.getY()*cellWith+cellWith/2,nNode.getX()*cellHeight+cellHeight/2);

                }
                break;
            case SCREEN_STATE_OVER:
                bGraphics.setColor(Color.red);
                bGraphics.fillRect(100,140,110,40);
                bGraphics.setColor(Color.red);
                bGraphics.drawRect(100,140,110,40);
                bGraphics.setColor(Color.red);
                bGraphics.setFont(new Font("微软雅黑",Font.BOLD,24));
                bGraphics.drawString("游戏结束",100,135);
                break;
            case SCREEN_STATE_SUCCESS:
                bGraphics.setColor(Color.green);
                bGraphics.setFont(new Font("微软雅黑",Font.BOLD,24));
                bGraphics.drawString("恭喜进入下一关",100,135);
                default:
                    break;
        }
        g.drawImage(bImage,0,0,this);
    }
    //鼠标放上去方法
    public void pannelMous(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        int i = y/43;
        int j = x/49;
        if (i>0&&i<9&&j>0&&j<=9) {//鼠标位置在图标上
            mouseI=i;
            mouseJ=j;
        }
    }
    //鼠标点击
    public void panelMouseClicked(MouseEvent e){
        if (e.getButton() == e.BUTTON1) {//鼠标左键
            int x = e.getX();
            int y = e.getY();
            int i = y / 43;
            int j = x / 49;//X轴轴对应列
            if(i > 0 && i< 9 && j > 0 && j < 9){
                 switch (screenState){//判断游戏当前所处的状态
                     case SCREEN_STATE_PLAY://游戏在进行还有任何图片被选中
                         if (map[i][j]!=0){
                             selectAI = i;
                             selectAJ = j;
                             screenState = SCREEN_STATE_SELECT_ONE;
                         }
                         break;
                     case SCREEN_STATE_SELECT_ONE://已有一个元素被选中，再次点击会比较是否能够相连
                         if(map[i][j]!=0){
                             code = gameImpl.isConneted(selectAI,selectAJ,i,j);
                             if (code != 0) {
                                 selectBI = i;
                                 selectBJ = j;
                                 screenState = SCREEN_STATE_DRAWLINE;
                             }else {//code = 0 把当前坐标存起来
                                 selectAI = i;
                                 selectAJ = j;
                             }
                         }
                         break;
                 }
            }
        }
    }
    public int[][] restart(){//重置的方法
        restart = gameImpl.getRandomArrayHelper(restart);
        int[][] reArray = new int[10][10];
        int index = 0;
        for (int i = 1; i <9 ; i++) {
            for (int j = 1; j <9; j++) {
                reArray[i][j] = restart[index];
                index++;
            }
        }
        return reArray;
    }
    public int[] reinit(){//初始化可以重置
        int index = 0;
        reinit = new int[64];
        for (int i = 1; i <9 ; i++) {
            for (int j = 1; j <9 ; j++) {
                reinit[index] = map[i][j];
                index++;
            }
        }
        return reinit;
    }
    @Override
    public void run() {
        while (true){
            time++;
            if (time == 20) {
                timeWR-=10;
                time=0;
            }
            try {
                if ( screenState == SCREEN_STATE_DRAWLINE) {
                    paintImmediately(0,0,400,450);//画线区域
                    screenState = SCREEN_STATE_PLAY;
                    map[selectAI][selectAJ]=0;
                    map[selectBI][selectBJ]=0;//清空两个图片
                    GameView.score();
                    int temp = 0;
                    restart = new int[64];
                    for (int i = 1; i <9 ; i++) {
                        for (int j = 1; j <9 ; j++) {
                            if(map[i][j] != 0){
                                restart[temp++] = map[i][j];
                            }
                        }
                    }
                }
                if (timeWR == 0) {//时间提示
                    screenState = SCREEN_STATE_OVER;
                }
                if(timeWR == 390){//下一局提示信息消失
                    screenState = SCREEN_STATE_PLAY;
                }
                if (GameView.rollScore().equals("3200")) {
                    GameView.level();
                    GameView.init();
                    reset();
                    timeWR = 390;
                    repaint();
                }
                Thread.sleep(105);
                repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


