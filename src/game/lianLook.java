package game;
import java.awt.event.ActionListener;
import javax.swing.*; //AWT的扩展
import java.awt.*; //抽象窗口工具包
import java.awt.event.*;

public class lianLook implements ActionListener {


    public static void main(String[] args) {
        lianLook llk = new lianLook(); // 初始化
        llk.randomBuild(); // 调用randomBuild
        llk.init(); // 调用init
    }


    JFrame mainFrame; // 主面板 ，JFrame定义的是一个容器，可向里面添加组件
    Container thisContainer; // 定义一个容器
    JPanel centerPanel, southPanel, northPanel; // 子面板
    // ，JPanel是个轻量级容器，可添加入JFrame中
    JButton diamondsButton[][] = new JButton[6][5];// 定义存储游戏按钮的数组
    JButton exitButton, resetButton, newlyButton; // 定义退出，重列，重新开始按钮
    JLabel fractionLable = new JLabel("0"); // 定义分数标签，并初始化为0.
    JButton firstButton, secondButton;  // 分别记录两次先后被选中的按钮

    int grid[][] = new int[8][7];// 储存游戏按钮位置
    static boolean pressInformation = false; // 声明了一个静态布尔类型的变量,判断是否有按钮被选中
    int x0 = 0, y0 = 0, x = 0, y = 0, fristMsg = 0, secondMsg = 0, validateLV; // 游戏按钮的位置坐标
    int i, j, k, n;// 消除方法控制

    public void init() { // init方法
        mainFrame = new JFrame("连连看"); // 定义主面板为命名为“连连看”的JFrame容器
        thisContainer = mainFrame.getContentPane(); // 初始化mainFrame.
        thisContainer.setLayout(new BorderLayout()); // 定义布局为东西南北中的形式
        centerPanel = new JPanel(); // 初始化centerPanel
        southPanel = new JPanel(); // 初始化southPanel
        northPanel = new JPanel(); // 初始化northPanel
        thisContainer.add(centerPanel, "Center"); // 将centerPanel加入mainFrame中的Center位置
        thisContainer.add(southPanel, "South"); // 将southPanel加入mainFrame中的South位置
        thisContainer.add(northPanel, "North"); // 将northPanel加入mainFrame中的North位置
        centerPanel.setLayout(new GridLayout(6, 5)); // 将centerPanel初始化为6*5的网格布局

        for (int cols = 0; cols < 6; cols++) { // 依次对第0列到第5列进行操作
            for (int rows = 0; rows < 5; rows++) { // 依次对第0行到第4行进行操作
                diamondsButton[cols][rows] = new JButton(String.valueOf(grid[cols + 1][rows + 1])); // 新建按钮
                diamondsButton[cols][rows].addActionListener(this);// 向此按键添加动作监听以接收来自它的动作
                centerPanel.add(diamondsButton[cols][rows]);// 将按键添加到centerPanel中
            }
        }
        exitButton = new JButton("退出"); // 新建“退出”按钮
        exitButton.addActionListener(this); // 向“退出”按钮添加事件监听
        resetButton = new JButton("重置"); // 新建“重列”按钮
        resetButton.addActionListener(this); // 向“重列”按钮添加事件监听
        newlyButton = new JButton("再来一局"); // 新建“再来一局”按钮
        newlyButton.addActionListener(this); // 向“再来一局”按钮添加事件监听
        southPanel.add(exitButton); // 将“退出”按钮添加到southPanel
        southPanel.add(resetButton); // 将“重列”按钮添加到southPanel
        southPanel.add(newlyButton); // 将“再来一局”按钮添加到southPanel

        fractionLable.setText(String.valueOf(Integer.parseInt(fractionLable.getText())));  // 分数
        northPanel.add(fractionLable); // 将“分数”标签加入northPanel
        mainFrame.setBounds(280, 100, 500, 450); // x:X轴上的起点 ，y:Y轴上的起点 ，
        // width:长度， height:宽度
        mainFrame.setVisible(true); // 框架可见
    }

    /**
     * 随机给格子充满数
     *   随机策略： 一次随机出来两个相同的数字，然后在找两个位置进行放置
     */
    public void randomBuild() {
        int randoms, cols, rows;
        //  twins 对  一个15对
        for (int twins = 1; twins <= 15; twins++) {
            randoms = (int) (Math.random() * 25 + 1); // 在1-25之间随机产生一个数字

            for (int alike = 1; alike <= 2; alike++) {
                cols = (int) (Math.random() * 6 + 1); // 选中一个网格
                rows = (int) (Math.random() * 5 + 1);
                System.out.println("cols  rows: "+cols+ " , "+ rows);

                while (grid[cols][rows] != 0) {
                    cols = (int) (Math.random() * 6 + 1); // 如果该格已经存在数字，则重新选择不为空的格子
                    rows = (int) (Math.random() * 5 + 1);
                    System.out.println("while cols  rows: "+cols+ " , "+ rows);

                }
                System.out.println("randoms: "+randoms);
                this.grid[cols][rows] = randoms; // 将随机产生的数字放入网格中

            }
            System.out.println(twins );
            System.out.println( "===================================="  );
            //   一共循环15次  ，即 15 对 30个数 全充满

        }
    }

