-- =============================================
-- FarmConnect Database Schema
-- SQL Server - Complete Setup (No Data)
-- =============================================

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

-- =============================================
-- DROP existing tables (in order due to FK)
-- =============================================
IF OBJECT_ID('dbo.OrderItem', 'U') IS NOT NULL DROP TABLE dbo.OrderItem;
IF OBJECT_ID('dbo.[Order]', 'U') IS NOT NULL DROP TABLE dbo.[Order];
IF OBJECT_ID('dbo.Product', 'U') IS NOT NULL DROP TABLE dbo.Product;
IF OBJECT_ID('dbo.[User]', 'U') IS NOT NULL DROP TABLE dbo.[User];
GO

-- =============================================
-- CREATE TABLES
-- =============================================

-- User table
CREATE TABLE dbo.[User] (
    [ID] INT IDENTITY(1,1) PRIMARY KEY,
    [Email] NVARCHAR(255) NOT NULL UNIQUE,
    [Name] NVARCHAR(255) NOT NULL,
    [Address] NVARCHAR(255),
    [Password] NVARCHAR(255) NOT NULL
);
GO

-- Product table
CREATE TABLE dbo.Product (
    [ProId] INT IDENTITY(1,1) PRIMARY KEY,
    [ProName] NVARCHAR(255) NOT NULL,
    [Des] NVARCHAR(MAX),
    [Quantity] INT NOT NULL DEFAULT 0,
    [Price] DECIMAL(18, 2) NOT NULL DEFAULT 0,
    [Unit] NVARCHAR(50),
    [Type] NVARCHAR(50),
    [ProductionDate] DATETIME,
    [ExpirationDate] DATETIME,
    [PricePercent] FLOAT DEFAULT 0,
    [UserID] INT,
    CONSTRAINT FK_Product_User FOREIGN KEY ([UserID]) REFERENCES dbo.[User]([ID])
);
GO

-- Order table
CREATE TABLE dbo.[Order] (
    [OrderId] INT IDENTITY(1,1) PRIMARY KEY,
    [UserID] INT NOT NULL,
    [OrderTime] DATETIME DEFAULT GETDATE(),
    [TotalAmount] DECIMAL(18, 2),
    [ProId] INT NULL,
    [OrderQuantity] INT NULL,
    [CustomerName] NVARCHAR(255),
    [CustomerSdt] NVARCHAR(20),
    [OrderPrice] DECIMAL(18, 2),
    CONSTRAINT FK_Order_User FOREIGN KEY ([UserID]) REFERENCES dbo.[User]([ID])
);
GO

-- OrderItem table
CREATE TABLE dbo.OrderItem (
    [ItemId] INT IDENTITY(1,1) PRIMARY KEY,
    [OrderId] INT NOT NULL,
    [ProId] INT NOT NULL,
    [Quantity] INT NOT NULL DEFAULT 1,
    [UnitPrice] DECIMAL(18, 2) NOT NULL,
    CONSTRAINT FK_OrderItem_Order FOREIGN KEY ([OrderId]) REFERENCES dbo.[Order]([OrderId]),
    CONSTRAINT FK_OrderItem_Product FOREIGN KEY ([ProId]) REFERENCES dbo.Product([ProId])
);
GO

-- =============================================
-- CREATE INDEXES
-- =============================================
CREATE INDEX IX_Product_UserID ON dbo.Product([UserID]);
CREATE INDEX IX_Order_UserID ON dbo.[Order]([UserID]);
CREATE INDEX IX_OrderItem_OrderId ON dbo.OrderItem([OrderId]);
CREATE INDEX IX_OrderItem_ProId ON dbo.OrderItem([ProId]);
GO

PRINT 'Database FarmConnect created successfully!';
PRINT 'Tables: User, Product, Order, OrderItem';
GO
