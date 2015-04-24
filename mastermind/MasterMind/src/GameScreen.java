import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Interactable;
import com.googlecode.lanterna.gui.TextGraphics;
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
import com.googlecode.lanterna.terminal.ACS;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.Key.Kind;
import java.util.Random;


public class GameScreen extends Window {
	private class SelectableButton extends Button {
		public int value = 1;

		public void moveRight() {        
			SettingsContainer.currentComponent = (SettingsContainer.currentComponent+1)%SettingsContainer.chars;
			setFocus(brow[SettingsContainer.currentComponent]);
		}
		
		public void moveLeft(){       
			SettingsContainer.currentComponent = (SettingsContainer.currentComponent-1+SettingsContainer.chars)%SettingsContainer.chars;
			setFocus(brow[SettingsContainer.currentComponent]);
		}

		private Action leftAction = new Action() {
			public void doAction() {
				moveLeft();
			} 
		};

		private Action rightAction = new Action() {
			public void doAction() {
				moveRight();
			} 
		};

		private Action defaultAction = new Action() {
			public void doAction() {
				++SettingsContainer.turnNumber;
				if(SettingsContainer.turnNumber > SettingsContainer.tries)
					close();
				else
					drawNextRow();
				setFocus(brow[0]);
				SettingsContainer.currentComponent = 0;
			}
		};

		public SelectableButton(String text) {
			super(text);
		}



        @Override
        public Interactable.Result keyboardInteraction(Key key) {
        	switch(key.getKind()) {
        	case Enter:
        		defaultAction.doAction();
        		return Result.EVENT_HANDLED;
        		
        	case ArrowRight:
        	case Tab:
        		rightAction.doAction();
        		return Result.EVENT_HANDLED;
        		
        	case Escape:
        		close();
        		
        	case ArrowLeft:
        	case ReverseTab:
        		leftAction.doAction();
        		return Result.EVENT_HANDLED;
        		//return Result.PREVIOUS_INTERACTABLE_LEFT;
        		
        	default:
        		return Result.EVENT_NOT_HANDLED;
        	}
        }
	}

	//Stworzenie dwóch paneli - głównego na wszystko i table na ogólny obszar gry.
	Panel mainPanel = new Panel(Panel.Orientation.VERTICAL);
	Panel tablePanel = new Panel(Panel.Orientation.HORISONTAL);

	//Tworzenie nowej tablicy na znaki (buttony)
	Table table = new Table(SettingsContainer.chars);

	//Żeby to działało potrzebujemy dwie jednokolumnowe tabelki na LAbele.
	Table ltable = new Table(1);
	Table rtable = new Table(1);
	//brow (buttonrow) to tablica komponentów - w tym wypadku guzików
	SelectableButton [] brow = new SelectableButton[SettingsContainer.chars];

	//Placeholdery
	Component two = new Label("tyst");

	//Tooltip mówi nam o możliwościach programu
	Component tooltip = new Label("Wciśnij klawisz 'F1' aby wywołać monit pomocy. Wciśnij klawisz 'Escape' aby wyjść z gry");



	GameScreen(){
		super("Gra");


		//Wrzucamy wszystkie panele w panele i w panele.
		tablePanel.addComponent(ltable);
		tablePanel.addComponent(table);
		tablePanel.addComponent(rtable);
		mainPanel.addComponent(tablePanel);
		mainPanel.addComponent(tooltip);
		addComponent(mainPanel);
		drawNextRow();
		setFocus(brow[0]);

	}

	public void drawNextRow() {
		if(SettingsContainer.ctype==3){
			for(int j = 0; j < SettingsContainer.chars; ++j){
				brow[j] = new SelectableButton("1");
			}
		}
		if(SettingsContainer.ctype==2){
			for(int j = 0; j < SettingsContainer.chars; ++j){
				brow[j] = new SelectableButton("a");
			}
		}
		if(SettingsContainer.ctype==1){
			for(int j = 0; j < SettingsContainer.chars; ++j){
				brow[j] = new SelectableButton(Character.toString(ACS.BLOCK_SOLID));				
			}
		}
		Component one = new Label(new String("" + SettingsContainer.turnNumber + "."));
		ltable.addRow(one);
		table.addRow(brow);
	}
	
	private int[] code = new int[SettingsContainer.chars];
	public void setRandomGoal(){
		Random generator = new Random();
		for (int i = 0;i<SettingsContainer.chars;++i){
			code[i] = generator.nextInt(SettingsContainer.chars+1);
			


}
