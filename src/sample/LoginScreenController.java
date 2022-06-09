package sample;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import helpers.UserHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javax.swing.*;
import java.io.IOException;
import java.sql.*;

import connectivity.ConnectionClass;

public class LoginScreenController {
    public PreparedStatement pst;
    public LoginScreenController() throws SQLException, ClassNotFoundException {

    }
    @FXML
    private JFXButton register;
    @FXML
    private Label wrongLogin;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton turnoff;



    public void turnOff(ActionEvent e)throws IOException{
        Stage stage = (Stage) turnoff.getScene().getWindow();
        stage.close();
    }

    public void checkLogin(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        checkIfLogged();
    }

    public void checkIfLogged() throws IOException, SQLException, ClassNotFoundException {
        Main m = new Main();

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String sql = "SELECT * FROM userdata WHERE username=? and password=?";

        pst = connection.prepareStatement(sql);
        pst.setString(1,username.getText());
        pst.setString(2,password.getText());
        ResultSet rs = pst.executeQuery();

        boolean isAdmin = username.getText().equals("admin") && password.getText().equals("admin");

        if(isAdmin){
            //Whatever, admin cannot send / recieve money
            UserHelper.createLoggedUser(username.getText(), 0, true, 0, 0);
            m.changeScene("tables.fxml");
        }
        else
        {
            int count = 0;
            while(rs.next()){
                count = count + 1;
            }
            if(count == 1){
                ((ResultSetImpl) rs).prev();
                int currentUserFunds = rs.getInt("funds");
                boolean isEuroMainCurrency = rs.getBoolean("isEuroMainCurrency");
                int instalmentsNo = rs.getInt("numberOfInstallments");
                int income = rs.getInt("income");

                UserHelper.createLoggedUser(username.getText(), currentUserFunds, isEuroMainCurrency, income, instalmentsNo);

                wrongLogin.setText("Login Successfully!");
                m.changeScene("userTables.fxml");
            }
            else{
                wrongLogin.setText("Wrong Login Or Password!");
            }
        }
        connection.close();
    }


    public void notRegisteredYet(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("registerScreen.fxml");
    }

}
