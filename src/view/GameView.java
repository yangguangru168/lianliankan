package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView extends JFrame implements ActionListener{
    MainPanel panel = new MainPanel();
    //工具栏
    JMenuBar menuBar = new JMenuBar();
    //菜单
    JMenu jMenu1 = new JMenu();
    JMenu jMenu2 = new JMenu();
    JMenu jMenu3= new JMenu();
    JButton button = new JButton();//下一局
    JButton startGame = new JButton();//开始游戏
    JButton exit = new JButton();//退出
    JButton restartGame = new JButton();//重置游戏

    static JLabel label = new JLabel("0");
    JLabel lscore = new JLabel("分数:");
    //积分数
    public GameView(){
        //设置窗口的大小
        this.setSize(700,650);
        //设置窗口显示
        this.setVisible(true);
        initFrame();
    }
    public static void score(){//获取分数
         label.setText(String.valueOf(Integer.parseInt(label.getText())+ 100));
    }
    public static String rollScore(){//下一关判断
         return label.getText();
    }
    public static void inti(){//重新初始化分数
        label.setText("0");
    }
    //初始化方法
    public void initFrame(){
        //关闭（X）窗口
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //清空布局管理器，取消默认值
        getContentPane().setLayout(null);
        setResizable(false);
        //设置窗口标题
        this.setTitle("连连看");
        //s设置菜单
        //设置下划线
        //下一局
        button.setText("下一局");
        button.setFont(new Font("微软雅黑",Font.PLAIN,12));
        button.addActionListener(this);
        button.setBounds(5,5,70,20);

        startGame.setText("开始游戏");
        startGame.setFont(new Font("微软雅黑",Font.PLAIN,12));
        startGame.addActionListener(this);
        startGame.setBounds(80,5,90,20);

        restartGame.setText("重置游戏");
        restartGame.setFont(new Font("微软雅黑",Font.PLAIN,12));
        restartGame.addActionListener(this);
        restartGame.setBounds(180,5,90,20);

        exit.setText("退出");
        exit.setFont(new Font("微软雅黑",Font.PLAIN,12));
        exit.addActionListener(this);
        exit.setBounds(280,5,60,20);

        label.setText(String.valueOf(Integer.parseInt(label.getText())));
        label.setFont(new Font("微软雅黑",Font.PLAIN,14));
        label.setBounds(470,5,100,20);

        lscore.setText(lscore.getText());
        lscore.setFont(new Font("微软雅黑",Font.PLAIN,15));
        lscore.setBounds(435,5,100,20);

        this.add(exit);
        this.add(restartGame);
        this.add(startGame);
        this.add(lscore);
        this.add(label);
        this.add(button);
        //this.setJMenuBar(menuBar);
        panel.setBounds(5,30,700,650);
        this.add(panel);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //新游戏
        if (e.getSource() == button) {
            panel.reset();
            panel.timeWR=390;
            label.setText("0");
        }
        if (e.getSource() == startGame) {
            new Thread(panel).start();
        }
        if (e.getSource() == restartGame) {
            panel.refresh();
        }
        if (e.getSource() == exit) {
            System.exit(0);
        }
    }
    public static void main(String[] args) {
        new GameView();
    }

}