    public void fraction() {
        fractionLable.setText(String.valueOf(Integer.parseInt(fractionLable.getText()) + 100));// 在原有数字上加100分
    }


    /**
     *  格子里面填数  重新装  重置
     */
    public void reload() {
        int save[] = new int[30];   //   格子内容
        int n = 0, cols, rows;
        int grid[][] = new int[8][7];
        //  充满 六行五列
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 5; j++) {
                if (this.grid[i][j] != 0) {
                    save[n] = this.grid[i][j]; // 将现在任然存在的数字存入save中
                    n++;
                }
            }
        }

        n = n - 1;
        this.grid = grid;
        while (n >= 0) {
            cols = (int) (Math.random() * 6 + 1);
            rows = (int) (Math.random() * 5 + 1); // 重新选择格子
            while (grid[cols][rows] != 0) {
                cols = (int) (Math.random() * 6 + 1);
                rows = (int) (Math.random() * 5 + 1); // 如果格子非空，则重新选择格子
            }
            this.grid[cols][rows] = save[n]; // 将save中的数字放入所选择的的格子中
            n--;
        }
        mainFrame.setVisible(false);
        pressInformation = false; // 这里一定要将按钮点击信息归为初始
        init(); // 调用init
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i + 1][j + 1] == 0) // 如果格子的值为0
                    diamondsButton[i][j].setVisible(false); // 则按键不可见
            }
        }
    }


    /**
     *    鼠标点击事件
     * @param placeX
     * @param placeY
     * @param bz
     */
    public void estimateEven(int placeX, int placeY, JButton bz) {
        if (pressInformation == false) // 尚未有按键被选中
        {
            x = placeX;
            y = placeY;
            secondMsg = grid[x][y]; // 将该键的值存储到secondMsg
            secondButton = bz; // 令secondButton为按下的键
            pressInformation = true; // 标示已经有键被选中
        } else { // 已经有按键被选中
            x0 = x; // 将second中的值转存到first
            y0 = y;
            fristMsg = secondMsg;     // 第一次选中  数值
            firstButton = secondButton;  //  按钮格子
            x = placeX;
            y = placeY; // 将该键存入second
            secondMsg = grid[x][y];     //  第二次选中 数值
            secondButton = bz;
            // System.out.println("第一次选中："+fristMsg);
            // System.out.println("第二次选中："+secondMsg);
            if (fristMsg == secondMsg && secondButton != firstButton) { // 如果两个格子数字相等且格子不相同
                xiao(); // 调用xiao方法
            }
        }
    }

    public void xiao() {
        //  1 、 同一排 相邻  或者同一列相邻
        if ((x0 == x && (y0 == y + 1 || y0 == y - 1) ) || ((x0 == x + 1 || x0 == x - 1) && (y0 == y))) { // 判断是否相邻
            remove(); // 调用remove
        } else {

            for (j = 0; j < 7; j++) {

                if (grid[x0][j] == 0) { // 判断第一个按钮同行哪个按钮为空   看Y轴

                    //  看看 空按钮是否在 两个按钮中
                    if (y > j) { // 如果第二个按钮的Y坐标大于空按钮的Y坐标说明第一按钮在第二按钮左边
                        System.out.println("第二个按钮到选中点距离为： "+(y-j));
                        for (i = y - 1; i >= j; i--) { // 判断第二按钮左侧直到第一按钮中间有没有按钮
                            if (grid[x][i] != 0) {
                                k = 0;
                                break;   // 跳出循环
                            } else {
                                k = 1;
                            } // K=1说明通过了第一次验证
                        }

                        if (k == 1) {
                            linePassOne();
                        }
                    } //  if 结束

                    if (y < j) { // 如果第二个按钮的Y坐标小于空按钮的Y坐标说明第一按钮在第二按钮右边
                        for (i = y + 1; i <= j; i++) { // 判断第二按钮左侧直到第一按钮中间有没有按钮
                            if (grid[x][i] != 0) {
                                k = 0;
                                break;
                            } else {
                                k = 1;
                            }
                        }
                        if (k == 1) {
                            linePassOne();
                        }
                    }
                    if (y == j) {
                        linePassOne();
                    }
                } // 判断 同行为空结束

                //  二折判断
                if (k == 2) {
                    if (x0 == x) {
                        remove();
                    }
                    if (x0 < x) {
                        for (n = x0; n <= x - 1; n++) {
                            if (grid[n][j] != 0) {
                                k = 0;
                                break;
                            }
                            if (grid[n][j] == 0 && n == x - 1) {
                                remove();
                            }
                        }
                    }

                    if (x0 > x) {
                        for (n = x0; n >= x + 1; n--) {
                            if (grid[n][j] != 0) {
                                k = 0;
                                break;
                            }
                            if (grid[n][j] == 0 && n == x + 1) {
                                remove();
                            }
                        }
                    }

                }
            }

            for (i = 0; i < 8; i++) { // 列
                if (grid[i][y0] == 0) {
                    if (x > i) {
                        for (j = x - 1; j >= i; j--) {
                            if (grid[j][y] != 0) {
                                k = 0;
                                break;
                            } else {
                                k = 1;
                            }
                        }
                        if (k == 1) {
                            rowPassOne();
                        }
                    }
                    if (x < i) {
                        for (j = x + 1; j <= i; j++) {
                            if (grid[j][y] != 0) {
                                k = 0;
                                break;
                            } else {
                                k = 1;
                            }
                        }
                        if (k == 1) {
                            rowPassOne();
                        }
                    }
                    if (x == i) {
                        rowPassOne();
                    }
                }
                if (k == 2) {
                    if (y0 == y) {
                        remove();
                    }
                    if (y0 < y) {
                        for (n = y0; n <= y - 1; n++) {
                            if (grid[i][n] != 0) {
                                k = 0;
                                break;
                            }
                            if (grid[i][n] == 0 && n == y - 1) {
                                remove();
                            }
                        }
                    }
                    if (y0 > y) {
                        for (n = y0; n >= y + 1; n--) {
                            if (grid[i][n] != 0) {
                                k = 0;
                                break;
                            }
                            if (grid[i][n] == 0 && n == y + 1) {
                                remove();
                            }
                        }
                    }
                }
            }
        }
    }

    public void linePassOne() {

        if (y0 > j) { // 第一按钮同行空按钮在左边
            for (i = y0 - 1; i >= j; i--) { // 判断第一按钮同左侧空按钮之间有没按钮
                if (grid[x0][i] != 0) {
                    k = 0;
                    break;
                } else {
                    k = 2;
                } // K=2说明通过了第二次验证
            }
        }
        if (y0 < j) {  // 第一按钮同行空按钮在与第二按钮之间
            for (i = y0 + 1; i <= j; i++) {   //  y0 和 j 直接找到空格  即2折的情况
                if (grid[x0][i] != 0) {
                    k = 0;
                    break;              //  之间不为空 结束
                } else {
                    k = 2;
                }
            }
        }

    }

    public void rowPassOne() {
        if (x0 > i) {
            for (j = x0 - 1; j >= i; j--) {
                if (grid[j][y0] != 0) {
                    k = 0;
                    break;
                } else {
                    k = 2;
                }
            }
        }
        if (x0 < i) {
            for (j = x0 + 1; j <= i; j++) {
                if (grid[j][y0] != 0) {
                    k = 0;
                    break;
                } else {
                    k = 2;
                }
            }
        }
    }

    public void remove() {
        firstButton.setVisible(false); // 第一个按键不可见
        secondButton.setVisible(false); // 第二个按键不可见
        fraction(); // 调用fraction， 在当前分数上加100分
        pressInformation = false; // 使该静态变量还原为false
        k = 0;
        grid[x0][y0] = 0; // 清除该游戏按钮
        grid[x][y] = 0; // 清除该游戏按钮
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == newlyButton) { // 如果newlyButton产生动作
            int grid[][] = new int[8][7]; // 产生新的存储按键的数组
            this.grid = grid;
            randomBuild(); // 调用randomBuild
            mainFrame.setVisible(false); // 使当前mainFrame不可见
            pressInformation = false; // 使该静态变量还原为false
            init(); // 调用init
        }
        if (e.getSource() == exitButton) // 如果exitButton产生动作
            System.exit(0); // 退出程序

        if (e.getSource() == resetButton) // 如果resetButton产生动作
            reload(); // 调用reload

        for (int cols = 0; cols < 6; cols++) {
            for (int rows = 0; rows < 5; rows++) {
                if (e.getSource() == diamondsButton[cols][rows]) // 如果游戏按键产生动作
                    estimateEven(cols + 1, rows + 1, diamondsButton[cols][rows]); // 调用estimateEven
            }
        }
    }


}
