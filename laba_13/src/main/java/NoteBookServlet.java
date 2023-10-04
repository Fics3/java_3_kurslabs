import com.example.NoteBookModel;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoteBookServlet extends HttpServlet {

    NoteBookModel noteBookModel = new NoteBookModel();

    public NoteBookServlet() {
    }

    @Override
    public void init(ServletConfig config) {
        noteBookModel.readFromFile();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/laba_13/NoteBook/add")) {
            noteBookModel.add(req.getParameter("name"), req.getParameter("phone"));
            noteBookModel.saveFile();
        } else if (uri.equals("/laba_13/NoteBook/reset")) {
            noteBookModel.reset();
            noteBookModel.saveFile();
        }
        req.setAttribute("notes",noteBookModel.getNotes());

        req.getRequestDispatcher("/Main.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        noteBookModel.saveFile();
    }
}