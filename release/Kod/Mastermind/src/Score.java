import java.io.Serializable;
import java.util.ArrayList;


public class Score implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<String> nName = new ArrayList<String>();
	public ArrayList<Integer> nScore = new ArrayList<Integer>();

public Score(){
for (int i = 0;i<10;i++){
	nName.add("Player");
	nScore.add(999);
}	
}

}