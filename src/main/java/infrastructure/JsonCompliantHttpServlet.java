package infrastructure;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JsonCompliantHttpServlet extends HttpServlet {

    private final Gson gson = new Gson();

    protected <T> T getRequestAsJson(HttpServletRequest request, Class<T> type) throws IOException {
        return gson.fromJson(request.getReader(), type);
    }

    protected <T> void sendResponseAsJson(HttpServletResponse response, T entity) throws IOException {
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");
        Objects.requireNonNull(entity, "Response entity cannot be null.");

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getOutputStream().print(gson.toJson(entity));
        response.getOutputStream().flush();
    }
}