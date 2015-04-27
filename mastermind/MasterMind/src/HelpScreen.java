import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Interactable;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.Interactable.Result;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.input.Key;


public class HelpScreen extends Window {
	private class SelectableButton2 extends Button {
		public void interact(){
            close();
		}
		
		public SelectableButton2(String text) {
			super(text);
		}

        @Override
        public Interactable.Result keyboardInteraction(Key key) {
        	switch(key.getKind()) {
        	
        	case Backspace:
        		interact();
        		return Result.EVENT_HANDLED;
        	default:
        		return Result.EVENT_NOT_HANDLED;
        	}
        }
	}
	
	HelpScreen(){
		super("Pomoc");
		
		Component pomoc = new Label("Sterowanie odbywa się przez strzałki (góra/dół = zmiana znaku, lewo/prawo = poruszanie się).");
		Component pomoc2 = new Label("Enter zatwierdza wzór.");
		Component pomoc3 = new Label("  W prawej sekcji pojawi się informacja o tym ile trafiliśmy kompletnie (na miejscu i dobry symbol/kolor) i informacja o tym ile trafiliśmy symboli, ale na złej pozycji. ");
		Component pomoc4 = new Label("Ponowne wciśnięcie F1 zamyka to okno");
		SelectableButton2 guzik = new SelectableButton2("Wciśnij Backspace by wyjść");
		addComponent(pomoc);
		addComponent(pomoc2);
		addComponent(pomoc3);
		addComponent(pomoc4);
		addComponent(guzik);
		setFocus(guzik);
	}
	
}
