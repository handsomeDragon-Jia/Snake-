import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Spanel extends JPanel implements KeyListener, ActionListener{
	//引入图片元素
	ImageIcon title=new ImageIcon("src\\title.jpg");
	ImageIcon right=new ImageIcon("src\\right.png");
	ImageIcon left=new ImageIcon("src\\left.png");
	ImageIcon up=new ImageIcon("src\\up.png");
	ImageIcon down=new ImageIcon("src\\down.png");
	ImageIcon body=new ImageIcon("src\\body.png");
	ImageIcon food=new ImageIcon("src\\food.png");

	int len;//定义长度
	int[] x=new int[816];//定义存放每节的横坐标的数组
	int[] y=new int[816];//定义存放每节的纵坐标的数组
	String fx="R";//方向，"R"代表右,"L"左,"U"上,"D"下
	boolean isstarted=false;//状态变量:是否开始
	boolean isfailed=false;//状态变量:是否失败
	Timer timer=new Timer(200,this);//加入定时器,每0.2秒判断一次
	int foodx;//食物横坐标
	int foody;//食物纵坐标
	Random random=new Random();//随机器
	
	public Spanel(){//构造函数
		initSnake();//初始化
		this.setFocusable(true);//可以获取焦点
		this.addKeyListener(this);//添加监听
		timer.start();//启动定时器
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		this.setBackground(Color.WHITE);
		title.paintIcon(this, g, 25, 6);//打印标题
		
		g.setColor(Color.BLACK);
		g.fillRect(25, 70, 850, 600);//填充游戏背景
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.CENTER_BASELINE,18));
		g.drawString("len:\t" + len, 700, 30);//身长标签
		g.drawString("score:\t" + (len-3)*20, 700, 50);//分数标签
		
		if(fx == "R"){//根据方向标识，打印对应的蛇头
			right.paintIcon(this, g, 25+25*x[0], 70+25*y[0]);
		}else if(fx == "L"){
			left.paintIcon(this, g, 25+25*x[0], 70+25*y[0]);
		}else if(fx == "U"){
			up.paintIcon(this, g, 25+25*x[0], 70+25*y[0]);
		}else if(fx == "D"){
			down.paintIcon(this, g, 25+25*x[0], 70+25*y[0]);
		}
		
		for(int i=1;i<len;i++){
			body.paintIcon(this, g, 25+25*x[i], 70+25*y[i]);
		}//循环输出蛇身
		
		food.paintIcon(this, g, 25+25*foodx, 70+25*foody);//打印食物
		
		if(isstarted == false){//如果未开始，提示按空格键开始
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial",Font.BOLD,40));
			g.drawString("press space to start", 250,350);
		}
		
		if(isfailed){//如果失败，显示是否重新游戏
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial",Font.BOLD,40));
			g.drawString("failed:press space to restart", 200,350);
		}
	}
	
	public void initSnake(){//初始化函数
		len=3;
		x[0]=3;
		y[0]=1;
		x[1]=2;
		y[1]=1;
		x[2]=1;
		y[2]=1;
		foodx=random.nextInt(34);
		foody=random.nextInt(24);
		fx="R";
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {//键盘监听中断
		int keycode=e.getKeyCode();//获取用户按键
		if(keycode == KeyEvent.VK_SPACE){//如果按下空格键
			if(isfailed){//如果已经失败
				isfailed=false;//改变状态，重新游戏
				initSnake();//重开后初始化
			}
			isstarted=!isstarted;//切换暂停和进行
			repaint();
		}
		if(keycode == KeyEvent.VK_W){//按下W,改变方向变量
			fx="U";
		}
		if(keycode == KeyEvent.VK_S){
			fx="D";
		}
		if(keycode == KeyEvent.VK_A){
			fx="L";
		}
		if(keycode == KeyEvent.VK_D){
			fx="R";
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {//按下时的监听中断函数
		// TODO Auto-generated method stub
		if(isstarted && !isfailed){//如果游戏进行中并且没有失败
			for(int i=len-1; i>0; i--){//循环后移身体坐标
				x[i]=x[i-1];
				y[i]=y[i-1];
			}
			if(fx == "R"){//查看方向状态码,改变蛇头坐标
				x[0]=x[0]+1;
				if(x[0] > 33)x[0]=0;
			}
			if(fx == "L"){
				x[0]=x[0]-1;
				if(x[0] < 0)x[0]=33;
			}
			if(fx == "U"){
				y[0]=y[0]-1;
				if(y[0] < 0)y[0]=23;
			}
			if(fx == "D"){
				y[0]=y[0]+1;
				if(y[0] > 23)y[0]=0;
			}
			
			repaint();
		}
		
		for(int i=1; i<len;i++){//判断蛇头与蛇身是否重叠
			if(x[0] == x[i] && y[0] == y[i]){
				isfailed=true;//如果重叠，判定失败
			}
		}
		
		if(foodx == x[0] && foody == y[0]){//判断蛇头坐标与食物坐标是否重叠
			
			len++;//如果重叠，身长加一
			
			x[len-1]=x[len-2];//将新身长的末尾坐标追加数组
			y[len-1]=y[len-2];
			
			foodx=random.nextInt(34);//重新生成食物坐标
			foody=random.nextInt(24);
			repaint();
			}
		
		timer.start();//重启定时器
	}
}
