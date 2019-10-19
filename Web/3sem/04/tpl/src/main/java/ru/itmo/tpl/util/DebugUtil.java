package ru.itmo.tpl.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Paths;

public class DebugUtil {
    private static final String SOURCE_WEBAPP_RELATIVE_PATH = "../../src/main/webapp";

    public static File getFile(ServletContext servletContext, String path) {
        File file = new File(servletContext.getRealPath("."),
                Paths.get(SOURCE_WEBAPP_RELATIVE_PATH, path).toString());
        if (file.exists()) {
            return file;
        } else {
            return new File(servletContext.getRealPath(path));
        }
    }
}
