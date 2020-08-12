package application;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the Roman Candle simulation. Detects relevant user actions with nodes, and draws the animation.
 * @author Csaba Nemeth
 * @version 1.0
 */
public class Controller {

	//All FXML scene components are  initialized below from the FXML file.
	
	//Buttons
    @FXML
    private Button btnStart;
    @FXML
    private Button btnExit;
    
    //Labels
    @FXML
    private Label lblChooseStarColour;
    @FXML
    private ColorPicker starColorPicker;
    @FXML
    private Label lblFiringAngleSlider;
    @FXML
    private Label lblExitVelocitySlider;
    @FXML
    private Label lblFiringAngle;
    @FXML
    private Label lblWindSpeed;
    
    //Sliders
    @FXML
    private Slider firingAngleSlider;
    @FXML
    private Slider windSpeedSlider;
    
    //Canvas
    @FXML
    private Canvas myCanvas;
    
    //Animation and drawing attributes.
    private GraphicsContext gc;
    private double startingTime;
    private Timeline timeline;
    
    //Approximated maximum particle heights.
    private double maxParticleHeight = 30; // m
    private double maxParticleDistance = 20; // m
    
    //Particle Manager attributes.
    private ArrayList<Particle> particles;
    private ParticleManager particleManager;
    
    //Asset paths.
    String imgPath;
    String explosionSoundPath;
    String flightSoundPath;
    
    //Graphical and media assets.
    Image backgroundImage;
    Media explosionSound;
    Media flightSound;
    
    //Media players. 
    MediaPlayer playExplosion;
    MediaPlayer playFlight;
       
