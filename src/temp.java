import java.sql.*;
import java.util.Scanner;

public class temp {
    public static void main(String[] args) {
        System.out.println("Enter ProductName:");
        Scanner scanner = new Scanner(System.in);
        String pName = scanner.nextLine();
        scanner.close();
        try {
            try(Connection con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", "root", "kfir1144")) {
                String query = "CALL getQuartersProfits(?)";
                try(PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, pName);
                    ResultSet rs = pstmt.executeQuery();
                    int numOfColumns = rs.getMetaData().getColumnCount();
                    while(rs.next()){
                        for(int j = 1; j <= numOfColumns; j++) System.out.print(rs.getString(j) + " ");
                        System.out.println();
                    }
                }
            }
        } catch(Exception ex) { ex.printStackTrace(); }
    }
}
