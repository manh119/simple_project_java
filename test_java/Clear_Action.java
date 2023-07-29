package test_java;
import java.awt.event.*;
import javax.swing.*;


public class Clear_Action  implements ActionListener{
	JTextField text;
	Total total;
	
	Clear_Action(JTextField t, Total to){
		this.text = t;
		total = to;
	}
	
	public void actionPerformed(ActionEvent e) {
		text.setText("");
		System.out.println("Clear action total from " + this.total.getTotal() + " to zero ");
		total.clear();
	}
	
	
	
}
