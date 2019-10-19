package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String generalUri = request.getRequestURI();
        String[] splitted = generalUri.split("\\+");
        if (!splitted[0].startsWith("/")) {
            splitted[0] = "/" + splitted[0];
        }
        response.setContentType(getContentTypeFromName(splitted[0]));
        for (String uri : splitted) {
            if (!uri.startsWith("/")) {
                uri = "/" + uri;
            }
            File file = new File(new File(getServletContext().getRealPath("")).getParentFile().getParentFile(), "/src/main/webapp/static" + uri);
            if (file.isFile()) {
                OutputStream outputStream = response.getOutputStream();
                Files.copy(file.toPath(), outputStream);
                outputStream.flush();
            } else {
                file = new File(getServletContext().getRealPath("/static" + uri));
                if (file.isFile()) {
                    OutputStream outputStream = response.getOutputStream();
                    Files.copy(file.toPath(), outputStream);
                    outputStream.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
