import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.plaf.synth.SynthSeparatorUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
 * *****************************************************
 * Author: Samuel Wood
 * Title: Lab8
 * Description: I know this isn't super scalable and and it's not perfect but 
 * 				I built this alone in one night so I'm fairly content with the result.
 * 				I didn't realize the first part of the lab because it was at the bottom 
 * 				of the two pages, so I read the first page, built this and then read the 
 * 				second page where it talks about having a partner and such (I couldn't make it to lab this week). So after the fact,
 * 				I built the UML diagram (I know that makes the whole thing pointless). My bad.
 * 
 * 				I've built lots of games that were very similar to this in Javascript and
 * 				so I felt as if I had a good grasp on the whole project before starting.
 * 				With that being said, I still found it to be a good challenge. It was fun. Thank you.
 * 				
 * ******************************************************
 */
public class Driver extends Application {
	
	private static int pX = 500;
	private static int pY = 300;
	private static int eX = 30;
	private static int eY = 30;
	private static int items = 2;
	private static int coins = 10;
	private Player player = new Player(pX,pY,"Player1", coins, "./src/assets/player.png");
	private Player enemy = new Player(eX,eY,"Enemy", coins, "./src/assets/enemy.png");

	private Image playerImg = new Image("File:"+player.getImg());
	private ImageView playerImgView = new ImageView(playerImg);
    
	private Image enemyimg = new Image("File:"+enemy.getImg());
	private ImageView enemyImgView = new ImageView(enemyimg);
    
	private ArrayList<Coin> coinCoordinates = new ArrayList<Coin>();
	private StackPane screen = new StackPane();
	
	private Text finish = new Text();

	public static void main(String[] args) {
		launch(args);

	}
	
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

		Button startBtn = new Button("START");
		startBtn.setOnAction(this::btnStartGame);
		
		startBtn.setMinWidth(200);
		startBtn.setMinHeight(200);

		screen.getChildren().add(startBtn);

		Group root = new Group(screen);

		Scene scene = new Scene(root, 1200, 800, Color.GREY);
		scene.addEventFilter(KeyEvent.KEY_TYPED, this::movePlayer);

		primaryStage.setTitle("Game Time");
		primaryStage.setScene(scene);
		primaryStage.show();	  

	}
	
	private void btnStartGame(ActionEvent args) {
		startGame();
	}
	private void startGame(){
		screen.getChildren().clear();
		pX = 500;
		pY = 300;
		eX = 30;
		eY = 30;
		enemyImgView.setFitWidth(50);
	    enemyImgView.setPreserveRatio(true);
	    enemyImgView.setManaged(false);
	    enemyImgView.setLayoutX(enemy.getpX());
	    enemyImgView.setLayoutY(enemy.getpY());
	    enemyImgView.toFront();
	    
	    playerImgView.setFitWidth(50);
	    playerImgView.setPreserveRatio(true);
	    playerImgView.setManaged(false);
	    playerImgView.setLayoutX(player.getpX());
	    playerImgView.setLayoutY(player.getpY());

		screen.getChildren().add(playerImgView);
		screen.getChildren().add(enemyImgView);
		
		for (int i = 0; i < items; i++) {
			Random r = new Random();
			int randomY = r.nextInt((550) + 1);
			int randomX = r.nextInt((1000) + 1);
			Coin newCoin = new Coin(randomX, randomY);
			coinCoordinates.add(newCoin);
			Image coin = new Image(coinCoordinates.get(i).getImg());
		    ImageView coinView = new ImageView(coin);
		    coinView.setFitWidth(50);
		    coinView.setPreserveRatio(true);
		    coinView.setManaged(false);
		    coinView.setLayoutX(coinCoordinates.get(i).getX());
		    coinView.setLayoutY(coinCoordinates.get(i).getY());
			screen.getChildren().add(coinView);
		}
		moveEnemy();

	}
	
	
	private void moveEnemy() {
		if (player.getpX() > enemy.getpX()) eX+=10;
		else if (player.getpX() < enemy.getpX()) eX-=10;
		
		if (player.getpY() > enemy.getpY()) eY+=10;
		else if (player.getpY() < enemy.getpY()) eY-=10;
		

		enemy.setpX(eX);
		enemy.setpY(eY);
		enemyImgView.setLayoutX(enemy.getpX());
	    enemyImgView.setLayoutY(enemy.getpY());
		
		int playerTopLeft = player.getpX();
		int playerTopRight = player.getpX()+50;
		int playerBottomLeft = player.getpY();
		int playerBottomRight = player.getpY()+50;
		
		int enemyTopLeft = enemy.getpX();
		int enemyTopRight = enemy.getpX()+50;
		int enemyBottomLeft = enemy.getpY();
		int enemyBottomRight = enemy.getpY()+50;

		if (areRectsColliding(enemyTopLeft, enemyTopRight, enemyBottomLeft, enemyBottomRight, 
				playerTopLeft, playerTopRight, playerBottomLeft, playerBottomRight)) {
			
			// was getting an error so I found this Platform.runLater fix
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	if (finish.getText().equals("")) {
				        endGame("YOU LOSE");

			    	}
			    }
			});
		} else {
			setTimeout(()->moveEnemy(), 300);
		}
	}
	
	private void movePlayer(KeyEvent event) {
		if(event.getCharacter().equals("a")) {
			pX-=10;
		} else if (event.getCharacter().equals("d")) {
			pX+=10;
		} else if (event.getCharacter().equals("s")) {
			pY+=10;
		} else if (event.getCharacter().equals("w")) {
			pY-=10;
		}
		
		player.setpX(pX);
		player.setpY(pY);
	    playerImgView.setLayoutX(player.getpX());
	    playerImgView.setLayoutY(player.getpY());
	    
	    detectCollision();
	}
	
	private void detectCollision() {
		
		for (int i = 0; i < coinCoordinates.size(); i++) {
			int coinTopLeftX = coinCoordinates.get(i).getX();
			int coinBottomRightX = coinCoordinates.get(i).getX()+50;
			int coinTopLeftY = coinCoordinates.get(i).getY();
			int coinBottomRightY = coinCoordinates.get(i).getY()+50;

			int playerTopLeft = player.getpX();
			int playerTopRight = player.getpX()+50;
			int playerBottomLeft = player.getpY();
			int playerBottomRight = player.getpY()+50;

			if (areRectsColliding(coinTopLeftX, coinBottomRightX, coinTopLeftY, coinBottomRightY, 
					playerTopLeft, playerTopRight, playerBottomLeft, playerBottomRight)) {
				removeCoin(i);
				break;
			}
		}
		
	}
	
	private void removeCoin(int i) {

		//removes them from screen. The 2 represents nodes other than coins.
		screen.getChildren().remove(i+2);

		//removes them from arraylist
		coinCoordinates.remove(i);
		
		//same 2 from before.
		if (screen.getChildren().size() == 2) {
			coinCoordinates.clear();
			items += 2;
			startGame();
		}
	}
	
	private boolean areRectsColliding(int r1TopLeftX, int r1BottomRightX,int r1TopLeftY, int r1BottomRightY, int
										r2TopLeftX,int r2BottomRightX, int r2TopLeftY, int r2BottomRightY) {
				if (r1TopLeftX < r2BottomRightX && r1BottomRightX >
					r2TopLeftX && r1TopLeftY < r2BottomRightY && r1BottomRightY >
					r2TopLeftY) {
					return true;
				}
				else {
					return false;
				}
			}
	
	private void endGame(String txt) {
		finish.setText(txt);
		screen.setAlignment(Pos.CENTER);
		screen.getChildren().clear();
		screen.getChildren().add(finish);
		finish.setFont(Font.font ("Verdana", 60));
		finish.setFill(Color.WHITE);
	}
	
	// Makeshift set time out that I did not write...https://stackoverflow.com/questions/26311470/what-is-the-equivalent-of-javascript-settimeout-in-java
	private static void setTimeout(Runnable runnable, int delay){
	    new Thread(() -> {
	        try {
	            Thread.sleep(delay);
	            runnable.run();
	        }
	        catch (Exception e){
	            System.err.println(e);
	        }
	    }).start();
	}
}
