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
import com.googlecode.lanterna.gui.dialog.TextInputDialog;
import com.googlecode.lanterna.gui.layout.LinearLayout;

/**
 * OptionsScreen to pierwsze okno które zobaczy użytkownik. Jest ono bardzo ważne, bo ustawiamy w nim kluczowe rzeczy.
 * @author Otoshigami
 *
 */
public class OptionsScreen extends Window {
	
	/**
	 *  Panel mainPanel służy jako główny panel okna. W nim znajdują się wszystkie inne obiekty. Jego orientacja jest pionowa.
	 */
	private Panel mainPanel = new Panel(Panel.Orientation.VERTICAL);
	
	/**
	 * Pomocniczy panel o orientacji poziomej
	 */
	private Panel optionPanel = new Panel(Panel.Orientation.HORISONTAL);
	
	/**
	 * Panel do lewej grupy radio buttonów.
	 */
	private Panel leftPanel = new Panel();
	
	/**
	 * Panel do prawj grupy radio buttonów.
	 */
	private Panel rightPanel = new Panel();
	
	/**
	 * Lista radio buttonów po lewej
	 */
	private RadioCheckBoxList option_left = new RadioCheckBoxList();
	
	/**
	 * Lista radio buttonów po prawej.
	 */
	private RadioCheckBoxList option_right = new RadioCheckBoxList();
	
	/**
	 * Konstruktor domyślny okna OptionsScreen robi wszystko i nadaje odpowiednią funkcjonalność
	 * temu oknu. Na początku dodaje on opcje do odpowiednich paneli i ustawia je w oknie.
	 * Ponadto tworzone zostają dwa guziki - exitButton, który wychodzi z programu i startButton,
	 * który sprawdza jakie opcje są zaznaczone, ustala takie zmienne w SettingsContainerze i
	 * wyświetla dodatkowe okna z wpisywaniem, jeśli korzystamy z opcji własnej. Są tam także dwa
	 * sprawdzenia - czy to co wpisaliśmy jest liczbą, oraz czy jest to w naszym zakresie.
	 * Po ustaleniu pól SettingsContainera okno się zamyka.
	 * 
	 */
	public OptionsScreen(){
		super("Konfiguracja");
		
		//Dodawanie opcji menu z lewej strony.
		option_left.addItem("4 znaki 9 prób");
		option_left.addItem("5 znaków 12 prób");
		option_left.addItem("6 znaków 15 prób");
		option_left.addItem("x znaków y prób");
		leftPanel.addComponent(option_left);
		option_right.addItem("kolory");
		option_right.addItem("znaki");
		option_right.addItem("cyfry");
		rightPanel.addComponent(option_right);

		optionPanel.addComponent(leftPanel);
		optionPanel.addComponent(rightPanel);
		
		//Dodawanie dolnego panelu, który będzie zawierał przycisk startu i przycisk końca.
		Panel buttonPanel = new Panel(new Border.Invisible(), Panel.Orientation.HORISONTAL);
		Button exitButton = new Button("Wyjście", new Action() {
			@Override
			public void doAction()  {
				getOwner().getScreen().stopScreen();
				System.exit(0);
			}
		});
		Button startButton = new Button("Zacznij", new Action() {
			@Override
			public void doAction()  {
				if(option_left.getCheckedItemIndex()==0){
					SettingsContainer.tries=9;
					SettingsContainer.chars=4;
				}
				if(option_left.getCheckedItemIndex()==1){
					SettingsContainer.tries=12;
					SettingsContainer.chars=5;
				}
				if(option_left.getCheckedItemIndex()==2){
					SettingsContainer.tries=15;
					SettingsContainer.chars=6;
				}
				if(option_left.getCheckedItemIndex()==3){
					boolean test = true;
					while(test){
						try{
							int new_chars=5;
							String temp = TextInputDialog.showTextInputBox(getOwner(),"Podaj ilość znaków","Podaj ilość znaków od 4 (minimum) do " + SettingsContainer.charsLimit + " (maksimum). Przycisk Cancel daje wartości domyślne.","5");
							if(temp!=null){
							new_chars=Integer.parseInt(temp);
							}
							if(new_chars>=4 && new_chars<=SettingsContainer.charsLimit){
								test = false;
								SettingsContainer.chars=new_chars;
							}else{
								MessageBox.showMessageBox(getOwner(),"Błąd","Wprowadzono coś co wykracza poza zakres");
							}
						}catch(NumberFormatException nfe){
							MessageBox.showMessageBox(getOwner(),"Błąd","Wprowadzono coś co nie jest poprawną liczbą całkowitą");
						}

					}
					test = true;
					while(test){
						try{
							int new_tries = 10;
							String temp = TextInputDialog.showTextInputBox(getOwner(),"Podaj ilość prób","Podaj ilość prób od 5 (minimum) do " + SettingsContainer.triesLimit + " (maksimum). Przycisk Cancel daje wartości domyślne.","10");
							if(temp!=null){
							new_tries=Integer.parseInt(temp);
							}
							if(new_tries>=5 && new_tries<=SettingsContainer.triesLimit){
								test = false;
								SettingsContainer.tries=new_tries;
							}else{
								MessageBox.showMessageBox(getOwner(),"Błąd","Wprowadzono coś co wykracza poza zakres");
							}
						}catch(NumberFormatException nfe){
							MessageBox.showMessageBox(getOwner(),"Błąd","Wprowadzono coś co nie jest poprawną liczbą całkowitą");
						}
					}
				}
				if(option_right.getCheckedItemIndex()==0){
					SettingsContainer.ctype=2; //kolory
				}
				if(option_right.getCheckedItemIndex()==1){
					SettingsContainer.ctype=0; //znaki
				}
				if(option_right.getCheckedItemIndex()==2){
					SettingsContainer.ctype=1; //cyfry
				}
				close();

				//game.play();

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
