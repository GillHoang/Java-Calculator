package Calculator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcPanel extends JPanel {
	JTextField show;
	JTextField ans;

	JButton[] bnList;
	String[] bttext = { "%", "CE", "C", "<=", "7", "8", "9", "x", "4", "5", "6", "-", "1", "2", "3", "+", "0", ".", "",
			"/", "", "", "=" };
	int col = 4;
	String operator = "";
	String bOperator = "";
	Double preNum;
	Double curNum;
	Double answer;

	public CalcPanel() {
		bnList = new JButton[30];
		setLayout(null);
		show = new JTextField("0");
		show.setBounds(15, 5, 260, 40);
		show.setHorizontalAlignment(SwingConstants.RIGHT);
		show.setFont(new Font("Digital-7", Font.BOLD, 25));
		show.setBorder(new LineBorder(Color.decode("#dddddd"), 1, true));
		show.setFocusable(false);
		show.setEditable(false);

		ans = new JTextField("0");
		ans.setBounds(15, 50, 260, 25);
		ans.setHorizontalAlignment(SwingConstants.RIGHT);
		ans.setFont(new Font("Digital-7", Font.BOLD, 25));
		ans.setBorder(new LineBorder(Color.decode("#dddddd"), 1, true));
		ans.setFocusable(false);
		ans.setEditable(false);

		this.add(show);
		this.add(ans);

		for (int i = 0; i < bttext.length; i++) {
			int x = i / col;
			int y = i % col;
			JButton b = new JButton(bttext[i]);
			b.setBounds(15 + (69) * y, 80 + (69) * x, 54, 54);
			b.setBackground(Color.white);
			b.setForeground(Color.black);
			Font font = new Font("Tahoma", Font.BOLD, 15);
			b.setFont(font);
			b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#dddddd"), 1),
					BorderFactory.createLineBorder(Color.white, 2)));
			if (bttext[i].equals("=")) {
				b.setSize(108 + 15, 54);
			}
			b.setActionCommand(bttext[i]);
			b.addActionListener(bntAction(show, ans));
			bnList[i] = b;
			this.add(b);
		}

		setBackground(Color.decode("#eeeeee"));
	}

	private ActionListener bntAction(JTextField show, JTextField ans) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = e.getActionCommand();
				String s = ans.getText();
				try {
					Integer.parseInt(name);
					if (s.equals("0")) {
						ans.setText(name);
					} else {
						if (answer != null && operator.length() != 1) {
							reset();
							s = ans.getText();
						}
						ans.setText(s + name);
					}
				} catch (NumberFormatException ex) {
					switch (name) {
					case "<=" -> {
						if (s.length() == 0) {
							ans.setText("0");
						} else
							ans.setText(s.substring(0, s.length() - 1));
					}
					case "+" -> {
						hehe("+");
					}
					case "-" -> {
						hehe("-");
					}
					case "x" -> {
						hehe("*");
					}
					case "/" -> {
						hehe("/");
					}
					case "=" -> {
						if (preNum == null && curNum == null && s.length() > 0) {
							return;
						} else if ( preNum != null && operator.length() == 1) {
							curNum = 0.0;
						}
						double a = cal(operator);
						show.setText(String.valueOf(preNum) + " " + operator + " " + String.valueOf(curNum) + " =");
						operator = "";
						ans.setText(String.valueOf(a));
						curNum = preNum;
						preNum = null;
					}
					case "." -> {
						if (s.contains("."))
							return;
						ans.setText(s.concat("."));
					}
					case "CE" -> {
						if (preNum != null && s.length() > 0) {
							ans.setText("");
						} else if (preNum != null && s.length() == 0) {
							reset();
						}
					}
					case "C" -> {
						reset();
					}
					case "%" -> {
						ans.setText(String.valueOf(Double.valueOf(s) / 100));
					}
					}
				}
			}
		};
	}

	private void hehe(String op) {
		String s = ans.getText();
		if (op == operator) return;
		if (preNum != null && op.length() == 1 && s.length() > 0) {
			double a = cal(bOperator);
			operator = op;
			bOperator = op;
			show.setText(a + " " + operator);
			ans.setText("");
			preNum = a;
			return;
		}
		operator = op;
		bOperator = op;
		show.setText(s + " " + operator);
		ans.setText("");
		preNum = Double.valueOf(s);
	}

	private double cal(String op) {
		double a = 0.0;
		String s = ans.getText();
		curNum = Double.valueOf(s);
		switch (op) {
		case "+" -> {
			a = preNum + curNum;
		}
		case "-" -> {
			a = preNum - curNum;
		}
		case "*" -> {
			a = preNum * curNum;
			a = round(a, 5);
		}
		}
		answer = a;
		return a;
	}

	private void reset() {
		ans.setText("");
		operator = "";
		bOperator = "";
		preNum = null;
		curNum = null;
		answer = null;
		show.setText("");
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}