package gurkiratkhaira_sec004_ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {

	@FXML
	private Button display;

	@FXML
	private TextArea display_area;

	@FXML
	private TextField student;

	@FXML
	private TextField player_playerid;

	@FXML
	private TextField player_firstname;

	@FXML
	private TextField player_lastname;

	@FXML
	private TextField player_address;

	@FXML
	private TextField player_postalcode;

	@FXML
	private TextField player_phonenumber;

	@FXML
	private TextField player_province;

	@FXML
	private Button player_addnew_button;

	@FXML
	private Button player_updateexisting_button;

	@FXML
	private TextField game_gameid;

	@FXML
	private TextField game_gametitle;

	@FXML
	private Button game_addgame_button;

	@FXML
	private Button application_displayallplayers_button;

	@FXML
	private TextField playergame_playerid;

	@FXML
	private Button playergame_addgametoplayer_button;

	@FXML
	private TextField playergame_gameid;

	@FXML
	private TextField playergame_playergameid;

	@FXML
	private TextField playergame_score;

	@FXML
	private DatePicker playergame_dateplayed;

	public Connection connection;

	public void initialize() {
		try {
			System.out.println("> Start Program ...");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("> Driver Loaded successfully.");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@ 199.212.26.208:1521:SQLD", "comp214_f20_145",
					"password");
			System.out.println("> Database connected successfully.");
			application_displayallplayers_buttonaction();
		} catch (Exception e) {
			System.err.println("Exception:" + e);
			System.err.println(e.getMessage());
		}
	}

	@FXML
	void application_displayallplayers_buttonaction() {
		try {
			// String ct = student.getText();
			// String query = "SELECT * FROM student where city='" + ct + "'";
			StringBuilder sb = new StringBuilder();
			
			String query = "SELECT * FROM Player";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);

			
			sb.append("\n==============================================================================\n"
					+"======================Player Table=======================\n=====================================================\n"+
					"Player ID\tFirst N\tLast N\tAddress\t\tPostal C\t\tProvince\tPhone N\n------------------------------------------------------------------------------------------\n");
			while (rs.next()) {
				String id = rs.getString("player_id");
				String fn = rs.getString("first_name");
				String ln = rs.getString("last_name");
				String ad = rs.getString("address");
				String pc = rs.getString("postal_code");
				String prv = rs.getString("province");
				String pn = rs.getString("phone_number");
				sb.append(id + "\t\t" + fn + "\t\t" + ln + "  \t" + ad + "\t" + pc + "\t\t" + prv + "\t\t" + pn + "\n");
			}
			query = "SELECT * FROM Game";
			st = connection.createStatement();
			rs = st.executeQuery(query);

			
			sb.append("\n==============================================================================\n=====Game Table=====\n====================\n"+
					"Game ID\t\tGame Title\n-----------------------------\n");
			while (rs.next()) {
				String id = rs.getString("game_id");
				String gt = rs.getString("game_title");
				sb.append(id + "\t\t\t" + gt + "\n");
			}
			query = "SELECT * FROM PlayerandGame";
			
			st = connection.createStatement();
			rs = st.executeQuery(query);
			
			sb.append("\n==============================================================================\n===========Player And Game Table==========\n==================================\n"+
					"PG ID\t\tGame ID\tPlayer ID\tP Date\t\tScore\n--------------------------------------------------------------\n");
			while (rs.next()) {
				String pgid = rs.getString("PLAYER_GAME_ID");
				String gid = rs.getString("GAME_ID");
				String pid = rs.getString("PLAYER_ID");
				String pd = rs.getString("PLAYING_DATE");
				String sc = rs.getString("SCORE");
				sb.append(pgid + "\t\t" + gid + "\t\t" + pid + "  \t" + pd + "\t" + sc + "\n");
			}
			display_area.setText(String.valueOf(sb));
			// st.close();
		} catch (Exception e) {
			System.err.println("Exception:" + e);
			System.err.println(e.getMessage());
		}
	}

	@FXML
	void displayAction() {
		try {

			String pid = student.getText();
			String query = "SELECT * FROM playerandgame where player_id='" + pid + "'";

			// String query = "SELECT * FROM Player";
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);

			StringBuilder sb = new StringBuilder();
			sb.append(
					"PG - ID\t\tGame ID\tPlayer ID\t\tP Date\t\tScore\n----------------------------------------------------------------------\n");
			while (rs.next()) {
				String pgid = rs.getString("PLAYER_GAME_ID");
				String gid = rs.getString("GAME_ID");
				String plid = rs.getString("PLAYER_ID");
				String pd = rs.getString("PLAYING_DATE");
				String sc = rs.getString("SCORE");
				sb.append(pgid + "\t\t" + gid + "\t\t" + plid + "  \t\t" + pd + "\t\t\t" + sc + "\n");
			}
			display_area.setText(String.valueOf(sb));
			// st.close();
		} catch (Exception e) {
			System.err.println("Exception:" + e);
			System.err.println(e.getMessage());
		}
	}

	@FXML
	void game_addgame_buttonaction() {
		try {

			String id = game_gameid.getText();
			String gt = game_gametitle.getText();
			

			String query = "INSERT INTO game " + "VALUES      ('" + id + "', " + "'" + gt +"')";

			Statement st = connection.createStatement();
			st.executeUpdate(query);

			query = "SELECT * FROM Game";
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);

			StringBuilder sb = new StringBuilder();
			sb.append(
					"Game ID\tGame Title\n------------------------------\n");
			while (rs.next()) {
				id = rs.getString("game_id");
				gt = rs.getString("game_title");
				sb.append(id + "\t\t" + gt + "\n");
			}
			display_area.setText(String.valueOf(sb));
			// st.close();
		} catch (Exception e) {
			System.err.println("Exception:" + e);
			System.err.println(e.getMessage());
		}
	}

	@FXML
	void player_addnew_buttonaction() {
		try {

			String id = player_playerid.getText();
			String fn = player_firstname.getText();
			String ln = player_lastname.getText();
			String ad = player_address.getText();
			String pc = player_postalcode.getText();
			String pn = player_phonenumber.getText();
			String prv = player_province.getText();

			String query = "INSERT INTO player " + "VALUES      ('" + id + "', " + "'" + fn + "', " + "'" + ln + "', "
					+ "'" + ad + "', " + "'" + pc + "', " + "'" + prv + "', " + "'" + pn + "')";

			Statement st = connection.createStatement();
			st.executeUpdate(query);

			query = "SELECT * FROM Player";
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);

			StringBuilder sb = new StringBuilder();
			sb.append(
					"Player ID\tFirst N\tLast N\tAddress\t\tPostal C\t\tProvince\tPhone N\n-------------------------------------------------------------------------------------------------------------\n");
			while (rs.next()) {
				id = rs.getString("player_id");
				fn = rs.getString("first_name");
				ln = rs.getString("last_name");
				ad = rs.getString("address");
				pc = rs.getString("postal_code");
				prv = rs.getString("province");
				pn = rs.getString("phone_number");
				sb.append(id + "\t\t" + fn + "\t\t" + ln + "  \t" + ad + "\t" + pc + "\t\t" + prv + "\t\t" + pn + "\n");
			}
			display_area.setText(String.valueOf(sb));
			// st.close();
		} catch (Exception e) {
			System.err.println("Exception:" + e);
			System.err.println(e.getMessage());
		}
	}

	@FXML
	void player_updateexisting_buttonaction() {
		try {

			String id = player_playerid.getText();
			String fn = player_firstname.getText();
			String ln = player_lastname.getText();
			String ad = player_address.getText();
			String pc = player_postalcode.getText();
			String pn = player_phonenumber.getText();
			String prv = player_province.getText();

			String query = "UPDATE player set first_name='" + fn + "', last_name='" + ln + "', address='" + ad
					+ "', postal_code='" + pc + "', province='" + prv + "', phone_number='" + pn + "' where player_id='"
					+ id + "'";

			Statement st = connection.createStatement();
			st.executeUpdate(query);

			query = "SELECT * FROM Player";
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);

			StringBuilder sb = new StringBuilder();
			sb.append(
					"Player ID\tFirst N\tLast N\tAddress\t\tPostal C\t\tProvince\tPhone N\n-------------------------------------------------------------------------------------------------------------\n");
			while (rs.next()) {
				id = rs.getString("player_id");
				fn = rs.getString("first_name");
				ln = rs.getString("last_name");
				ad = rs.getString("address");
				pc = rs.getString("postal_code");
				prv = rs.getString("province");
				pn = rs.getString("phone_number");
				sb.append(id + "\t\t" + fn + "\t\t" + ln + "  \t" + ad + "\t" + pc + "\t\t" + prv + "\t\t" + pn + "\n");
			}
			display_area.setText(String.valueOf(sb));
			// st.close();
		} catch (Exception e) {
			System.err.println("Exception:" + e);
			System.err.println(e.getMessage());
		}
	}

	@FXML
	void playergame_addgametoplayer_buttonaction() {
		try {

			String pgid = playergame_playergameid.getText();
			String gid = playergame_gameid.getText();
			String pid = playergame_playerid.getText();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy",Locale.US);
			String dp = (playergame_dateplayed.getValue()).format(formatter);
			String sc = playergame_score.getText();
			

			String query = "INSERT INTO playerandgame VALUES('" + pgid + "', " + "'" + gid +"', " + "'" + pid +"', " + "'" + dp +"', " + "'" + sc +"')";

			Statement st = connection.createStatement();
			st.executeUpdate(query);

			query = "SELECT * FROM playerandgame";
			st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);

			StringBuilder sb = new StringBuilder();
			sb.append(
					"PG ID\tGame ID\tPlayer ID\tDate Played\t\tScore\n--------------------------------------------------\n");
			while (rs.next()) {
				pgid = rs.getString("PLAYER_GAME_ID");
				gid = rs.getString("GAME_ID");
				pid = rs.getString("PLAYER_ID");
				dp = rs.getString("PLAYING_DATE");
				sc = rs.getString("SCORE");
				sb.append(pgid + "\t" + gid+ "\t\t" + pid+ "\t\t\t" + dp+ "\t" + sc + "\n");
			}
			display_area.setText(String.valueOf(sb));
			// st.close();
		} catch (Exception e) {
			System.err.println("Exception:" + e);
			System.err.println(e.getMessage());
		}
	}

}
