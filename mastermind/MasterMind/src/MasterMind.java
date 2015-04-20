
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
	
	public static void main(String[] args) {
		 Terminal terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));
		  GUIScreen textGUI = TerminalFacade.createGUIScreen();
		    if(textGUI == null) {
		        System.err.println("Couldn't allocate a terminal!");
		        return;
		    }
		    textGUI.getScreen().startScreen();
		    textGUI.setTitle("Mastermind");

		  MainMenu menu = new MainMenu();
		  textGUI.showWindow(menu, GUIScreen.Position.CENTER);
		    //textGUI.getScreen().stopScreen();
	}
}
