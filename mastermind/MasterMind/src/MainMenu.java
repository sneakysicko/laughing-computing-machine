import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.dialog.MessageBox;


public class MainMenu extends Window{
	public MainMenu()
    {
        super("Menu główne");
        addComponent(new Button("Start gry"));
        addComponent(new Button("Konfiguracja", new Action() {
                @Override
                public void doAction() {
                	OptionsScreen options = new OptionsScreen();
          		  	getOwner().showWindow(options, GUIScreen.Position.CENTER);
          		  	close();
                }
            }));
        addComponent(new Button("Wyjście", new Action(){
        	public void doAction() {
        		getOwner().getScreen().stopScreen();
             }
        }));
    }
}
