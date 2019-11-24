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
		
	}
	
	public void setClick() {
		MainApp.app.fadeOut(getRoot());
		MainController mc = (MainController) MainApp.app.getController("main");
		
		mc.getGame().setRedValue(redValue);
		mc.getGame().setGreenValue(greenValue);
		mc.getGame().setBlueValue(blueValue);
		mc.getGame().paint();
		
	}

	@Override
	public void reset() {
		MainController mc = (MainController) MainApp.app.getController("main");
		
		mc.getGame().setRedValue(155);
		mc.getGame().setGreenValue(33);
		mc.getGame().setBlueValue(66);
		mc.getGame().paint(); 
		redSd.setValue(155);
		greenSd.setValue(33);
		blueSd.setValue(66);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
		
}
