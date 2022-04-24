-- Create SERVICES table
CREATE TABLE if not exists Services (
    name varchar(25) NOT NULL,
    price numeric NOT NULL,
    type varchar(25) NOT NULL,
    PRIMARY KEY (name)
);

-- Create CUSTOMERS table
CREATE TABLE if not exists Customers (
    customerID varchar(25) NOT NULL,
    name varchar(25) NOT NULL,
    number_free_days numeric,
    price_service_A numeric,
    price_service_B numeric,
    price_service_C numeric,
    discount_service_A numeric,
    discount_service_B numeric,
    discount_service_C numeric,
    start_service_A date,
    start_service_B date,
    start_service_C date,
    PRIMARY KEY (customerID)
);

-- Insert info into Services:
INSERT INTO Services(name, price, type) VALUES ('serviceA',0.2,'business_day');
INSERT INTO Services(name, price, type) VALUES ('serviceB',0.24,'business_day');
INSERT INTO Services(name, price, type) VALUES ('serviceC',0.4,'calendar_day');


-- Insert info into Customers
INSERT INTO Customers(customerID, name, number_free_days, price_service_A, price_service_B,
                      price_service_C,discount_service_A, discount_service_B, discount_service_C, start_service_A,
                      start_service_B, start_service_C)
VALUES ('Customer X','X',0,0.2,null,0.4,0.2,null,0.2,'2019-09-20',null, '2019-09-20');

INSERT INTO Customers(customerID, name, number_free_days, price_service_A, price_service_B,
                      price_service_C,discount_service_A, discount_service_B, discount_service_C, start_service_A,
                      start_service_B, start_service_C)
VALUES ('Customer Y','Y',200,null,0.24,0.4,null,0.3,0.3,null,'2018-01-01','2018-01-01');

