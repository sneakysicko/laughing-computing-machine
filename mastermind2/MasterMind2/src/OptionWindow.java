import javax.swing.JFrame;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class OptionWindow extends JFrame {
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	public OptionWindow() {
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
		
		JButton play_button = new JButton("Graj!");
		play_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(option_easy.isSelected()){
					SettingsContainer.chars = 4;
					SettingsContainer.tries= 9;
				}
				if(option_normal.isSelected()){
					SettingsContainer.chars = 5;
					SettingsContainer.tries= 12;
				}
				if(option_hard.isSelected()){
					SettingsContainer.chars = 6;
					SettingsContainer.tries= 15;
				}
				if(option_custom.isSelected()){
					CustomWindow c_win = new CustomWindow();
					c_win.setVisible(true);
				}
				if(set_colors.isSelected()){
					SettingsContainer.ctype =1;
				}
				if(set_alpha.isSelected()){
					SettingsContainer.ctype =2;
				}
				if(set_numeric.isSelected()){
					SettingsContainer.ctype =3;
				}
				GameWindow game_win = new GameWindow();
				game_win.setVisible(true);
				//this.setVisible(false);
			}
		});
		
		play_button.setBounds(186, 224, 91, 23);
		getContentPane().add(play_button);
		
	}
}
