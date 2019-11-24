package main;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.ColorController;
import views.LoginController;
import views.MainController;
import views.MasterController;
import views.RegisterController;
import views.StartController;

public class MainApp extends Application {

	public static MainApp app;

	private StackPane mainPage;

	private Map<String, MasterController> controllerMap = new HashMap<>();

	public MasterController getController(String name) {
		return controllerMap.get(name);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		app = this;
		try {
			FXMLLoader mainLoader = new FXMLLoader();
			mainLoader.setLocation(getClass().getResource("/views/MainLayout.fxml"));
			mainPage = mainLoader.load();
			// 메인컨트롤러도 맵에 넣어준다.
			MainController mc = mainLoader.getController();
			mc.setRoot(mainPage);
			controllerMap.put("main", mc);
			
			FXMLLoader startLoader= new FXMLLoader();
			startLoader.setLocation(getClass().getResource("/views/StartLayout.fxml"));
			AnchorPane startPage = startLoader.load();
			
			StartController sc = startLoader.getController();
			sc.setRoot(startPage);
			controllerMap.put("cover", sc);

			// 로그인 컨트롤러 및 페이지 추출
			FXMLLoader loginLoader = new FXMLLoader();
			loginLoader.setLocation(getClass().getResource("/views/LoginLayout.fxml"));
			AnchorPane loginPage = loginLoader.load();

			LoginController lc = loginLoader.getController();
			lc.setRoot(loginPage);
			controllerMap.put("login", lc);

			// 회원가입 컨트롤러 및 페이지 추출
			FXMLLoader registerLoader = new FXMLLoader();
			registerLoader.setLocation(getClass().getResource("/views/RegisterLayout.fxml"));
			AnchorPane registerPage = registerLoader.load();

			RegisterController rc = registerLoader.getController();
			rc.setRoot(registerPage);
			controllerMap.put("register", rc);
			
			FXMLLoader colorLoader = new FXMLLoader();
			colorLoader.setLocation(getClass().getResource("/views/ColorLayout.fxml"));
			AnchorPane colorPage = colorLoader.load();
			
			ColorController cc = colorLoader.getController();
			cc.setRoot(colorPage);
			controllerMap.put("color", cc);

			Scene scene = new Scene(mainPage);
			scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
//			mainPage.getChildren().add(colorPage);
			mainPage.getChildren().add(loginPage);
			mainPage.getChildren().add(startPage);

			primaryStage.setScene(scene);
			primaryStage.show();

			sc.init();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FX로딩중 오류 발생");
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void loadPane(String name) {
		MasterController c = controllerMap.get(name);
		c.reset();
		Pane pane = c.getRoot();
		mainPage.getChildren().add(pane);

		pane.setTranslateX(-800);
		pane.setOpacity(0);

		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(), 0);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(), 1);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), toRight, fadeOut);

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}

	public void slideOut(Pane pane) {
		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(), 800);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(), 0);

		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), (e) -> {
			mainPage.getChildren().remove(pane);
		}, toRight, fadeOut);

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	public void fadeOut(Pane pane) {
		Timeline timeline = new Timeline();
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(), 0);

		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), (e) -> {
			mainPage.getChildren().remove(pane);
		},  fadeOut);

		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
}
