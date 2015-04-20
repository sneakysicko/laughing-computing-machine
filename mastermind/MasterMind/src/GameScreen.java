import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.CheckBox;
import com.googlecode.lanterna.gui.component.CheckBoxList;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.RadioCheckBoxList;
import com.googlecode.lanterna.gui.component.Table;
import com.googlecode.lanterna.gui.component.TextArea;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import com.googlecode.lanterna.gui.layout.LinearLayout;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.Key.Kind;

public class GameScreen extends Window {
	GameScreen(){
		super("Gra");
		Panel mainPanel = new Panel(Panel.Orientation.VERTICAL);
		Panel tablePanel = new Panel(Panel.Orientation.HORISONTAL);
		Table table = new Table(SettingsContainer.chars);
		Table ltable = new Table(1);
		Table rtable = new Table(1);
		TextArea tooltip = new TextArea("Wciśnij klawisz 'F1' aby wywołać monit pomocy. Wciśnij klawisz 'Escape' aby wyjść z gry");
		tablePanel.addComponent(ltable);
		tablePanel.addComponent(table);
		tablePanel.addComponent(rtable);
		mainPanel.addComponent(tablePanel);
		mainPanel.addComponent(tooltip);
		addComponent(mainPanel);

		Component [] brow = new Component[SettingsContainer.chars + 2];
		Component one = new Label("test");
		Component two = new Label("tyst");
		boolean check = true;
		for(int i =1;i<=SettingsContainer.tries;i++){
			for(int j = 0; j<SettingsContainer.chars;j++){
				brow[j] = new Button("1");
			}
			one = new Label(new String(""+i + "."));
			ltable.addRow(one);
			table.addRow(brow);
			while(check){
				Key key = getOwner().getScreen().readInput();
				if(key != null){
				switch(key.toString()){
				case("Escape"): 
					getOwner().getScreen().stopScreen();
				break;
				case("ArrowDown"):
					break;
				case("ArrowUp"):
					break;
				case("ArrowLeft"):
					break;
				case("ArrowRight"):
					break;
				case("Enter"):
					check = false;
					break;
				case("F1"):
					break;
				}
				}
			}
			check = true;
			//rtable.addRow(two);

		}
	}
}
