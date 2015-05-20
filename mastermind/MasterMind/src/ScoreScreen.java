import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Interactable;
import com.googlecode.lanterna.gui.InteractableContainer;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.Interactable.Result;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.CheckBox;
import com.googlecode.lanterna.gui.component.CheckBoxList;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.InteractableComponent;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.RadioCheckBoxList;
import com.googlecode.lanterna.gui.component.Table;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.gui.dialog.MessageBox;
import com.googlecode.lanterna.gui.dialog.TextInputDialog;
import com.googlecode.lanterna.gui.layout.LinearLayout;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

public class ScoreScreen extends Window {
	private Panel mainPanel = new Panel(Panel.Orientation.VERTICAL);
	private Panel tablePanel = new Panel(Panel.Orientation.HORISONTAL);
	private Panel buttonPanel = new Panel(new Border.Invisible(), Panel.Orientation.HORISONTAL);
	private Table lptable = new Table(1);
	private Table nicktable = new Table(1);
	private Table scoretable = new Table(1);
	String nickname = null;
	String username = null;
	Score scores;
	Integer userscore;
	ScoreScreen(){
		super("Scores");
		userscore = SettingsContainer.turnNumber;
		buttonPanel.addComponent(highButton, LinearLayout.GROWS_HORIZONTALLY);
		buttonPanel.addComponent(againButton, LinearLayout.GROWS_HORIZONTALLY);
		tablePanel.addComponent(lptable);
		tablePanel.addComponent(nicktable);
		tablePanel.addComponent(scoretable);
		mainPanel.addComponent(tablePanel);
		mainPanel.addComponent(buttonPanel);
		
		addComponent(mainPanel);
      
       
       
		mainPanel.addShortcut(Key.Kind.Escape, new Action() {
			
			@Override
			public void doAction() {
				close();
			}
		});
		close();
	}
	Button closeButton = new Button("Zamknij", new Action() {

		@Override
		public void doAction() {
			close();
			
		}});	
	Button okButton = new Button("OK", new Action() {

		@Override
		public void doAction() {
			mainPanel.removeComponent(tablePanel);
			buttonPanel.removeAllComponents();
			
			buttonPanel.addComponent(againButton, LinearLayout.GROWS_HORIZONTALLY);
			setFocus(againButton);
			buttonPanel.addComponent(closeButton, LinearLayout.GROWS_HORIZONTALLY);
			
		}});	
	Button highButton = new Button("High Scores", new Action() {

		@Override
		public void doAction() {
			
			while(true){
				
					 nickname=(TextInputDialog.showTextInputBox(getOwner(),"Podaj swój nick(max 10 znaków)","Nick",null));
					if(nickname.length()>10 || nickname.isEmpty()){
						MessageBox.showMessageBox(getOwner(),"Błąd","Wprowadzono niepoprawną nazwę!");
						
						
					}else{
						
						username=nickname;
						break;
						}
				}
			
			buttonPanel.removeAllComponents();
			buttonPanel.addComponent(okButton, LinearLayout.GROWS_HORIZONTALLY);
			setFocus(okButton);
			//setScore();
			try{
				updateScore();
				}
			 catch (FileNotFoundException e) {
				scores = new Score();
				sortScore();
				setScore();
			}
			for (int i = 0 ; i<scores.nName.size();++i){
				lptable.addRow(new Label(""+ (i+1)));
				nicktable.addRow(new Label(scores.nName.get(i)));
				scoretable.addRow(new Label(""+scores.nScore.get(i)));
				
			}
			
			
		}});	
	
	
	
	public void updateScore() throws FileNotFoundException{
		scores = getScore();
		sortScore();
		setScore();	
			}
	
	
	public void sortScore(){
		String player;
		Integer playerscore;
		String ptmp;
		Integer stmp;
		for (int i = 0 ;i<10;i++){
			playerscore = scores.nScore.get(i);
			player = scores.nName.get(i);
			if (userscore<=playerscore){
				stmp = playerscore;
				ptmp = player;
				scores.nScore.set(i, userscore);
				scores.nName.set(i, username);
				userscore = stmp;
				username = ptmp;
				
			}
			
			
		}
		
		
	}
	
	
	public Score getScore() throws FileNotFoundException{
		
		try {
			ObjectInputStream i=new ObjectInputStream(new FileInputStream("scores.tmp") );
			
			scores = (Score) i.readObject();
			i.close();
			return scores;
			} catch (Exception e) {
			throw new FileNotFoundException();
			
			}

		
	}
	public void setScore(){
		
		try {
			ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("scores.tmp") );
			o.writeObject(scores);
			o.close();
			} catch (IOException e) {
			System.out.println("IOException!");

		
	}
	}
	
	Button againButton = new Button("Zagraj jeszcze raz", new Action() {

		@Override
		public void doAction() {
			mainPanel.removeComponent(tablePanel);
			buttonPanel.removeAllComponents();
			
			buttonPanel.addComponent(oldSetButton, LinearLayout.GROWS_HORIZONTALLY);
			setFocus(oldSetButton);
			buttonPanel.addComponent(newSetButton, LinearLayout.GROWS_HORIZONTALLY);
			
		}
		
		
	});		
	
	
	Button oldSetButton = new Button("Poprzednie ustawienia", new Action() {

		@Override
		public void doAction() {
			SettingsContainer.currentComponent = 0;
			SettingsContainer.win = false;
			SettingsContainer.turnNumber = 0;
			GameScreen game = new GameScreen();
           	MasterMind.textGUI.showWindow(game, GUIScreen.Position.CENTER);			
           	close();
		
		}
		
		
	});		
	Button newSetButton = new Button("Nowe ustawienia", new Action() {

		@Override
		public void doAction() {
			SettingsContainer.currentComponent = 0;
			SettingsContainer.win = false;
			SettingsContainer.tries = 0;
			SettingsContainer.turnNumber = 0;
			SettingsContainer.chars = 0;
			SettingsContainer.ctype = 0;
			
			OptionsScreen options = new OptionsScreen();
		    MasterMind.textGUI.showWindow(options, GUIScreen.Position.CENTER);
			GameScreen game = new GameScreen();
           	MasterMind.textGUI.showWindow(game, GUIScreen.Position.CENTER);			
			close();
		
		}
		
		
	});		
	
	
	
	}
