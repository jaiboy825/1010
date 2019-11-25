package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mysql.cj.jdbc.ha.BestResponseTimeBalanceStrategy;

import domain.UserVO;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.MainApp;
import views.MainController;

public class Game {
	private int gap = 5; //블럭과 블럭간의 사이 크기
	private int size = 35; // 블럭의 가로세로 사이즈
	private Pane pane;
	private Map<Group, String[]> pointMap = new HashMap<>();
	private Map<Group, Integer> numMap = new HashMap<>();
	private ArrayList<Group> nextBlockArr = new ArrayList<Group>();
	private Group[] group = new Group[3];
	private int redValue = 155;
	private int greenValue = 33;
	private int blueValue = 66;
	private Label scoreLabel;
	private Label bestScoreLabel;
	private int score = 0;
	private int bestScore;
	private int[] current = new int[3];
	private Rectangle[][] rectArr = new Rectangle[10][10];
	private Rectangle[][] nextRectArr = new Rectangle[3][];
	private Boolean[][] isFull = new Boolean[10][10];
	private boolean[] widthLines = new boolean[10];
	private boolean[] heightLines = new boolean[10];
	private int numCheck = 0; 
	private	Random rd = new Random();
	private String[] point = new String[19];
	private UserVO user;
	double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    private Button gobtn;
    private Text TextGameOver = new Text("Game Over");
    private boolean gameOver = false;
    private String saveText = "";
    
	
	public Game(Pane pane , Label scoreLabel, Label bestScoreLabel , Button gobtn) {
		this.pane = pane;
		this.scoreLabel = scoreLabel;
		this.gobtn = gobtn;
		this.bestScoreLabel = bestScoreLabel;
		// 1칸
		point[0] = "0,0";
		// 2칸 가로
		point[1] = "0,0:1,0";
		// 2칸 세로
		point[2] = "0,0:0,1";
		// 3칸 가로
		point[3] = "0,0:1,0:2,0";
		// 3칸 세로
		point[4] = "0,0:0,1:0,2";
		// 4칸 가로
		point[5] = "0,0:1,0:2,0:3,0";
		// 4칸 세로
		point[6] = "0,0:0,1:0,2:0,3";
		// 5칸 가로
		point[7] = "0,0:-2,0:-1,0:1,0:2,0";
		// 5칸 세로
		point[8] = "0,0:0,-2:0,-1:0,1:0,2";

		// ㄴ
		point[9] = "0,0:0,1:1,0";
		// 역 ㄱ
		point[10] = "0,0:0,-1:1,0";
		// ㄱ
		point[11] = "0,0:-1,0:-1,0";
		// 역 ㄴ
		point[12] = "0,0:0,1:-1,0";

		// 큰 ㄴ
		point[13] = "0,0:0,2:0,1:1,0:2,0";
		// 큰 역 ㄱ
		point[14] = "0,0:0,-2:0,-1:1,0:2,0";
		// 큰 ㄱ
		point[15] = "0,0:0,-2:0,-1:-1,0:-2,0";
		// 큰 역 ㄴ
		point[16] = "0,0:0,2:0,1:-1,0:-2,0";
		// 2x2 네모
		point[17] = "0,0:1,0:0,1:1,1";
		// 3x3 네모
		point[18] = "0,0:-1,-1:-1,0:-1,1:0,1:0,-1:1,-1:1,0:1,1";
		for (int i = 0; i < group.length; i++) {
			group[i] = new Group();
		}
		score = 0;

		TextGameOver.setFill(Color.BLACK);
		TextGameOver.setStrokeWidth(50);
		TextGameOver.setFont(new Font(50));
		TextGameOver.setX(123);
		TextGameOver.setY(270);
		
		
	}
	
