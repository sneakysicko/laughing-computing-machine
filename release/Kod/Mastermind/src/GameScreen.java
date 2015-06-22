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
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.ACS;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.Key.Kind;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;

/**
 * Klasa GameScreen jest nowym oknem, w którym odbywa się większość działań naszego programu. Jest to w sumie cały grywalny moduł MasterMinda.
 * @author Otoshigami
 *
 */
public class GameScreen extends Window {
	
	/**
	 * Klasa ColorControler to nowy guzik, wprowadzony tylko w celu ominięcia limitów Lanterny. Jest to po prostu bezużyteczny guzik, który pełni jedynie funkcje jakiegoś Interactable komponentu w głównym panelu (jest to konieczne aby obsłużyć Labele, gdy gramy na kolory).
	 * @author Otoshigami
	 *
	 */
    private class ColorControler extends Button {
    	
    	/**
    	 * Konstruktor ColorControlera jest najzwyczajniejszym wywołaniem konstruktora guzika.
    	 * @param s	Napis, jaki chcemy żeby pokazał się na guziku.
    	 */
        public ColorControler(String s) {
            super(s);
        }
        
        
        /**
         * keyboardInteraction przeładowuje odpowiednik w Buttonie. Odpowiada za obsługę zdarzen z klawiatury gdy element ma fokus. Tutaj zależy nam na tym żeby zdarzenia nie mogły być obsługiwane, przez co Lanterna zacznie szukać czy są jakieś skróty klawiaturowe w MainPanelu, o co nam właśnie chodzi.
         * @param key
         */
        @Override
        public Interactable.Result keyboardInteraction(Key key) {
            return Result.EVENT_NOT_HANDLED;
        }
    }

    /**
     * SelectableButton to kolejny guzik, tym razem z funkcjami. Jest on głównym elementem gry jeśli wybraliśmy cyfry lub litery.
     * @author Otoshigami
     *
     */
	private class SelectableButton extends Button {
		/**
		 * Prywatne pole value jest stworzone dla wygody. Przechowuje ono wartość liczbową, która służy do porównania naszego wybranego wzorka z wzorem wylosowanym przez program.
		 */
		private int value;
		
		/**
		 * Funkcja moveRight nic nie zwraca i nic nie przyjmuje, odpowiada po prostu za ruch w prawo. Czyni to zmieniając pole currentComponent w SettingsContainerze, a potem nadając fokus odpowiedniemu guzikowi z tablicy brow.
		 */
		public void moveRight() {        
			SettingsContainer.currentComponent = (SettingsContainer.currentComponent+1)%SettingsContainer.chars;
			setFocus(brow[SettingsContainer.currentComponent]);
		}
		
		/**
		 * Funkcja moveLeft nic nie zwraca i nic nie przyjmuje, odpowiada po prostu za ruch w lewo. Czyni to zmieniając pole currentComponent w SettingsContainerze, a potem nadając fokus odpowiedniemu guzikowi z tablicy brow.
		 */
		public void moveLeft(){       
			SettingsContainer.currentComponent = (SettingsContainer.currentComponent-1+SettingsContainer.chars)%SettingsContainer.chars;
			setFocus(brow[SettingsContainer.currentComponent]);
		}
		
		/**
		 * Funkcja moveUp odpowiada za zwiększanie wartości w guziku. Zmienia ona i wartość (pole value) i wyświetlany tekst (pobiera go z tablicy charArr, ktora jest wypełniona liczbami lub literkami.
		 */
		public void moveUp(){
            value = (value+1)%6;
            setText(charArr[SettingsContainer.ctype][value].toString());
		}
		
		
		/**
		 * Funkcja moveDown odpowiada za zmniejszanie wartości w guziku. Zmienia ona i wartość (pole value) i wyświetlany tekst (pobiera go z tablicy charArr, ktora jest wypełniona liczbami lub literkami.
		 */
		public void moveDown(){
            value = (value-1+6)%6;
            setText(charArr[SettingsContainer.ctype][value].toString());
		}
		
		/**
		 * Trzymając się wzorców w Lanternie, to akcje powinny wywoływać jakieś funkcje w guzikach. Ta akcja wywołuje funkcję moveUp.
		 */
		private Action UpAction = new Action() {
			public void doAction() {
				moveUp();
			} 
		};
		
