package views;

import javafx.scene.layout.Pane;

public abstract class MasterController {
	private Pane root; //가장 위쪽의 루트 저장

	public Pane getRoot() {
		return root;
	}

	public void setRoot(Pane root) {
		this.root = root;
	}
	public abstract void reset();

	public abstract void init();


}
	