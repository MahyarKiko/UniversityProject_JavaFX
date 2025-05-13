package application;

import javafx.collections.ObservableList;




/**
 * diese Klasse repraesentiert eine Aufgaben Einheit
 */

public class Item{
	    	   
	 private String Title; //  titel der Aufgabe
	 private String Detail; //  details der Aufgabe
	 private boolean IsDone; // es gibt an, ob die Aufgabe erledigt ist oder nicht
	 private ObservableList<String> tasks; // liste der Aufgaben aber nicht im Aktuellen Code verwendet
	
	/**
     * konstruktor zur initialisierung einer neuen Aufgaben Einheit
     *
     * @param title Der Titel der Aufgabe
     * @param detail Die Details oder Beschreibung der Aufgabe
     * @param isDone  gibt an,ob die Aufgabe erledigt ist oder nicht
     */
	
	public Item(String title, String detail, boolean isDone) {
		super();
		Title = title;
		Detail = detail;
		IsDone = isDone;
	}
	
	
	 /**
     * ueberprüft ob die Aufgabe als erledigt markiert ist
     *
     * @return true, wenn die Aufgabe erledigt ist, sonst false
     */
	
	public boolean isIsDone() {
		return IsDone;
	}
	

	/**
     * setzt den Status der Aufgabe erledigt oder nicht erledigt
     *
     * @param isDone Der neue Status der Aufgabe
     */
	
	public void setIsDone(boolean isDone) {
		IsDone = isDone;
	}
	

	 /**
     * gibt den Titel der Aufgabe zurueck
     *
     * @return Der Titel der Aufgabe
     */
	
	public String getTitle() {
		return Title;
	}
	
	
	 /**
     * setztt den Titel der Aufgabe
     *
     * @param title Der neue Titel der Aufgabe
     */
	
	public void setTitle(String title) {
		Title = title;
	}
	
	 /**
     * gibt die Details oder Beschreibung der Aufgabe zurueck
     *
     * @return Die Details oder Beschreibung der Aufgabe
     */
	
	public String getDetail() {
		return Detail;
	}
	
	 /**
     * setzt die Details oder Beschreibung der Aufgabe
     *
     * @param detail Die neuen Details oder Beschreibung der Aufgabe
     */
	
	public void setDetail(String detail) {
		Detail = detail;
	}
	
	 /**
     * gibt die Liste der Aufgaben zurück
     *
     * @return Die Liste der Aufgaben
     */
	
	public ObservableList<String> getTasks() {
		return tasks;
	}
	
	/**
     * setzt die Liste der Aufgaben
     *
     * @param tasks Die neue Liste der Aufgaben
     */
	
	public void setTasks(ObservableList<String> tasks) {
		this.tasks = tasks;
	}


	



}
