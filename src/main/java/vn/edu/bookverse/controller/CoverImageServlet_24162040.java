package vn.edu.bookverse.controller;

import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.*;

@WebServlet("/uploads/*")
public class CoverImageServlet_24162040 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = Paths.get(request.getPathInfo()).getFileName().toString();
        Path image = Paths.get(System.getProperty("user.dir"), "uploads", name);
        if (!Files.isRegularFile(image)) { response.sendError(404); return; }
        response.setContentType(Files.probeContentType(image)); Files.copy(image, response.getOutputStream());
    }
}
