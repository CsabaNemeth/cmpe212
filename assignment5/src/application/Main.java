package application;
	
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Main class used in to initialize the roman candle simulation.
 * @author Csaba Nemeth
 * @version 1.0
 */
public class Main extends Application {
	@Override
	/**
	 * Start method initiates the panes and shows the primary stage (window).
	 */
	public void start(Stage primaryStage) {
		try {
			
			//Create panes, with StackPane as the root class.
			GridPane gridPane = (GridPane)FXMLLoader.load(getClass().getResource("Assignment5FXML.fxml"));
			StackPane root = new StackPane(gridPane);
			
			//Lock the size of the gridPane to allow scaling.
			gridPane.setMinSize(1000, 700);
			gridPane.setMaxSize(1000, 700);
			
			//Calculate scaling factor by the root width and gridPane dimensions.
			NumberBinding maxScale = Bindings.min(root.widthProperty().divide(1000),
			                                      root.heightProperty().divide(700));
			//Scale the gridPane accordingly.
			gridPane.scaleXProperty().bind(maxScale);
			gridPane.scaleYProperty().bind(maxScale);

			//Initialize scene with the root pane.
			Scene scene = new Scene(root,1000,700);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Roman Candle Simulation: Csaba Nemeth"); //Set Title
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main method for the Roman Candle simulation.
	 * @param args Java String[] args argument.
	 */
	public static void main(String[] args) {
		launch(args);
	} //End main method.
} //End Main class.
