package vn.edu.bookverse.controller;

import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.math.*;
import java.nio.file.*;
import java.util.*;
import vn.edu.bookverse.entity.*;
import vn.edu.bookverse.service.*;

@WebServlet("/admin/*")
@MultipartConfig(maxFileSize=5 * 1024 * 1024, maxRequestSize=6 * 1024 * 1024)
public class AdminServlet_24162040 extends HttpServlet {
    final CatalogService_24162040 c = new CatalogService_24162040();

    protected void doGet(HttpServletRequest q, HttpServletResponse p) throws IOException, jakarta.servlet.ServletException {
        String path = q.getPathInfo(); int page = Math.max(1, num(q.getParameter("page")));
        if (path.equals("/books")) { q.setAttribute("items", c.books.page(page, 8)); q.setAttribute("pages", (int)Math.ceil(c.books.count() / 8d)); q.setAttribute("kind", "books"); }
        else if (path.equals("/authors")) { q.setAttribute("items", c.authors.page(page, 8)); q.setAttribute("pages", (int)Math.ceil(c.authors.count() / 8d)); q.setAttribute("kind", "authors"); }
        else if (path.equals("/book-form")) { q.setAttribute("book", q.getParameter("id") == null ? new Book_24162040() : c.books.find(Long.parseLong(q.getParameter("id")))); q.setAttribute("authors", c.authors.all()); q.getRequestDispatcher("/views/admin/book-form.jsp").forward(q, p); return; }
        else if (path.equals("/author-form")) { q.setAttribute("author", q.getParameter("id") == null ? new Author_24162040() : c.authors.find(Long.parseLong(q.getParameter("id")))); q.getRequestDispatcher("/views/admin/author-form.jsp").forward(q, p); return; }
        q.getRequestDispatcher("/views/admin/list.jsp").forward(q, p);
    }

    protected void doPost(HttpServletRequest q, HttpServletResponse p) throws IOException, jakarta.servlet.ServletException {
        String path = q.getPathInfo();
        if (path.equals("/book-save")) {
            Book_24162040 b = q.getParameter("id").isBlank() ? new Book_24162040() : c.books.find(Long.parseLong(q.getParameter("id")));
            b.title = q.getParameter("title"); b.isbn = q.getParameter("isbn"); b.publisher = q.getParameter("publisher");
            b.publisherDate = java.time.LocalDate.parse(q.getParameter("publisherDate")); b.quantity = Integer.parseInt(q.getParameter("quantity"));
            b.price = new BigDecimal(q.getParameter("price")); b.description = q.getParameter("description");
            Part cover = q.getPart("cover"); if (cover != null && cover.getSize() > 0) b.coverUrl = saveCover(q, cover);
            b.authors.clear(); b.authors.add(c.authors.find(Long.parseLong(q.getParameter("authorId")))); c.books.save(b); p.sendRedirect("books"); return;
        }
        if (path.equals("/author-save")) {
            Author_24162040 a = q.getParameter("id").isBlank() ? new Author_24162040() : c.authors.find(Long.parseLong(q.getParameter("id")));
            a.name = q.getParameter("name"); a.biography = q.getParameter("biography"); c.authors.save(a); p.sendRedirect("authors"); return;
        }
        long id = Long.parseLong(q.getParameter("id")); if (path.equals("/book-delete")) c.books.delete(id); else c.authors.delete(id); p.sendRedirect(path.startsWith("/book") ? "books" : "authors");
    }

    private String saveCover(HttpServletRequest request, Part cover) throws IOException {
        String type = cover.getContentType();
        if (type == null || !type.startsWith("image/")) throw new IOException("Chỉ được tải tệp ảnh.");
        String original = cover.getSubmittedFileName(); String extension = original == null ? "" : original.substring(original.lastIndexOf('.') + 1).toLowerCase(Locale.ROOT);
        if (!Set.of("jpg", "jpeg", "png", "webp", "gif").contains(extension)) throw new IOException("Ảnh phải là JPG, PNG, WEBP hoặc GIF.");
        Path folder = Paths.get(System.getProperty("user.dir"), "uploads"); Files.createDirectories(folder);
        String file = UUID.randomUUID() + "." + extension;
        try (InputStream in = cover.getInputStream()) { Files.copy(in, folder.resolve(file)); }
        return request.getContextPath() + "/uploads/" + file;
    }

    private int num(String s) { try { return Integer.parseInt(s); } catch (Exception e) { return 1; } }
}
