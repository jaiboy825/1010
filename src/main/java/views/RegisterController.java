package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import main.MainApp;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import util.JDBCUtil;
import util.Util;

public class RegisterController extends MasterController {
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private PasswordField pass;
	@FXML
	private PasswordField passConfirm;
	@FXML
	private TextArea txtInfo;

	@Override
	public void reset() {
		txtId.setText("");
		txtName.setText("");
		pass.setText("");
		passConfirm.setText("");
		txtInfo.setText("");
	}

	public void register() {
		String id = txtId.getText().trim();
		String name = txtName.getText().trim();
		String strPass = pass.getText().trim();
		String confirm = passConfirm.getText().trim();
		String info = txtInfo.getText().trim();

		if (id.isEmpty() || name.isEmpty() || strPass.isEmpty() || info.isEmpty()) {
			Util.showAlert("비어있음", "필수 값이 비어있습니다.", AlertType.INFORMATION);
			return;
		}
		if (!strPass.equals(confirm)) {
			Util.showAlert("불일치", "비밀번호와 확인이 다릅니다.", AlertType.INFORMATION);
			return;
		}

		Connection con = JDBCUtil.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 회원이 존재하는지 검사할 쿼리
		String sqlExist = "SELECT * FROM diary_users WHERE id = ?";
		// 회원을 가입시킬 쿼리
		String sqlInsert = "INSERT INTO diary_users(id, name, pass, info)" + " VALUES (?, ?, PASSWORD(?), ?)";

		try {
			pstmt = con.prepareStatement(sqlExist);
			pstmt.setString(1, id); // 해당 아이디의 사용자가 존재하는 지 검사.
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Util.showAlert("회원중복", "이미 해당 id의 유저가 존재", AlertType.INFORMATION);
				return;
			}

			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);

			pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, strPass);
			pstmt.setString(4, info);

			if (pstmt.executeUpdate() != 1) {
				Util.showAlert("에러", "데이터베이스 입력 중 오류발생", AlertType.ERROR);
				return;
			}

			Util.showAlert("성공", "성공적으로 회원가입되었습니다.", AlertType.INFORMATION);
			MainApp.app.slideOut(getRoot());
		} catch (Exception e) {
			e.printStackTrace();
			Util.showAlert("에러", "데이터베이스 입력 중 오류발생", AlertType.ERROR);
			return;
		} finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
			JDBCUtil.close(con);
		}

	}

	public void cancel() {
		MainApp.app.slideOut(getRoot());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
