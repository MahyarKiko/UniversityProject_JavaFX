module ToDoListProjectHS{
	requires javafx.controls;
	requires javafx.graphics;
	requires java.prefs;
	requires com.google.gson;
	requires javafx.base;
	
	

	
	opens application to javafx.graphics, javafx.fxml, javafx.controls, com.google.gson;
}


