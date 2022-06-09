package helpers;

import DTO.CurrentUserDTO;
import connectivity.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserHelper {
    private static CurrentUserDTO loggedUser;

    public static void createLoggedUser(String userName, int funds, boolean isEuroMainCurrency, int income, int instalmentsNo)
    {
        loggedUser = new CurrentUserDTO(userName, funds, isEuroMainCurrency, income, instalmentsNo);
    }

    public static CurrentUserDTO getCurrentUser()
    {
        return loggedUser;
    }

    public static void UpdateCurrentUserFunds() throws SQLException, ClassNotFoundException {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String sql = "SELECT * FROM userdata WHERE username=?";

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, loggedUser.userName);
        ResultSet rs = pst.executeQuery();
        rs.next();

        int currentUserFunds = rs.getInt("funds");

        loggedUser.funds = currentUserFunds;

        connection.close();
    }
}
