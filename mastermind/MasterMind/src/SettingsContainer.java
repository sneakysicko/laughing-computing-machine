/**
 * Klasa SettingsContainer jest podręcznym zbiornikiem zmiennych, które są potrzebne w kilku różnych klasach.
 * @author Otoshigami
 *
 */
public class SettingsContainer {
	/**
	 * tries to zmienna oznaczająca ilość prób. Domyślnie ustawiona na wartość "dziesięć".
	 */
	public static int tries = 10; 
	
	/**
	 * Zmienna oznaczająca ilość znaków. domyślnie ustawiona na wartość 5.
	 */
	public static int chars = 5; 
	
	/**
	 * 0 - znaki, 1- cyfry, 2 - kolory. Zmienna ta oznacza typ znaków, od niej zależy większość działania GameScreen.
	 */
	public static int ctype = 1; 
	
	/**
	 * Ze względu na limity w Lanternie należy wprowadzić pewne ograniczenia górne do zmiennych chars i tries. Zmienna charLimit jest właśnie zmienną ograniczającą ilość znaków.
	 */
	public static final int charsLimit = 9; 
	
	/**
	 * Ze względu na limity w Lanternie należy wprowadzić pewne ograniczenia górne do zmiennych chars i tries. Zmienna charLimit jest właśnie zmienną ograniczającą ilość prób.
	 */
	public static final int triesLimit = 15; 
	
	/**
	 * turnNumber to zmienna która opisuje nam ilość tur. Zaczyna od jeden i jest zmieniana, ewentualnie resetowana dalej. Jest bardzo ważne żeby była dostępna wszędzie, ponieważ korzystają z niej GameScreen i ScoreScreen.
	 */
	public static int turnNumber = 1;
	
	/**
	 * currentComponent to zmienna mówiąca nam indeks komponentu który jest aktualnie zaznaczony. Jest to ważne ze względu na ograniczenia lanterny. Jest umieszczona tutaj ze względu na korzystanie z HelpScreena i kilku pomocniczych klas w GameScreenie. Przechowana tutaj zmienna nie zmieni swojej wartości, co pozwala na kontynuowanie gry.
	 */
	public static int currentComponent = 0; 
	
	/**
	 * win jest zmienną która nam mówi czy gracz zwyciężył, czy nie. Ze względu na konstrukcję Lanterny lepszym rozwiązaniem jest zrobienie z tego statycznej zmiennej, żeby GameScreen mógł komunikować się ze ScoreScreenem.
	 */
	public static boolean win = false;
}
