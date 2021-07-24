import java.sql.*;

public class Logger {

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
                Statement stmt = con.createStatement();
                String query = "SELECT id, firstname, lastname, email, reg_date  from users WHERE id=? AND pwd=?";
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

    public String getUserName() { return ((this.connected) ? this.user.firstName + this.user.lastName : ""); }

    public int getUserId() { return ((this.connected) ? this.user.id : -1); }

    public String toString() { return ((this.connected) ? this.user.toString() : ""); }

}
