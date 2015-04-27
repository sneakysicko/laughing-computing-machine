
public class SettingsContainer {
	public static int tries = 10; //Zmienna oznaczająca ilość prób. Domyślnie ustawiona na tą wartość
	public static int chars = 5; //Zmienna oznaczająca ilość znaków. domyślnie ustawiona na taką wartość
	public static int ctype = 1; //Typ znaków: 1 - kolory, 2-litery, 3 - cyferki.
	public static final int charsLimit = 9; //Zmienne wewnętrzne limitujące opcje custom (od dołu zawsze 0)
	public static final int triesLimit = 15; //Jak wyżej
	public static int turnNumber = 1; //Trackuje która jest tura, wykorzystywany do gry i w score screenie
	public static int currentComponent = 0; // Wykorzystywany do zlokalizowania który komponent jest obecnie zaznaczony w GameScreenie.
	public static boolean win = false;
}
