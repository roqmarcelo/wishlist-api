package infrastructure;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public class JsonCompliantHttpServlet<T> extends HttpServlet {

    private final Gson gson = new Gson();
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public JsonCompliantHttpServlet() {
        type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected T getRequestAsJson(HttpServletRequest request) throws IOException {
        return gson.fromJson(request.getReader(), type);
    }

    protected void sendResponseAsJson(HttpServletResponse response, T entity) throws IOException {
        Objects.requireNonNull(response, "HttpServletResponse response cannot be null.");
        Objects.requireNonNull(entity, "Response entity cannot be null.");

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getOutputStream().print(gson.toJson(entity));
        response.getOutputStream().flush();
    }
}