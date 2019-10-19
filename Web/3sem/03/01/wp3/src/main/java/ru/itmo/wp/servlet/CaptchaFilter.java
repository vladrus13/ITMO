package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if ("/captcha/pic".equals(uri)) {
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            ImageIO.write(Objects.requireNonNull(ImageUtils.getImage(request.getSession().getAttribute("Captcha-Value").toString())), "png", os);
            os.close();
        } else {
            if (Objects.equals(request.getMethod(), "POST")) {
                // answer capt
                if (Objects.equals(request.getParameter("captcha"), request.getSession().getAttribute("Captcha-Value").toString())) {
                    // right
                    request.getSession().setAttribute("Captcha-Status", 2);
                    response.sendRedirect(request.getSession().getAttribute("URI").toString());
                } else {
                    // wrong
                    request.getSession().setAttribute("Captcha-Value", randGen());
                    response.sendRedirect("/captcha.html");
                }
            } else {
                // its not answer captcha
                if (request.getSession().getAttribute("Captcha-Status") == null) {
                    // gen captcha
                    request.getSession().setAttribute("Captcha-Status", 1);
                    request.getSession().setAttribute("Captcha-Value", randGen());
                    request.getSession().setAttribute("URI", uri);
                    response.sendRedirect("/captcha.html");
                    chain.doFilter(request, response);
                } else {
                    request.getSession().setAttribute("Captcha-Value", randGen());
                    if (Objects.equals(request.getSession().getAttribute("Captcha-Status").toString(), "1")
                            && !Objects.equals(uri, "/captcha.html")) {
                        response.sendRedirect("/captcha.html");
                    }
                    chain.doFilter(request, response);
                }
            }
        }
    }

    private int randGen() {
        return ((int) (Math.random() * 900) + 99);
    }
}
