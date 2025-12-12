/* ====== DROP (chạy lại cho sạch) ====== */
IF OBJECT_ID('dbo.[Order]', 'U') IS NOT NULL DROP TABLE dbo.[Order];
IF OBJECT_ID('dbo.Product', 'U') IS NOT NULL DROP TABLE dbo.Product;
IF OBJECT_ID('dbo.[User]', 'U') IS NOT NULL DROP TABLE dbo.[User];
GO

/* ====== USER ====== */
CREATE TABLE dbo.[User]
(
    ID        INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_User PRIMARY KEY,
    Email     NVARCHAR(255)     NOT NULL CONSTRAINT UQ_User_Email UNIQUE,
    [Name]    NVARCHAR(150)     NOT NULL,
    [Address] NVARCHAR(300)     NULL
);
GO

/* ====== PRODUCT ====== */
CREATE TABLE dbo.Product
(
    ProId     INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_Product PRIMARY KEY,
    ProName   NVARCHAR(200)     NOT NULL,
    [Des]     NVARCHAR(1000)    NULL,
    Quantity  INT              NOT NULL CONSTRAINT DF_Product_Quantity DEFAULT (0),
    Price     DECIMAL(18,2)     NOT NULL CONSTRAINT DF_Product_Price DEFAULT (0),
    Unit      NVARCHAR(50)      NULL,

    -- User 1 - N Product
    UserID    INT              NOT NULL,

    CONSTRAINT CK_Product_Quantity CHECK (Quantity >= 0),
    CONSTRAINT CK_Product_Price CHECK (Price >= 0),

    -- Không cascade (mặc định NO ACTION)
    CONSTRAINT FK_Product_User FOREIGN KEY (UserID)
        REFERENCES dbo.[User](ID)
);
GO

/* ====== ORDER ====== */
CREATE TABLE dbo.[Order]
(
    OrderId        INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_Order PRIMARY KEY,

    -- User 1 - N Order
    UserID         INT              NOT NULL,

    -- Product 1 - N Order
    ProId          INT              NOT NULL,

    OrderQuantity  INT              NOT NULL,
    OrderTime      DATETIME2(0)     NOT NULL CONSTRAINT DF_Order_OrderTime DEFAULT (SYSDATETIME()),
    CustomerName   NVARCHAR(150)    NOT NULL,
    CustomerSdt    NVARCHAR(20)     NOT NULL,

    CONSTRAINT CK_Order_OrderQuantity CHECK (OrderQuantity > 0),

    -- Không cascade (mặc định NO ACTION)
    CONSTRAINT FK_Order_User FOREIGN KEY (UserID)
        REFERENCES dbo.[User](ID),

    CONSTRAINT FK_Order_Product FOREIGN KEY (ProId)
        REFERENCES dbo.Product(ProId)
);
GO

/* Index gợi ý */
CREATE INDEX IX_Product_UserID ON dbo.Product(UserID);
CREATE INDEX IX_Order_UserID   ON dbo.[Order](UserID);
CREATE INDEX IX_Order_ProId    ON dbo.[Order](ProId);
GO
