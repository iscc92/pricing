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
                                         number_of_free_days numeric,
                                         start_date date,
                                         PRIMARY KEY (customerID)
);

-- Create Contracts table
CREATE TABLE if not exists Contracts (
                                         id uuid NOT NULL ,
                                         start_date date,
                                         discount_percentage_value numeric,
                                         discount_start_date date,
                                         discount_end_date date,
                                         service_name varchar(255)
                                             references services,
                                         PRIMARY KEY (id)
);

create table if not exists Customers_contracts
(
    customer_customerid varchar(255) NOT NULL
        references customers,
    contracts_id uuid NOT NULL
        references contracts
);


-- Insert info into Services:
INSERT INTO Services(name, price, type) VALUES ('serviceA',0.2,'BUSINESS_DAY');
INSERT INTO Services(name, price, type) VALUES ('serviceB',0.24,'BUSINESS_DAY');
INSERT INTO Services(name, price, type) VALUES ('serviceC',0.4,'CALENDAR_DAY');

-- Insert info into Customers:
INSERT INTO Customers(customerID, name, number_of_free_days, start_date) VALUES ('X', 'Customer X', 0, '2019-09-20');
INSERT INTO Customers(customerID, name, number_of_free_days, start_date) VALUES ('Y', 'Customer Y', 200, '2018-01-01');

-- Insert info into Contracts:
INSERT INTO Contracts(id, service_name, start_date, discount_percentage_value,
                      discount_start_date, discount_end_date)
VALUES ('c5605548-c407-11ec-9d64-0242ac120002', 'serviceA', '2019-09-20', null, null, null);
INSERT INTO Contracts(id, service_name, start_date, discount_percentage_value,
                      discount_start_date, discount_end_date)
VALUES ('3a358c26-c408-11ec-9d64-0242ac120002', 'serviceC', '2019-09-20', 0.2, '2019-09-22', '2019-09-24');

INSERT INTO Contracts(id, service_name, start_date, discount_percentage_value,
                      discount_start_date, discount_end_date)
VALUES ('5a071cd6-c408-11ec-9d64-0242ac120002', 'serviceB', '2018-01-01', 0.3, '2018-01-01', '3000-01-01');
INSERT INTO Contracts(id, service_name, start_date, discount_percentage_value,
                      discount_start_date, discount_end_date)
VALUES ('5fb40626-c408-11ec-9d64-0242ac120002', 'serviceC', '2018-01-01', 0.3, '2018-01-01', '3000-01-01');

-- Insert info into Customers_contracts:
INSERT INTO Customers_contracts(customer_customerid, contracts_id) VALUES ('X', 'c5605548-c407-11ec-9d64-0242ac120002');
INSERT INTO Customers_contracts(customer_customerid, contracts_id) VALUES ('X', '3a358c26-c408-11ec-9d64-0242ac120002');
INSERT INTO Customers_contracts(customer_customerid, contracts_id) VALUES ('Y', '5a071cd6-c408-11ec-9d64-0242ac120002');
INSERT INTO Customers_contracts(customer_customerid, contracts_id) VALUES ('Y', '5fb40626-c408-11ec-9d64-0242ac120002');