	public void loadingGame(UserVO user) {
		String saved = user.getGame();
		score = user.getScore();
		scoreLabel.setText("Score : "+score + "점");
		bestScore = user.getBestScore(); 
		bestScoreLabel.setText("Best : " + bestScore + "점");
		System.out.println(saved);
		if(saved != null) {
		String[] tfs = saved.split(",");
		int z = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if (tfs[z].equals("1")) {
					isFull[i][j] = true;
				}else {
					isFull[i][j] = false;
				}
				z++;
			}
		}
		paint();
	}
		this.user = user;
	}
	
	public void setRedValue(int redValue) {
		this.redValue = redValue;
	}

	public void setGreenValue(int greenValue) {
		this.greenValue = greenValue;
	}

	public void setBlueValue(int blueValue) {
		this.blueValue = blueValue;
	}
	
	private void clearLines() {
		for(int i = 0; i < 10; i++) {
			widthLines[i] = true;
			heightLines[i] = true;
		}
		
		for(int i = 0; i < 10; i++ ) {
			for(int j = 0; j < 10; j++) {
				if(!isFull[i][j])widthLines[i] = false;
				if(!isFull[j][i]) heightLines[i] = false;
				}
		}
		
		for(int i = 0; i < 10; i++) {
			if(widthLines[i]) {
				for(int j = 0; j < 10; j++ ) {
					isFull[i][j] = false;
				}
				score = score+10;
			}
			if(heightLines[i]) {
				for(int j = 0; j < 10; j++ ) {
					isFull[j][i] = false;
				}
				score = score+10;
			}
		}
	}
	
	private void checkGameOver() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				for(int x = 0; x < nextBlockArr.size(); x++) {
					String[] pointList = pointMap.get(nextBlockArr.get(x));
					if(checkPossible(i, j, pointList)) {
						gobtn.setDisable(true);
						return;
					}
				}
			}
		}
		if(score > bestScore) {
			bestScore = score;
			System.out.println("best  : " + bestScore + " ,  score : "+ score );
		}
		bestScoreLabel.setText("Best : " + bestScore + "점");
		score = 0;
		gameOver = true;
		pane.getChildren().add(TextGameOver);
		MainController mc = (MainController)MainApp.app.getController("main");
		mc.gameOver();
				
	}
	
	public void paint() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(isFull[i][j]) {
					rectArr[i][j].setFill(Color.rgb(redValue, greenValue, blueValue));
				}else {
					rectArr[i][j].setFill(Color.rgb(205, 205,205,0.5));
				}
			}
		}
		
		for(int i = 0; i < nextRectArr.length; i++) {
			for(int j = 0; j < nextRectArr[i].length; j++) {
				nextRectArr[i][j].setFill(Color.rgb(redValue, greenValue, blueValue));
				if(j == 0) nextRectArr[i][j].setFill(Color.rgb(redValue, greenValue, blueValue,0.5));

			}
		}
		
	}
	

	public void draw() {
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				int x = i * (size+gap) +50;
				int y = j * (size+gap) + 50;
				Rectangle rect = new Rectangle(size,size);
				rect.setArcHeight(15);
				rect.setArcWidth(15);
				rect.setFill(Color.rgb(205, 205,205,0.5));
				rect.setLayoutX(x);
				rect.setLayoutY(y);
				pane.getChildren().add(rect);
				rectArr[i][j] = rect;
				isFull[i][j] = false;
				if(isFull[i][j] == true) {
					rectArr[i][j].setFill(Color.rgb(redValue, greenValue, blueValue));
				}else {
					rectArr[i][j].setFill(Color.rgb(205, 205,205,0.5));
				}
			}
		}
		
		current[0] = rd.nextInt(point.length);
		current[1] = rd.nextInt(point.length);
		current[2] = rd.nextInt(point.length);
		nextBlock(current[0], 0);
		nextBlock(current[1], 1);
		nextBlock(current[2], 2);
	}

	private void nextBlock(int a, int b) {
		String[] pointList = point[a].split(":");
		nextRectArr[b] = new Rectangle[pointList.length];
		System.out.println(a);
		for(int i = 0; i < pointList.length; i++) {
			String[] point = pointList[i].split(",");
			int x = b*6+Integer.parseInt(point[0])+2;
			int y = Integer.parseInt(point[1])+2;
			Rectangle rect = new Rectangle(size,size);
			rect.setFill(Color.rgb(redValue, greenValue, blueValue));
			rect.setArcHeight(15);
			rect.setArcWidth(15);
			x = x*(size+gap);
			y = y*(size+gap)+500;
			rect.setLayoutX(x);
			rect.setLayoutY(y);
			if(Integer.parseInt(point[0]) == 0 && Integer.parseInt(point[1]) == 0) 			
				rect.setFill(Color.rgb(redValue, greenValue, blueValue , 0.7));
			nextRectArr[b][i] = rect;
			group[b].getChildren().add(rect);
	

		}
		pointMap.put(group[b], pointList);
		numMap.put(group[b], b);
		nextBlockArr.add(group[b]);
		group[b].setOnMousePressed(OnMousePressedEventHandler);
		group[b].setOnMouseDragged(OnMouseDraggedEventHandler);
		group[b].setOnMouseReleased(OnMouseDragExitedHandler);

		pane.getChildren().add(group[b]);
	}
	
	private boolean checkPossible(int x , int y , String pointList[]) {
		if(x >= 10 || y >= 10 || x < 0 || y < 0) return false;
		for (int i = 0; i < pointList.length; i++) {
			String[] point = pointList[i].split(",");
			int plusX = Integer.parseInt(point[0]);
			int plusY = Integer.parseInt(point[1]);
			if(x+plusX < 0 || x+plusX >= 10 || y + plusY < 0 || y+plusY >= 10) return false;
			if(isFull[x+plusX][y+plusY]) return false;
		}
		return true;
				
	}
	
	public String saveGame() {
		String resultString = "";
		saveText = "";
		int tf = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(isFull[i][j]) tf = 1;
				else tf = 0;
				saveText = saveText + tf + ",";
			}
		}
		System.out.println("BEST : "+ bestScore);
		resultString = saveText+":"+score + ":"+ bestScore;
		return resultString;
	}
	
	   EventHandler<MouseEvent> OnMousePressedEventHandler = new EventHandler<MouseEvent>()
	    {
	        @Override
	        public void handle(MouseEvent mouseEvent)
	        {
	        	if(gameOver) return;
	            orgSceneX = mouseEvent.getSceneX();
	            orgSceneY = mouseEvent.getSceneY();
	            orgTranslateX = ((Node)(mouseEvent.getSource())).getTranslateX();
	            orgTranslateY = ((Node) (mouseEvent.getSource())).getTranslateY();
//	            System.out.println("x : "+orgSceneX + " , y : " + orgSceneY );
//	            System.out.println("trans x : "+ orgTranslateX + " , trans y : " + orgTranslateY );
	        }
	    };
	    EventHandler<MouseEvent> OnMouseDragExitedHandler = new EventHandler<MouseEvent>()
	    {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(gameOver) return;
				Group rectGroup = ( ((Group) mouseEvent.getSource()));
	            double mouseX = mouseEvent.getX()-50;
	            double mouseY = mouseEvent.getY()-50;
	    		int bs = gap + size; //블록 크기
	    		int x = (int) (mouseX / bs);
	    		int y = (int) (mouseY / bs);
	    		String[] pointList = pointMap.get(rectGroup);
	    		if(!checkPossible(x, y, pointList)) {
	    		
	    			for(int i = 0; i < rectGroup.getChildren().size(); i++) {
		        	rectGroup.getChildren().get(i).setTranslateX(0);
		        	rectGroup.getChildren().get(i).setTranslateY(0);
					}
	    		
	    		return;
	    		
	    		}
	    		
	    		for(int i = 0; i < pointList.length; i++) {
	    			String[] point = pointList[i].split(",");
	    			int plusX = Integer.parseInt(point[0]);
	    			int plusY = Integer.parseInt(point[1]);
	    			isFull[x+plusX][y+plusY] = true;
	    			System.out.println("x , y  : ( " + (x+plusX) + " , " + (y+plusY) + " )");
	    		}
	    		score += pointList.length;
	    		pane.getChildren().remove(rectGroup);
	    		nextBlockArr.remove(rectGroup);
	    		numCheck++;
	    		System.out.println(numCheck);
	    		if(numCheck == 3) {
	    			group[0] = new Group();
	    			group[1] = new Group();
	    			group[2] = new Group();
	    			current[0] = rd.nextInt(point.length);
	    			current[1] = rd.nextInt(point.length);
	    			current[2] = rd.nextInt(point.length);
	    			nextBlock(current[0] , 0);
	    			nextBlock(current[1] , 1);
	    			nextBlock(current[2] , 2);
//	    			nextBlock(18, 0);
//	    			nextBlock(18, 1);
//	    			nextBlock(18, 2);
	    			numCheck = 0;
	    		}
	    		
	    		clearLines();
	     		
	    		for(int i = 0; i < 10; i++) {
	    			for(int j = 0; j < 10; j++) {
	    				if(isFull[i][j]) {
	    					rectArr[i][j].setFill(Color.rgb(redValue, greenValue, blueValue));
	    				}else {
	    					rectArr[i][j].setFill(Color.rgb(205, 205,205,0.5));
	    				}
	    			}
	    		}
	    		checkGameOver();

	    		scoreLabel.setText("Score : "+score + "점");
	    		
			}
	    };

	    EventHandler<MouseEvent> OnMouseDraggedEventHandler = new EventHandler<MouseEvent>()
	    {
	        @Override
	        public void handle(MouseEvent mouseEvent)
	        {
				if(gameOver) return;
	        	Group rectGroup = ( ((Group) mouseEvent.getSource()));
	            double offsetX = mouseEvent.getSceneX() - orgSceneX;
	            double offsetY = mouseEvent.getSceneY() - orgSceneY;
	            double newTranslateX = orgTranslateX + offsetX;
	            double newTranslateY = orgTranslateY + offsetY;

	            for(int i = 0; i < rectGroup.getChildren().size(); i++) {
	            	rectGroup.getChildren().get(i).setTranslateX(newTranslateX);
	            	rectGroup.getChildren().get(i).setTranslateY(newTranslateY);
	            }

	    		
	        }
	    };
}
