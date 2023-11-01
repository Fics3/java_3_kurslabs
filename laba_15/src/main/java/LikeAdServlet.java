import org.example.AdModel;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.PriorityQueue;

public class LikeAdServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String adTitle = request.getParameter("adTitle");

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");


        @SuppressWarnings("unchecked")
        PriorityQueue<AdModel> ads = (PriorityQueue<AdModel>) getServletContext().getAttribute("ads");

        for (AdModel ad : ads) {
            if (ad.getTitle().equals(adTitle)) {
                ad.like(username);
                break;
            }
        }

        response.sendRedirect(request.getContextPath());
    }
}