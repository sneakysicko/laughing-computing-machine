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

	//Stworzenie dwóch paneli - głównego na wszystko i table na ogólny obszar gry.
	Panel mainPanel = new Panel(Panel.Orientation.VERTICAL);
	Panel tablePanel = new Panel(Panel.Orientation.HORISONTAL);

	//Tworzenie nowej tablicy na znaki (buttony)
	Table table = new Table(SettingsContainer.chars);

	//Żeby to działało potrzebujemy dwie jednokolumnowe tabelki na LAbele.
	Table ltable = new Table(1);
	Table rtable = new Table(1);
	//brow (buttonrow) to tablica komponentów - w tym wypadku guzików
	Component [] brow = new Component[SettingsContainer.chars];

	//Placeholdery
	Component two = new Label("tyst");

	//Tooltip mówi nam o możliwościach programu
	TextArea tooltip = new TextArea("Wciśnij klawisz 'F1' aby wywołać monit pomocy. Wciśnij klawisz 'Escape' aby wyjść z gry");
	
	GameScreen(){
		super("Gra");


		//Wrzucamy wszystkie panele w panele i w panele.
		tablePanel.addComponent(ltable);
		tablePanel.addComponent(table);
		tablePanel.addComponent(rtable);
		mainPanel.addComponent(tablePanel);
		mainPanel.addComponent(tooltip);
		addComponent(mainPanel);
		
	}

	void play(){
		//Check jest warunkiem żeby pętla sterowania się nie przerywała.
				boolean check = true;

				//Ogólnie tą pętlę wykonujemy tyle razy ile mamy prób, bo skończeniu powinna być przegrana
				for(int i =1;i<=SettingsContainer.tries;i++){
					//Poniższa pętla ma za zadanie rysować nam guziki.
					for(int j = 0; j<SettingsContainer.chars;j++){
						brow[j] = new Button("1"); //Wypełniamy brow guzikami
					}
					Component one = new Label(new String(""+i + ".")); // wypełniamy one liczbami porządkowymi

					//Dodajemy tutaj do tablic poszczególne elementy
					ltable.addRow(one);
					table.addRow(brow);

					MasterMind.screen.refresh();
					//Pętla sterująca, ma odpowiadać za sterowanie itd.

					while(check){
						Key key = MasterMind.terminal.readInput();
						if(key != null){
							switch(key.toString()){
							case("Escape"): 
								MasterMind.screen.stopScreen();
							System.exit(0);
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

					rtable.addRow(two);
					MasterMind.screen.refresh();
				}
		
	}
}
