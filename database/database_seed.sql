-- =============================================
-- FarmConnect - Seed Data
-- 10 Users, 100 Products, 50 Orders
-- Realistic data with uneven distribution
-- =============================================

USE FarmConnect;
GO

-- =============================================
-- 1. INSERT 10 USERS
-- =============================================
INSERT INTO dbo.[User] ([Email], [Name], [Address], [Password]) VALUES
(N'admin@gmail.com', N'Nguyen Van Admin', N'Ha Noi', N'123456'),
(N'trader1@gmail.com', N'Tran Thi Lan', N'TP Ho Chi Minh', N'123456'),
(N'farmer1@gmail.com', N'Le Van Minh', N'Binh Duong', N'123456'),
(N'buyer1@gmail.com', N'Pham Thi Hoa', N'Da Nang', N'123456'),
(N'seller1@gmail.com', N'Hoang Van Nam', N'Can Tho', N'123456'),
(N'customer1@gmail.com', N'Vo Thi Mai', N'Hai Phong', N'123456'),
(N'market1@gmail.com', N'Dang Van Hung', N'Dong Nai', N'123456'),
(N'store1@gmail.com', N'Bui Thi Nga', N'Long An', N'123456'),
(N'shop1@gmail.com', N'Ngo Van Duc', N'Tien Giang', N'123456'),
(N'farm1@gmail.com', N'Ly Thi Thao', N'Lam Dong', N'123456');
GO

-- =============================================
-- 2. INSERT 100 PRODUCTS (Uneven distribution)
-- =============================================

