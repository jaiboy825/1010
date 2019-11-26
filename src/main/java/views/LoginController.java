package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.UserVO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.MainApp;
import util.JDBCUtil;
import util.Util;

public class LoginController extends MasterController {
	@FXML
	private TextField txtId;
	@FXML
	private PasswordField passField;
	@FXML
	private AnchorPane loginPane;

	@Override
	public void reset() {
		txtId.setText("");
		passField.setText("");

	}

	public void loginProcess() {
		String id = txtId.getText();
		String pass = passField.getText();

		if (id.isEmpty() || pass.isEmpty()) {
			Util.showAlert("에러", "아이디나 비밀번호는 공백일 수 없습니다", AlertType.ERROR);
			return;
		}

		// 여기서 실제로 데이터베이스 연결이 이루어져야 한다.
		UserVO user = checkLogin(id, pass);

		if (user != null) {
			MainController mc = (MainController) MainApp.app.getController("main");
			mc.setLoginInfo(user);
			MainApp.app.slideOut(getRoot());
			mc.getGame().loadingGame(user);
		} else {
			Util.showAlert("에러", "존재하지 않는 아이디이거나 비밀번호가 틀립니다.", AlertType.ERROR);
			return;
		}
	}

	private UserVO checkLogin(String id, String pass) {
		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM diary_users WHERE id = ? AND pass = PASSWORD(?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				UserVO user = new UserVO();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setInfo(rs.getString("info"));
				user.setPass(rs.getString("pass"));
				user.setGame(rs.getString("game"));
				user.setBestScore(rs.getInt("bestScore"));
				user.setScore(rs.getInt("score"));
				user.setNextBlock(rs.getString("nextBlock"));
				return user;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			Util.showAlert("에러", "데이터베이스 처리중 오류 발생. 인터넷 확인 필요", AlertType.ERROR);
			return null;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}
	}

	public void openRegisterPage() {
		MainApp.app.loadPane("register");
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
