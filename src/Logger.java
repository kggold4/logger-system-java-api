import java.sql.*;
import java.util.ArrayList;

public class Logger  implements users_management{

    private class ConnectedUser {

        public int id;
        public String firstName;
        public String lastName;
        public String email;
        public String reg_date;

        public ConnectedUser(int id, String firstName, String lastName, String email, String reg_date) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.reg_date = reg_date;
        }

        public String toString() {
            return "\nid: " + id + "\nname: " + firstName + " " + lastName + "\nemail: " + email + "\nregister date: "
                    + reg_date + "\n";
        }
    }

    private boolean connected;
    private ConnectedUser user;

    public Logger() { this.user = null; this.connected = false; }

    public boolean connect(int id, String password) {
        try {
            try(Connection con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/logger", "root", "kfir1144")) {

                String query = "SELECT id, firstname, lastname, email, reg_date from users WHERE id=? AND pwd=?";
                try(PreparedStatement pstmt = con.prepareStatement(query)) {

                    pstmt.setInt(1, id);
                    pstmt.setString(2, password);
                    ResultSet rs = pstmt.executeQuery();

                    rs.next();
                    String user_id_string = rs.getString(1);
                    int user_id = Integer.parseInt(user_id_string);
                    String user_firstName = rs.getString(2);
                    String user_laseName = rs.getString(3);
                    String user_email = rs.getString(4);
                    String user_regDate = rs.getString(5);

                    this.user = new ConnectedUser(user_id, user_firstName, user_laseName, user_email, user_regDate);
                }
            }
        } catch(Exception ex) { ex.printStackTrace(); return false; }
        this.connected = true;
        return true;
    }

    public void disconnect() { this.user = null; this.connected = false; }

    public boolean check() { return this.connected; }

    public String create(String firstName, String lastName, String email, String password) {
        int user_id;
        try {
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/logger", "root", "kfir1144")) {
                String query = "SELECT MAX(id) FROM users";
                try(PreparedStatement pstmt = con.prepareStatement(query)) {
                    ResultSet rs = pstmt.executeQuery();
                    rs.next();
                    user_id = Integer.parseInt(rs.getString(1));
                }
            }
        } catch(Exception ex) { ex.printStackTrace(); return "Error"; }
        user_id++;
        try {
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/logger", "root", "kfir1144")) {

                String query = "INSERT INTO users VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
                try(PreparedStatement pstmt = con.prepareStatement(query)) {

                    pstmt.setInt(1, user_id);
                    pstmt.setString(2, firstName);
                    pstmt.setString(3, lastName);
                    pstmt.setString(4, email);
                    pstmt.setString(5, password);
                    pstmt.executeUpdate();
                }
            }
        } catch(Exception ex) { ex.printStackTrace(); return "Error"; }
        return "" + user_id;
    }

    public boolean delete(int id, String password) {
        if(id == 100001) return false; // cannot delete admin
        boolean current_connected = connect(id, password);
        if(!current_connected) return false;

        try {
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/logger", "root", "kfir1144")) {
                String query = "DELETE FROM users WHERE id=? AND pwd=?";
                try(PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setInt(1, id);
                    pstmt.setString(2, password);
                    pstmt.executeUpdate();
                }
            }
        } catch(Exception ex) { ex.printStackTrace(); return false; }
        return true;
    }

    public String[][] printAllUsers(int id, String password) throws SQLException {
        if(id != 100001) throw new IllegalArgumentException("Must Connect to Admin User");
        boolean current_connected = connect(id, password);
        if(!current_connected) throw new IllegalArgumentException("Wrong Password");

        try {
            try(Connection con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/logger", "root", "kfir1144")) {

                String query = "SELECT id, firstname, lastname, email, reg_date from users";
                try(PreparedStatement pstmt = con.prepareStatement(query)) {

                    ResultSet rs = pstmt.executeQuery();

                    int numOfColumns = rs.getMetaData().getColumnCount();
                    ArrayList<ArrayList<String>> result_al = new ArrayList<>();
                    while(rs.next()){
                        ArrayList<String> row = new ArrayList<>();
                        for(int col = 1; col <= numOfColumns; col++){
                            row.add(rs.getString(col));
                        }
                        result_al.add(row);
                    }

                    String[][] result = new String[numOfColumns][result_al.size()];
                    for(int i = 0; i < numOfColumns; i++) {
                        for(int j = 0; j < result_al.size(); j++) {
                            result[i][j] = result_al.get(i).get(j);
                        }
                    }

                    disconnect();
                    return result;
                }
            }
        } catch(Exception ex) { ex.printStackTrace(); }
        throw new SQLException("Something Went Wrong, Please Try Again");
    }

    public String getUserName() { return ((this.connected) ? this.user.firstName + this.user.lastName : ""); }

    public int getUserId() { return ((this.connected) ? this.user.id : -1); }

    public String toString() { return ((this.connected) ? this.user.toString() : ""); }

}
