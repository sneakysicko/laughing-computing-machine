import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Interactable;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.Interactable.Result;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.input.Key;

/**
 * Okno HelpScreen jest po prostu zbiorem Labelów, które mają za zadanie przekazać użytkownikowi o co biega w programie.
 * @author Otoshigami
 *
 */
public class HelpScreen extends Window {
	/**
	 * Klasa SelectableButton2 służy dokładnie do tego do czego SelectableButton w GameScreenie
	 * Chcemy mieć guzik który operuje odpowiednie skróty klawiszowe.
	 * @author Otoshigami
	 *
	 */
	private class SelectableButton2 extends Button {
		
		/**
		 * Interact to prosta funkcja, która zamyka okno pomocy
		 */
		public void interact(){
            close();
		}
		
		/**
    	 * Konstruktor SelectableButton2 jest najzwyczajniejszym wywołaniem konstruktora guzika.
    	 * @param s	Napis, jaki chcemy żeby pokazał się na guziku.
    	 */
		public SelectableButton2(String text) {
			super(text);
		}
		
		/**
		 * Funkcja odpowiedzialna za interakcje z klawiatura. W tym wypadku operujemy tylko na Backspace
		 * który zamyka nasze okno
		 * @return Rezultat mówiący czy zdarzenie zostało obsłużone, czy nie.
		 * @param key Klawisz który naciskamy.
		 */
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
	
	/**
	 * Konstruktor tworzy serię labeli, które opisują sterowanie w GameScreenie, tworzy 
	 * SelectableButton2 odpowiedzialny za sterowanie i ustawia je odpowiednio w oknie.
	 * Ustala także focus na guziku, aby sterowanie miało sens.
	 */
	HelpScreen(){
		super("Pomoc");
		
		Component pomoc = new Label("Sterowanie odbywa się przez strzałki (góra/dół = zmiana znaku, lewo/prawo = poruszanie się).");
		Component pomoc2 = new Label("Enter zatwierdza wzór.");
		Component pomoc3 = new Label("W prawej sekcji pojawi się informacja o tym ile trafiliśmy kompletnie [B]");
		Component pomoc4 = new Label("(na miejscu i dobry symbol/kolor) i informacja o tym ile trafiliśmy symboli, ale na złej pozycji [W]. ");
		SelectableButton2 guzik = new SelectableButton2("Wciśnij Backspace by wyjść");
		addComponent(pomoc);
		addComponent(pomoc2);
		addComponent(pomoc3);
		addComponent(pomoc4);
		addComponent(guzik);
		setFocus(guzik);
	}
	
}
