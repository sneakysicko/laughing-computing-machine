
import java.nio.charset.Charset;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.input.Key.Kind;
public class MasterMind {
	static Terminal terminal;
	static GUIScreen textGUI;
	static Screen screen;
	public static void main(String[] args) {
		// Terminal terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));
		terminal = new SwingTerminal();
		screen = new Screen(terminal);
		//GUIScreen textGUI = TerminalFacade.createGUIScreen();
		textGUI = new GUIScreen(screen);
		    if(textGUI == null) {
		        System.err.println("Couldn't allocate a terminal!");
		        return;
		    }
		    textGUI.getScreen().startScreen();
		    textGUI.setTitle("Mastermind");

		  //MainMenu menu = new MainMenu();
		    OptionsScreen options = new OptionsScreen();
		  textGUI.showWindow(options, GUIScreen.Position.CENTER);
		   textGUI.getScreen().stopScreen();
		    System.exit(0);
	}
}
