import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String searchName = req.getParameter("searchResult");

        req.setAttribute("searchResult",searchName);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/Main.jsp");
        dispatcher.forward(req,resp);
    }
}
