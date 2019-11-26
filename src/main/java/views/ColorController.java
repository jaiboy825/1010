package views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import main.MainApp;

public class ColorController extends MasterController{
	@FXML Slider redSd;
	@FXML Slider greenSd;
	@FXML Slider blueSd;
	@FXML Slider slid;
	@FXML Canvas canvas;
	
	private int redValue = 155;
	private int greenValue = 33;
	private int blueValue = 66;
	
	@FXML 
	private void initialize() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.setFill(Color.rgb(redValue, greenValue, blueValue));
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		redSd.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
            			redValue = new_val.intValue();
            			gc.setFill(Color.rgb(redValue, greenValue, blueValue));
            			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                }
            });
		greenSd.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
            			greenValue = new_val.intValue();
            			gc.setFill(Color.rgb(redValue, greenValue, blueValue));
            			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                }
            });
		blueSd.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
            			blueValue = new_val.intValue();
            			gc.setFill(Color.rgb(redValue, greenValue, blueValue));
            			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                }
            });
		
		slid.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {
            	LoginController lc = (LoginController) MainApp.app.getController("login");
            			lc.bgm.setVolume(new_val.floatValue());
            			
                }
            });
		
	}
	
	public void setClick() {
		MainApp.app.fadeOut(getRoot());
		MainController mc = (MainController) MainApp.app.getController("main");
		
		mc.getGame().setRedValue(redValue);
		mc.getGame().setGreenValue(greenValue);
		mc.getGame().setBlueValue(blueValue);
		mc.getGame().paint();
		
	}

	public Slider getRedSd() {
		return redSd;
	}

	public Slider getGreenSd() {
		return greenSd;
	}

	public Slider getBlueSd() {
		return blueSd;
	}

	@Override
	public void reset() {

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
		
}
