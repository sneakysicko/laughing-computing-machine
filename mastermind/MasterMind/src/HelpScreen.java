import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.input.Key;


public class HelpScreen extends Window {
	HelpScreen(){
		super("Pomoc");
		
		Component pomoc = new Label("Sterowanie odbywa się przez strzałki (góra/dół = zmiana znaku, lewo/prawo = poruszanie się).");
		Component pomoc2 = new Label("Enter zatwierdza wzór.");
		Component pomoc3 = new Label("  W prawej sekcji pojawi się informacja o tym ile trafiliśmy kompletnie (na miejscu i dobry symbol/kolor) i informacja o tym ile trafiliśmy symboli, ale na złej pozycji. ");
		Component pomoc4 = new Label("Ponowne wciśnięcie F1 zamyka to okno");
		
		addComponent(pomoc);
		addComponent(pomoc2);
		addComponent(pomoc3);
		addComponent(pomoc4);
	}
	public void play(){
		boolean check = true;
		while(check){
			Key key = MasterMind.terminal.readInput();
			if(key != null){
				switch(key.toString()){
				case("Backspace"): 
				close();
				break;
				default:
				break;
				}
			}
		}
	}
}
