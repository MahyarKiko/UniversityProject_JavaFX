package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;



/**
 * Die Klasse TaskManagerTester ist  Testklasse fuer die Item  und CustomTaskItem Klassen
 * es enthaelt die Methoden zum Testen der Funktionalitaet beider Klassen
 */
public class TaskManagerTester extends Application {
	
	/**
     * die Hauptmethode des Programms. Sie startet die javaFx Anwendung
     *
     * @param args Die Befehlszeilenargumente
     */
    public static void main(String[] args) {
    	launch();
        testItemClass();
    }
    
    /**
     * die start Methode wird beim Starten der javaFx Anwendung aufgerufen
     * sie erstellt eine Szene 
     *
     * @param primaryStage Die Hauptbuehne der Anwendung
     */
    public void start(Stage primaryStage) {
    	
    	
    	Scene s = new Scene(testCustomTaskItemClass(), 300, 100);// erstelle eine Szene mit
    	//einem getesteten CustomTaskItem
    	primaryStage.setScene(s); 
	
    }

    
    /**
     * eine Methode fuer Testen der klasse Item 
     * sie erstellt ein Item Objek und  aktualisiert seine Eigenschaften und
     *  gibt sie aus
     */
    private static void testItemClass() {
    	
    	 
        Item item1 = new Item("Aufgabe abschließen","Projekt bis Freitag abschließen", false);//erstelle ein Item objekt 
    	 //und teste seine Eigenschaften und Aktualisierung

        System.out.println("klasse Item testen:");
        System.out.println("Title: " + item1.getTitle());
        System.out.println("Detail: " + item1.getDetail());
        System.out.println("Is Done: " + item1.isIsDone());

        item1.setTitle("Aktualisiert Titel");
        item1.setDetail("Aktualisiert detail");
        item1.setIsDone(true);

        System.out.println("Aktualisiert Titel: " + item1.getTitle());
        System.out.println("Aktualisiert Detail: " + item1.getDetail());
        System.out.println("Aktualisiert Is Done: " + item1.isIsDone());
        System.out.println("Tst der Artikelklasse ist abgeschlossen\n");
    }

    /**
     * eine Methode fuer Testen der CustomTaskItem klasse
     * sie erstellt ein CustomTaskItem objekt und aktualisiert seine Eigenschaften 
     * und gibt sie aus
     *
     * @return Ein HBox container mit dem getesteten CustomTaskItem
     */
    
    private static HBox testCustomTaskItemClass() {
    	
    	
        CustomTaskItem customTaskItem1 = new CustomTaskItem("Lebensmittel kaufen", "Milch, Eier, Brot", true);
     // erstelle ein CustomTaskItem objekt und teste seine Eigenschaften und Aktualisierung

        System.out.println("CustomTaskItem Klasse testen:");
        
        System.out.println("Title: " + customTaskItem1.getLblTaskTitle().getText());
         System.out.println("Detail: " + customTaskItem1.getLblTaskDetail().getText());
        System.out.println("Is Done: " + customTaskItem1.getChBoxIsDone().isSelected());

       customTaskItem1.getLblTaskTitle().setText("Kaufe neue Lebensmittel");
        customTaskItem1.getLblTaskDetail().setText("Gemüse, Obst, Snacks");
        customTaskItem1.getChBoxIsDone().setSelected(false);

        System.out.println("Aktualisiert Title: " + customTaskItem1.getLblTaskTitle().getText());
        System.out.println("Aktualisiert Detail: " + customTaskItem1.getLblTaskDetail().getText());
        System.out.println("Aktualisiert Is Done: " + customTaskItem1.getChBoxIsDone().isSelected());
       
        System.out.println("Test der Klasse CustomTaskItem ist abgeschlossen");
        
        return customTaskItem1; 
    }
}
