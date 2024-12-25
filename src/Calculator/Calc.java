package Calculator;

import javax.swing.*;
import java.awt.*;

public class Calc extends JFrame {
	private static final long serialVersionUID = 1L;
	CalcPanel cp;

	public Calc() {
		super("haha");
		cp = new CalcPanel();
		this.add(cp);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocation(700, 100);
		setSize(300, 550);
		setResizable(false);
		setBackground(Color.decode("#333333"));

	}

	public static void main(String[] args) {
		new Calc();
	}
}
