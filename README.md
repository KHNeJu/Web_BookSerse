# BookVerse

Spring Boot (embedded Tomcat) + Servlet/JPA/JSP. Java 17+ is required.

## Run in VS Code

Open the project terminal and run:

```powershell
.\run.ps1
```

Open `http://localhost:8081/`.

## OTP flow

The app starts without asking for a Gmail password. Fill in the registration form in the browser and click **Dang ky**. The VS Code terminal then asks:

```text
Enter Gmail app password (16 characters):
```

Enter the Gmail App Password (the text is hidden). The OTP is sent to the email entered in the form. The new account is saved to the database only after the correct OTP is submitted within 5 minutes.

Default admin: `admin@bookverse.vn` / `Admin@123`.

## Shopping and COD orders

- Add books from the catalog or book-detail page. Cart quantities are limited by the current stock.
- Sign in, open **Giỏ hàng**, then choose **Tiến hành đặt hàng** to place a COD order.
- Open **Đơn hàng** to view order history and filter it by status.

Order statuses are stored in `customer_orders.status`. While the application is running, connect an H2 client to:

```text
jdbc:h2:file:./data/bookverse;MODE=MySQL;AUTO_SERVER=TRUE
```

Use account `sa` with an empty password. For example:

```sql
UPDATE customer_orders SET status = 'CONFIRMED' WHERE id = 1;
```

Reload **Đơn hàng** to see the change. Valid values are `NEW`, `CONFIRMED`, `PREPARING`, `SHIPPING`, `DELIVERING`, `DELIVERED`, `CANCELLED`, and `RETURNED`.
