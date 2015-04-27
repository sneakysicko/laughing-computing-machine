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

import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;


public class GameScreen extends Window {
    private class ColorControler extends Button {
        ColorControler(String s) {
            super(s);
        }

        @Override
        public Interactable.Result keyboardInteraction(Key key) {
            return Result.EVENT_NOT_HANDLED;
        }
    }

	private class SelectableButton extends Button {
		private int value;
		private boolean is_checked = false;
		
		public void moveRight() {        
			SettingsContainer.currentComponent = (SettingsContainer.currentComponent+1)%SettingsContainer.chars;
			setFocus(brow[SettingsContainer.currentComponent]);
		}
		
		public void moveLeft(){       
			SettingsContainer.currentComponent = (SettingsContainer.currentComponent-1+SettingsContainer.chars)%SettingsContainer.chars;
			setFocus(brow[SettingsContainer.currentComponent]);
		}
		
		
		public void moveUp(){
            value = (value+1)%6;
            setText(charArr[SettingsContainer.ctype][value].toString());
		}

		public void moveDown(){
            value = (value-1+6)%6;
            setText(charArr[SettingsContainer.ctype][value].toString());
		}

		private Action UpAction = new Action() {
			public void doAction() {
				moveUp();
			} 
		};
		private Action DownAction = new Action() {
			public void doAction() {
				moveDown();
			} 
		};

		
		
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
				check_em();
				if(SettingsContainer.win == true){
					close();
				}
				two = new Label("B:" + perfect_hits + "W:" + semi_hits);
				rtable.addRow(two);
				++SettingsContainer.turnNumber;
				if(SettingsContainer.turnNumber > SettingsContainer.tries)
					close();
				else
					drawNextRow();
				setFocus(brow[0]);
				SettingsContainer.currentComponent = 0;
			}
		};

		public SelectableButton(String text, int value) {
			super(text);
            this.value = value;
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
        		
        	case ArrowDown:
        		DownAction.doAction();
        		return Result.EVENT_HANDLED;
        	case ArrowUp:
        		
        		UpAction.doAction();
        		return Result.EVENT_HANDLED;
        	case ArrowLeft: 		
        	case ReverseTab:
        		leftAction.doAction();
        		return Result.EVENT_HANDLED;
        		//return Result.PREVIOUS_INTERACTABLE_LEFT;
        	
        	case Backspace:
        		HelpScreen pomoc = new HelpScreen();
        		MasterMind.textGUI.showWindow(pomoc, GUIScreen.Position.CENTER);
        		return Result.EVENT_HANDLED;
        	default:
        		return Result.EVENT_NOT_HANDLED;
        	}
        }
	}

    private final Character[][] charArr = {
        //Znaki
        {'a', 'b', 'c', 'd', 'e', 'f'},
        //Cyfry
        {'1', '2', '3', '4', '5', '6'} };
    
    private final Terminal.Color[] colorArray = {Terminal.Color.RED, Terminal.Color.GREEN, 
    		Terminal.Color.YELLOW, Terminal.Color.BLUE,
    		Terminal.Color.MAGENTA, Terminal.Color.CYAN};

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
	Label [] sbrow = new Label[SettingsContainer.chars];
	//Placeholdery
	Component two = new Label("tyst");
	
	ColorControler invisible = new ColorControler("");
	int[] colorValue = new int[SettingsContainer.chars];

	//Tooltip mówi nam o możliwościach programu
	Component tooltip = new Label("Wciśnij klawisz 'Backspace' aby wywołać monit pomocy. Wciśnij klawisz 'Escape' aby wyjść z gry");
	private int[] codes = new int[SettingsContainer.chars];
	public int perfect_hits = 0;
	public int semi_hits = 0;
	public TreeSet<Integer> inputTree= new TreeSet<Integer>();
	public TreeSet<Integer> codeTree = new TreeSet<Integer>();
	GameScreen(){
		super("Gra");


		//Wrzucamy wszystkie panele w panele i w panele.
		tablePanel.addComponent(ltable);
		tablePanel.addComponent(table);
		tablePanel.addComponent(rtable);
		mainPanel.addComponent(tablePanel);
		mainPanel.addComponent(tooltip);
		mainPanel.addComponent(invisible);
		addComponent(mainPanel);
		drawNextRow();
		invisible.setVisible(false);
		if(SettingsContainer.ctype != 2)
			setFocus(brow[0]);
		else
			setFocus(invisible);
		setRandomGoal();
		
		mainPanel.addShortcut(Key.Kind.Escape, new Action() {
			
			@Override
			public void doAction() {
				close();
			}
		});
		
		if(SettingsContainer.ctype == 2) {
			mainPanel.addShortcut(Key.Kind.ArrowLeft, new Action() {
				
				@Override
				public void doAction() {
					SettingsContainer.currentComponent = (SettingsContainer.currentComponent-1+SettingsContainer.chars)%SettingsContainer.chars;
				}
			});
			mainPanel.addShortcut(Key.Kind.ArrowRight, new Action() {

				@Override
				public void doAction() {
					SettingsContainer.currentComponent = (SettingsContainer.currentComponent+1)%SettingsContainer.chars;
				}
			});
			mainPanel.addShortcut(Key.Kind.ArrowUp, new Action() {

				@Override
				public void doAction() {
					colorValue[SettingsContainer.currentComponent] = (colorValue[SettingsContainer.currentComponent] +1)%6;
		            sbrow[SettingsContainer.currentComponent].setTextColor(colorArray[colorValue[SettingsContainer.currentComponent]]);
				}
			});
			mainPanel.addShortcut(Key.Kind.ArrowDown, new Action() {

				@Override
				public void doAction() {
					colorValue[SettingsContainer.currentComponent] = (colorValue[SettingsContainer.currentComponent] +5)%6;
		            sbrow[SettingsContainer.currentComponent].setTextColor(colorArray[colorValue[SettingsContainer.currentComponent]]);
				}
			});
		}
	}

	public void drawNextRow() {
		if(SettingsContainer.ctype==2){
			for(int j = 0; j < SettingsContainer.chars; ++j){
				sbrow[j] = new Label(Character.toString(ACS.BLOCK_SOLID), colorArray[0]);				
			}
		}
        else {
			for(int j = 0; j < SettingsContainer.chars; ++j)
                brow[j] = new SelectableButton(charArr[SettingsContainer.ctype][0].toString(), 0);
        }

		Component one = new Label(new String("" + SettingsContainer.turnNumber + "."));
		ltable.addRow(one);
		if(SettingsContainer.ctype != 2)
			table.addRow(brow);
		else
			table.addRow(sbrow);
	}
	
	
	//Ustalenie kodu do zgadniecia
	public void setRandomGoal(){
		Random generator = new Random();
		for (int i=0;i<SettingsContainer.chars;++i){
			codes[i] = generator.nextInt(SettingsContainer.chars);
			
		}
		tooltip = new Label(Arrays.toString(codes));
		mainPanel.addComponent(tooltip);
		
	}
	
	public void check_em(){
        perfect_hits = 0;
        semi_hits = 0;
        codeTree.clear();
        inputTree.clear();
		for(int i = 0; i<SettingsContainer.chars;i++){
			if(codes[i]==brow[i].value){
				perfect_hits++;
			}
			else
			{
				inputTree.add(brow[i].value);
				codeTree.add(codes[i]);
			}
		}
		if(perfect_hits==SettingsContainer.chars){
			SettingsContainer.win = true;
		}
		for(Integer val : codeTree){
			if(inputTree.contains(val)){
				semi_hits++;
                System.out.println("Semi: " + val);
			}
		}
	}
	
	
	
	
	
}
