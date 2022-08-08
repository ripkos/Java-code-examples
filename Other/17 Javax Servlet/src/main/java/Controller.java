import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller extends HttpServlet {

    private ServletContext context;

    @Override
    public void init() {
        context = getServletContext();
    }
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, java.io.IOException
    {
        RequestDispatcher disp = null;
        response.setContentType("text/html"); // Nie da sie tego pozbyc
        //Ladowanie odpowiedzi

        HttpSession ses = request.getSession();
        Lock lock = new ReentrantLock();
        lock.lock();
        ses.setAttribute("lock",lock);
        //Przekazywanie sterowania do Model(pobieranie danych)
        disp = context.getRequestDispatcher("/Model");
        disp.include(request,response);
        //Przekazywanie sterowania do View(s koncem)
        disp = context.getRequestDispatcher("/View");
        disp.forward(request,response);
    }

}
