# BookVerse

Spring Boot (embedded Tomcat) + Servlet/JPA/JSP. Java 17+ is required.

## Run in VS Code

Open the project terminal and run:

```powershell
.\run.ps1
```

Open `http://localhost:8081/`.

## OTP flow

Configure the Gmail App Password once in Windows PowerShell:

```powershell
[Environment]::SetEnvironmentVariable('BOOKVERSE_SMTP_PASSWORD', 'your-16-character-app-password', 'User')
```

Restart the VS Code terminal, then run `.\run.ps1`. Registration now sends OTP automatically without requesting the App Password again. The new account is saved only after the correct OTP is submitted within 5 minutes.

Do not put the App Password in `run.ps1`, source code, or Git. If the Gmail password changes, update the environment variable with the same command.

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
