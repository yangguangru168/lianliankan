package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel{
    private Graphics bg;//绘制图形的属性,画笔
    private BufferedImage bimage;
    public MyPanel(){
        bimage = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_BGR);
        bg=bimage.getGraphics();

    }
    public void paint(Graphics g){
        bg.setColor(Color.white);//设置画板背景颜色
        //设置区域
        bg.fillRect(0,0,1000,1000);
        //画矩形
        bg.setColor(Color.cyan);
        //矩形坐标
        bg.fillRect(5,5,200,20);
        //调用画笔画矩形
        g.drawImage(bimage,0,0,this);

    }
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        MyPanel myPanel = new MyPanel();
        jFrame.add(myPanel);
        jFrame.setSize(1000,1000);
        jFrame.setVisible(true);
    }

}
