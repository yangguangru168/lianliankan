package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView extends JFrame implements ActionListener{
    MainPanel panel = new MainPanel();
    JButton button = new JButton();//下一局
    JButton startGame = new JButton();//开始游戏
    JButton exit = new JButton();//退出
    JButton restartGame = new JButton();//重置游戏
    static JLabel label = new JLabel("0");//初始分数
    static JLabel level = new JLabel("1");//初始化关卡
    JLabel lscore = new JLabel("分数:");
    JLabel lel = new JLabel("关卡：");
    boolean flag = false;
    //积分数
    public GameView(){
        //设置窗口的大小
        this.setSize(700,650);
        this.setVisible(true);
        initFrame();
    }
    public static void score(){//获取分数
         label.setText(String.valueOf(Integer.parseInt(label.getText())+ 100));
    }
    public static void level(){//关卡
         level.setText(String.valueOf(Integer.parseInt(level.getText())+ 1));
    }
    public static String rollScore(){//下一关判断
         return label.getText();
    }
    public static void init(){//重新初始化分数
        label.setText("0");
    }

    public void viewbutton(JButton button,String str,int x,int y){
        button.setText(str);
        button.setFont(new Font("微软雅黑",Font.PLAIN,12));
        button.addActionListener(this);
        button.setBounds(x,y,90,20);
    }
    public void viewlable(JLabel label,int x,int y){
        label.setText(String.valueOf(Integer.parseInt(label.getText())));
        label.setFont(new Font("微软雅黑",Font.PLAIN,14));
        label.setBounds(x,y,100,20);
    }
    public void lable(JLabel label,int x,int y){
        label.setText(label.getText());
        label.setFont(new Font("微软雅黑",Font.PLAIN,14));
        label.setBounds(x,y,100,20);
    }
    //初始化方法
    public void initFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setResizable(false);
        //设置窗口标题
        this.setTitle("连连看");

        viewbutton(startGame,"开始游戏",520,100);
        viewbutton(button,"下一局",520,140);
        viewbutton(restartGame,"重置布局",520,180);
        viewbutton(exit,"退出",520,220);

        viewlable(label,560,50);
        viewlable(level,560,20);
        lable(lscore,520,50);
        lable(lel,520,20);

        this.add(exit);
        this.add(restartGame);
        this.add(startGame);
        this.add(lscore);
        this.add(lel);
        this.add(level);
        this.add(label);
        this.add(button);
        panel.setBounds(5,30,500,550);
        this.add(panel);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //新游戏
        if(flag){
            if (e.getSource() == button) {
                panel.reset();
                panel.timeWR=390;
                label.setText("0");
            }
            if (e.getSource() == restartGame) {
                panel.refresh();
            }
        }
        if (e.getSource() == startGame) {
            new Thread(panel).start();
            flag = true;
        }

        if (e.getSource() == exit) {
            System.exit(0);
        }
    }
    public static void main(String[] args) {
        new GameView();
    }

}
