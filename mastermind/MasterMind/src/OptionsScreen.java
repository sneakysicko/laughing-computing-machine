import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.CheckBox;
import com.googlecode.lanterna.gui.component.CheckBoxList;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.RadioCheckBoxList;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import com.googlecode.lanterna.gui.layout.LinearLayout;

public class OptionsScreen extends Window {
	public OptionsScreen(){
		super("Konfiguracja");
		
		Panel mainPanel = new Panel(Panel.Orientation.VERTICAL);
		Panel optionPanel = new Panel(Panel.Orientation.HORISONTAL);
		Panel leftPanel = new Panel();
		Panel rightPanel = new Panel();
		
		
        
        //Tworzenie dwóch checkboxów z jedną opcją na raz.
		RadioCheckBoxList option_left = new RadioCheckBoxList();
		RadioCheckBoxList option_right = new RadioCheckBoxList();
		
		//Dodawanie opcji menu z lewej strony.
		option_left.addItem("4 znaki 9 prób");
		option_left.addItem("5 znaków 12 prób");
		option_left.addItem("6 znaków 15 prób");
		option_left.addItem("x znaków y prób");
		leftPanel.addComponent(option_left);
		//addComponent(option_left);
		option_right.addItem("kolory");
		option_right.addItem("znaki");
		option_right.addItem("cyfry");
		//addComponent(option_right);
		rightPanel.addComponent(option_right);
		
		optionPanel.addComponent(leftPanel);
		optionPanel.addComponent(rightPanel);
		//Dodawanie dolnego panelu, który będzie zawierał przycisk startu i przycisk końca.
		Panel buttonPanel = new Panel(new Border.Invisible(), Panel.Orientation.HORISONTAL);
        Button exitButton = new Button("Wyjście", new Action() {
                @Override
                public void doAction()  {
                	getOwner().getScreen().stopScreen();
                }
            });
        Button startButton = new Button("Zacznij", new Action() {
            @Override
            public void doAction()  {
            	GameScreen game = new GameScreen();
            	getOwner().showWindow(game, GUIScreen.Position.CENTER);
            	close();
            }
        });
        buttonPanel.addComponent(startButton);
        buttonPanel.addComponent(new EmptySpace(1, 1), LinearLayout.GROWS_HORIZONTALLY);
        buttonPanel.addComponent(exitButton);
        mainPanel.addComponent(optionPanel);
        mainPanel.addComponent(buttonPanel, LinearLayout.GROWS_HORIZONTALLY);
        addComponent(mainPanel);
	}
}
