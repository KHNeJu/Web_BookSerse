package vn.edu.bookverse.config;

import jakarta.persistence.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import vn.edu.bookverse.entity.*;
import java.time.*;

@WebListener
public class AppListener_24162040 implements ServletContextListener {
    public void contextInitialized(ServletContextEvent ignored) {
        EntityManager e = Jpa_24162040.em();
        try {
            long books = e.createQuery("select count(b) from Book b", Long.class).getSingleResult();
            if (books > 0) {
                long oldBooks = e.createQuery("select count(b) from Book b where b.title='Atomic Habits'", Long.class).getSingleResult();
                if (oldBooks == 0) return;
                e.getTransaction().begin();
                e.createNativeQuery("delete from rating").executeUpdate();
                e.createNativeQuery("delete from book_author").executeUpdate();
                e.createNativeQuery("delete from books").executeUpdate();
                e.createNativeQuery("delete from author").executeUpdate();
                e.getTransaction().commit();
            }

            e.getTransaction().begin();
            String[] names = {"Dafydd Stuttard & Marcus Pinto", "Michal Zalewski", "Gene Kim & Jez Humble", "Gene Kim & Kevin Behr", "Jon Erickson", "Michael Sikorski & Andrew Honig"};
            Author_24162040[] authors = new Author_24162040[names.length];
            for (int i = 0; i < names.length; i++) { authors[i] = new Author_24162040(names[i]); e.persist(authors[i]); }
            String[][] data = {
                    {"The Web Application Hacker's Handbook", "9781118026472", "Wiley", "2011-09-27", "18", "https://covers.openlibrary.org/b/isbn/9781118026472-L.jpg", "Web security, pentest và cách nhận diện lỗ hổng ứng dụng web."},
                    {"The Tangled Web", "9781593273880", "No Starch Press", "2011-11-15", "20", "https://covers.openlibrary.org/b/isbn/9781593273880-L.jpg", "Bảo mật trình duyệt và ứng dụng web hiện đại."},
                    {"The DevOps Handbook", "9781942788003", "IT Revolution Press", "2016-10-06", "25", "https://covers.openlibrary.org/b/isbn/9781942788003-L.jpg", "Thực hành DevOps, tự động hóa và độ tin cậy hệ thống."},
                    {"The Phoenix Project", "9781942788294", "IT Revolution Press", "2018-02-07", "22", "https://covers.openlibrary.org/b/isbn/9781942788294-L.jpg", "Tiểu thuyết về IT, DevOps và cải tiến quy trình vận hành."},
                    {"Hacking: The Art of Exploitation", "9781593271442", "No Starch Press", "2008-02-01", "16", "https://covers.openlibrary.org/b/isbn/9781593271442-L.jpg", "Nền tảng lập trình, bảo mật và khai thác lỗ hổng có đạo đức."},
                    {"Practical Malware Analysis", "9781593272906", "No Starch Press", "2012-02-01", "14", "https://covers.openlibrary.org/b/isbn/9781593272906-L.jpg", "Phân tích mã độc trong môi trường thực hành an toàn."}
            };
            for (int i = 0; i < data.length; i++) {
                Book_24162040 b = new Book_24162040();
                b.title = data[i][0]; b.isbn = data[i][1]; b.publisher = data[i][2]; b.publisherDate = LocalDate.parse(data[i][3]);
                b.quantity = Integer.parseInt(data[i][4]); b.price = java.math.BigDecimal.valueOf(150000L + i * 25000L); b.coverUrl = data[i][5]; b.description = data[i][6]; b.authors.add(authors[i]); e.persist(b);
            }
            if (e.createQuery("select count(u) from User u where u.email='admin@bookverse.vn'", Long.class).getSingleResult() == 0) {
                User_24162040 admin = new User_24162040(); admin.fullName = "Quản trị viên"; admin.email = "admin@bookverse.vn";
                admin.passwordHash = java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest("Admin@123".getBytes()));
                admin.isAdmin = true; admin.active = true; e.persist(admin);
            }
            e.getTransaction().commit();
        } catch (Exception ex) { if (e.getTransaction().isActive()) e.getTransaction().rollback(); throw new RuntimeException(ex); }
        finally { e.close(); }
    }
}