		/**
		 * Trzymając się wzorców w Lanternie, to akcje powinny wywoływać jakieś funkcje w guzikach. Ta akcja wywołuje funkcję moveDown.
		 */
		private Action DownAction = new Action() {
			public void doAction() {
				moveDown();
			} 
		};

		
		/**
		 * Trzymając się wzorców w Lanternie, to akcje powinny wywoływać jakieś funkcje w guzikach. Ta akcja wywołuje funkcję moveLeft.
		 */
		private Action leftAction = new Action() {
			public void doAction() {
				moveLeft();
			} 
		};
		
		
		/**
		 * Trzymając się wzorców w Lanternie, to akcje powinny wywoływać jakieś funkcje w guzikach. Ta akcja wywołuje funkcję moveRight.
		 */
		private Action rightAction = new Action() {
			public void doAction() {
				moveRight();
			} 
		};

		/**
		 * Trzymając się wzorców w Lanternie, to akcje powinny wywoływać jakieś funkcje w guzikach. Ta akcja wywołuje funkcję doAction (domyślna nazwa funkcji do domyślnej akcji).
		 * 
		 * 
		 */
		private Action defaultAction = new Action() {
			/**
			 * Funkcja doAction wywołuje funkcję checkValues, a potem sprawdza czy zmienna win w SettingsContainer jest true. Jeśli tak, to przechodzimy do ScoreScreena (wygrana).
			 * Jeśli nie, to tworzymy nowy Label zawierający podpowiedzi jak dobry był nasz traf i dodajemy go do tablicy rtable.
			 * Potem zwiększa turę i sprawdza czy już skończyliśmy. Jeśli tak to także przechodzimy do ScoreScreena. 
			 * W przeciwnym wypadku wywołujemy funkcję drawNextRow, ustalamy fokus na pierwszy guzik i odpowiednio zmieniamy currentComponent na zero.
			 */
			public void doAction() {
				checkValues();
				if(SettingsContainer.win == true){
					close();
					ScoreScreen score = new ScoreScreen();
					MasterMind.textGUI.showWindow(score,GUIScreen.Position.CENTER);
					
				}				
				two = new Label("B:" + perfect_hits + "W:" + semi_hits);
				rtable.addRow(two);
				++SettingsContainer.turnNumber;
				if(SettingsContainer.turnNumber > SettingsContainer.tries){
					close();
					ScoreScreen score = new ScoreScreen();
					MasterMind.textGUI.showWindow(score,GUIScreen.Position.CENTER);}
				else
					drawNextRow();
				setFocus(brow[0]);
				SettingsContainer.currentComponent = 0;
			}
		};
		
		/**
		 * Konstruktor SelectableButton pozwala nam tworzyć guziki z odpowiednią wartością i napisem
		 * @param text Tekst który wyświetla się na guziku
		 * @param value Wartość znana tylko programowi
		 */
		public SelectableButton(String text, int value) {
			super(text);
            this.value = value;
		}


