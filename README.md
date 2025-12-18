# FarmConnect – Manage Purchasing and Selling Agricultural Products for the Farm

Ứng dụng quản lý **FarmConnect** được xây dựng dưới dạng **ứng dụng desktop Java**, phục vụ cho bài tập lớn môn **Lập trình Hướng đối tượng**.
  
---

## 1. Giới thiệu

FarmConnect là ứng dụng quản lý mô phỏng một hệ thống nông trại, cho phép người dùng quản lý các thông tin cơ bản như dữ liệu nghiệp vụ, người dùng và các đối tượng liên quan thông qua giao diện đồ họa.

Dự án tập trung vào việc áp dụng các nguyên lý **Lập trình Hướng đối tượng (OOP)** trong Java, kết hợp với giao diện **Java Swing** và khả năng kết nối cơ sở dữ liệu thông qua **JDBC**.

---

## 2. Mục tiêu dự án

* Áp dụng các nguyên lý OOP: đóng gói, kế thừa, đa hình, trừu tượng
* Xây dựng ứng dụng desktop với Java Swing
* Kết nối và thao tác dữ liệu với cơ sở dữ liệu quan hệ
* Tổ chức mã nguồn rõ ràng theo mô hình nhiều lớp

---

## 3. Công nghệ sử dụng

* **Ngôn ngữ:** Java
* **Giao diện:** Java Swing
* **Cơ sở dữ liệu:** SQL Server
* **Kết nối CSDL:** JDBC
* **IDE:** IntelliJ IDEA

---

## 4. Cấu trúc thư mục

```
FarmConnect
│── src
│   ├── DBConnect     // Kết nối cơ sở dữ liệu
│   ├── Model         // Các lớp mô hình dữ liệu (Entity)
│   ├── Server        // Xử lý logic và nghiệp vụ
│   ├── UI            // Giao diện người dùng (Swing)
│   └── App.java      // Điểm khởi chạy chương trình
│
│── lib                // Thư viện ngoài (JDBC, JCommon, JFreeChart)
│── README.md
```

---

## 5. Chức năng chính

* Quản lý dữ liệu trong hệ thống (thêm / sửa / xóa / tìm kiếm)
* Giao diện thao tác trực quan bằng Java Swing
* Kết nối và đồng bộ dữ liệu với cơ sở dữ liệu SQL Server

---

## 6. Hướng dẫn chạy chương trình

1. Cài đặt **JDK 8** hoặc cao hơn
2. Import project vào IDE (IntelliJ IDEA hoặc NetBeans)
3. Cấu hình kết nối CSDL trong package `DBConnect`
4. Chạy file `App.java` để khởi động chương trình

---

## 7. Hạn chế

* Giao diện còn đơn giản
* Chưa phân quyền người dùng chi tiết
* Chưa có xử lý ngoại lệ đầy đủ cho mọi trường hợp

---

## 8. Hướng phát triển

* Bổ sung phân quyền và đăng nhập người dùng
* Cải thiện giao diện người dùng
* Xuất báo cáo (PDF / Excel)
* Chuyển sang JavaFX hoặc phát triển phiên bản Web

---

## 9. Thông tin tác giả

* **Sinh viên:** Hà Văn Phong
* **Môn học:** Lập trình Hướng đối tượng
* **Công nghệ:** Java Swing – SQL Server
