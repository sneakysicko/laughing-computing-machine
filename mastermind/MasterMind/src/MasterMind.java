
import java.nio.charset.Charset;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.input.Key.Kind;


/**
 * Klasa MasterMind to główna klasa, która zajmuje się początkowym uruchomieniem programu. Zawiera też deklaracje kilku zmiennych, które są kluczowe.
 * @author Otoshigami
 *
 */
public class MasterMind {
	
	/**
	 * Pole terminal jest polem statycznym przez właściwości Lanterny. Odpowiada ono za terminal działający 'pod' interfejsem lanterny.
	 */
	static Terminal terminal;
	
	/**
	 * Pole textGUI jest polem statycznym przez właściwośi Lanterny. Odpowiada ono za najwyższą warstwę - graficzną.
	 */
	static GUIScreen textGUI;
	
	/**
	 * Pole screen jest polem statycznym przez właściwości Lanterny. Odpowiada ono za warstwę obrazu.
	 */
	static Screen screen;
	
	
	public static void main(String[] args) {
		terminal = new SwingTerminal();
		screen = new Screen(terminal);
		textGUI = new GUIScreen(screen);
		    if(textGUI == null) {
		        System.err.println("Couldn't allocate a terminal!");
		        return;
		    }
		    textGUI.getScreen().startScreen();
		    textGUI.setTitle("Mastermind");

		    OptionsScreen options = new OptionsScreen();
		    textGUI.showWindow(options, GUIScreen.Position.CENTER);
       	    GameScreen game = new GameScreen();
           	textGUI.showWindow(game, GUIScreen.Position.CENTER);
		    textGUI.getScreen().stopScreen();
		    System.exit(0);
	}
}
