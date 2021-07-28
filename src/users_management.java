import java.sql.SQLException;

public interface users_management {
    public boolean connect(int id, String password);
    public void disconnect();
    public boolean check();
    public String create(String firstName, String lastName, String email, String password);
    public boolean delete(int id, String password);
    public String[][] printAllUsers(int id, String password) throws SQLException;
    public String getUserName();
    public int getUserId();
}
