package test_java;
import javax.swing.*;


public class basic_GUI {
	 public static void main(String[] args) {
		 JFrame mainWindow = new JFrame("Simple caculator");
		 mainWindow.setSize(400 , 400);
		 
		 Total total = new Total(0);
		 
		 JTextField input = new JTextField(20);
		 
		 JButton minus = new JButton("-");
		 minus.addActionListener(new ComputeAction(input, total));
		 
		 JButton plus  = new JButton("+");
		 plus.addActionListener(new ComputeAction(input, total));
		 
		 JButton clear = new JButton("Clear");
		 clear.addActionListener(new Clear_Action(input,  total));		 

		 
		 JPanel panel = new JPanel();
		 panel.add(input);
		 panel.add(plus);
		 panel.add(minus);
		 panel.add(clear);
		 
		 mainWindow.add(panel);
		 
		 mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 mainWindow.setVisible(true);
	 }
}
