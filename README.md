# FarmConnect  
**Ứng dụng quản lý – Bài tập lớn Lập trình Hướng đối tượng (Java)**

## 1. Giới thiệu
**FarmConnect** là ứng dụng desktop được xây dựng bằng **Java Swing**, sử dụng **SQL Server** làm hệ quản trị cơ sở dữ liệu, phục vụ cho mục đích **quản lý [nông trại / sản phẩm / đơn hàng / người dùng – chỉnh theo bài toán của bạn]**.

Dự án được thực hiện trong khuôn khổ **bài tập lớn môn Lập trình Hướng đối tượng**, với mục tiêu:
- Vận dụng các nguyên lý OOP trong Java  
- Xây dựng giao diện người dùng bằng Java Swing  
- Kết nối và thao tác dữ liệu với SQL Server thông qua JDBC  

---

## 2. Công nghệ sử dụng
- **Ngôn ngữ:** Java  
- **Giao diện:** Java Swing  
- **Cơ sở dữ liệu:** Microsoft SQL Server  
- **IDE:** IntelliJ IDEA  
- **Kết nối CSDL:** JDBC (Microsoft SQL Server JDBC Driver)

---

## 3. Chức năng chính
Ứng dụng hiện hỗ trợ các chức năng sau:

- 🔐 **Đăng nhập / Đăng xuất hệ thống**
- 👤 **Quản lý người dùng** (thêm / sửa / xóa / xem)
- 🌱 **Quản lý [đối tượng chính của bài toán]**  
  - Thêm mới  
  - Cập nhật  
  - Xóa  
  - Tìm kiếm  
- 📊 **Thống kê / báo cáo cơ bản**
- 🗂️ **Quản lý dữ liệu từ CSDL SQL Server**

> *(Bạn có thể thêm hoặc bớt chức năng cho đúng với bài của mình, nhưng giữ cấu trúc bullet như trên để README trông “chuẩn đồ án”)*

---

## 4. Giao diện ứng dụng
Một số màn hình chính của hệ thống:

### Màn hình đăng nhập
![Login Screen](https://res.cloudinary.com/dsqz47hw3/image/upload/v1765734941/Screenshot_2025-12-15_004153_aiewp4.png)

### Màn hình quản lý chính
![Main Screen](https://res.cloudinary.com/dsqz47hw3/image/upload/v1765779810/Screenshot_2025-12-15_132212_izfd8n.png)

### Màn hình quản lý dữ liệu
![Management Screen]()

---

## 5. Cấu trúc project
Cấu trúc thư mục chính của dự án:

```
FarmConnect/
│
├── src/                # Mã nguồn Java
│   ├── model/          # Các lớp đối tượng (Entity)
│   ├── dao/            # Data Access Object (làm việc với CSDL)
│   ├── ui/             # Giao diện Swing (JFrame, JPanel, Dialog)
│   ├── service/        # Xử lý nghiệp vụ
│   └── util/           # Lớp tiện ích (DB Connection, Helper)
│
├── lib/                # Thư viện ngoài (JDBC Driver)
├── .gitignore
├── FarmConnect.iml
└── README.md
```

> *(Nếu tên package của bạn khác, chỉ cần đổi lại cho đúng — cấu trúc này giảng viên rất thích)*

---

## 6. Cài đặt và chạy chương trình

### 6.1. Yêu cầu môi trường
- **JDK:** 8 hoặc cao hơn  
- **IDE:** IntelliJ IDEA  
- **SQL Server:** SQL Server 2019 / 2022  
- **JDBC Driver:** Microsoft JDBC Driver for SQL Server  

---

### 6.2. Cấu hình cơ sở dữ liệu
1. Tạo database trong SQL Server:
```sql
CREATE DATABASE FarmConnect;
GO
```

2. Chạy các script tạo bảng và dữ liệu mẫu  
*(file SQL có thể để trong thư mục `database/` nếu bạn muốn bổ sung)*

---

### 6.3. Cấu hình kết nối CSDL
Trong project, chỉnh thông tin kết nối trong lớp kết nối CSDL (ví dụ `DBConnection.java`):

```java
String url = "jdbc:sqlserver://XW:1433;DatabaseName=FarmConnect;encrypt=true;trustServerCertificate=true;";
String user = "your_username";
String password = "your_password";
```

---

### 6.4. Chạy chương trình
1. Mở project bằng **IntelliJ IDEA**  
2. Kiểm tra đã add JDBC Driver trong `lib/`  
3. Chạy class `App` (hoặc class chứa `public static void main`)  
4. Đăng nhập và sử dụng hệ thống  

---

## 7. Áp dụng OOP trong dự án
Dự án có áp dụng các nguyên lý của lập trình hướng đối tượng:
- **Encapsulation:** đóng gói dữ liệu trong các lớp model  
- **Inheritance:** kế thừa cho các lớp giao diện / đối tượng  
- **Polymorphism:** đa hình trong xử lý nghiệp vụ  
- **Abstraction:** tách lớp DAO, Service  

---

## 8. Hạn chế và hướng phát triển
### Hạn chế
- Giao diện còn đơn giản  
- Chưa tối ưu trải nghiệm người dùng  
- Chưa phân quyền chi tiết  

### Hướng phát triển
- Thêm phân quyền người dùng  
- Nâng cấp giao diện  
- Xuất báo cáo (PDF / Excel)  
- Chuyển sang JavaFX hoặc Web  

---

## 9. Thông tin tác giả
- **Sinh viên:** Hà Văn Phong  
- **Môn học:** Lập trình Hướng đối tượng  
- **Công nghệ:** Java Swing – SQL Server  
