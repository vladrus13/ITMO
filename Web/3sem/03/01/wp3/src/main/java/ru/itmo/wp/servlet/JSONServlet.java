package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

public class JSONServlet extends HttpServlet {

    private static final String AUTH_POST = "/message/auth";
    private static final String FIND_POST = "/message/findAll";
    private static final String ADD_POST = "/message/add";

    private static LinkedList<LinkedHashMap<String, String>> messages = new LinkedList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        switch (uri) {
            case AUTH_POST:
                doPostAuth(request, response);
                break;
            case FIND_POST:
                doPostFind(request, response);
                break;
            case ADD_POST:
                doPostAdd(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void doPostAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        OutputStream outputStream = response.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        String answer = "";
        if (request.getSession().getAttribute("name") != null) {
            answer = request.getSession().getAttribute("name").toString();
        } else {
            if (request.getParameter("user") != null) {
                answer = request.getParameter("user");
                request.getSession().setAttribute("name", request.getParameter("user"));
            }
        }
        outputStreamWriter.write("\"" + answer + "\"");
        outputStreamWriter.flush();
        outputStream.flush();
    }

    private void doPostFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        OutputStream outputStream = response.getOutputStream();
        String json = new Gson().toJson(messages);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write(json);
        outputStreamWriter.flush();
        outputStream.flush();
    }

    private void doPostAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        messages.add(makePair(request.getSession().getAttribute("name").toString(), request.getParameter("text")));
    }

    private LinkedHashMap<String, String> makePair(String name, String text) {
        LinkedHashMap <String, String> returned = new LinkedHashMap<>();
        returned.put("user", name);
        returned.put("text", text);
        return returned;
    }
}
