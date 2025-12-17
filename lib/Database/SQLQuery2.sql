USE [FarmConnect] -- Đảm bảo bạn đang chọn đúng Database
GO

-- =============================================
-- PHẦN 1: CLEAN UP (Xóa bảng cũ theo thứ tự ngược)
-- =============================================
IF OBJECT_ID('dbo.Order', 'U') IS NOT NULL DROP TABLE [dbo].[Order];
IF OBJECT_ID('dbo.Product', 'U') IS NOT NULL DROP TABLE [dbo].[Product];
IF OBJECT_ID('dbo.User', 'U') IS NOT NULL DROP TABLE [dbo].[User];
GO

SET NOCOUNT ON; -- Tắt thông báo "1 row affected" để chạy nhanh hơn

-- =============================================
-- PHẦN 2: TẠO BẢNG (CREATE TABLES)
-- =============================================

-- 1. Bảng User
CREATE TABLE [User] (
    ID INT IDENTITY(1,1) PRIMARY KEY,
    Email VARCHAR(100) NOT NULL UNIQUE,
    Name NVARCHAR(100),       
    Address NVARCHAR(255),
    Password VARCHAR(255)     
);
GO

-- 2. Bảng Product (Đã thêm cột Type)
CREATE TABLE [Product] (
    ProId INT IDENTITY(1,1) PRIMARY KEY,
    ProName NVARCHAR(150) NOT NULL,
    [Des] NVARCHAR(MAX),      
    Quantity INT DEFAULT 0,
    Price DECIMAL(18, 2),     
    Unit NVARCHAR(50),        
    [Type] NVARCHAR(50),      -- <== Cột Loại sản phẩm
    ProductionDate DATETIME,
    ExpirationDate DATETIME,  
    UserID INT,
    
    CONSTRAINT FK_Product_User FOREIGN KEY (UserID) REFERENCES [User](ID)
);
GO

-- 3. Bảng Order
CREATE TABLE [Order] (
    OrderId INT IDENTITY(1,1) PRIMARY KEY,
    UserID INT,               -- Người tạo đơn (hoặc chủ đơn hàng)
    ProId INT,                
    OrderQuantity INT,
    OrderTime DATETIME DEFAULT GETDATE(),
    CustomerName NVARCHAR(100),
    OrderPrice DECIMAL(18, 2),
    CustomerSdt VARCHAR(20),  
    
    CONSTRAINT FK_Order_User FOREIGN KEY (UserID) REFERENCES [User](ID),
    CONSTRAINT FK_Order_Product FOREIGN KEY (ProId) REFERENCES [Product](ProId)
);
GO

PRINT '--> [OK] Da tao xong cau truc bang.';

-- =============================================
-- PHẦN 3: SEED DATA (DỮ LIỆU MẪU)
-- =============================================

-- A. TẠO 10 USER
INSERT INTO [User] (Email, Name, Address, Password)
VALUES 
(N'nongtraixanh@gmail.com', N'Nông Trại Xanh (User 1)', N'Phường 8, Đà Lạt, Lâm Đồng', 'hash_pass'),
(N'minhkhang_bds@gmail.com', N'Phạm Minh Khang', N'Quận 7, TP.HCM', 'hash_pass'),
(N'thuyhang_spa@gmail.com', N'Nguyễn Thuỳ Hằng', N'Cầu Giấy, Hà Nội', 'hash_pass'),
(N'haisan_tuoisong@gmail.com', N'Lê Văn Hải', N'Vũng Tàu, Bà Rịa', 'hash_pass'),
(N'tiembanh_co3@gmail.com', N'Trần Thị Ba', N'Ninh Kiều, Cần Thơ', 'hash_pass'),
(N'hoptacxa_mocchau@gmail.com', N'HTX Mộc Châu', N'Mộc Châu, Sơn La', 'hash_pass'),
(N'vuontrai_cailay@gmail.com', N'Vườn Trái Cây Út', N'Cai Lậy, Tiền Giang', 'hash_pass'),
(N'nhahang_phuongnam@gmail.com', N'Nhà Hàng Phương Nam', N'Quận 1, TP.HCM', 'hash_pass'),
(N'taphoa_bachhoa@gmail.com', N'Tạp Hóa Cô Bảy', N'Biên Hòa, Đồng Nai', 'hash_pass'),
(N'startup_organic@gmail.com', N'Phan Organic Food', N'Thủ Đức, TP.HCM', 'hash_pass');

PRINT '--> [OK] Da tao xong 10 Users.';

-- B. TẠO 100 PRODUCTS CHO USER 1 (Có logic gán Type)
DECLARE @i INT = 1;
DECLARE @BaseName NVARCHAR(100);
DECLARE @Adjective NVARCHAR(50);
DECLARE @FinalName NVARCHAR(200);
DECLARE @Unit NVARCHAR(20);
DECLARE @Price DECIMAL(18,2);
DECLARE @ProductType NVARCHAR(50);

