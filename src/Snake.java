import javax.swing.JFrame;

public class Snake {

	public static void main(String[] args){
		
		JFrame frame=new JFrame();//创建窗口
		frame.setBounds(500, 200, 910, 730);//设置窗口位置以及大小
		frame.setResizable(false);//设置窗口大小不可改变
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置点击关闭后退出程序
		frame.add(new Spanel());//添加画布
		frame.setVisible(true);//设置可见
		
	}
}