    @FXML
    /*
     * When the start button is pressed, the current animation is cancelled and a
     * new instance of the ParticleManager is created to launch a set of stars. The timeline 
     * is initialized and animated through the drawParticles() method.
     */
    void startButtonPressed(ActionEvent event) {
    	if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING)
    		timeline.stop();
    	drawBackground();
    	try {
    		particleManager = new ParticleManager(windSpeedSlider.getValue(), firingAngleSlider.getValue());
    		startingTime = convertToSeconds(System.currentTimeMillis());
    		particleManager.start(0, starColorPicker.getValue());
    		playFlightSound();
    	} catch (EnvironmentException except) {
    		System.out.println(except.getMessage());
    		return;
    	} catch (EmitterException except) {
    		System.out.println(except.getMessage());			
    		return;
    	} //End try Catch.
    	timeline = new Timeline(
    			new KeyFrame(Duration.ZERO, actionEvent -> drawParticles()),
    			new KeyFrame(Duration.millis(20)));
    	timeline.setCycleCount(Timeline.INDEFINITE);
    	timeline.playFromStart();
    } //End startButtonPressed method.
    
    @FXML
    /*
     * When the exit button is pressed, the window is immediately closed. 
     */
    void exitSimulation(ActionEvent event) {
    	Stage stage = (Stage) btnExit.getScene().getWindow();
    	stage.close();
    } //End exitSimulation method.
    
    @FXML
    /*
     * The initialize method loads asset paths, loads the background image and
     * initializes the graphics context. The method sets up the slider response using lambda functions,
     * and creates the value labels.
     */
    void initialize() {
    	
    	//Grab asset paths from local directory.
    	imgPath = this.getClass().getResource("background.jpg").toString();
    	explosionSoundPath = this.getClass().getResource("explosion.mp3").toString();
    	flightSoundPath = this.getClass().getResource("flightpath.mp3").toString();
    	
    	//Image and graphics context, as well as initial scene call.
		backgroundImage = new Image(imgPath);
    	gc = myCanvas.getGraphicsContext2D();
    	drawBackground();
    	
    	//Setup initial values for sliders and color picker.
    	lblFiringAngle.setText("" + firingAngleSlider.getValue() + " °");
    	lblWindSpeed.setText("" + windSpeedSlider.getValue() + " km/h [Right]");
    	starColorPicker.setValue(Color.RED);
    	
    	//Add listeners to slider values and define actions upon value changes.
    	firingAngleSlider.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		lblFiringAngle.setText("" + roundValue(newVal.doubleValue(), 1) + " °");
    		setNewLaunchAngle(newVal.doubleValue());
    		drawBackground();
    	}); //End lambda function and listener response.
    	windSpeedSlider.valueProperty().addListener((observableValue, oldVal, newVal) ->
    	{
    		setNewWind(newVal.doubleValue());
    		lblWindSpeed.setText("" + roundValue(newVal.doubleValue(), 1) + " m/s [Right]");
    	}); //End lambda function and listener response.
    	
    } // end initialize
    
   /*
    * The drawParticles method grabs all particles currently simulated by the particle manager
    * and draws them using the internal colour and render size. If the particles array list is empty 
    * then all stars have been launched and the animation timeline is stopped. The method also observes if 
    * a star has just appeared or disappeared (via methods in ParticleManager), and plays the corresponding sound.
    */
    private void drawParticles() {
    	drawBackground();
    	try {
    		particles = particleManager.getFireworks(convertToSeconds(System.currentTimeMillis()) - startingTime);
            for (Particle particle : particles) {
            	gc.setFill(particle.getColour());
            	double[] position = scalePosition(particle.getPosition());
            	double size = particle.getRenderSize();
            	gc.fillOval(position[0], position[1], size, size);
            }
    	}
    	catch (IndexOutOfBoundsException err) {
    		timeline.stop();
    		return;
    	} //End try catch block.
    	
    	//Play media sounds upon indication from the particle manager.
    	if (particleManager.starJustExploded()) {
    		playExplosionSound();
    	} //End if statement.
    	if (particleManager.starJustLaunched()) {
    		playFlightSound();
    	} //End if statement.
    	drawLaunchTube();	
    } //End drawParticles method.
    
    /*
     * The drawLaunchTube method assembles the launch-tube sprite and draws it to the canvas. 
     * The sprite responds to the current angle read on the firingAngleSlider, therefore
     * it must translate and rotate the canvas to achieve the effect. 
     */
    private void drawLaunchTube() {
    	double launchTubeWidth = 10;
    	double launchTubeHeight = 40;
    	
    	//Save current context, translate to sprite location and complete rotation.
    	gc.save();
    	gc.translate(myCanvas.getWidth()/2, myCanvas.getHeight());
    	gc.rotate(180 + firingAngleSlider.getValue());
    	gc.translate(-myCanvas.getWidth()/2, -myCanvas.getHeight());
    	
    	//Draw sprite
    	gc.setFill(Color.LIGHTGREY);
    	gc.fillRect(myCanvas.getWidth()/2 - launchTubeWidth/2, myCanvas.getHeight(), launchTubeWidth, launchTubeHeight);
    	gc.fillOval(myCanvas.getWidth()/2 - 15, myCanvas.getHeight() - 15, 30, 30);
    	
    	//Restore saved contexts.
    	gc.restore();
    	
    } //End drawLaunchTube method.
    
    /*
     * Accepts a position (x, y) in meters and scales the value to the corresponding 
     * pixel position on the canvas. Uses the approximate particle limits as a boundary value.
     */
    private double[] scalePosition(double[] position) {
    	double x = position[0];
    	double y = position[1];
    	x = x * (myCanvas.getWidth()/2 / maxParticleDistance);
    	y = y * (myCanvas.getHeight() / maxParticleHeight);
    	x = myCanvas.getWidth()/2 + x;
    	y = myCanvas.getHeight() - y;
    	return new double[] {x, y};	
    } //End scalePosition method.
    
    /*
     * Plays the explosion sound at the end of the star flight.
     * Instantiates a media player using the mp3 file path from the initialize() and plays the file. 
     * A new instance is needed for every-time the sound is played, therefore the player is local.
     */
    private void playExplosionSound() {
    	explosionSound = new Media(explosionSoundPath);
    	playExplosion = new MediaPlayer(explosionSound);
    	playExplosion.play();
    } //End playExplosionSound method.
    
    /*
     * Plays the sound of the star flight.
     * Instantiates a media player using the mp3 file path from the initialize() and plays the file. 
     * A new instance is needed for every-time the sound is played, therefore the player is local.
     */
    private void playFlightSound() {
		flightSound = new Media(flightSoundPath);
		playFlight = new MediaPlayer(flightSound);
		playFlight.setVolume(0.7);
		playFlight.play();
    } //End playFlightSound method.
    
    //Draws the background image on the canvas and overlays the launch-tube sprite.
    private void drawBackground() {
		gc.drawImage(backgroundImage, 0, 0, myCanvas.getWidth(), myCanvas.getHeight());
		drawLaunchTube();
    } //End drawBackground.
    
    //**The following methods allow for live updates of wind and angle during the simulation.
    
    //Passes a new wind-speed to the particle manager environment object, if an instance exist.
    private void setNewWind(double wind ) {
    	if (particleManager != null) {
    		particleManager.setEnvironmentWind(wind);
    	} //End null check.
    } //End setNewWind method.
    
    //Passes a new wind-speed to the particle manager launch-tube, if an instance exists.
    private void setNewLaunchAngle(double angle) {
    	if (particleManager != null) {
    		particleManager.setLaunchTubeAngle(angle);
    	} //End null check.
    } //End setNewLaunchAngleMethod.
    
    //Truncates the passed value to the given amount of decimal places.
    private double roundValue(double value, int decPlaces) {
        double scale = Math.pow(10, decPlaces);
        return Math.round(value * scale) / scale;
    } //End roundValue method.
    
    //Converts milliseconds to seconds.
    private double convertToSeconds(double millis) {
    	return millis * 0.001;
    } //End convertToSeconds method.
    
} //End Class Controller.