package views;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

import domain.UserVO;
import game.Game;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import main.MainApp;
import util.JDBCUtil;

public class MainController extends MasterController {
	@FXML
	private Button LogoutBtn;
	@FXML
	private Label BestLabel;
	@FXML
	private Label ScoreLabel;
	@FXML
	private Label loginInfo;
	@FXML
	private StackPane mainPage;
	@FXML
	private Pane gamePane;
	@FXML
	public Button gobtn;

	private UserVO user;
	private Game game;



	public Game getGame() {
		return game;
	}


	@FXML
	private void initialize() {
		game = new Game(gamePane , ScoreLabel , gobtn);
		game.draw();
	}

	
	public void setLoginInfo(UserVO user) {
		this.user = user;
		loginInfo.setText(user.getName() + "[" + user.getId() + "]");
	}

	public void gameStart() {
		reset();
		MainController mc = (MainController)MainApp.app.getController("main");
		game = new Game(gamePane, ScoreLabel , gobtn );
		mc.setGame(game);
		game.draw();
	}

	public void logout() {
		saveGame();
		this.user = null;
		reset();
		MainController mc = (MainController)MainApp.app.getController("main");
		ColorController cc = (ColorController)MainApp.app.getController("color");
		game = new Game(gamePane , ScoreLabel , gobtn);
		game.draw();
		cc.reset();
		mc.setGame(game);
		MainApp.app.loadPane("login");
		

	}
	
	public void saveGame() {
		String save = game.saveGame();
		Connection con =  JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		String sql = "update diary_users set game=? where id = ?";
		String id = user.getId();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, save);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("데이터베이스 업데이트중");
			e.printStackTrace();
		}finally {
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}
	
	public UserVO getUser() {
		return user;
	}


	public void gameOver() {
		gobtn.setOpacity(1);
		gobtn.setDisable(false);
	}
	
	public void setGame(Game game) {
		this.game = game;
	}


	public void setColor() {
		MainApp.app.loadPane("color");
	}
	
	

	
	@Override
	public void reset() {
		gamePane.getChildren().clear();
		gobtn.setOpacity(0);
		gobtn.setDisable(true);
		
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	}

