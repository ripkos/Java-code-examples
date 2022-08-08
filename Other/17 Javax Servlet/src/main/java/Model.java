import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Model extends HttpServlet {
    Connection conn = null;
    public void connect() {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // db parameters
            String url = "jdbc:sqlite::resource:1.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            /*
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

             */
        }
    }
    public void selectAll(List<Car> cars, String as){
        String sql = "SELECT `CarID`, `Name`, `Type` FROM Car WHERE `Type` LIKE '%"+as+"%' ";

        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                cars.add(new Car(rs.getInt("CarID"),rs.getString("Name"),rs.getString("Type")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void init() {
        connect();
    }
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, java.io.IOException
    {
        Map map = request.getParameterMap();
        String[] val = (String[]) map.get("Type");
        String parm=val == null ? "" : val[0];
        HttpSession ses = request.getSession();
        List<Car> cars = new ArrayList<>();
        selectAll(cars,parm);
        ses.setAttribute("cars", cars);
    }
}
