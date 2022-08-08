import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class View extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, java.io.IOException
    {
        HttpSession ses = request.getSession();
        Lock lock = (Lock)ses.getAttribute("lock");
        lock.unlock();
        List<Car> cars = (List<Car>) ses.getAttribute("cars");
        PrintWriter out = response.getWriter();
        //Poczatek
        out.println("<html><body>");
        out.println(defaultBody());
        if(cars.isEmpty()){
            out.println("No cars found");
        }
            else{
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Name</th>");
                out.println("<th>Type</th>");
                out.println("</tr>");
                for (Car car : cars) {
                    out.println(car.toHTMLrow());
                }
                out.println("</table>");
        }

        //Finish
        out.println("</body></html>");
    }

    private String defaultBody(){
        return "<h1>Car finder</h1>" +
                "<br>" +
                "<form method=\"get\" action=\"http://localhost:8080/TPO5_PM_S20865_war_exploded/Controller\">" +
                "<p>Type:</p><input type=\"text\" size=\"60\" name=\"Type\"><br>" +
                "<input type=\"submit\" value=\"Find\">" +
                "</form>";
    }
}
