package views;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import main.MainApp;

public class StartController extends MasterController {
	@FXML
	private Pane startPane;

	@Override
	public void init() {
		KeyFrame frame = new KeyFrame(Duration.millis(5), e -> {
			MainApp.app.fadeOut(getRoot());
		});
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(frame);
		timeline.delayProperty().set(Duration.millis(1200));

		timeline.play();
	}

	@Override
	public void reset() {

	}
}
