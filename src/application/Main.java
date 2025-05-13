package application;

import javafx.scene.control.CheckBox;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;




/**
 * diese Klasse stellt eine Anwendung zur Verwaltung von Aufgaben dar
 * die Anwendung verwendet JavaFx fuer die Benutzeroberflaeche und Json Dateien
 * fuer Speicherung von Aufgabeninformationen
 *
 * @author Mahyar Aghazadeh
 * @version 1.0
 */
public class Main extends Application {
	
	/**
     * die Hauptmethode,die beim Starten der Anwendung aufgerufen wird
     *
     * @param args Die Befehlszeilenargumente
     */

	private double x = 0;
	private double y = 0;

	String filePath = "savefile.kiko";

	boolean isFullScreen = true;
	

	public static ObservableList<CustomTaskItem> tasks;

	ListView<CustomTaskItem> listView;

	List<Item> todos = new ArrayList<>();

	TextField txtTaskTitle, txtTaskDetail;
	Button btAddTask, btnReturn, closeBtn, resizeBtn, miniBtn, btnNewTask;
	Label lblTaskTitle, lblTaskDetail;

	VBox root, addTaskLayout, dashbord, taskBox;
	HBox menuBar, programBody, addTaskLayoutBtns;
	Scene s;

	String title = "", detail = "";

	int itemEditPosition = -1, itemDeletePosition = -1, itemCheckPosition = -1;

	boolean isEdit = false;

