-- ============================================================
-- SAMPLE DATA — Pharmacy Management System
-- run this after all CREATE TABLE statements
-- run in this exact order — foreign keys require parent rows first
-- ============================================================


-- ============================================================
-- TABLE 1: Medicine
-- ============================================================
INSERT INTO Medicine (medicineName, genericName, category, dosageForm, unit, reorderLevel)
VALUES 
('Panadol',        'Paracetamol',     'Analgesic',       'Tablet',  'tablet', 50),
('Amoxil',         'Amoxicillin',     'Antibiotic',      'Capsule', 'capsule',30),
('Brufen',         'Ibuprofen',       'Analgesic',       'Tablet',  'tablet', 40),
('Flagyl',         'Metronidazole',   'Antibiotic',      'Tablet',  'tablet', 25),
('Vitamin C',      'Ascorbic Acid',   'Vitamin',         'Tablet',  'tablet', 60),
('ORS',            'Oral Rehydration','Electrolyte',     'Sachet',  'sachet', 20),
('Cotrimoxazole',  'Trimethoprim',    'Antibiotic',      'Tablet',  'tablet', 35),
('Metformin',      'Metformin HCl',   'Antidiabetic',    'Tablet',  'tablet', 15),
('Atenolol',       'Atenolol',        'Antihypertensive','Tablet',  'tablet', 10),
('Omeprazole',     'Omeprazole',      'Antacid',         'Capsule', 'capsule',20);


-- ============================================================
-- TABLE 2: Stock
-- note: stockID is auto generated — we do not insert it
-- some batches have availableQuantity below 10 to test low stock
-- ============================================================
INSERT INTO Stock (medicineID, batchNo, availableQuantity, unitCost, manufactureDate, expiryDate, receivedDate, storageCondition)
VALUES
-- Panadol — two batches
(1, 'BN-2024-001', 150, 2.50,  '2024-01-10', '2026-12-31', '2024-02-01', 'Room temperature'),
(1, 'BN-2024-002', 8,   2.50,  '2024-03-10', '2026-06-30', '2024-04-01', 'Room temperature'),

-- Amoxil
(2, 'BN-2024-003', 200, 5.00,  '2024-01-15', '2026-11-30', '2024-02-10', 'Room temperature'),

-- Brufen
(3, 'BN-2024-004', 6,   3.00,  '2024-02-01', '2026-10-31', '2024-03-01', 'Room temperature'),

-- Flagyl
(4, 'BN-2024-005', 120, 4.50,  '2024-02-15', '2026-09-30', '2024-03-15', 'Room temperature'),

-- Vitamin C
(5, 'BN-2024-006', 300, 1.50,  '2024-03-01', '2027-01-31', '2024-04-01', 'Room temperature'),

-- ORS — low stock
(6, 'BN-2024-007', 5,   1.00,  '2024-03-10', '2026-08-31', '2024-04-10', 'Room temperature'),

-- Cotrimoxazole
(7, 'BN-2024-008', 180, 3.50,  '2024-04-01', '2026-12-31', '2024-05-01', 'Room temperature'),

-- Metformin
(8, 'BN-2024-009', 90,  6.00,  '2024-04-15', '2027-02-28', '2024-05-15', 'Refrigerated'),

-- Atenolol — low stock
(9, 'BN-2024-010', 7,   4.00,  '2024-05-01', '2026-11-30', '2024-06-01', 'Room temperature'),

-- Omeprazole
(10,'BN-2024-011', 110, 7.50,  '2024-05-15', '2027-03-31', '2024-06-15', 'Room temperature');


-- ============================================================
-- TABLE 3: PurchaseOrder
-- three orders in different stages of the lifecycle
-- ============================================================
INSERT INTO PurchaseOrder (orderDate, expectedDate, receivedDate, status, totalAmount)
VALUES
-- first order fully received
('2024-02-01', '2024-02-10', '2024-02-09', 'received', 1250.00),

-- second order submitted and approved but not yet received
('2024-05-01', '2024-05-15', NULL,          'approved', 875.00),

-- third order still in draft
('2024-06-01', NULL,          NULL,          'draft',    0.00);


-- ============================================================
-- TABLE 4: OrderItem
-- line items for each purchase order
-- orderID 1 = received, orderID 2 = approved, orderID 3 = draft
-- ============================================================
INSERT INTO OrderItem (orderID, medicineID, qtyOrdered, qtyReceived, unitCost, totalCost)
VALUES
-- items for order 1 (received)
(1, 1, 200, 200, 2.50,  500.00),   -- Panadol
(1, 2, 150, 150, 5.00,  750.00),   -- Amoxil

-- items for order 2 (approved, not received yet)
(2, 3, 100, 0,   3.00,  300.00),   -- Brufen
(2, 5, 250, 0,   1.50,  375.00),   -- Vitamin C
(2, 6, 200, 0,   1.00,  200.00),   -- ORS

-- items for order 3 (draft — nothing confirmed yet)
(3, 8, 100, 0,   6.00,  0.00),     -- Metformin
(3, 9, 80,  0,   4.00,  0.00);     -- Atenolol


-- ============================================================
-- TABLE 5: Dispensing
-- remember — inserting here fires the trigger automatically
-- which reduces availableQuantity in Stock
-- dispenseDate is filled automatically by DEFAULT GETDATE()
-- so we do not include it in the INSERT
-- ============================================================
INSERT INTO Dispensing (stockID, medicineID, qtyDispensed)
VALUES
(1,  1,  20),   -- 20 Panadol from batch BN-2024-001
(3,  2,  15),   -- 15 Amoxil from batch BN-2024-003
(5,  4,  10),   -- 10 Flagyl from batch BN-2024-005
(6,  5,  30),   -- 30 Vitamin C from batch BN-2024-006
(8,  7,  25),   -- 25 Cotrimoxazole from batch BN-2024-008
(1,  1,  10),   -- 10 more Panadol from same batch
(3,  2,  5),    -- 5 more Amoxil
(9,  8,  20),   -- 20 Metformin from batch BN-2024-009
(11, 10, 15),   -- 15 Omeprazole from batch BN-2024-011
(1,  1,  8);    -- 8 more Panadol

select * from Dispensing

select * from Stock

SELECT * FROM Dispensing ORDER BY dispensingID;

SELECT * FROM sys.triggers WHERE name = 'trg_ReduceStockOnDispense';

-- check current stock vs what it should be
SELECT stockID, availableQuantity FROM Stock WHERE stockID IN (1, 3, 9, 11);

SELECT stockID, SUM(qtyDispensed) as totalDispensed
FROM Dispensing
GROUP BY stockID
ORDER BY stockID;


-- drop the old trigger first
DROP TRIGGER trg_ReduceStockOnDispense;

-- create the corrected trigger that handles multiple rows correctly
CREATE TRIGGER trg_ReduceStockOnDispense
ON Dispensing
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- GROUP BY stockID and SUM qtyDispensed
    -- handles multiple rows for the same stockID in one batch
    UPDATE Stock
    SET availableQuantity = availableQuantity - i.totalQty
    FROM Stock
    INNER JOIN (
        SELECT stockID, SUM(qtyDispensed) as totalQty
        FROM inserted
        GROUP BY stockID
    ) i ON Stock.stockID = i.stockID;
END;


-- fix stockID 1: should be 112
UPDATE Stock SET availableQuantity = 112 WHERE stockID = 1;

-- fix stockID 3: should be 180
UPDATE Stock SET availableQuantity = 180 WHERE stockID = 3;