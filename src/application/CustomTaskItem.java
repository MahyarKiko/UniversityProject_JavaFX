package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.prefs.Preferences;


/**
 * die Klasse CustomTaskItem stellt eine benutzerdefinierte Darstellung eines Aufgabenelements dar
 * sie erweitert die HBox und enthaelt labels, checkboxen und buttons zur Anzeige, Bearbeitung und Loeschung von Aufgaben
 */
public class CustomTaskItem extends HBox{
	
	 VBox items;  // container fuer Aufgabenelemente
	 HBox btns;   // container fuer Schaltflaechen 
	 Label lblTaskTitle, lblTaskDetail;  // Labels fuer Aufgabentitel und details
     CheckBox isDone;  // Checkbox fuer Aufgabenstatus
	
	Button btnDeleteTask, btnEditTask; // Buttons fuer loeschen und bearbeiten
	 private Preferences prefs = Preferences.userNodeForPackage(CustomTaskItem.class);
	
	
	/**
     * gibt die Checkbox zurueck, die den Status der Aufgabe darstellt wenn es erledigt oder nicht erledigt ist
     *
     * @return Die Checkbox zur Darstellung des Aufgabenstatus
     */
	
	public CheckBox getChBoxIsDone() {
		return isDone;
	}
	
	
	 /**
     * gibt den Button zurueck, um die Aufgabe zu loeschen
     *
     * @return der Button zum Loeschen der Aufgabe
     */
	
	public Button getBtnDeleteTask() {
        return btnDeleteTask;
    }
	
	
	 /**
     * gibt den Button zurueck,um die Aufgabe zu bearbeiten
     *
     * @return Der button zum Bearbeiten der Aufgabe
     */

    public Button getBtnEditTask() {
        return btnEditTask;
    }
	
    
    /**
     * gibt Label zurueck,was den Titel der Aufgabe enthaeelt
     *
     * @return Das Label zur Darstellung des Aufgabentitels
     */
    
    public Label getLblTaskTitle() {
    	return lblTaskTitle;
    }
    
    
    /**
     * gibt den VBox Container zurueck, der die Aufgabenelemente enthaelt
     *
     * @return Die VBox,die  die Aufgabenelemente enthaelt
     */
    
    public VBox getItems() {
    	return items;
    }
    
     
    /**
     * gibt das Label zurueck, das die Details der Aufgabe enthaelt
     *
     * @return Das Label zur Darstellung der Aufgabendetails
     */
    
    public Label getLblTaskDetail( ) {
    	return lblTaskDetail;
    }
    
    
    /**
     * konstruktor zur Initialisierung ein benutzerdefinierten Aufgabenelement
     *
     * @param taskTitle   Der Titel der Aufgabe
     * @param taskDetail  Die Details der Aufgabe
     * @param isDoneValue Der Status der Aufgabe, ob es erledigt oder nicht erledigt ist
     */
	
	public CustomTaskItem(String taskTitle, String taskDetail, boolean isDoneValue) {
		
		//initialisierung der UI Elemente
		
		items = new VBox();
		
		lblTaskTitle = new Label(taskTitle);
		//instanz des labels lblTaskTitle erstellt
		 
		lblTaskTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
		// Schriftgroeße und Textfarbe anpassen

		lblTaskDetail = new Label(taskDetail);
		lblTaskDetail.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: black;");
	

		
       isDone = new CheckBox();
       isDone.setSelected(isDoneValue);
       isDone.setPrefWidth(50);


//initialisierung der Bottons fuer loeschen und bearbeiten
		btns = new HBox(); 
		btns.setPrefWidth(150);
		
		btnDeleteTask = new Button("Delete");
		btnDeleteTask.setPrefWidth(70);
		btnDeleteTask.setPrefHeight(20);
		btns.setSpacing(5);
		
	    //aktionen fuer bottons festlegen
		btnDeleteTask.setOnAction(v -> {
		System.out.println( "Delete");
		});
		
		btnEditTask = new Button("Edit");
		btnEditTask.setPrefWidth(70);
		btnEditTask.setPrefHeight(20);
		
		btnEditTask.setOnAction(v -> {
			System.out.println( "Edit");
			});
		
		if(isDoneValue) {
			setStyle("-fx-background-color: #f27a17"); //Hintergrundfarbe der HBox aendern,
			btnDeleteTask.setStyle("-fx-background-color: #f3f3f3; -fx-font-weight: bold; -fx-text-fill: #E42121;");
			btnEditTask.setStyle("-fx-background-color: #f3f3f3; -fx-font-weight: bold; -fx-text-fill: #78CB7B;");
		//wenn die Aufgabe erledigt ist
		} else {
			btnDeleteTask.setStyle("-fx-background-color: #E42121; -fx-font-weight: bold; -fx-text-fill: white;");
			btnEditTask.setStyle("-fx-background-color: #78CB7B; -fx-font-weight: bold;");
		}

		// UI Elemente fuer Container hierarchie hinzufuegen
		btns.getChildren().addAll(btnEditTask, btnDeleteTask);
		items.getChildren().addAll(lblTaskTitle,lblTaskDetail);
		getChildren().addAll(isDone,items, btns);
	
		setPadding(new Insets(15,15,15,15)); // Padding und breitenänderungslistener festlegen
	
		 widthProperty().addListener((obs, oldWidth, newWith) -> { 
	            items.setPrefWidth(newWith.doubleValue()- 250);
	        });
		
	}
	
	

}