	Utility utility = new Utility();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();	
		}
	
	/**
     * diese Methode wird aufgerufen,um die Benutzeroberflaeche der Anwendung zu initialisieren
     *
     * @param primaryStage Die Stage der Anwendung
     */

	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		/*
	     * initialisierung der Anwendung und Aufbau der GUI und Anzeige der Stage
					
		*/
		
	
	 	/*
		 * erstellen einer ObservableList fuer die benutzerdefinierten Aufgabenobjekte
		 */
		tasks = FXCollections.observableArrayList();
		
		listView = new ListView<>(tasks);
		
		listView.setMinHeight(650);
		
		// Event Handler hinzufeugen, um die Farbe bei einem Mausklick zu aendern
        listView.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown() ) {
                listView.setStyle("-fx-background-color: #c3c3c3;"); 
               
            }
        });

		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("TODoList");
		primaryStage.setFullScreen(true);
	

		
		/*
		 * erstellen der Menueleiste an oberen Rand der Anwendung
		 */
		root = new VBox();
		menuBar = new HBox();
		btnReturn = new Button();

		menuBar.setStyle("-fx-border-color: #aaaaaa");
		menuBar.setMinHeight(50);
		menuBar.setAlignment(Pos.BASELINE_RIGHT);
		menuBar.setPadding(new Insets(12.5, 5, 5, 5));
		closeBtn = new Button("❌");

		// hinzufuegen der Schließ Funktionalitaet beim klicken auf den Schließen Button
		closeBtn.setOnAction(v -> {
			primaryStage.close();
		}
		);

		resizeBtn = new Button("⬛");
		
		resizeBtn.setOnAction(v -> {
			isFullScreen = !isFullScreen;
			primaryStage.setFullScreen(isFullScreen);
		}
		);

		miniBtn = new Button("—");

		miniBtn.setOnAction(v -> primaryStage.setIconified(true));
		
		// hinzufuegen der Buttons zur Menueliste
		menuBar.getChildren().add(miniBtn); //minimieren und maximieren
		menuBar.getChildren().add(resizeBtn); // fullscrenn
		menuBar.getChildren().add(closeBtn); //schließen
		menuBar.setSpacing(5);
		menuBar.setStyle("-fx-background-color: #fda049"); 

		menuBar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				x = event.getSceneX();
				y = event.getSceneY();
			}
		});
		// Drag and Drop Funktionalitaet zum Verschieben der Anwendungsfenster
		menuBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - x);
				primaryStage.setY(event.getScreenY() - y);
			}
		}
		);

		programBody = new HBox();
		programBody.setStyle("-fx-border-color: #aaaaaa");
		programBody.setPadding(new Insets(5, 5, 5, 5));
		double programBodyHeight = primaryStage.getHeight() - menuBar.getHeight();
		
		/*
		 * erstellen des Dashboards llinker Bereich mit New Task Button und Add Task
		 * Layout
		 */
		dashbord = new VBox();
		dashbord.setStyle("-fx-border-color: #aaaaaa ");
		dashbord.setPrefWidth(250);
		dashbord.setAlignment(Pos.TOP_CENTER);
		dashbord.setStyle("-fx-background-color: #d8d8d8");

		addTaskLayout = new VBox();
		addTaskLayout.setAlignment(Pos.CENTER);
		addTaskLayout.setPadding(new Insets(0, 15, 0, 15));
		btnNewTask = new Button("New Task");

		/*
		 * konfiguration New Task Button,um das Hinzufuegen einer neuen Aufgabe zu
		 * ermoeglichen
		 */
		btnNewTask.setPrefWidth(200);
		btnNewTask.setPrefHeight(50);
		btnNewTask.setStyle("-fx-background-color:#f27a17; -fx-text-fill: black;");
		Insets margin = new Insets(5, 0, 0, 0); // Abstand von Oben fuer den Button new task
		VBox.setMargin(btnNewTask, margin);

		btnNewTask.setOnAction(v -> {
	    System.out.println("New Task");
		btnNewTask.setVisible(false);
		addTaskLayout.setVisible(true);

		});
		addTaskLayout.setSpacing(5);

		// erstellen von Labels und Textfeldern fuer den Task Titel und Task Details
		lblTaskTitle = new Label("Task Title");
		lblTaskTitle.setPrefWidth(250);
		lblTaskTitle.setTextAlignment(TextAlignment.LEFT);
		txtTaskTitle = new TextField();
		txtTaskTitle.setPromptText("Enter Titel");
		lblTaskTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");

		lblTaskDetail = new Label("Task Detail");
		lblTaskDetail.setPrefWidth(250);
		lblTaskDetail.setTextAlignment(TextAlignment.LEFT);
		txtTaskDetail = new TextField();
		txtTaskDetail.setPromptText("Enter Detail");
		lblTaskDetail.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");

		// konfiguration Return Button,um zum Dashboard zurueckzukehren
		btnReturn = new Button("Return");
		btnReturn.setPrefWidth(100);
		btnReturn.setPrefHeight(50);
		btnReturn.setStyle("-fx-background-color:#f27a17; -fx-text-fill: black;");

		btnReturn.setOnAction(v -> {
	    btnNewTask.setVisible(true); // mache den New Task Button sichtbar
	    addTaskLayout.setVisible(false); // verstecke das Layout zum Hinzufuegen einer neuen Aufgabe

		clearAddTaskTexts(); // Loesche den Inhalt der Textfelder fuer Titel und Details
			
		input(title, detail); // ueberpruefe die Eingabe und aktiviere oder deaktiviere den Add Task Button
									// entsprechend
		});
		
		// erstelle  Add Task Button und konfiguriere seine Eigenschaften
		btAddTask = new Button("Add Task");
		btAddTask.setPrefWidth(100);
		btAddTask.setPrefHeight(50);
		btAddTask.setDisable(true);
		btAddTask.setStyle("-fx-background-color:#78CB7B; -fx-text-fill: black;");

		todos = utility.readFromJsonFile(filePath);

		if (todos.size() > 0)
			for (Item item : todos) {
				InitLoadTask(item);
			}

		/*registriere Listener fuer die Textfelder txtTaskTitle und txtTaskDetail
		 wenn sich der Text in einem der Textfelder aendert,wird der Listener
		 aufgerufen
		 und die Methode => input(title, detail) wird aufgerufen,um den Add Task
		 Button zu aktivieren oder zu deaktivieren*/
		
		txtTaskTitle.textProperty().addListener((observable, oldValue, newValue) -> {
			title = newValue;
			input(title, detail);
		});

		txtTaskDetail.textProperty().addListener((observable, oldValue, newValue) -> {

			detail = newValue;
			input(title, detail);
		});


		// aktion, wenn der Add Task Button geklickt wird
		btAddTask.setOnAction(v -> {
			InitAddTask();
		});

		// erstelle eine hBox addTaskLayoutBtns, um die Bottons Return und Add
		// Task zu enthalten

		addTaskLayoutBtns = new HBox();
		addTaskLayoutBtns.setPrefWidth(200);
		

		// fuege die Buttons Return und Add Task der HBox addTaskLayoutBtns hinzu

		addTaskLayoutBtns.getChildren().addAll(btnReturn, btAddTask);
		addTaskLayout.getChildren().addAll(lblTaskTitle, txtTaskTitle, lblTaskDetail, txtTaskDetail, addTaskLayoutBtns);
		// fuege die Labels, Textfelder und Buttons dem addTaskLayout hinzu, um das
		// Layout zum Hinzufuegen neuer Aufgaben zu erstellen
		
		addTaskLayout.setVisible(false);// verstecke das addTaskLayout zu Beginn bis der Benutzer auf den New Task
										// Button klickt

		// erstelle ein VBox taskBox zum Anzeigen der Aufgabenliste
		taskBox = new VBox();
		taskBox.setStyle("-fx-border-color: #aaaaaa");
		taskBox.setPadding(new Insets(15, 15, 15, 15));
		taskBox.setStyle("-fx-background-color: #d8d8d8");
		
		

		// fuege die Buttons btnNewTask und addTaskLayout zum dashbord hinzu
		dashbord.getChildren().addAll(btnNewTask, addTaskLayout);
		taskBox.getChildren().addAll(listView);// fuege listView zum taskBox hinzu
		programBody.getChildren().addAll(dashbord, taskBox);// fuege dashbord und taskBox zum programBody hinzu
		root.getChildren().addAll(menuBar, programBody);// fuege menuBar und programBody zur Wurzel container 
														// root hinzu

		//erstelle eine neue Szene s mit einer Breite von 1024 und Hoehe von 768 
		
		s = new Scene(root, 1024, 768);
		s.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		// hoehe der programBody wird angepasstm wenn sich die Hoehe der Szene s aendert
		
		s.heightProperty().addListener((obs, oldHeight, newHeight) -> {
			programBody.setPrefHeight(newHeight.doubleValue() - 51);
		});

		//breite der taskBox wird angepasst, wenn sich die Breite der Szene s ändert
		s.widthProperty().addListener((obs, oldWidth, newWith) -> {
			taskBox.setPrefWidth(newWith.doubleValue() - 200);
		});
		// Hoehe der listView wird angepasst, wenn sich die Hoehe der taskBox aendert
		taskBox.heightProperty().addListener((obs, oldHeight, newHeight) -> {
			listView.setPrefHeight(newHeight.doubleValue());
		});
		// breite der listView wird angepasst, wenn sich die Breite der taskBox
		// aendert
		taskBox.widthProperty().addListener((obs, oldWidth, newWith) -> {
			listView.setPrefWidth(newWith.doubleValue());
		});
		
		

		// setze die szene s als Haupt Szene fuer das Hauptfenster primaryStage und zeige es an
		primaryStage.setScene(s);
		primaryStage.show();
		primaryStage.setScene(s);
	
	}
	
	/**
     * diese Methode ueberprueft die Eingabe von Titel und Details und aktiviert den
     * Add Task botton entsprechend
     *
     * @param _title  Der Titel der Aufgabe
     * @param _detail Die Details der Aufgabe
     * @return true, wenn sowohl der Titel als auch die Details nicht leer sind*/
	
	// Methode input ueberprüft, ob der Titel und die Details leer sind und
	// deaktiviert oderr enabled den Buttoon btAddTask entsprechend
	private boolean input(String _title, String _detail) {

		boolean task = (_title.isBlank() || _detail.isBlank());

		btAddTask.setDisable(task);

		return !task;
	}

	// Methode clearAddTaskTexts leert die Textfelder txtTaskTitle und
	// txtTaskDetail
	private void clearAddTaskTexts() {
		txtTaskTitle.clear();
		txtTaskDetail.clear();
	}

	private void InitAddTask() {

		if (isEdit) {
			// wenn sich die Anwendung im Edit Modus befindet
			// setze den Edit Modus zurueck und blende das Add Task Layout aus

			isEdit = false;
			btnNewTask.setVisible(true);
			addTaskLayout.setVisible(false);
			btAddTask.setText("Add Task");

			// wenn eine Aufgabe bearbeitet wurde, aktualisiere die Anzeige der Aufgaben
			if (itemEditPosition != -1) {

				String titleValue = txtTaskTitle.getText().toString().trim(),
					 detailValue = txtTaskDetail.getText().toString().trim();

				tasks.get(itemEditPosition).lblTaskTitle.setText(titleValue);
				tasks.get(itemEditPosition).lblTaskDetail.setText(detailValue);

				todos.get(itemEditPosition).setTitle(titleValue);
				todos.get(itemEditPosition).setDetail(detailValue);

				utility.writeToJsonFile(todos, filePath);

				itemEditPosition = -1;

			}

			// tasks obseverable

			// leere die Textfelder fuer Titel und Details
			clearAddTaskTexts();

		} else {

			// wenn sich die Anwendung nicht im Edit Modus befindet, es heißt es wird eine
			// neue Aufgabe hinzugefügt
			if (input(title, detail)) {

				CustomTaskItem taskItem = new CustomTaskItem(txtTaskTitle.getText(), txtTaskDetail.getText(), false);
				tasks.add(taskItem);

				Item todoItem = new Item(txtTaskTitle.getText(), txtTaskDetail.getText(), false);
				todos.add(todoItem);

				// speichere die aktualisierte Aufgabenliste in einer Json Datei

				utility.writeToJsonFile(todos, filePath);

				// fuege der neuen Aufgabe die entsprechenden Buttons und Labels hinzu
				Button editTask = taskItem.getBtnEditTask();
				Button deleteTask = taskItem.getBtnDeleteTask();
				Label lblTittle = taskItem.getLblTaskTitle(), lblTaskDetail = taskItem.getLblTaskDetail();
				CheckBox chBoxIsDone = taskItem.getChBoxIsDone();

				VBox item = taskItem.getItems();

				chBoxIsDone.setOnAction(c -> {
					itemCheckPosition = tasks.indexOf(taskItem);
					boolean isDone = taskItem.getChBoxIsDone().isSelected();

					if (itemCheckPosition != -1) {

						todos.get(itemCheckPosition).setIsDone(isDone);
						utility.writeToJsonFile(todos, filePath);

					}

					if (isDone) {
						taskItem.setStyle("-fx-background-color: #f27a17");
						deleteTask.setStyle("-fx-background-color: #f3f3f3; -fx-font-weight: bold; -fx-text-fill: #E42121;");
						editTask.setStyle("-fx-background-color: #f3f3f3; -fx-font-weight: bold; -fx-text-fill: #78CB7B;");
					} else {
						taskItem.setStyle("-fx-background-color: none");
						deleteTask.setStyle("-fx-background-color: #E42121; -fx-font-weight: bold; -fx-text-fill: white;");
						editTask.setStyle("-fx-background-color: #78CB7B; -fx-font-weight: bold;");
					}

					itemCheckPosition = -1;
				});

				editTask.setOnAction // registriere die Aktion, wenn der Edit Button einer aAufgabe geklickt wird
				// setze die Textfelder fuer Titel und Details entsprechend der ausgewaehlten
				// Aufgabe
				(e -> {

					txtTaskTitle.setText(lblTittle.getText());
					txtTaskDetail.setText(lblTaskDetail.getText());

					btnNewTask.setVisible(false);
					addTaskLayout.setVisible(true);
					btAddTask.setText("Edit Task");
					isEdit = true;

					itemEditPosition = tasks.indexOf(taskItem);

				});
				
				// loesche die entsprechende Aufgabe aus der Liste tasks
				deleteTask.setOnAction(e -> {

					itemDeletePosition = tasks.indexOf(taskItem);

					// erzeuge einen Alert dialog vom Typ Confirmation
					Alert alert = new Alert(AlertType.CONFIRMATION);

					alert.setTitle("Confirm Delete");
					alert.setHeaderText("Are you sure you want to delete this entry?");

					// zeige den Dialog und warte auf die Benutzerantwort

					alert.showAndWait().ifPresent(response -> {

						if (response == ButtonType.OK) {
						// Der Benutzer hat OK geklickt, fuehre den Loeschvorgang durch

							Alert confirmationAlert = new Alert(AlertType.INFORMATION);
							confirmationAlert.setTitle("Confirm Delete");
							confirmationAlert.setHeaderText(null);
							confirmationAlert.setContentText("The task has been deleted!");
							confirmationAlert.showAndWait();

							if (itemDeletePosition != -1) {
								tasks.remove(taskItem);
								todos.remove(itemDeletePosition);
								itemDeletePosition = -1;

								utility.writeToJsonFile(todos, filePath);
							}

							System.out.println("Entry has been deleted!");
						}
					});
				});

				// passe die Groeße des Aufgaben Items an die Groeße des ListView an
				listView.widthProperty().addListener((obs, oldWidth, newWith) -> {
					item.setPrefWidth(newWith.doubleValue() - 250);
				});

				// leere die Textfelder fuer Titel und Details
				clearAddTaskTexts();

			}
		}

	}
	
	/**
     * initialisiert das Laden einer Aufgabe aus der Json datei und zeigt es an
     *
     * @param itemValue Die Aufgabe, die geladen und angezeigt werden soll
     */
	
	private void InitLoadTask(Item itemValue) {
		CustomTaskItem taskItem = new CustomTaskItem(itemValue.getTitle(), itemValue.getDetail(), itemValue.isIsDone());
		tasks.add(taskItem);

		// fuege der neuen Aufgabe die entsprechenden Buttons und Labels hinzu
		Button editTask = taskItem.getBtnEditTask();
		Button deleteTask = taskItem.getBtnDeleteTask();
		Label lblTittle = taskItem.getLblTaskTitle(), lblTaskDetail = taskItem.getLblTaskDetail();
		CheckBox chBoxIsDone = taskItem.getChBoxIsDone();

		VBox item = taskItem.getItems();

		chBoxIsDone.setOnAction(c -> {
			itemCheckPosition = tasks.indexOf(taskItem);
			boolean isDone = taskItem.getChBoxIsDone().isSelected();

			if (itemCheckPosition != -1) {

				todos.get(itemCheckPosition).setIsDone(isDone);
				utility.writeToJsonFile(todos, filePath);

			}
			// ueberprüfen, ob die Aufgabe erledigt ist oder nicht
			if (isDone) {
				taskItem.setStyle("-fx-background-color: #f27a17"); // Wenn erledigt, aendere das Erscheinungsbild der taskItem Anzeige
				deleteTask.setStyle("-fx-background-color: #f3f3f3; -fx-font-weight: bold; -fx-text-fill: #E42121;");// aendere das Erscheinungsbild der deleteTask 
				//Button, um es hervorzuheben
				editTask.setStyle("-fx-background-color: #f3f3f3; -fx-font-weight: bold; -fx-text-fill: #78CB7B;"); // aendere das Erscheinungsbild der editTask Button,
				//um es hervorzuheben
			} else {
				taskItem.setStyle("-fx-background-color: none");
				deleteTask.setStyle("-fx-background-color: #E42121; -fx-font-weight: bold; -fx-text-fill: white;"); // aendere das Erscheinungsbild der deleteTask Button
				//um es hervorzuheben und den Text weiß zu machen
				editTask.setStyle("-fx-background-color: #78CB7B; -fx-font-weight: bold;");  // aendere das Erscheinungsbild der editTask Button,
				// es hervorzuheben
			}

			itemCheckPosition = -1;
		});

		editTask.setOnAction // registriere die Aktion, wenn der Edit Button einer Aufgabe geklickt wird
		// setze die Textfelder fuer Titel und Details entsprechend der ausgewaehlten
		// Aufgabe
		(e -> {

			txtTaskTitle.setText(lblTittle.getText());
			txtTaskDetail.setText(lblTaskDetail.getText());

			btnNewTask.setVisible(false);
			addTaskLayout.setVisible(true);
			btAddTask.setText("Edit Task");
			isEdit = true;

			itemEditPosition = tasks.indexOf(taskItem);


		});
		// loesche die entsprechende Aufgabe aus der Liste tasks
		deleteTask.setOnAction(e -> {

			itemDeletePosition = tasks.indexOf(taskItem);

			// erzeuge einen Alert dialog vom Typ confirmation
			Alert alert = new Alert(AlertType.CONFIRMATION);

			alert.setTitle("Confirm Delete");
			alert.setHeaderText("Are you sure you want to delete this entry?");

			// zeige den Dialog und warte auf die Benutzerantwort

			alert.showAndWait().ifPresent(response -> {

				if (response == ButtonType.OK) {
					// der Benutzer hat OK geklickt, fuehre den Loeschvorgang durch

					Alert confirmationAlert = new Alert(AlertType.INFORMATION);
					confirmationAlert.setTitle("Confirm Delete");
					confirmationAlert.setHeaderText(null);
					confirmationAlert.setContentText("The task has been deleted!");
					confirmationAlert.showAndWait();

					if (itemDeletePosition != -1) {
						tasks.remove(taskItem);
						todos.remove(itemDeletePosition);
						itemDeletePosition = -1;

						utility.writeToJsonFile(todos, filePath);
					}

					System.out.println("Entry has been deleted!");
				}
			});
		});
		
		// passe die Groeße des Aufgaben Items  an die Größe des ListView
		// listView an
		listView.widthProperty().addListener((obs, oldWidth, newWith) -> {
			item.setPrefWidth(newWith.doubleValue() - 250);
		});

		// leere die Textfelder fuer Titel und Details
		clearAddTaskTexts();

	}

}
