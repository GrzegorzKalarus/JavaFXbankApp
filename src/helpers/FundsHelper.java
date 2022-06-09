package helpers;

import DTO.CurrentUserDTO;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import connectivity.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static helpers.FileHelper.makePotwierdzenie;

public class FundsHelper {
    public static void DepositFunds(int amount) throws SQLException, ClassNotFoundException {
        CurrentUserDTO currentUser = UserHelper.getCurrentUser();

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
            String sql2 = "UPDATE userdata SET funds = funds + ? WHERE username = ?";

            PreparedStatement pst2 = connection.prepareStatement(sql2);
            pst2.setInt(1, amount);
            pst2.setString(2, currentUser.userName);
            pst2.execute();

            connection.close();
    }
    public static boolean SendFunds(String accountNumber, int amount) throws SQLException, ClassNotFoundException {
        CurrentUserDTO sender = UserHelper.getCurrentUser();
        if (amount > sender.funds)
        {
            //Unable to send. Not enough money
            return false;
        }
        else
        {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "SELECT * FROM userdata WHERE accountNumber = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, accountNumber);

            ResultSet rs = pst.executeQuery();

            int count = 0;
            while(rs.next()){
                count = count + 1;
            }
            if(count == 1){
                ((ResultSetImpl) rs).prev();
                boolean isCurrentUserEuro = sender.isEuroMainCurrency;
                boolean isRecieverUserEuro = rs.getBoolean("isEuroMainCurrency");

                int recievedAmount = amount;

                if(isCurrentUserEuro && isRecieverUserEuro || !isCurrentUserEuro && !isRecieverUserEuro)
                {
                    //Nie ma przewalutowania
                }
                else if(isCurrentUserEuro && !isRecieverUserEuro)
                {
                    recievedAmount = ((int)amount * 4);
                }
                else if(!isCurrentUserEuro && isRecieverUserEuro)
                {
                    recievedAmount = ((int)amount / 4);
                }
                String sql2 = "UPDATE userdata SET funds = funds + ? WHERE accountNumber = ?";
                String sql3 = "UPDATE userdata SET funds = funds - ? WHERE username = ?";

                PreparedStatement pst2 = connection.prepareStatement(sql2);
                pst2.setInt(1, recievedAmount);
                pst2.setString(2, accountNumber);

                PreparedStatement pst3 = connection.prepareStatement(sql3);
                pst3.setInt(1, amount);
                pst3.setString(2, sender.userName);

                pst2.execute();
                pst3.execute();

                connection.close();

                makePotwierdzenie(sender.userName, sender.isEuroMainCurrency,amount, accountNumber);

                return true;
            }
            else{
                connection.close();
                return false;
            }

            //Pobieranie rekordu po accountNumber w bazce (są unique)
            //Connection
            //Select * where accountnumberr = accountNUmber
            //Jeżeli 0 -> return false
            //Jeżeli 1 -> wyślij przelew (UPDATE {tabela} WHERE accountNumber = ?(accountNumber) SET funds = funds + ?(amount)


        }
    }
}
