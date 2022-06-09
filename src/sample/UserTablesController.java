package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import DTO.CurrentUserDTO;
import domain.DataUser;
import helpers.CreditHelper;
import helpers.FundsHelper;
import helpers.UserHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import static helpers.UserHelper.UpdateCurrentUserFunds;


public class UserTablesController implements Initializable {



    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField tfIncome;
    @FXML
    private TextField tfNumberOfInstallments;
    @FXML
    private TextField tfFunds;
    @FXML
    private TableView<DataUser> tvDataUser;

    @FXML
    private TableColumn<DataUser, String> colName;
    @FXML
    private TableColumn<DataUser, String> colSurname;
    @FXML
    private TableColumn<DataUser, Integer> colAge;
    @FXML
    private TableColumn<DataUser, Integer> colIncome;
    @FXML
    private TableColumn<DataUser, Integer> colNumberOfInstallments;
    @FXML
    private TableColumn<DataUser, Integer> colFunds;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Label currentUserLabel;
    @FXML
    private TextField tfRecieverAccNr;
    @FXML
    private TextField tfHowMuchTransfer;
    @FXML
    private Label isTransferSuccess;
    @FXML
    private Label isCreditTakeSuccess;
    @FXML
    private TextField tfHowMuchCredit;
    @FXML
    private TextField tfInstalmentsNo;
    @FXML
    private  TextField tfDepositAmount;
    @FXML
    private Button tfDepositBtn;
    private CurrentUserDTO currentUser;

    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        if(event.getSource() == btnInsert){
            insertRecord();
        }else if (event.getSource() == btnUpdate){
            updateRecord();
        }else if(event.getSource() == btnDelete){
            deleteButton();
        } else if(event.getSource() == tfDepositBtn){
            depositMoney();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //showData();
        currentUser = UserHelper.getCurrentUser();
        try {
            UpdateCurrentUserFunds();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        UpdateMainLabel();
       // tfName.setText(currentUser.userName);

    }
    private void UpdateMainLabel()
    {
        currentUserLabel.setText("Zalogowano jako:  Username: " + currentUser.userName + "  funds: " + currentUser.funds + "  Euro? " + currentUser.isEuroMainCurrency);
    }

    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdatabase", "root","toor");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
/*
    public ObservableList<DataUser> getDataUserList(){
        ObservableList<DataUser> dataUserList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM userdata";
        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            DataUser dataUser;
            while(rs.next()){
                dataUser = new DataUser(rs.getInt("id"),rs.getString("username"),rs.getString("password"),rs.getString("name"), rs.getString("surname"), rs.getInt("age"),rs.getInt("income"),rs.getInt("numberOfInstallments"),rs.getInt("funds"));
                dataUserList.add(dataUser);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return dataUserList;
    }

 */
/*
    public void showData(){
        ObservableList<DataUser> dataUserList = getDataUserList();

        colName.setCellValueFactory(new PropertyValueFactory<DataUser, String>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<DataUser, String>("surname"));
        colAge.setCellValueFactory(new PropertyValueFactory<DataUser, Integer>("age"));
        colIncome.setCellValueFactory(new PropertyValueFactory<DataUser, Integer>("income"));
        colNumberOfInstallments.setCellValueFactory(new PropertyValueFactory<DataUser, Integer>("numberOfInstallments"));
        colFunds.setCellValueFactory(new PropertyValueFactory<DataUser, Integer>("funds"));

        tvDataUser.setItems(dataUserList);
    }


 */
    // public PreparedStatement pst;
    private void insertRecord() throws SQLException, ClassNotFoundException {
       // tfFunds.setText("" + dataUser.getFunds());
        int howMuch = Integer.parseInt(tfHowMuchTransfer.getText());
        //tfHowMuchTransfer.setText(numberToWord(jml));

        boolean sentSuccesfully = FundsHelper.SendFunds(tfRecieverAccNr.getText(), howMuch);
        if(sentSuccesfully)
        {
            //Label na scrrenie -> udało się wysłać
            isTransferSuccess.setText("Money sent!");
            UpdateCurrentUserFunds();
            UpdateMainLabel();
        }
        else
        {
            //Label na scrrenie -> nie udało się wysłać
            isTransferSuccess.setText("Money send fail!");
        }
    }

    private void depositMoney() throws SQLException, ClassNotFoundException {
        int howMuch = Integer.parseInt(tfDepositAmount.getText());

        FundsHelper.DepositFunds(howMuch);
        UpdateCurrentUserFunds();
        UpdateMainLabel();
    }
    private void updateRecord() throws SQLException, ClassNotFoundException {
        int howMuch = Integer.parseInt(tfHowMuchCredit.getText());
        int instalmentsNumber = Integer.parseInt(tfInstalmentsNo.getText());

        boolean creditTaken = CreditHelper.GetCredit(currentUser.userName, howMuch, instalmentsNumber);

        if (creditTaken)
        {
            isCreditTakeSuccess.setText("Credit taken, congratulations!");
            UpdateCurrentUserFunds();
            UpdateMainLabel();
        }
        else
        {
            isCreditTakeSuccess.setText("Your credit was not approved, sorry.");
        }
        //showData();
    }
    private void deleteButton() throws IOException {
        Main m = new Main();
        m.changeScene("loginScreen.fxml");
    }

    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
/*
    @FXML
    public void handleMouseAction(MouseEvent e){
        DataUser dataUser = tvDataUser.getSelectionModel().getSelectedItem();

        tfName.setText(dataUser.getName());
        tfSurname.setText(dataUser.getSurname());
        tfAge.setText("" + dataUser.getAge());
        tfIncome.setText("" + dataUser.getIncome());
        tfNumberOfInstallments.setText("" + dataUser.getNumberOfInstallments());
        tfFunds.setText("" + dataUser.getFunds());
    }



 */


}