WHILE @i <= 100
BEGIN
    -- Logic xoay vòng 5 loại sản phẩm
    DECLARE @Group INT = @i % 5; 
    
    IF @Group = 0 
    BEGIN
        SET @BaseName = N'Cà chua bi';
        SET @ProductType = N'Rau củ';
    END
    ELSE IF @Group = 1 
    BEGIN
        SET @BaseName = N'Dâu tây New Zealand';
        SET @ProductType = N'Trái cây';
    END
    ELSE IF @Group = 2 
    BEGIN
        SET @BaseName = N'Xà lách thủy canh';
        SET @ProductType = N'Rau ăn lá';
    END
    ELSE IF @Group = 3 
    BEGIN
        SET @BaseName = N'Khoai lang mật';
        SET @ProductType = N'Củ';
    END
    ELSE 
    BEGIN
        SET @BaseName = N'Bơ 034';
        SET @ProductType = N'Trái cây';
    END

    -- Random tính chất (Hữu cơ, VietGAP...)
    DECLARE @AdjType INT = ABS(CHECKSUM(NEWID())) % 5;
    IF @AdjType = 0 SET @Adjective = N'hữu cơ';
    ELSE IF @AdjType = 1 SET @Adjective = N'VietGAP';
    ELSE IF @AdjType = 2 SET @Adjective = N'loại 1';
    ELSE IF @AdjType = 3 SET @Adjective = N'mới thu hoạch';
    ELSE SET @Adjective = N'xuất khẩu';

    SET @FinalName = @BaseName + N' ' + @Adjective + N' (Mã ' + CAST(@i AS NVARCHAR) + N')';
    
    -- Random Giá và Đơn vị tính
    IF @ProductType = N'Trái cây' OR @Group = 0 
    BEGIN 
        SET @Unit = N'Hộp 500g';
        SET @Price = (ABS(CHECKSUM(NEWID())) % 20 + 10) * 5000; -- 50k - 150k
    END
    ELSE 
    BEGIN
        SET @Unit = N'Kg';
        SET @Price = (ABS(CHECKSUM(NEWID())) % 10 + 2) * 5000; -- 10k - 60k
    END

    INSERT INTO [Product] (ProName, [Des], Quantity, Price, Unit, [Type], ProductionDate, ExpirationDate, UserID)
    VALUES (
        @FinalName, 
        N'Nông sản sạch từ FarmConnect, đảm bảo tươi ngon.', 
        ABS(CHECKSUM(NEWID())) % 200 + 10, -- Số lượng tồn: 10 - 210
        @Price, 
        @Unit,
        @ProductType,
        DATEADD(DAY, -ABS(CHECKSUM(NEWID()) % 5), GETDATE()), -- SX gần đây
        DATEADD(DAY, 10, GETDATE()), 
        1 -- Của User 1
    );

    SET @i = @i + 1;
END
PRINT '--> [OK] Da tao xong 100 Products cho User 1.';

-- C. TẠO 100 ORDERS CHO USER 1
DECLARE @j INT = 1;
DECLARE @RandProID INT;
DECLARE @RandQty INT;
DECLARE @CurrentPrice DECIMAL(18,2);

WHILE @j <= 100
BEGIN
    -- Lấy ngẫu nhiên 1 sản phẩm CỦA USER 1 để đặt hàng
    SELECT TOP 1 @RandProID = ProId, @CurrentPrice = Price 
    FROM Product 
    WHERE UserID = 1 -- Chỉ mua hàng của User 1
    ORDER BY NEWID();

    SET @RandQty = ABS(CHECKSUM(NEWID())) % 10 + 1; -- Mua 1-10 món

    INSERT INTO [Order] (UserID, ProId, OrderQuantity, OrderTime, CustomerName, OrderPrice, CustomerSdt)
    VALUES (
        1, -- Gắn với User 1 (người bán hoặc người quản lý)
        @RandProID,
        @RandQty,
        DATEADD(HOUR, -ABS(CHECKSUM(NEWID()) % 2000), GETDATE()), -- Thời gian ngẫu nhiên 3 tháng qua
        N'Khách hàng ' + CHAR(65 + (@j % 26)), -- Tên giả A, B, C...
        @CurrentPrice * @RandQty, 
        '09' + RIGHT('00000000' + CAST(ABS(CHECKSUM(NEWID())) AS VARCHAR(10)), 8)
    );

    SET @j = @j + 1;
END
PRINT '--> [OK] Da tao xong 100 Orders cho User 1.';

SET NOCOUNT OFF;
GO

-- =============================================
-- PHẦN 4: HIỂN THỊ KẾT QUẢ KIỂM TRA
-- =============================================
PRINT '=== THONG KE DU LIEU ===';
SELECT 'User' as [Table], Count(*) as [Total] FROM [User]
UNION ALL
SELECT 'Product (Total)', Count(*) FROM [Product]
UNION ALL
SELECT 'Order (Total)', Count(*) FROM [Order];

PRINT '=== 5 SAN PHAM MAU ===';
SELECT TOP 5 ProId, ProName, [Type], Price, Unit, UserID FROM Product;