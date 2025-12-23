USE master;
GO

-- Create the database if it doesn't exist
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'FarmConnect')
BEGIN
    CREATE DATABASE FarmConnect;
END
GO

USE FarmConnect;
GO

-- Create User table
IF OBJECT_ID('dbo.[User]', 'U') IS NOT NULL DROP TABLE dbo.[User];
CREATE TABLE dbo.[User] (
    [ID] INT IDENTITY(1,1) PRIMARY KEY,
    [Email] NVARCHAR(255) NOT NULL UNIQUE,
    [Name] NVARCHAR(255) NOT NULL,
    [Address] NVARCHAR(255), -- Nullable to match diagram but not strictly required by DAO
    [Password] NVARCHAR(255) NOT NULL
);
GO

-- Create Product table
IF OBJECT_ID('dbo.Product', 'U') IS NOT NULL DROP TABLE dbo.Product;
CREATE TABLE dbo.Product (
    [ProId] INT IDENTITY(1,1) PRIMARY KEY,
    [ProName] NVARCHAR(255) NOT NULL,
    [Des] NVARCHAR(MAX),
    [Quantity] INT NOT NULL DEFAULT 0,
    [Price] DECIMAL(18, 2) NOT NULL DEFAULT 0,
    [Unit] NVARCHAR(50),
    [Type] NVARCHAR(50), -- Used in search queries
    [ProductionDate] DATETIME, -- From diagram, not used in code (Nullable)
    [ExpirationDate] DATETIME, -- Used in Insert/Select
    [PricePercent] FLOAT DEFAULT 0,
    [UserID] INT,
    CONSTRAINT FK_Product_User FOREIGN KEY ([UserID]) REFERENCES dbo.[User]([ID])
);
GO

-- Create Order table
IF OBJECT_ID('dbo.[Order]', 'U') IS NOT NULL DROP TABLE dbo.[Order];
CREATE TABLE dbo.[Order] (
    [OrderId] INT IDENTITY(1,1) PRIMARY KEY,
    [UserID] INT NOT NULL,
    [ProId] INT NOT NULL,
    [OrderQuantity] INT NOT NULL DEFAULT 1,
    [OrderTime] DATETIME DEFAULT GETDATE(),
    [CustomerName] NVARCHAR(255), -- From diagram (Nullable)
    [CustomerSdt] NVARCHAR(20),   -- From diagram (Nullable)
    [OrderPrice] DECIMAL(18, 2),  -- From diagram (Nullable)
    CONSTRAINT FK_Order_User FOREIGN KEY ([UserID]) REFERENCES dbo.[User]([ID]),
    CONSTRAINT FK_Order_Product FOREIGN KEY ([ProId]) REFERENCES dbo.Product([ProId])
);
GO

-- Insert dummy data for testing (Optional)
INSERT INTO dbo.[User] ([Email], [Name], [Address], [Password])
VALUES ('admin@example.com', 'Admin User', '123 Farm Lane', 'admin123');
GO
