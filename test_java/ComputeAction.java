package test_java;
import javax.swing.*;
import java.awt.event.*;


public class ComputeAction implements ActionListener{
	Total total;
	JTextField text;
	
	ComputeAction(JTextField t, Total to) {
		this.text = t;
		this.total = to;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {

			JFrame Window = new JFrame("test caculator");
			Window.setSize(400 , 400);
			Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Window.setVisible(true);
			
			
			JButton clicked = (JButton)e.getSource();
			int num = Integer.parseInt(text.getText());
			
			if (clicked.getText().equals("+"))
				this.total.add(num);
			else
				this.total.sub(num);
			
			text.setText(total.toString());
		}
	}
}
