CREATE DATABASE PharmacyDB;

use PharmacyDB

CREATE LOGIN kalab WITH PASSWORD = 'Pharmacy123!';
CREATE USER kalab FOR LOGIN kalab;
ALTER ROLE db_owner ADD MEMBER kalab;

CREATE TABLE Medicine (
    medicineID      INT             IDENTITY(1,1)   PRIMARY KEY,
    medicineName    VARCHAR(100)    NOT NULL,
    genericName     VARCHAR(100)    NOT NULL,
    category        VARCHAR(50)     NOT NULL,
    dosageForm      VARCHAR(50)     NOT NULL,
    unit            VARCHAR(20)     NOT NULL,
    reorderLevel    INT             NOT NULL DEFAULT 0
);

CREATE TABLE Stock (
    stockID             INT             IDENTITY(1,1)   PRIMARY KEY,
    medicineID          INT             NOT NULL,
    batchNo             VARCHAR(50)     NOT NULL,
    availableQuantity   INT             NOT NULL DEFAULT 0,
    unitCost            DECIMAL(10,2)   NOT NULL,
    manufactureDate     DATE            NOT NULL,
    expiryDate          DATE            NOT NULL,
    receivedDate        DATE            NOT NULL,
    storageCondition    VARCHAR(50)     NOT NULL DEFAULT 'Room temperature',

    CONSTRAINT fk_stock_medicine FOREIGN KEY (medicineID)
        REFERENCES Medicine(medicineID)
);

CREATE TABLE PurchaseOrder (
    orderID         INT             IDENTITY(1,1)   PRIMARY KEY,
    orderDate       DATE            NOT NULL,
    expectedDate    DATE            NULL,
    receivedDate    DATE            NULL,
    status          VARCHAR(20)     NOT NULL DEFAULT 'draft',
    totalAmount     DECIMAL(10,2)   NOT NULL DEFAULT 0.00,

    CONSTRAINT chk_order_status CHECK (
        status IN ('draft', 'submitted', 'approved', 'received', 'closed')
    )
);

CREATE TABLE OrderItem (
    orderItemID     INT             IDENTITY(1,1)   PRIMARY KEY,
    orderID         INT             NOT NULL,
    medicineID      INT             NOT NULL,
    qtyOrdered      INT             NOT NULL,
    qtyReceived     INT             NOT NULL DEFAULT 0,
    unitCost        DECIMAL(10,2)   NOT NULL,
    totalCost       DECIMAL(10,2)   NOT NULL DEFAULT 0.00,

    CONSTRAINT fk_orderitem_order FOREIGN KEY (orderID)
        REFERENCES PurchaseOrder(orderID),

    CONSTRAINT fk_orderitem_medicine FOREIGN KEY (medicineID)
        REFERENCES Medicine(medicineID)
);

CREATE TABLE Dispensing (
    dispensingID    INT             IDENTITY(1,1)   PRIMARY KEY,
    stockID         INT             NOT NULL,
    medicineID      INT             NOT NULL,
    qtyDispensed    INT             NOT NULL,
    dispenseDate    DATETIME        NOT NULL DEFAULT GETDATE(),

    CONSTRAINT fk_dispensing_stock FOREIGN KEY (stockID)
        REFERENCES Stock(stockID),

    CONSTRAINT fk_dispensing_medicine FOREIGN KEY (medicineID)
        REFERENCES Medicine(medicineID)
);

CREATE TRIGGER trg_ReduceStockOnDispense
ON Dispensing
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE Stock
    SET availableQuantity = availableQuantity - inserted.qtyDispensed
    FROM Stock
    INNER JOIN inserted
        ON Stock.stockID = inserted.stockID;
END;