-- RAU CU - 25 san pham (so luong cao 100-500, gia thap 5K-40K)
INSERT INTO dbo.Product ([ProName], [Des], [Quantity], [Price], [Unit], [Type], [ExpirationDate], [PricePercent], [UserID]) VALUES
(N'Rau muong', N'Rau muong huu co', 450, 8000, N'Bo', N'Rau cu', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Cai ngot', N'Cai ngot Da Lat', 380, 12000, N'Kg', N'Rau cu', DATEADD(DAY, 4, GETDATE()), 0, 1),
(N'Cai thia', N'Cai thia tuoi', 320, 10000, N'Kg', N'Rau cu', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Xa lach', N'Xa lach xoan', 280, 15000, N'Kg', N'Rau cu', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Rau dền', N'Rau den do', 200, 9000, N'Bo', N'Rau cu', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Ca rot', N'Ca rot Da Lat', 500, 18000, N'Kg', N'Rau cu', DATEADD(DAY, 14, GETDATE()), 0, 1),
(N'Khoai tay', N'Khoai tay Da Lat', 420, 22000, N'Kg', N'Rau cu', DATEADD(DAY, 30, GETDATE()), 0, 1),
(N'Cu cai trang', N'Cu cai trang huu co', 180, 15000, N'Kg', N'Rau cu', DATEADD(DAY, 10, GETDATE()), 0, 1),
(N'Su hao', N'Su hao xanh', 220, 12000, N'Kg', N'Rau cu', DATEADD(DAY, 7, GETDATE()), 0, 1),
(N'Bap cai', N'Bap cai trang', 350, 14000, N'Kg', N'Rau cu', DATEADD(DAY, 14, GETDATE()), 0, 1),
(N'Hanh tay', N'Hanh tay nhap khau', 280, 28000, N'Kg', N'Rau cu', DATEADD(DAY, 21, GETDATE()), 0, 1),
(N'Toi Ly Son', N'Toi Ly Son chinh goc', 80, 180000, N'Kg', N'Rau cu', DATEADD(DAY, 60, GETDATE()), 0, 1),
(N'Gung', N'Gung ta tuoi', 150, 35000, N'Kg', N'Rau cu', DATEADD(DAY, 30, GETDATE()), 0, 1),
(N'Ot cay', N'Ot hiem xanh', 120, 25000, N'Kg', N'Rau cu', DATEADD(DAY, 7, GETDATE()), 0, 1),
(N'Bi dao', N'Bi dao xanh', 180, 12000, N'Kg', N'Rau cu', DATEADD(DAY, 21, GETDATE()), 0, 1),
(N'Muop', N'Muop huong', 200, 10000, N'Kg', N'Rau cu', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Dua leo', N'Dua leo baby', 300, 15000, N'Kg', N'Rau cu', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Ca chua', N'Ca chua bi', 250, 20000, N'Kg', N'Rau cu', DATEADD(DAY, 7, GETDATE()), 0, 1),
(N'Dau bap', N'Dau bap xanh', 180, 22000, N'Kg', N'Rau cu', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Dau cove', N'Dau cove', 220, 18000, N'Kg', N'Rau cu', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Mong toi', N'Rau mong toi', 150, 8000, N'Bo', N'Rau cu', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Rau ma', N'Rau ma tuoi', 100, 12000, N'Bo', N'Rau cu', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Ngo gai', N'Ngo gai thom', 80, 15000, N'Bo', N'Rau cu', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Hanh la', N'Hanh la tuoi', 200, 10000, N'Bo', N'Rau cu', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Rau ram', N'Rau ram thom', 120, 12000, N'Bo', N'Rau cu', DATEADD(DAY, 3, GETDATE()), 0, 1);
GO

-- TRAI CAY - 20 san pham (so luong trung binh 50-400, gia 15K-200K)
INSERT INTO dbo.Product ([ProName], [Des], [Quantity], [Price], [Unit], [Type], [ExpirationDate], [PricePercent], [UserID]) VALUES
(N'Cam sanh', N'Cam sanh Vinh Long', 400, 28000, N'Kg', N'Trai cay', DATEADD(DAY, 14, GETDATE()), 0, 1),
(N'Cam xoan', N'Cam xoan Tuyen Quang', 250, 35000, N'Kg', N'Trai cay', DATEADD(DAY, 10, GETDATE()), 0, 1),
(N'Xoai cat Hoa Loc', N'Xoai loai 1', 180, 65000, N'Kg', N'Trai cay', DATEADD(DAY, 7, GETDATE()), 0, 1),
(N'Xoai Dai Loan', N'Xoai ngot', 120, 45000, N'Kg', N'Trai cay', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Buoi da xanh', N'Buoi Ben Tre', 200, 45000, N'Qua', N'Trai cay', DATEADD(DAY, 21, GETDATE()), 0, 1),
(N'Buoi Dien', N'Buoi Dien Ha Noi', 80, 55000, N'Qua', N'Trai cay', DATEADD(DAY, 30, GETDATE()), 0, 1),
(N'Dua hau', N'Dua hau ruot do', 350, 18000, N'Kg', N'Trai cay', DATEADD(DAY, 7, GETDATE()), 0, 1),
(N'Dua luoi', N'Dua luoi ngot', 100, 45000, N'Qua', N'Trai cay', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Chuoi gia', N'Chuoi gia huong', 500, 15000, N'Nai', N'Trai cay', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Chuoi cau', N'Chuoi cau', 300, 25000, N'Nai', N'Trai cay', DATEADD(DAY, 4, GETDATE()), 0, 1),
(N'Thanh long', N'Thanh long ruot do', 380, 32000, N'Kg', N'Trai cay', DATEADD(DAY, 10, GETDATE()), 0, 1),
(N'Mit Thai', N'Mit Thai chin cay', 60, 55000, N'Kg', N'Trai cay', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Sau rieng Ri6', N'Sau rieng loai 1', 40, 185000, N'Kg', N'Trai cay', DATEADD(DAY, 4, GETDATE()), 0, 1),
(N'Sau rieng Musang', N'Sau rieng nhap', 25, 250000, N'Kg', N'Trai cay', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Nho xanh', N'Nho xanh My', 80, 120000, N'Kg', N'Trai cay', DATEADD(DAY, 14, GETDATE()), 0, 1),
(N'Nho den', N'Nho den Uc', 60, 150000, N'Kg', N'Trai cay', DATEADD(DAY, 14, GETDATE()), 0, 1),
(N'Tao Fuji', N'Tao Fuji Nhat', 90, 85000, N'Kg', N'Trai cay', DATEADD(DAY, 21, GETDATE()), 0, 1),
(N'Le Han Quoc', N'Le vang Han Quoc', 70, 95000, N'Kg', N'Trai cay', DATEADD(DAY, 21, GETDATE()), 0, 1),
(N'Oi', N'Oi le Dai Loan', 150, 25000, N'Kg', N'Trai cay', DATEADD(DAY, 7, GETDATE()), 0, 1),
(N'Bo', N'Bo sap Dak Lak', 120, 55000, N'Kg', N'Trai cay', DATEADD(DAY, 5, GETDATE()), 0, 1);
GO

-- THIT - 15 san pham (so luong thap 20-100, gia cao 70K-350K)
INSERT INTO dbo.Product ([ProName], [Des], [Quantity], [Price], [Unit], [Type], [ExpirationDate], [PricePercent], [UserID]) VALUES
(N'Thit heo ba roi', N'Thit heo sach', 65, 135000, N'Kg', N'Thit', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Thit heo nac vai', N'Thit nac tuoi', 80, 145000, N'Kg', N'Thit', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Thit heo xay', N'Thit xay tuoi', 50, 125000, N'Kg', N'Thit', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Suon heo', N'Suon non', 45, 155000, N'Kg', N'Thit', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Thit bo Uc', N'Thit bo nhap', 30, 320000, N'Kg', N'Thit', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Thit bo My', N'Thit bo loai 1', 25, 350000, N'Kg', N'Thit', DATEADD(DAY, 5, GETDATE()), 0, 1),
(N'Thit bo Viet', N'Thit bo ta', 40, 280000, N'Kg', N'Thit', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Thit ga ta', N'Ga ta nuoi tu nhien', 100, 95000, N'Kg', N'Thit', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Thit ga cong nghiep', N'Ga tuoi', 150, 55000, N'Kg', N'Thit', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Canh ga', N'Canh ga tuoi', 80, 75000, N'Kg', N'Thit', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Thit vit', N'Vit co', 60, 75000, N'Kg', N'Thit', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Thit ngan', N'Ngan ta', 35, 85000, N'Kg', N'Thit', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Thit de', N'De co Ninh Binh', 20, 280000, N'Kg', N'Thit', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Thit cuu', N'Cuu nhap', 15, 320000, N'Kg', N'Thit', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Thit tho', N'Tho tuoi', 25, 180000, N'Kg', N'Thit', DATEADD(DAY, 2, GETDATE()), 0, 1);
GO

-- HAI SAN - 15 san pham (so luong thap 10-80, gia cao 50K-500K)
INSERT INTO dbo.Product ([ProName], [Des], [Quantity], [Price], [Unit], [Type], [ExpirationDate], [PricePercent], [UserID]) VALUES
(N'Ca hoi', N'Ca hoi Na Uy', 15, 450000, N'Kg', N'Hai san', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Ca ngu', N'Ca ngu dai duong', 20, 380000, N'Kg', N'Hai san', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Tom su', N'Tom su Phu Yen', 45, 280000, N'Kg', N'Hai san', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Tom the', N'Tom the chan trang', 60, 220000, N'Kg', N'Hai san', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Tom hum', N'Tom hum Binh Dinh', 10, 850000, N'Kg', N'Hai san', DATEADD(DAY, 1, GETDATE()), 0, 1),
(N'Muc ong', N'Muc ong tuoi', 35, 180000, N'Kg', N'Hai san', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Muc la', N'Muc la mot nang', 25, 350000, N'Kg', N'Hai san', DATEADD(DAY, 30, GETDATE()), 0, 1),
(N'Cua bien', N'Cua gach', 18, 450000, N'Kg', N'Hai san', DATEADD(DAY, 1, GETDATE()), 0, 1),
(N'Ghẹ', N'Ghe xanh', 30, 280000, N'Kg', N'Hai san', DATEADD(DAY, 1, GETDATE()), 0, 1),
(N'Ca nuc', N'Ca nuc bien', 80, 55000, N'Kg', N'Hai san', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Ca basa', N'Ca basa fillet', 100, 65000, N'Kg', N'Hai san', DATEADD(DAY, 3, GETDATE()), 0, 1),
(N'Ca dieu hong', N'Ca dieu hong tuoi', 50, 85000, N'Kg', N'Hai san', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Ngao', N'Ngao hoa', 70, 45000, N'Kg', N'Hai san', DATEADD(DAY, 1, GETDATE()), 0, 1),
(N'So huyet', N'So huyet Kien Giang', 40, 120000, N'Kg', N'Hai san', DATEADD(DAY, 1, GETDATE()), 0, 1),
(N'Hau sua', N'Hau sua Vung Tau', 35, 150000, N'Kg', N'Hai san', DATEADD(DAY, 1, GETDATE()), 0, 1);
GO

-- GAO - 10 san pham (so luong rat cao 300-1000, gia trung binh 20K-60K)
INSERT INTO dbo.Product ([ProName], [Des], [Quantity], [Price], [Unit], [Type], [ExpirationDate], [PricePercent], [UserID]) VALUES
(N'Gao ST25', N'Gao ST25 chinh goc', 800, 42000, N'Kg', N'Gao', DATEADD(DAY, 180, GETDATE()), 0, 1),
(N'Gao ST24', N'Gao ST24 thom', 600, 38000, N'Kg', N'Gao', DATEADD(DAY, 180, GETDATE()), 0, 1),
(N'Gao Jasmine', N'Gao Jasmine', 700, 28000, N'Kg', N'Gao', DATEADD(DAY, 180, GETDATE()), 0, 1),
(N'Gao Tam Thai', N'Gao Tam Thai do', 300, 55000, N'Kg', N'Gao', DATEADD(DAY, 180, GETDATE()), 0, 1),
(N'Gao Nang Thom', N'Gao Nang Thom', 500, 32000, N'Kg', N'Gao', DATEADD(DAY, 180, GETDATE()), 0, 1),
(N'Gao lut', N'Gao lut do', 250, 35000, N'Kg', N'Gao', DATEADD(DAY, 180, GETDATE()), 0, 1),
(N'Nep cai hoa vang', N'Nep Bac Bo', 180, 55000, N'Kg', N'Gao', DATEADD(DAY, 180, GETDATE()), 0, 1),
(N'Nep than', N'Nep than deo', 150, 48000, N'Kg', N'Gao', DATEADD(DAY, 180, GETDATE()), 0, 1),
(N'Gao Nhat', N'Gao Nhat nhap', 100, 65000, N'Kg', N'Gao', DATEADD(DAY, 180, GETDATE()), 0, 1),
(N'Gao thom Campuchia', N'Gao nhap', 400, 25000, N'Kg', N'Gao', DATEADD(DAY, 180, GETDATE()), 0, 1);
GO

-- TRUNG - 8 san pham (so luong rat cao 300-1500, gia thap 1K-6K)
INSERT INTO dbo.Product ([ProName], [Des], [Quantity], [Price], [Unit], [Type], [ExpirationDate], [PricePercent], [UserID]) VALUES
(N'Trung ga ta', N'Trung ga nuoi tu nhien', 1200, 4500, N'Qua', N'Trung', DATEADD(DAY, 21, GETDATE()), 0, 1),
(N'Trung ga cong nghiep', N'Trung ga CP', 1500, 3200, N'Qua', N'Trung', DATEADD(DAY, 21, GETDATE()), 0, 1),
(N'Trung ga omega', N'Trung ga giau omega', 500, 5500, N'Qua', N'Trung', DATEADD(DAY, 21, GETDATE()), 0, 1),
(N'Trung vit', N'Trung vit tuoi', 800, 3800, N'Qua', N'Trung', DATEADD(DAY, 21, GETDATE()), 0, 1),
(N'Trung vit muoi', N'Hot vit muoi', 400, 5000, N'Qua', N'Trung', DATEADD(DAY, 60, GETDATE()), 0, 1),
(N'Trung cut', N'Trung cut tuoi', 1000, 1200, N'Qua', N'Trung', DATEADD(DAY, 14, GETDATE()), 0, 1),
(N'Trung ngong', N'Trung ngong', 200, 8000, N'Qua', N'Trung', DATEADD(DAY, 14, GETDATE()), 0, 1),
(N'Trung ga Ac', N'Trung ga Ac bo duong', 300, 6000, N'Qua', N'Trung', DATEADD(DAY, 14, GETDATE()), 0, 1);
GO

-- GIA VI & KHAC - 7 san pham (so luong trung binh 100-300)
INSERT INTO dbo.Product ([ProName], [Des], [Quantity], [Price], [Unit], [Type], [ExpirationDate], [PricePercent], [UserID]) VALUES
(N'Nuoc mam Phu Quoc', N'Nuoc mam 40 do', 150, 85000, N'Chai', N'Gia vi', DATEADD(DAY, 365, GETDATE()), 0, 1),
(N'Nuoc tuong', N'Nuoc tuong Maggi', 200, 25000, N'Chai', N'Gia vi', DATEADD(DAY, 365, GETDATE()), 0, 1),
(N'Dau an', N'Dau an Neptune', 180, 52000, N'Chai', N'Gia vi', DATEADD(DAY, 365, GETDATE()), 0, 1),
(N'Muoi bien', N'Muoi bien tu nhien', 300, 8000, N'Kg', N'Gia vi', DATEADD(DAY, 730, GETDATE()), 0, 1),
(N'Duong trang', N'Duong Bien Hoa', 250, 22000, N'Kg', N'Gia vi', DATEADD(DAY, 365, GETDATE()), 0, 1),
(N'Bot ngot', N'Bot ngot Ajinomoto', 200, 45000, N'Kg', N'Gia vi', DATEADD(DAY, 730, GETDATE()), 0, 1),
(N'Hat nem', N'Hat nem Knorr', 180, 35000, N'Goi', N'Gia vi', DATEADD(DAY, 365, GETDATE()), 0, 1);
GO

-- =============================================
-- 2B. SAN PHAM HET HAN VA SAP HET HAN (for statistics)
-- =============================================

-- San pham DA HET HAN (ExpirationDate < today)
INSERT INTO dbo.Product ([ProName], [Des], [Quantity], [Price], [Unit], [Type], [ExpirationDate], [PricePercent], [UserID]) VALUES
(N'Sua tuoi Vinamilk', N'Sua tuoi tiet trung', 50, 32000, N'Hop', N'Sua', DATEADD(DAY, -5, GETDATE()), 0, 1),
(N'Thit heo ba chi', N'Thit heo ba chi tuoi', 20, 125000, N'Kg', N'Thit', DATEADD(DAY, -3, GETDATE()), 0, 1),
(N'Rau muong huu co', N'Rau muong sach', 30, 8000, N'Bo', N'Rau cu', DATEADD(DAY, -2, GETDATE()), 0, 1),
(N'Ca hoi Nauy', N'Ca hoi tuoi fillet', 10, 450000, N'Kg', N'Hai san', DATEADD(DAY, -1, GETDATE()), 0, 1),
(N'Trung ga sach', N'Trung ga nuoi tu nhien', 100, 4500, N'Qua', N'Trung', DATEADD(DAY, -7, GETDATE()), 0, 1);
GO

-- San pham SAP HET HAN (ExpirationDate = today or tomorrow)
INSERT INTO dbo.Product ([ProName], [Des], [Quantity], [Price], [Unit], [Type], [ExpirationDate], [PricePercent], [UserID]) VALUES
(N'Sua chua TH True Milk', N'Sua chua vi dau', 80, 28000, N'Loc', N'Sua', DATEADD(DAY, 1, GETDATE()), 0, 1),
(N'Thit bo Uc nhap khau', N'Thit bo loai 1', 15, 320000, N'Kg', N'Thit', DATEADD(DAY, 2, GETDATE()), 0, 1),
(N'Tom su trang', N'Tom su Phu Yen tuoi', 25, 280000, N'Kg', N'Hai san', DATEADD(DAY, 1, GETDATE()), 0, 1),
(N'Banh mi sandwich', N'Banh mi cat lat', 40, 5000, N'Cai', N'Khac', DATEADD(DAY, 0, GETDATE()), 0, 1),
(N'Xuc xich Vissan', N'Xuc xich heo tuoi', 60, 45000, N'Goi', N'Thit', DATEADD(DAY, 2, GETDATE()), 0, 1);
GO

-- =============================================
-- 3. INSERT 50 ORDERS WITH ORDER ITEMS
-- (Distributed across different dates)
-- =============================================

-- Orders from 90 days ago to today
DECLARE @i INT = 1;
DECLARE @OrderDate DATETIME;
DECLARE @RandomHour INT;
DECLARE @NumItems INT;
DECLARE @ItemIndex INT;
DECLARE @RandomProId INT;
DECLARE @RandomQty INT;
DECLARE @UnitPrice DECIMAL(18,2);
DECLARE @OrderId INT;
DECLARE @TotalAmount DECIMAL(18,2);

WHILE @i <= 50
BEGIN
    -- Random date in last 90 days, more recent orders are more frequent
    IF @i <= 10
        SET @OrderDate = DATEADD(DAY, -(@i * 8), GETDATE()) -- Older orders (80-8 days ago)
    ELSE IF @i <= 25
        SET @OrderDate = DATEADD(DAY, -((@i - 10) * 3), GETDATE()) -- Medium (45-3 days ago)
    ELSE
        SET @OrderDate = DATEADD(DAY, -(50 - @i), GETDATE()) -- Recent (25-0 days ago)
    
    SET @RandomHour = CAST(RAND() * 12 + 7 AS INT); -- 7 AM to 7 PM
    SET @OrderDate = DATEADD(HOUR, @RandomHour, @OrderDate);
    
    -- Insert Order
    INSERT INTO dbo.[Order] ([UserID], [OrderTime], [TotalAmount])
    VALUES (1, @OrderDate, 0);
    
    SET @OrderId = SCOPE_IDENTITY();
    SET @TotalAmount = 0;
    
    -- Random 1-5 items per order
    SET @NumItems = CAST(RAND() * 5 + 1 AS INT);
    SET @ItemIndex = 1;
    
    WHILE @ItemIndex <= @NumItems
    BEGIN
        -- Random product (ID 1-100)
        SET @RandomProId = CAST(RAND() * 100 + 1 AS INT);
        
        -- Random quantity (1-10)
        SET @RandomQty = CAST(RAND() * 10 + 1 AS INT);
        
        -- Get unit price
        SELECT @UnitPrice = [Price] FROM dbo.Product WHERE [ProId] = @RandomProId;
        
        IF @UnitPrice IS NOT NULL
        BEGIN
            -- Insert OrderItem
            INSERT INTO dbo.OrderItem ([OrderId], [ProId], [Quantity], [UnitPrice])
            VALUES (@OrderId, @RandomProId, @RandomQty, @UnitPrice);
            
            SET @TotalAmount = @TotalAmount + (@UnitPrice * @RandomQty);
        END
        
        SET @ItemIndex = @ItemIndex + 1;
    END
    
    -- Update Order total
    UPDATE dbo.[Order] SET [TotalAmount] = @TotalAmount WHERE [OrderId] = @OrderId;
    
    SET @i = @i + 1;
END
GO

PRINT '';
PRINT '=== DATA INSERTED SUCCESSFULLY ===';
PRINT 'Users: 10';
PRINT 'Products: 100 (Rau cu: 25, Trai cay: 20, Thit: 15, Hai san: 15, Gao: 10, Trung: 8, Gia vi: 7)';
PRINT 'Orders: 50 (with multiple items each)';
PRINT '';
PRINT 'Login: admin@gmail.com / 123456';
GO
