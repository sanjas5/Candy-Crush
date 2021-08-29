package gui;
import javax.swing.JFrame;

public class Igra
{
	public static void main(String[] args) 
	{
		BoardGUI panel = new BoardGUI(8, 8);
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(600, 600);
		frame.setTitle("Candy Crush");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
