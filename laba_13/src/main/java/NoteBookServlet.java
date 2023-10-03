import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class NoteBookServlet extends HttpServlet {

    NoteBookModel noteBookModel = new NoteBookModel();

    public NoteBookServlet() {
    }

    @Override
    public void init(ServletConfig config) {
        noteBookModel.readFromFile();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        if (uri.equals("/laba_13/NoteBook/add")) {
                noteBookModel.add(req.getParameter("name"), req.getParameter("phone"));
                noteBookModel.saveFile();
        } else if (uri.equals("/laba_13/NoteBook/reset")) {
            noteBookModel.reset();
            noteBookModel.saveFile();
        }
        PrintWriter out = resp.getWriter();
        out.println("<html>\n<body>\n");
        out.println("Last request URI was:" + uri);
        out.println(getMainPage());
        out.println("</body>\n</html>");
    }

    public String getMainPage() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ArrayList<String>> entry : noteBookModel.getNotes().entrySet()) {
            sb.append("<p>name: ").append(entry.getKey()).append("\nphones:").append(entry.getValue()).append("</p>");
        }
        sb.append("<form method=\"GET\" action=\"/laba_13/NoteBook/add\">\n");
        sb.append("Name: <input type=\"text\" name=\"name\">\n");
        sb.append("Phone: <input type=\"text\" name=\"phone\">\n");
        sb.append("<input type=\"submit\" value=\"add\">\n");
        sb.append("</form>");
        sb.append("<a href=\"/laba_13/NoteBook/reset\">reset</a>");
        return sb.toString();
    }

    @Override
    public void destroy() {
        noteBookModel.saveFile();
    }
}