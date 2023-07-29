package test_java;
import javax.swing.*;

public class JDialogDemo {
	public static void main(String[] args) {
		String name = JOptionPane.showInputDialog("what is your name ? ");
		JOptionPane.showMessageDialog(null, "hello World " + name);
	}
}
