import org.example.AdModel;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.PriorityQueue;

public class AddAdServlet extends HttpServlet {
    @Override
    public void init() {
        PriorityQueue<AdModel> ads = new PriorityQueue<>();

        getServletContext().setAttribute("ads", ads);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String username = (String) session.getAttribute("username");
            String adTitle = request.getParameter("adTitle");
            String adText = request.getParameter("adText");

            if (adTitle != null && adText != null) {
                AdModel newAd = new AdModel(adTitle, adText, username);
                @SuppressWarnings("unchecked")
                PriorityQueue<AdModel> ads = (PriorityQueue<AdModel>) getServletContext().getAttribute("ads");
                ads.add(newAd);
            }
        }

        response.sendRedirect(request.getContextPath());
    }
}
