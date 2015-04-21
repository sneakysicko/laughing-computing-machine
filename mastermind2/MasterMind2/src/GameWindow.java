import javax.swing.JFrame;
import javax.swing.JScrollPane;


public class GameWindow extends JFrame {
	public GameWindow() {
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 422, 185);
		getContentPane().add(scrollPane);
	}
}
