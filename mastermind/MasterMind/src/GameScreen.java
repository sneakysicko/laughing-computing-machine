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
import com.googlecode.lanterna.gui.component.Table;
import com.googlecode.lanterna.gui.component.TextArea;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import com.googlecode.lanterna.gui.layout.LinearLayout;

public class GameScreen extends Window {
	GameScreen(){
		super("Gra");
		Panel mainPanel = new Panel(Panel.Orientation.VERTICAL);
		Table table = new Table(3);
		TextArea tooltip = new TextArea("Wciśnij klawisz '?' aby wywołać monit pomocy. Wciśnij klawisz 'x' aby wyjść z gry");
		
		mainPanel.addComponent(table);
		mainPanel.addComponent(tooltip);
		addComponent(mainPanel);
		for(int i =1;i<=SettingsContainer.tries;i++){
			
			//table.addRow(arg0);
		}
	}
}
