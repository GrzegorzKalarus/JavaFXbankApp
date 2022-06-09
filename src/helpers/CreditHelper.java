package helpers;

import DTO.CurrentUserDTO;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import connectivity.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditHelper {
    public static boolean GetCredit(String username, int amountOfCredit, int instalmentsNumber) throws SQLException, ClassNotFoundException {
        CurrentUserDTO creditTaker = UserHelper.getCurrentUser();

        if (amountOfCredit > ((int)creditTaker.income * instalmentsNumber * 0.4))
        {
            //Unable to get a credit, not enough income
            return false;
        }
        else
        {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "SELECT * FROM userdata WHERE username = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            int count = 0;
            while(rs.next()){
                count = count + 1;
            }
            if(count == 1){
                String sql2 = "UPDATE userdata SET funds = funds + ? WHERE username = ?";

                PreparedStatement pst2 = connection.prepareStatement(sql2);
                pst2.setInt(1, amountOfCredit);
                pst2.setString(2, username);
                pst2.execute();

                connection.close();

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
