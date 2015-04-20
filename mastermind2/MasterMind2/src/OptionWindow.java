import javax.swing.JFrame;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import java.awt.Dimension;


public class OptionWindow extends JFrame {
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	public OptionWindow() {
		setFocusTraversalPolicyProvider(true);
		setPreferredSize(new Dimension(400, 300));
		setMinimumSize(new Dimension(500, 300));
		getContentPane().setPreferredSize(new Dimension(400, 200));
		getContentPane().setMinimumSize(new Dimension(400, 200));
		getContentPane().setSize(new Dimension(600, 200));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MasterMind - konfiguracja");
		getContentPane().setLayout(null);
		
		JRadioButton option_easy = new JRadioButton("4 znaki  \r\n9 pr\u00F3b");
		option_easy.setSelected(true);
		buttonGroup.add(option_easy);
		option_easy.setBounds(26, 56, 109, 23);
		getContentPane().add(option_easy);
		
		JRadioButton option_normal = new JRadioButton("5 znak\u00F3w 12 pr\u00F3b");
		buttonGroup.add(option_normal);
		option_normal.setBounds(26, 94, 141, 23);
		getContentPane().add(option_normal);
		
		JRadioButton option_hard = new JRadioButton("6 znak\u00F3w 15 pr\u00F3b");
		buttonGroup.add(option_hard);
		option_hard.setBounds(26, 131, 141, 23);
		getContentPane().add(option_hard);
		
		JRadioButton option_custom = new JRadioButton("x znak\u00F3w y pr\u00F3b");
		buttonGroup.add(option_custom);
		option_custom.setBounds(26, 165, 141, 23);
		getContentPane().add(option_custom);
		
		JRadioButton set_colors = new JRadioButton("Kolory");
		set_colors.setSelected(true);
		buttonGroup_1.add(set_colors);
		set_colors.setBounds(300, 56, 109, 23);
		getContentPane().add(set_colors);
		
		JRadioButton set_alpha = new JRadioButton("Znaki");
		buttonGroup_1.add(set_alpha);
		set_alpha.setBounds(300, 94, 109, 23);
		getContentPane().add(set_alpha);
		
		JRadioButton set_numeric = new JRadioButton("Cyfry\r\n");
		buttonGroup_1.add(set_numeric);
		set_numeric.setBounds(300, 131, 109, 23);
		getContentPane().add(set_numeric);
		
	}
}
