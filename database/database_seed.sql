USE FarmConnect;
GO

-- 1. Insert 10 Users
-- User 1 is the main test user
INSERT INTO dbo.[User] ([Email], [Name], [Address], [Password])
VALUES (N'nongdan1@gmail.com', N'Nguyễn Văn A', N'Hà Nội', N'123456');

-- Users 2-10
DECLARE @i INT = 2;
WHILE @i <= 10
BEGIN
    INSERT INTO dbo.[User] ([Email], [Name], [Address], [Password])
    VALUES (
        N'user' + CAST(@i AS NVARCHAR(10)) + '@gmail.com', 
        N'Người dùng ' + CAST(@i AS NVARCHAR(10)), 
        N'Tỉnh ' + CAST(@i AS NVARCHAR(10)), 
        N'123456'
    );
    SET @i = @i + 1;
END
GO

-- 2. Insert 100 Products for User 1 (ID = 1)
DECLARE @p INT = 1;
DECLARE @ProductName NVARCHAR(50);
DECLARE @ProductType NVARCHAR(50);
DECLARE @BasePrice DECIMAL(18,2);

WHILE @p <= 100
BEGIN
    -- Generate some realistic looking product names
    IF @p % 5 = 0 BEGIN SET @ProductName = N'Cà rốt tươi'; SET @ProductType = N'Rau củ'; SET @BasePrice = 15000; END
    ELSE IF @p % 5 = 1 BEGIN SET @ProductName = N'Thịt heo sạch'; SET @ProductType = N'Thịt'; SET @BasePrice = 120000; END
    ELSE IF @p % 5 = 2 BEGIN SET @ProductName = N'Gạo ST25'; SET @ProductType = N'Gạo'; SET @BasePrice = 35000; END
    ELSE IF @p % 5 = 3 BEGIN SET @ProductName = N'Cam sành'; SET @ProductType = N'Trái cây'; SET @BasePrice = 25000; END
    ELSE BEGIN SET @ProductName = N'Trứng gà ta'; SET @ProductType = N'Trứng'; SET @BasePrice = 3500; END

    INSERT INTO dbo.Product (
        [ProName], [Des], [Quantity], [Price], [Unit], [Type], 
        [ProductionDate], [ExpirationDate], [PricePercent], [UserID]
    )
    VALUES (
        @ProductName + N' Lô ' + CAST(@p AS NVARCHAR(10)), -- Name variation
        N'Mô tả chi tiết cho sản phẩm ' + CAST(@p AS NVARCHAR(10)), 
        CAST(RAND()*100 + 10 AS INT), -- Random quantity 10-110
        @BasePrice + (CAST(RAND()*5000 AS INT)), -- Random price variation
        N'Kg', 
        @ProductType,
        DATEADD(DAY, -10, GETDATE()), -- Produced 10 days ago
        DATEADD(DAY, 30, GETDATE()),  -- Expires in 30 days
        0, 
        1 -- Belongs to User 1
    );
    SET @p = @p + 1;
END
GO

-- 3. Insert 100 Orders for User 1
-- Assuming User 1 is the BUYER (UserID on Order table)
-- We will pick random products from the products we just created (ProId 1 to 100)
DECLARE @o INT = 1;
DECLARE @RandomProId INT;
DECLARE @OrderQty INT;
DECLARE @OrderDate DATETIME;

WHILE @o <= 100
BEGIN
    SET @RandomProId = CAST(RAND()*100 + 1 AS INT); -- Random ProId 1-100 (assuming Identity started at 1)
    SET @OrderQty = CAST(RAND()*5 + 1 AS INT); -- 1 to 5 items
    SET @OrderDate = DATEADD(HOUR, -CAST(RAND()*2400 AS INT), GETDATE()); -- Random time in last 100 days

    -- Check if ProId exists (just to be safe, though we just made them)
    IF EXISTS (SELECT 1 FROM dbo.Product WHERE ProId = @RandomProId)
    BEGIN
        INSERT INTO dbo.[Order] (
            [UserID], [ProId], [OrderQuantity], [OrderTime], 
            [CustomerName], [CustomerSdt], [OrderPrice]
        )
        VALUES (
            1, -- User 1 bought this
            @RandomProId, 
            @OrderQty, 
            @OrderDate,
            N'Nguyễn Văn A', -- User 1's name
            N'0909123456', 
            (SELECT Price FROM dbo.Product WHERE ProId = @RandomProId) * @OrderQty -- Calculate price
        );
    END
    SET @o = @o + 1;
END
GO