		/**
		 * Funkcja keyboardInteraction implementuję całą obsługę klawiatury przez nasz guzik.
		 * @param key Klawisz który wcisnęliśmy mając fokus na guziku
		 * @return Rezultat informujący o rozstrzygnięciu zdarzenia lub jego braku.
		 */
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
        	default:
        		return Result.EVENT_NOT_HANDLED;
        	}
        }
	}

	/**
	 * Stała tablica znaków charArr służy do wypełniania guzików jakimś tekstem. Jest to tablica o wymiarach 2x6, zawierająca po prostu sześć pierwszych cyfr i liter alfabetu.
	 */
    private final Character[][] charArr = {
        {'a', 'b', 'c', 'd', 'e', 'f'},
        {'1', '2', '3', '4', '5', '6'} };
    
    /**
     * Stała tablica kolorów colorArray to specjalna tablica przechowująca zmienne określające kolory Terminala. Służy nam ona do zmieniana kolorów labeli, których używamy zamiast buttonów. Zawiera ona sześć dobranych kolorów.
     */
    private final Terminal.Color[] colorArray = {Terminal.Color.RED, Terminal.Color.GREEN, 
    		Terminal.Color.YELLOW, Terminal.Color.BLUE,
    		Terminal.Color.MAGENTA, Terminal.Color.CYAN};

	/**
	 * Panel mainPanel służy jako główny panel okna gry. W nim znajdują się wszystkie inne obiekty. Jego orientacja jest pionowa.
	 */
	private Panel mainPanel = new Panel(Panel.Orientation.VERTICAL);
	
	/**
	 * Panel tablePanel powstał w celu organizacji tablic, z których składa się okno gry. Jego orientacja jest pozioma.
	 */
	private Panel tablePanel = new Panel(Panel.Orientation.HORISONTAL);

	/**
	 * table to nasza główna tablica, która ma tyle kolumn ile wynosi zmienna chars w SettingsContainer. Przechowywać będzie Labele lub SelectableButtony.
	 */
	private Table table = new Table(SettingsContainer.chars);

	/**
	 * Lewa tablica, która przechowuje Labele z numerem tury. Ma tylko jedną kolumnę
	 */
	private Table ltable = new Table(1);
	
	/**
	 * Prawa tablica, która przechowuje Labele z podpowiedziami. Ma tylko jedną kolumnę.
	 */
	private Table rtable = new Table(1);

	/**
	 * button row, w skrócie brow to tablica SelectableButtonów, która startuje z tyloma guzikami ile wynosi zmienna chars w SettingsContainerze.
	 */
	private SelectableButton [] brow = new SelectableButton[SettingsContainer.chars];
	
	/**
	 * special button row, w skrócie sbrow to tak naprawdę tablica przechowująca "fałszywe" guziki, czyli Labele. Tak jak w przypadku brow, startuje z tyloma etykietami ile wynosi zmienna chars.
	 */
	private Label [] sbrow = new Label[SettingsContainer.chars];

	/**
	 * two jest pewnego rodzaju placeholderem, na którego miejsce będziemy wrzucać podpowiedzi.
	 */
	private Component two = new Label("a");
	
	/**
	 * invisible to nowy guzik - ColorControler. Nazywa się tak bo z zamiaru ma być niewidoczny.
	 */
	private ColorControler invisible = new ColorControler("");
	
	/**
	 * Tablica colorValue to obejście tego, ze labele nie mogą mieć pola wartości. Dlatego właśnie tworzymy do nich pomocniczą tablicę, która będzie zawierała wartości liczbowe.
	 */
	private int[] colorValue = new int[SettingsContainer.chars];

	/**
	 * tooltip mówi nam o opcjach, które powinny być zawsze wiadome. Jest to zwykły label.
	 */
	private Component tooltip = new Label("Wciśnij klawisz 'Backspace' aby wywołać monit pomocy. Wciśnij klawisz 'Escape' aby wyjść z gry");
	
	/**
	 * Tablica intów codes to tablica na wylosowany kod, który mamy odgadnąć.
	 */
	private int[] codes = new int[SettingsContainer.chars];
	
	/**
	 * perfect_hits odpowiada liczbie elementów które trafiliśmy w stu procentach.
	 */
	private int perfect_hits = 0;
	
	/**
	 * semi_hits odpowiada liczbie elementów które mają dobry kolor, ale są na złym miejscu.
	 */
	private int semi_hits = 0;
	
	/**
	 * inputTree jest TreeSetem dla wpisywanych przez nas wartości.
	 */
	private TreeSet<Integer> inputTree= new TreeSet<Integer>();
	
	/**
	 * codeTree jest treesetem dla wylosowanych wartości
	 */
	private TreeSet<Integer> codeTree = new TreeSet<Integer>();
	
	/**
	 * Konstruktor GameScreen robi wiele rzeczy, które sprawiają że gra działa. Przede wszystkim ustawia wszystkie panele tam gdzie trzeba
	 * i wykonuje drawNewRow aby narysować pierwszy rządek. Ustawia także przycisk invisible na niewidzialny.
	 * Potem konstruktor sprawdza czy gramy na kolory. Jeśli tak, to focus idzie na invisible, jeśli nie, to
	 * focus idzie na pierwszy guzik z tablicy brow. Potem wykonywana jest funkcja setRandomGoal. 
	 * Na samym końcu do głównego panelu dodawane są skróty klawiszowe. Opis poszczególnych skrótów:
	 * Escape : zamyka program
	 * Backspace: tworzy nowe okno pomocy i wyświetla je
	 * Skróty tworzone tylko przy grze na kolory:
	 * ArrowLeft: Zmienia current component i wygląd labela z BLOCK_SOLID na BLOCK_SPARSE, symulując ruch w lewo
	 * ArrowRight: Zmienia current component i wygląd labela z BLOCK_SOLID na BLOCK_SPARSE, symulując ruch w prawo
	 * ArrowUp: Zwiększa wartość w odpowiednim colorValue i zmienia kolor obecnego labela (sbrow) na kolejny.
	 * ArrowDown: Zmniejsza wartość w odpowiednim colorValue i zmienia kolor obecnego labela (sbrow) na poprzedni.
	 * Enter: Robi to samo co Enter w SelectableButton, aczkolwiek uwzględnia to że pracujemy na labelach.
	 */
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
		mainPanel.addShortcut(Key.Kind.Backspace, new Action() {

			@Override
			public void doAction() {
				HelpScreen pomoc = new HelpScreen();
        		MasterMind.textGUI.showWindow(pomoc, GUIScreen.Position.CENTER);
			}
		});
		if(SettingsContainer.ctype == 2) {
			mainPanel.addShortcut(Key.Kind.ArrowLeft, new Action() {
				
				@Override
				public void doAction() {
					sbrow[SettingsContainer.currentComponent].setText(Character.toString(ACS.BLOCK_SOLID));
					SettingsContainer.currentComponent = (SettingsContainer.currentComponent-1+SettingsContainer.chars)%SettingsContainer.chars;
					sbrow[SettingsContainer.currentComponent].setText(Character.toString(ACS.BLOCK_SPARSE));
				}
			});
			mainPanel.addShortcut(Key.Kind.ArrowRight, new Action() {

				@Override
				public void doAction() {
					sbrow[SettingsContainer.currentComponent].setText(Character.toString(ACS.BLOCK_SOLID));
					SettingsContainer.currentComponent = (SettingsContainer.currentComponent+1)%SettingsContainer.chars;
					sbrow[SettingsContainer.currentComponent].setText(Character.toString(ACS.BLOCK_SPARSE));
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
			mainPanel.addShortcut(Key.Kind.Enter, new Action() {

				@Override
				public void doAction() {
					checkValues();
					if(SettingsContainer.win == true){
						close();
						ScoreScreen score = new ScoreScreen();
							MasterMind.textGUI.showWindow(score,GUIScreen.Position.CENTER);
							
					}
					two = new Label("B:" + perfect_hits + "W:" + semi_hits);
					rtable.addRow(two);
					++SettingsContainer.turnNumber;
					if(SettingsContainer.turnNumber > SettingsContainer.tries){
						close();
						ScoreScreen score = new ScoreScreen();
						MasterMind.textGUI.showWindow(score,GUIScreen.Position.CENTER);
					}
					else{
						sbrow[SettingsContainer.currentComponent].setText(Character.toString(ACS.BLOCK_SOLID));
						drawNextRow();
					}
					//if(SettingsContainer.ctype!=2)
						//setFocus(brow[0]);
					SettingsContainer.currentComponent = 0;
					Arrays.fill(colorValue,0);
					sbrow[0].setText(Character.toString(ACS.BLOCK_SPARSE));
				}
			});
		}
	}
	
	/**
	 * Funkcja drawNextRow rysuje kolejny rządek. Sprawdza ona czy korzystamy z kolorów, jeśli tak
	 * to wypełnia sbrow znakami BLOCK_SOLID i pierwszy ustala na BLOCK_SPARSE. W przeciwnym
	 * wypadku wypełnia po prostu browy wartościami zero i pierwszymi literami/cyframi.
	 * Potem wypełnia komponent one odpowiednim numerem tury i go wypisuje, a także wypełnia środkową
	 * tablicę table odpowiednim elementem (brow lub sbrow).
	 */
	public void drawNextRow() {
		if(SettingsContainer.ctype==2){
			for(int j = 0; j < SettingsContainer.chars; ++j){
				sbrow[j] = new Label(Character.toString(ACS.BLOCK_SOLID), colorArray[0]);				
			}
			sbrow[0].setText(Character.toString(ACS.BLOCK_SPARSE));
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
	
	
	/**
	 * setRandomGoal to funkcja służąca do stworzenia kodu, który będziemy odgadywać. Powołuje ona
	 * generator liczb pseudolosowych i wypełnia tablicę codes kolejnymi intami od 0 do 5.
	 * 
	 */
	public void setRandomGoal(){
		Random generator = new Random();
		for (int i=0;i<SettingsContainer.chars;++i){
			codes[i] = generator.nextInt(6);
			
		}
		
		
	}
	
	/**
	 * Funkcja sprawdzająca checkValues na początku czyści zarówno zmienne określające ilość trafień
	 * jak i treesety. Potem mamy rozgałęzienie - osobny kod jest wykonywany jeśli gramy na kolory
	 * , osobny jak gramy na co innego. Różnica jest taka że korzystamy z pola value w SelectableButton
	 * lub z tablicy colorValue. Jak coś idealnie pasuje, zwiększamy perfect_hits. Jak coś po prostu
	 * się znajduje, ale nie na dobrym miejscu i nie jest perfect_hitem, to zwiększamy semi_hits.
	 */
	public void checkValues(){
        perfect_hits = 0;
        semi_hits = 0;
        codeTree.clear();
        inputTree.clear();
        if(SettingsContainer.ctype!=2){
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
                //System.out.println("Semi: " + val);
			}
		}
        }
        else{
        	for(int i = 0; i<SettingsContainer.chars;i++){
    			if(codes[i]==colorValue[i]){
    				perfect_hits++;
    			}
    			else
    			{
    				inputTree.add(colorValue[i]);
    				codeTree.add(codes[i]);
    			}
    		}
    		if(perfect_hits==SettingsContainer.chars){
    			SettingsContainer.win = true;
    		}
    		for(Integer val : codeTree){
    			if(inputTree.contains(val)){
    				semi_hits++;
    			}
    		}
        }
	}
	
	
	
	
	
}
