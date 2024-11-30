INSERT INTO clients (is_deleted, created_at, updated_at, nip, phone, contact_person, email, name, address)
VALUES
    (false, '2021-01-01 08:00:00', '2021-01-01 08:00:00', 'NIP001', '555-0001', 'Alice Johnson', 'alice@example.com', 'Alpha Corp', '123 Maple Street'),
    (false, '2021-01-02 09:15:00', '2021-01-02 09:15:00', 'NIP002', '555-0002', 'Bob Smith', 'bob@example.com', 'Beta LLC', '456 Oak Avenue'),
    (false, '2021-01-03 10:30:00', '2021-01-03 10:30:00', 'NIP003', '555-0003', 'Carol Williams', 'carol@example.com', 'Gamma Inc', '789 Pine Road'),
    (false, '2021-01-04 11:45:00', '2021-01-04 11:45:00', 'NIP004', '555-0004', 'David Brown', 'david@example.com', 'Delta Co', '101 Elm Street'),
    (false, '2021-01-05 13:00:00', '2021-01-05 13:00:00', 'NIP005', '555-0005', 'Eve Davis', 'eve@example.com', 'Epsilon Ltd', '202 Birch Lane'),
    (false, '2021-01-06 14:15:00', '2021-01-06 14:15:00', 'NIP006', '555-0006', 'Frank Miller', 'frank@example.com', 'Zeta Partners', '303 Cedar Blvd'),
    (false, '2021-01-07 15:30:00', '2021-01-07 15:30:00', 'NIP007', '555-0007', 'Grace Wilson', 'grace@example.com', 'Eta Group', '404 Spruce Terrace'),
    (false, '2021-01-08 16:45:00', '2021-01-08 16:45:00', 'NIP008', '555-0008', 'Henry Moore', 'henry@example.com', 'Theta Enterprises', '505 Willow Way'),
    (false, '2021-01-09 18:00:00', '2021-01-09 18:00:00', 'NIP009', '555-0009', 'Irene Taylor', 'irene@example.com', 'Iota Solutions', '606 Aspen Court'),
    (false, '2021-01-10 19:15:00', '2021-01-10 19:15:00', 'NIP010', '555-0010', 'Jack Anderson', 'jack@example.com', 'Kappa Services', '707 Poplar Drive'),
    (false, '2021-01-11 08:30:00', '2021-01-11 08:30:00', 'NIP011', '555-0011', 'Karen Thomas', 'karen@example.com', 'Lambda Trading', '808 Walnut Street'),
    (false, '2021-01-12 09:45:00', '2021-01-12 09:45:00', 'NIP012', '555-0012', 'Leo Jackson', 'leo@example.com', 'Mu Manufacturing', '909 Chestnut Blvd'),
    (false, '2021-01-13 11:00:00', '2021-01-13 11:00:00', 'NIP013', '555-0013', 'Mia White', 'mia@example.com', 'Nu Technologies', '1010 Redwood Ave'),
    (false, '2021-01-14 12:15:00', '2021-01-14 12:15:00', 'NIP014', '555-0014', 'Nick Harris', 'nick@example.com', 'Xi Innovations', '1111 Cypress Lane'),
    (false, '2021-01-15 13:30:00', '2021-01-15 13:30:00', 'NIP015', '555-0015', 'Olivia Martin', 'olivia@example.com', 'Omicron Ventures', '1212 Palm Street');
INSERT INTO client_orders (is_deleted, total_amount, client_id, created_at, order_date, updated_at, payment_status, status)
VALUES
    (false, 1500.00, 1, '2021-02-01 10:00:00', '2021-02-05 10:00:00', '2021-02-01 10:00:00', 'PAID', 'COMPLETED'),
    (false, 2500.50, 2, '2021-02-02 11:15:00', '2021-02-06 11:15:00', '2021-02-02 11:15:00', 'UNPAID', 'PENDING'),
    (false, 1750.75, 3, '2021-02-03 12:30:00', '2021-02-07 12:30:00', '2021-02-03 12:30:00', 'PARTIAL', 'IN_PROGRESS'),
    (false, 2000.00, 4, '2021-02-04 13:45:00', '2021-02-08 13:45:00', '2021-02-04 13:45:00', 'PAID', 'CONFIRMED'),
    (false, 2250.25, 5, '2021-02-05 15:00:00', '2021-02-09 15:00:00', '2021-02-05 15:00:00', 'NEW', 'NEW'),
    (false, 3000.00, 6, '2021-02-06 16:15:00', '2021-02-10 16:15:00', '2021-02-06 16:15:00', 'PAID', 'COMPLETED'),
    (false, 2750.50, 7, '2021-02-07 17:30:00', '2021-02-11 17:30:00', '2021-02-07 17:30:00', 'UNPAID', 'PENDING'),
    (false, 3250.75, 8, '2021-02-08 18:45:00', '2021-02-12 18:45:00', '2021-02-08 18:45:00', 'PARTIAL', 'IN_PROGRESS'),
    (false, 3500.00, 9, '2021-02-09 20:00:00', '2021-02-13 20:00:00', '2021-02-09 20:00:00', 'PAID', 'CONFIRMED'),
    (false, 3750.25, 10, '2021-02-10 21:15:00', '2021-02-14 21:15:00', '2021-02-10 21:15:00', 'NEW', 'NEW'),
    (false, 4000.00, 11, '2021-02-11 08:30:00', '2021-02-15 08:30:00', '2021-02-11 08:30:00', 'PAID', 'COMPLETED'),
    (false, 4250.50, 12, '2021-02-12 09:45:00', '2021-02-16 09:45:00', '2021-02-12 09:45:00', 'UNPAID', 'PENDING'),
    (false, 4500.75, 13, '2021-02-13 11:00:00', '2021-02-17 11:00:00', '2021-02-13 11:00:00', 'PARTIAL', 'IN_PROGRESS'),
    (false, 4750.00, 14, '2021-02-14 12:15:00', '2021-02-18 12:15:00', '2021-02-14 12:15:00', 'PAID', 'CONFIRMED'),
    (false, 5000.25, 15, '2021-02-15 13:30:00', '2021-02-19 13:30:00', '2021-02-15 13:30:00', 'NEW', 'NEW');

INSERT INTO client_invoices (is_deleted, total_amount, client_id, client_order_id, created_at, date_issued, due_date, updated_at, invoice_number, payment_status)
VALUES
    (false, 1500.00, 1, 1, '2021-02-02 09:00:00', '2021-02-02 09:00:00', '2021-03-02 09:00:00', '2021-02-02 09:00:00', 'INV-0001', 'PAID'),
    (false, 2500.50, 2, 2, '2021-02-03 10:15:00', '2021-02-03 10:15:00', '2021-03-03 10:15:00', '2021-02-03 10:15:00', 'INV-0002', 'UNPAID'),
    (false, 1750.75, 3, 3, '2021-02-04 11:30:00', '2021-02-04 11:30:00', '2021-03-04 11:30:00', '2021-02-04 11:30:00', 'INV-0003', 'PARTIAL'),
    (false, 2000.00, 4, 4, '2021-02-05 12:45:00', '2021-02-05 12:45:00', '2021-03-05 12:45:00', '2021-02-05 12:45:00', 'INV-0004', 'PAID'),
    (false, 2250.25, 5, 5, '2021-02-06 14:00:00', '2021-02-06 14:00:00', '2021-03-06 14:00:00', '2021-02-06 14:00:00', 'INV-0005', 'NEW'),
    (false, 3000.00, 6, 6, '2021-02-07 15:15:00', '2021-02-07 15:15:00', '2021-03-07 15:15:00', '2021-02-07 15:15:00', 'INV-0006', 'PAID'),
    (false, 2750.50, 7, 7, '2021-02-08 16:30:00', '2021-02-08 16:30:00', '2021-03-08 16:30:00', '2021-02-08 16:30:00', 'INV-0007', 'UNPAID'),
    (false, 3250.75, 8, 8, '2021-02-09 17:45:00', '2021-02-09 17:45:00', '2021-03-09 17:45:00', '2021-02-09 17:45:00', 'INV-0008', 'PARTIAL'),
    (false, 3500.00, 9, 9, '2021-02-10 19:00:00', '2021-02-10 19:00:00', '2021-03-10 19:00:00', '2021-02-10 19:00:00', 'INV-0009', 'PAID'),
    (false, 3750.25, 10, 10, '2021-02-11 20:15:00', '2021-02-11 20:15:00', '2021-03-11 20:15:00', '2021-02-11 20:15:00', 'INV-0010', 'NEW'),
    (false, 4000.00, 11, 11, '2021-02-12 08:30:00', '2021-02-12 08:30:00', '2021-03-12 08:30:00', '2021-02-12 08:30:00', 'INV-0011', 'PAID'),
    (false, 4250.50, 12, 12, '2021-02-13 09:45:00', '2021-02-13 09:45:00', '2021-03-13 09:45:00', '2021-02-13 09:45:00', 'INV-0012', 'UNPAID'),
    (false, 4500.75, 13, 13, '2021-02-14 11:00:00', '2021-02-14 11:00:00', '2021-03-14 11:00:00', '2021-02-14 11:00:00', 'INV-0013', 'PARTIAL'),
    (false, 4750.00, 14, 14, '2021-02-15 12:15:00', '2021-02-15 12:15:00', '2021-03-15 12:15:00', '2021-02-15 12:15:00', 'INV-0014', 'PAID'),
    (false, 5000.25, 15, 15, '2021-02-16 13:30:00', '2021-02-16 13:30:00', '2021-03-16 13:30:00', '2021-02-16 13:30:00', 'INV-0015', 'NEW');

INSERT INTO goods (is_deleted, weight, client_order_id, created_at, updated_at, dimensions, name, description, special_handling_instructions)
VALUES
    (false, 500.0, 1, '2021-02-02 09:30:00', '2021-02-02 09:30:00', '2x2x2', 'Stalowe belki', 'Wysokiej jakości stalowe belki', 'Ostrożnie przy przenoszeniu'),
    (false, 300.0, 2, '2021-02-03 10:45:00', '2021-02-03 10:45:00', '1x1x3', 'Drewniane deski', 'Deski z drewna sosnowego', 'Chronić przed wilgocią'),
    (false, 700.0, 3, '2021-02-04 12:00:00', '2021-02-04 12:00:00', '3x2x1', 'Szklane panele', 'Delikatne szklane panele', 'Oznaczyć jako kruche'),
    (false, 200.0, 4, '2021-02-05 13:15:00', '2021-02-05 13:15:00', '1x1x1', 'Elektronika', 'Różne artykuły elektroniczne', 'Trzymać z dala od magnesów'),
    (false, 600.0, 5, '2021-02-06 14:30:00', '2021-02-06 14:30:00', '2x3x2', 'Części maszyn', 'Ciężkie komponenty maszynowe', 'Użyć wózka widłowego'),
    (false, 400.0, 6, '2021-02-07 15:45:00', '2021-02-07 15:45:00', '2x2x1', 'Tekstylia', 'Różnorodne tekstylia', 'Chronić przed wilgocią'),
    (false, 550.0, 7, '2021-02-08 17:00:00', '2021-02-08 17:00:00', '2x1x2', 'Chemikalia', 'Chemikalia przemysłowe', 'Materiał niebezpieczny'),
    (false, 650.0, 8, '2021-02-09 18:15:00', '2021-02-09 18:15:00', '3x3x2', 'Meble', 'Meble biurowe', 'Ostrożnie przy przenoszeniu'),
    (false, 350.0, 9, '2021-02-10 19:30:00', '2021-02-10 19:30:00', '1x2x1', 'Farmaceutyki', 'Materiały medyczne', 'Temperatura kontrolowana'),
    (false, 450.0, 10, '2021-02-11 20:45:00', '2021-02-11 20:45:00', '2x1x1', 'Części samochodowe', 'Komponenty samochodowe', 'Ostrożnie przy przenoszeniu'),
    (false, 500.0, 11, '2021-02-12 09:00:00', '2021-02-12 09:00:00', '2x2x2', 'Stalowe belki', 'Wysokiej jakości stalowe belki', 'Ostrożnie przy przenoszeniu'),
    (false, 300.0, 12, '2021-02-13 10:15:00', '2021-02-13 10:15:00', '1x1x3', 'Drewniane deski', 'Deski z drewna sosnowego', 'Chronić przed wilgocią'),
    (false, 700.0, 13, '2021-02-14 11:30:00', '2021-02-14 11:30:00', '3x2x1', 'Szklane panele', 'Delikatne szklane panele', 'Oznaczyć jako kruche'),
    (false, 200.0, 14, '2021-02-15 12:45:00', '2021-02-15 12:45:00', '1x1x1', 'Elektronika', 'Różne artykuły elektroniczne', 'Trzymać z dala od magnesów'),
    (false, 600.0, 15, '2021-02-16 14:00:00', '2021-02-16 14:00:00', '2x3x2', 'Części maszyn', 'Ciężkie komponenty maszynowe', 'Użyć wózka widłowego');


INSERT INTO invoices (is_deleted, total_amount, created_at, date_issued, due_date, updated_at, invoice_number, invoice_type, payment_status)
VALUES
    (false, 1500.00, '2021-03-01 09:00:00', '2021-03-01 09:00:00', '2021-04-01 09:00:00', '2021-03-01 09:00:00', 'INV-1001', 'CLIENT', 'PAID'),
    (false, 2500.50, '2021-03-02 10:15:00', '2021-03-02 10:15:00', '2021-04-02 10:15:00', '2021-03-02 10:15:00', 'INV-1002', 'SUPPLIER', 'UNPAID'),
    (false, 1750.75, '2021-03-03 11:30:00', '2021-03-03 11:30:00', '2021-04-03 11:30:00', '2021-03-03 11:30:00', 'INV-1003', 'SUBCONTRACTOR', 'PARTIAL'),
    (false, 2000.00, '2021-03-04 12:45:00', '2021-03-04 12:45:00', '2021-04-04 12:45:00', '2021-03-04 12:45:00', 'INV-1004', 'OTHER', 'PAID'),
    (false, 2250.25, '2021-03-05 14:00:00', '2021-03-05 14:00:00', '2021-04-05 14:00:00', '2021-03-05 14:00:00', 'INV-1005', 'CLIENT', 'NEW'),
    (false, 3000.00, '2021-03-06 15:15:00', '2021-03-06 15:15:00', '2021-04-06 15:15:00', '2021-03-06 15:15:00', 'INV-1006', 'SUPPLIER', 'PAID'),
    (false, 2750.50, '2021-03-07 16:30:00', '2021-03-07 16:30:00', '2021-04-07 16:30:00', '2021-03-07 16:30:00', 'INV-1007', 'SUBCONTRACTOR', 'UNPAID'),
    (false, 3250.75, '2021-03-08 17:45:00', '2021-03-08 17:45:00', '2021-04-08 17:45:00', '2021-03-08 17:45:00', 'INV-1008', 'OTHER', 'PARTIAL'),
    (false, 3500.00, '2021-03-09 19:00:00', '2021-03-09 19:00:00', '2021-04-09 19:00:00', '2021-03-09 19:00:00', 'INV-1009', 'CLIENT', 'PAID'),
    (false, 3750.25, '2021-03-10 20:15:00', '2021-03-10 20:15:00', '2021-04-10 20:15:00', '2021-03-10 20:15:00', 'INV-1010', 'SUPPLIER', 'NEW'),
    (false, 4000.00, '2021-03-11 08:30:00', '2021-03-11 08:30:00', '2021-04-11 08:30:00', '2021-03-11 08:30:00', 'INV-1011', 'SUBCONTRACTOR', 'PAID'),
    (false, 4250.50, '2021-03-12 09:45:00', '2021-03-12 09:45:00', '2021-04-12 09:45:00', '2021-03-12 09:45:00', 'INV-1012', 'OTHER', 'UNPAID'),
    (false, 4500.75, '2021-03-13 11:00:00', '2021-03-13 11:00:00', '2021-04-13 11:00:00', '2021-03-13 11:00:00', 'INV-1013', 'CLIENT', 'PARTIAL'),
    (false, 4750.00, '2021-03-14 12:15:00', '2021-03-14 12:15:00', '2021-04-14 12:15:00', '2021-03-14 12:15:00', 'INV-1014', 'SUPPLIER', 'PAID'),
    (false, 5000.25, '2021-03-15 13:30:00', '2021-03-15 13:30:00', '2021-04-15 13:30:00', '2021-03-15 13:30:00', 'INV-1015', 'SUBCONTRACTOR', 'NEW');

INSERT INTO locations (is_deleted, latitude, longitude, created_at, updated_at, postal_code, city, country, state, name, address)
VALUES
    (false, 52.2297, 21.0122, '2021-04-01 09:00:00', '2021-04-01 09:00:00', '00-001', 'Warszawa', 'Polska', 'Mazowieckie', 'Magazyn Centralny', 'ul. Marszałkowska 1'),
    (false, 50.0647, 19.9450, '2021-04-02 10:15:00', '2021-04-02 10:15:00', '30-001', 'Kraków', 'Polska', 'Małopolskie', 'Oddział Południe', 'ul. Floriańska 2'),
    (false, 51.1079, 17.0385, '2021-04-03 11:30:00', '2021-04-03 11:30:00', '50-001', 'Wrocław', 'Polska', 'Dolnośląskie', 'Oddział Zachód', 'ul. Świdnicka 3'),
    (false, 53.1325, 23.1688, '2021-04-04 12:45:00', '2021-04-04 12:45:00', '15-001', 'Białystok', 'Polska', 'Podlaskie', 'Oddział Wschód', 'ul. Lipowa 4'),
    (false, 54.3520, 18.6466, '2021-04-05 14:00:00', '2021-04-05 14:00:00', '80-001', 'Gdańsk', 'Polska', 'Pomorskie', 'Oddział Północ', 'ul. Długa 5'),
    (false, 52.4064, 16.9252, '2021-04-06 15:15:00', '2021-04-06 15:15:00', '60-001', 'Poznań', 'Polska', 'Wielkopolskie', 'Centrum Logistyczne', 'ul. Półwiejska 6'),
    (false, 50.2599, 19.0216, '2021-04-07 16:30:00', '2021-04-07 16:30:00', '40-001', 'Katowice', 'Polska', 'Śląskie', 'Magazyn Śląsk', 'ul. Mariacka 7'),
    (false, 51.7592, 19.4550, '2021-04-08 17:45:00', '2021-04-08 17:45:00', '90-001', 'Łódź', 'Polska', 'Łódzkie', 'Magazyn Centralny 2', 'ul. Piotrkowska 8'),
    (false, 53.4285, 14.5528, '2021-04-09 19:00:00', '2021-04-09 19:00:00', '70-001', 'Szczecin', 'Polska', 'Zachodniopomorskie', 'Oddział Szczecin', 'ul. Wojska Polskiego 9'),
    (false, 49.8224, 19.0584, '2021-04-10 20:15:00', '2021-04-10 20:15:00', '43-300', 'Bielsko-Biała', 'Polska', 'Śląskie', 'Magazyn Południe', 'ul. 11 Listopada 10'),
    (false, 50.6663, 17.9280, '2021-04-11 08:30:00', '2021-04-11 08:30:00', '45-001', 'Opole', 'Polska', 'Opolskie', 'Oddział Opole', 'ul. Krakowska 11'),
    (false, 51.2465, 22.5684, '2021-04-12 09:45:00', '2021-04-12 09:45:00', '20-001', 'Lublin', 'Polska', 'Lubelskie', 'Oddział Lublin', 'ul. Krakowskie Przedmieście 12'),
    (false, 50.0412, 21.9991, '2021-04-13 11:00:00', '2021-04-13 11:00:00', '35-001', 'Rzeszów', 'Polska', 'Podkarpackie', 'Magazyn Wschód', 'ul. 3 Maja 13'),
    (false, 51.9375, 15.5045, '2021-04-14 12:15:00', '2021-04-14 12:15:00', '65-001', 'Zielona Góra', 'Polska', 'Lubuskie', 'Oddział Zielona Góra', 'ul. Kupiecka 14'),
    (false, 53.0138, 18.5984, '2021-04-15 13:30:00', '2021-04-15 13:30:00', '87-100', 'Toruń', 'Polska', 'Kujawsko-Pomorskie', 'Oddział Toruń', 'ul. Szeroka 15');

INSERT INTO roles (is_deleted, created_at, updated_at, name, description)
VALUES
    (false, '2021-05-01 08:00:00', '2021-05-01 08:00:00', 'Administrator', 'Odpowiada za zarządzanie systemem'),
    (false, '2021-05-02 09:15:00', '2021-05-02 09:15:00', 'Kierowca', 'Odpowiada za transport towarów'),
    (false, '2021-05-03 10:30:00', '2021-05-03 10:30:00', 'Logistyk', 'Zarządza planowaniem tras i transportu'),
    (false, '2021-05-04 11:45:00', '2021-05-04 11:45:00', 'Menedżer', 'Nadzoruje działanie firmy'),
    (false, '2021-05-05 13:00:00', '2021-05-05 13:00:00', 'Obsługa klienta', 'Odpowiada za kontakt z klientami'),
    (false, '2021-05-06 14:15:00', '2021-05-06 14:15:00', 'Technik', 'Zajmuje się serwisem pojazdów'),
    (false, '2021-05-07 15:30:00', '2021-05-07 15:30:00', 'Magazynier', 'Odpowiada za zarządzanie magazynem'),
    (false, '2021-05-08 16:45:00', '2021-05-08 16:45:00', 'Księgowy', 'Odpowiada za finanse firmy'),
    (false, '2021-05-09 18:00:00', '2021-05-09 18:00:00', 'HR', 'Zarządza zasobami ludzkimi'),
    (false, '2021-05-10 19:15:00', '2021-05-10 19:15:00', 'IT Support', 'Odpowiada za wsparcie techniczne'),
    (false, '2021-05-11 08:30:00', '2021-05-11 08:30:00', 'Marketing', 'Zajmuje się promocją firmy'),
    (false, '2021-05-12 09:45:00', '2021-05-12 09:45:00', 'Sprzedaż', 'Odpowiada za sprzedaż usług'),
    (false, '2021-05-13 11:00:00', '2021-05-13 11:00:00', 'Ochrona', 'Zapewnia bezpieczeństwo'),
    (false, '2021-05-14 12:15:00', '2021-05-14 12:15:00', 'Kontrola jakości', 'Nadzoruje jakość usług'),
    (false, '2021-05-15 13:30:00', '2021-05-15 13:30:00', 'Zaopatrzenie', 'Odpowiada za zakup towarów');

INSERT INTO routes (distance, estimated_time, is_deleted, created_at, end_location_id, start_location_id, updated_at, preferred_route)
VALUES
    (300.0, 4.5, false, '2021-06-01 08:00:00', 2, 1, '2021-06-01 08:00:00', 'Droga ekspresowa S7'),
    (250.0, 3.5, false, '2021-06-02 09:15:00', 3, 2, '2021-06-02 09:15:00', 'Autostrada A4'),
    (200.0, 3.0, false, '2021-06-03 10:30:00', 4, 3, '2021-06-03 10:30:00', 'Droga krajowa 94'),
    (350.0, 5.0, false, '2021-06-04 11:45:00', 5, 4, '2021-06-04 11:45:00', 'Autostrada A2'),
    (400.0, 6.0, false, '2021-06-05 13:00:00', 6, 5, '2021-06-05 13:00:00', 'Droga ekspresowa S8'),
    (450.0, 6.5, false, '2021-06-06 14:15:00', 7, 6, '2021-06-06 14:15:00', 'Autostrada A1'),
    (500.0, 7.0, false, '2021-06-07 15:30:00', 8, 7, '2021-06-07 15:30:00', 'Droga ekspresowa S3'),
    (550.0, 8.0, false, '2021-06-08 16:45:00', 9, 8, '2021-06-08 16:45:00', 'Autostrada A6'),
    (600.0, 9.0, false, '2021-06-09 18:00:00', 10, 9, '2021-06-09 18:00:00', 'Droga krajowa 11'),
    (650.0, 9.5, false, '2021-06-10 19:15:00', 11, 10, '2021-06-10 19:15:00', 'Autostrada A18'),
    (700.0, 10.0, false, '2021-06-11 08:30:00', 12, 11, '2021-06-11 08:30:00', 'Droga ekspresowa S17'),
    (750.0, 11.0, false, '2021-06-12 09:45:00', 13, 12, '2021-06-12 09:45:00', 'Droga krajowa 19'),
    (800.0, 12.0, false, '2021-06-13 11:00:00', 14, 13, '2021-06-13 11:00:00', 'Autostrada A8'),
    (850.0, 13.0, false, '2021-06-14 12:15:00', 15, 14, '2021-06-14 12:15:00', 'Droga ekspresowa S19'),
    (900.0, 14.0, false, '2021-06-15 13:30:00', 1, 15, '2021-06-15 13:30:00', 'Autostrada A2');
INSERT INTO users (is_deleted, created_at, updated_at, phone_number, first_name, last_name, username, email, address, password, status)
VALUES
    (false, '2021-07-01 08:00:00', '2021-07-01 08:00:00', '555-1001', 'Jan', 'Kowalski', 'jank', 'jan.kowalski@example.com', 'ul. Długa 1, Warszawa', 'haslo123', 'ACTIVE'),
    (false, '2021-07-02 09:15:00', '2021-07-02 09:15:00', '555-1002', 'Anna', 'Nowak', 'annan', 'anna.nowak@example.com', 'ul. Krótka 2, Kraków', 'haslo123', 'ACTIVE'),
    (false, '2021-07-03 10:30:00', '2021-07-03 10:30:00', '555-1003', 'Piotr', 'Wiśniewski', 'piotrw', 'piotr.wisniewski@example.com', 'ul. Średnia 3, Wrocław', 'haslo123', 'ACTIVE'),
    (false, '2021-07-04 11:45:00', '2021-07-04 11:45:00', '555-1004', 'Katarzyna', 'Wójcik', 'katarzynaw', 'katarzyna.wojcik@example.com', 'ul. Szeroka 4, Poznań', 'haslo123', 'ACTIVE'),
    (false, '2021-07-05 13:00:00', '2021-07-05 13:00:00', '555-1005', 'Michał', 'Kowalczyk', 'michalk', 'michal.kowalczyk@example.com', 'ul. Wąska 5, Gdańsk', 'haslo123', 'ACTIVE'),
    (false, '2021-07-06 14:15:00', '2021-07-06 14:15:00', '555-1006', 'Agnieszka', 'Kamińska', 'agnieszkak', 'agnieszka.kaminska@example.com', 'ul. Polna 6, Szczecin', 'haslo123', 'ACTIVE'),
    (false, '2021-07-07 15:30:00', '2021-07-07 15:30:00', '555-1007', 'Tomasz', 'Lewandowski', 'tomaszl', 'tomasz.lewandowski@example.com', 'ul. Leśna 7, Lublin', 'haslo123', 'ACTIVE'),
    (false, '2021-07-08 16:45:00', '2021-07-08 16:45:00', '555-1008', 'Monika', 'Zielińska', 'monikaz', 'monika.zielinska@example.com', 'ul. Ogrodowa 8, Białystok', 'haslo123', 'ACTIVE'),
    (false, '2021-07-09 18:00:00', '2021-07-09 18:00:00', '555-1009', 'Andrzej', 'Szymański', 'andrzejs', 'andrzej.szymanski@example.com', 'ul. Lipowa 9, Katowice', 'haslo123', 'ACTIVE'),
    (false, '2021-07-10 19:15:00', '2021-07-10 19:15:00', '555-1010', 'Magdalena', 'Woźniak', 'magdalenaw', 'magdalena.wozniak@example.com', 'ul. Klonowa 10, Łódź', 'haslo123', 'ACTIVE'),
    (false, '2021-07-11 08:30:00', '2021-07-11 08:30:00', '555-1011', 'Krzysztof', 'Dąbrowski', 'krzysztofd', 'krzysztof.dabrowski@example.com', 'ul. Topolowa 11, Rzeszów', 'haslo123', 'ACTIVE'),
    (false, '2021-07-12 09:45:00', '2021-07-12 09:45:00', '555-1012', 'Sylwia', 'Pawłowska', 'sylwiap', 'sylwia.pawlowska@example.com', 'ul. Akacjowa 12, Olsztyn', 'haslo123', 'ACTIVE'),
    (false, '2021-07-13 11:00:00', '2021-07-13 11:00:00', '555-1013', 'Adam', 'Michalski', 'adamm', 'adam.michalski@example.com', 'ul. Brzozowa 13, Toruń', 'haslo123', 'ACTIVE'),
    (false, '2021-07-14 12:15:00', '2021-07-14 12:15:00', '555-1014', 'Ewa', 'Wróbel', 'ewaw', 'ewa.wrobel@example.com', 'ul. Jesionowa 14, Kielce', 'haslo123', 'ACTIVE'),
    (false, '2021-07-15 13:30:00', '2021-07-15 13:30:00', '555-1015', 'Paweł', 'Jankowski', 'pawelj', 'pawel.jankowski@example.com', 'ul. Dębowa 15, Zielona Góra', 'haslo123', 'ACTIVE');
INSERT INTO drivers (is_deleted, created_at, date_of_birth, hire_date, medical_certificate_expiry, updated_at, user_id, license_category, license_number, driver_status)
VALUES
    (false, '2021-07-16 08:00:00', '1980-01-01', '2020-01-15', '2022-01-15', '2021-07-16 08:00:00', 1, 'C+E', 'LIC-0001', 'ACTIVE'),
    (false, '2021-07-17 09:15:00', '1982-02-02', '2020-02-15', '2022-02-15', '2021-07-17 09:15:00', 3, 'C+E', 'LIC-0002', 'ACTIVE'),
    (false, '2021-07-18 10:30:00', '1984-03-03', '2020-03-15', '2022-03-15', '2021-07-18 10:30:00', 5, 'C+E', 'LIC-0003', 'ACTIVE'),
    (false, '2021-07-19 11:45:00', '1986-04-04', '2020-04-15', '2022-04-15', '2021-07-19 11:45:00', 7, 'C+E', 'LIC-0004', 'ACTIVE'),
    (false, '2021-07-20 13:00:00', '1988-05-05', '2020-05-15', '2022-05-15', '2021-07-20 13:00:00', 9, 'C+E', 'LIC-0005', 'ACTIVE'),
    (false, '2021-07-21 14:15:00', '1990-06-06', '2020-06-15', '2022-06-15', '2021-07-21 14:15:00', 11, 'C+E', 'LIC-0006', 'ACTIVE'),
    (false, '2021-07-22 15:30:00', '1992-07-07', '2020-07-15', '2022-07-15', '2021-07-22 15:30:00', 13, 'C+E', 'LIC-0007', 'ACTIVE');
INSERT INTO driver_reports (is_deleted, created_at, date, driver_id, updated_at, content)
VALUES
    (false, '2021-08-01 08:00:00', '2021-07-31 17:00:00', 1, '2021-08-01 08:00:00', 'Brak problemów podczas trasy.'),
    (false, '2021-08-02 09:15:00', '2021-08-01 17:00:00', 2, '2021-08-02 09:15:00', 'Niewielkie opóźnienie z powodu korków.'),
    (false, '2021-08-03 10:30:00', '2021-08-02 17:00:00', 3, '2021-08-03 10:30:00', 'Problemy z załadunkiem w magazynie.'),
    (false, '2021-08-04 11:45:00', '2021-08-03 17:00:00', 4, '2021-08-04 11:45:00', 'Awaria klimatyzacji w pojeździe.'),
    (false, '2021-08-05 13:00:00', '2021-08-04 17:00:00', 5, '2021-08-05 13:00:00', 'Utrudnienia na drodze z powodu remontu.'),
    (false, '2021-08-06 14:15:00', '2021-08-05 17:00:00', 6, '2021-08-06 14:15:00', 'Dostawa zrealizowana przed czasem.'),
    (false, '2021-08-07 15:30:00', '2021-08-06 17:00:00', 7, '2021-08-07 15:30:00', 'Klient zgłosił uszkodzenie towaru.'),
    (false, '2021-08-08 16:45:00', '2021-08-07 17:00:00', 1, '2021-08-08 16:45:00', 'Problemy z dokumentacją na granicy.'),
    (false, '2021-08-09 18:00:00', '2021-08-08 17:00:00', 2, '2021-08-09 18:00:00', 'Brak uwag, trasa przebiegła pomyślnie.'),
    (false, '2021-08-10 19:15:00', '2021-08-09 17:00:00', 3, '2021-08-10 19:15:00', 'Zgłoszono potrzebę serwisu pojazdu.'),
    (false, '2021-08-11 08:30:00', '2021-08-10 17:00:00', 4, '2021-08-11 08:30:00', 'Opóźnienie z powodu kontroli drogowej.'),
    (false, '2021-08-12 09:45:00', '2021-08-11 17:00:00', 5, '2021-08-12 09:45:00', 'Brak miejsc parkingowych na miejscu dostawy.'),
    (false, '2021-08-13 11:00:00', '2021-08-12 17:00:00', 6, '2021-08-13 11:00:00', 'Problemy z nawigacją, zalecam aktualizację map.'),
    (false, '2021-08-14 12:15:00', '2021-08-13 17:00:00', 7, '2021-08-14 12:15:00', 'Klient odmówił przyjęcia towaru.'),
    (false, '2021-08-15 13:30:00', '2021-08-14 17:00:00', 1, '2021-08-15 13:30:00', 'Pozytywna współpraca z zespołem załadunkowym.');
INSERT INTO logisticians (is_deleted, created_at, updated_at, user_id, department)
VALUES
    (false, '2021-08-16 08:00:00', '2021-08-16 08:00:00', 2, 'Planowanie tras'),
    (false, '2021-08-17 09:15:00', '2021-08-17 09:15:00', 4, 'Zarządzanie flotą'),
    (false, '2021-08-18 10:30:00', '2021-08-18 10:30:00', 6, 'Obsługa klienta'),
    (false, '2021-08-19 11:45:00', '2021-08-19 11:45:00', 8, 'Magazynowanie'),
    (false, '2021-08-20 13:00:00', '2021-08-20 13:00:00', 10, 'Kontrola jakości'),
    (false, '2021-08-21 14:15:00', '2021-08-21 14:15:00', 12, 'Planowanie strategiczne'),
    (false, '2021-08-22 15:30:00', '2021-08-22 15:30:00', 14, 'Zaopatrzenie');
INSERT INTO messages (is_deleted, read_status, created_at, date_sent, receiver_id, sender_id, updated_at, content, subject)
VALUES
    (false, false, '2021-09-01 08:00:00', '2021-09-01 08:00:00', 2, 1, '2021-09-01 08:00:00', 'Witaj Anno, czy mogłabyś przesłać mi raport z ostatniego tygodnia?', 'Prośba o raport'),
    (false, true,  '2021-09-02 09:15:00', '2021-09-02 09:15:00', 1, 2, '2021-09-02 09:15:00', 'Cześć Janie, oczywiście, zaraz prześlę.', 'Odp: Prośba o raport'),
    (false, false, '2021-09-03 10:30:00', '2021-09-03 10:30:00', 3, 1, '2021-09-03 10:30:00', 'Czy możemy umówić się na spotkanie w sprawie nowego projektu?', 'Spotkanie w sprawie projektu'),
    (false, false, '2021-09-04 11:45:00', '2021-09-04 11:45:00', 4, 3, '2021-09-04 11:45:00', 'Przesyłam aktualizację harmonogramu.', 'Aktualizacja harmonogramu'),
    (false, true,  '2021-09-05 13:00:00', '2021-09-05 13:00:00', 5, 4, '2021-09-05 13:00:00', 'Dziękuję za informację.', 'Re: Aktualizacja harmonogramu'),
    (false, false, '2021-09-06 14:15:00', '2021-09-06 14:15:00', 6, 5, '2021-09-06 14:15:00', 'Proszę o przesłanie specyfikacji technicznej.', 'Specyfikacja techniczna'),
    (false, false, '2021-09-07 15:30:00', '2021-09-07 15:30:00', 7, 6, '2021-09-07 15:30:00', 'Specyfikacja w załączniku.', 'Re: Specyfikacja techniczna'),
    (false, false, '2021-09-08 16:45:00', '2021-09-08 16:45:00', 8, 7, '2021-09-08 16:45:00', 'Czy możemy przesunąć termin dostawy?', 'Przesunięcie terminu'),
    (false, true,  '2021-09-09 18:00:00', '2021-09-09 18:00:00', 7, 8, '2021-09-09 18:00:00', 'Tak, to nie będzie problemem.', 'Re: Przesunięcie terminu'),
    (false, false, '2021-09-10 19:15:00', '2021-09-10 19:15:00', 9, 8, '2021-09-10 19:15:00', 'Potrzebuję dodatkowych informacji o projekcie.', 'Dodatkowe informacje'),
    (false, false, '2021-09-11 08:30:00', '2021-09-11 08:30:00', 10, 9, '2021-09-11 08:30:00', 'Przesyłam dokumentację.', 'Re: Dodatkowe informacje'),
    (false, true,  '2021-09-12 09:45:00', '2021-09-12 09:45:00', 11, 10, '2021-09-12 09:45:00', 'Dziękuję za szybką odpowiedź.', 'Podziękowanie'),
    (false, false, '2021-09-13 11:00:00', '2021-09-13 11:00:00', 12, 11, '2021-09-13 11:00:00', 'Zapraszam na spotkanie zespołu w piątek.', 'Spotkanie zespołu'),
    (false, false, '2021-09-14 12:15:00', '2021-09-14 12:15:00', 13, 12, '2021-09-14 12:15:00', 'Potwierdzam swoją obecność.', 'Re: Spotkanie zespołu'),
    (false, false, '2021-09-15 13:30:00', '2021-09-15 13:30:00', 14, 13, '2021-09-15 13:30:00', 'Czy mógłbyś przejrzeć ten dokument?', 'Prośba o przegląd dokumentu');
INSERT INTO news (is_deleted, created_at, date_posted, posted_by, updated_at, content, title)
VALUES
    (false, '2021-10-01 08:00:00', '2021-10-01 08:00:00', 1, '2021-10-01 08:00:00', 'Z przyjemnością informujemy o otwarciu nowego oddziału w Gdańsku.', 'Otwarcie nowego oddziału'),
    (false, '2021-10-02 09:15:00', '2021-10-02 09:15:00', 2, '2021-10-02 09:15:00', 'Zapraszamy wszystkich pracowników na szkolenie z zakresu bezpieczeństwa.', 'Szkolenie BHP'),
    (false, '2021-10-03 10:30:00', '2021-10-03 10:30:00', 3, '2021-10-03 10:30:00', 'Wprowadzamy nowe procedury dotyczące pracy zdalnej.', 'Nowe procedury'),
    (false, '2021-10-04 11:45:00', '2021-10-04 11:45:00', 4, '2021-10-04 11:45:00', 'Nasza firma zdobyła nagrodę za najlepszą obsługę klienta.', 'Nagroda dla firmy'),
    (false, '2021-10-05 13:00:00', '2021-10-05 13:00:00', 5, '2021-10-05 13:00:00', 'Organizujemy konkurs na najlepszy projekt innowacyjny.', 'Konkurs dla pracowników'),
    (false, '2021-10-06 14:15:00', '2021-10-06 14:15:00', 6, '2021-10-06 14:15:00', 'Zmiany w grafiku pracy w okresie świątecznym.', 'Grafik świąteczny'),
    (false, '2021-10-07 15:30:00', '2021-10-07 15:30:00', 7, '2021-10-07 15:30:00', 'Wprowadzamy nowe benefity dla pracowników.', 'Nowe benefity'),
    (false, '2021-10-08 16:45:00', '2021-10-08 16:45:00', 8, '2021-10-08 16:45:00', 'Zapraszamy na firmowy turniej piłki nożnej.', 'Turniej piłkarski'),
    (false, '2021-10-09 18:00:00', '2021-10-09 18:00:00', 9, '2021-10-09 18:00:00', 'Przypominamy o konieczności aktualizacji haseł.', 'Bezpieczeństwo IT'),
    (false, '2021-10-10 19:15:00', '2021-10-10 19:15:00', 10, '2021-10-10 19:15:00', 'Nowy system zarządzania projektami zostanie wdrożony w przyszłym tygodniu.', 'Nowy system'),
    (false, '2021-10-11 08:30:00', '2021-10-11 08:30:00', 11, '2021-10-11 08:30:00', 'Spotkanie integracyjne odbędzie się w piątek.', 'Spotkanie integracyjne'),
    (false, '2021-10-12 09:45:00', '2021-10-12 09:45:00', 12, '2021-10-12 09:45:00', 'Wprowadzamy nową politykę dotyczącą pracy zdalnej.', 'Polityka pracy zdalnej'),
    (false, '2021-10-13 11:00:00', '2021-10-13 11:00:00', 13, '2021-10-13 11:00:00', 'Firma osiągnęła rekordowe wyniki finansowe w tym kwartale.', 'Wyniki finansowe'),
    (false, '2021-10-14 12:15:00', '2021-10-14 12:15:00', 14, '2021-10-14 12:15:00', 'Rozpoczynamy współpracę z nowym partnerem biznesowym.', 'Nowy partner'),
    (false, '2021-10-15 13:30:00', '2021-10-15 13:30:00', 15, '2021-10-15 13:30:00', 'Zapraszamy do udziału w ankiecie satysfakcji pracowników.', 'Ankieta satysfakcji');
INSERT INTO notifications (is_deleted, read_status, created_at, date_sent, updated_at, user_id, message)
VALUES
    (false, false, '2021-11-01 08:00:00', '2021-11-01 08:00:00', '2021-11-01 08:00:00', 1, 'Masz nowe zadanie do wykonania.'),
    (false, false, '2021-11-02 09:15:00', '2021-11-02 09:15:00', '2021-11-02 09:15:00', 2, 'Twoje hasło wygaśnie za 5 dni.'),
    (false, false, '2021-11-03 10:30:00', '2021-11-03 10:30:00', '2021-11-03 10:30:00', 3, 'Nowe szkolenie jest dostępne.'),
    (false, false, '2021-11-04 11:45:00', '2021-11-04 11:45:00', '2021-11-04 11:45:00', 4, 'Twój raport został zatwierdzony.'),
    (false, false, '2021-11-05 13:00:00', '2021-11-05 13:00:00', '2021-11-05 13:00:00', 5, 'Nowa polityka firmy została opublikowana.'),
    (false, false, '2021-11-06 14:15:00', '2021-11-06 14:15:00', '2021-11-06 14:15:00', 6, 'Przypomnienie o zbliżającym się spotkaniu.'),
    (false, false, '2021-11-07 15:30:00', '2021-11-07 15:30:00', '2021-11-07 15:30:00', 7, 'Zadanie zostało przydzielone do Ciebie.'),
    (false, false, '2021-11-08 16:45:00', '2021-11-08 16:45:00', '2021-11-08 16:45:00', 8, 'Zmiana w harmonogramie pracy.'),
    (false, false, '2021-11-09 18:00:00', '2021-11-09 18:00:00', '2021-11-09 18:00:00', 9, 'Nowa wiadomość w Twojej skrzynce odbiorczej.'),
    (false, false, '2021-11-10 19:15:00', '2021-11-10 19:15:00', '2021-11-10 19:15:00', 10, 'Prośba o zatwierdzenie dokumentu.'),
    (false, false, '2021-11-11 08:30:00', '2021-11-11 08:30:00', '2021-11-11 08:30:00', 11, 'Aktualizacja systemu planowana na jutro.'),
    (false, false, '2021-11-12 09:45:00', '2021-11-12 09:45:00', '2021-11-12 09:45:00', 12, 'Twoja prośba została odrzucona.'),
    (false, false, '2021-11-13 11:00:00', '2021-11-13 11:00:00', '2021-11-13 11:00:00', 13, 'Masz nowe powiadomienie systemowe.'),
    (false, false, '2021-11-14 12:15:00', '2021-11-14 12:15:00', '2021-11-14 12:15:00', 14, 'Przypomnienie o terminie wykonania zadania.'),
    (false, false, '2021-11-15 13:30:00', '2021-11-15 13:30:00', '2021-11-15 13:30:00', 15, 'Ankieta satysfakcji jest dostępna do wypełnienia.');
INSERT INTO vehicle_types (is_deleted, maximum_load, created_at, updated_at, name, dimensions, description, special_features)
VALUES
    (false, 20000.0, '2021-07-01 08:00:00', '2021-07-01 08:00:00', 'Ciągnik siodłowy', '6x2.5x3.5', 'Ciężarówka z naczepą', 'Naczepa typu firanka'),
    (false, 10000.0, '2021-07-02 09:15:00', '2021-07-02 09:15:00', 'Samochód dostawczy', '4x2x2.5', 'Dostawczak', 'Chłodnia'),
    (false, 15000.0, '2021-07-03 10:30:00', '2021-07-03 10:30:00', 'Wywrotka', '5x2.5x3', 'Pojazd do przewozu materiałów sypkich', 'Hydrauliczna wywrotka'),
    (false, 8000.0, '2021-07-04 11:45:00', '2021-07-04 11:45:00', 'Ciężarówka', '4x2x2.5', 'Standardowa ciężarówka', 'Plandeka'),
    (false, 12000.0, '2021-07-05 13:00:00', '2021-07-05 13:00:00', 'Cysterna', '5x2.5x3', 'Pojazd do przewozu płynów', 'Zbiornik ze stali nierdzewnej'),
    (false, 9000.0, '2021-07-06 14:15:00', '2021-07-06 14:15:00', 'Laweta', '4.5x2x2.5', 'Pojazd do przewozu samochodów', 'Najazdy hydrauliczne'),
    (false, 7000.0, '2021-07-07 15:30:00', '2021-07-07 15:30:00', 'Furgonetka', '3x1.8x2', 'Mały pojazd dostawczy', 'Izoterma'),
    (false, 11000.0, '2021-07-08 16:45:00', '2021-07-08 16:45:00', 'Chłodnia', '5x2.5x3', 'Pojazd z kontrolowaną temperaturą', 'Agregat chłodniczy'),
    (false, 13000.0, '2021-07-09 18:00:00', '2021-07-09 18:00:00', 'Kontenerowiec', '6x2.5x3', 'Pojazd do przewozu kontenerów', 'Uchwyty twist-lock'),
    (false, 6000.0, '2021-07-10 19:15:00', '2021-07-10 19:15:00', 'Bus', '3x2x2', 'Pojazd do przewozu osób', 'Klimatyzacja'),
    (false, 14000.0, '2021-07-11 08:30:00', '2021-07-11 08:30:00', 'Hakowiec', '5x2.5x3', 'Pojazd z systemem hakowym', 'System załadowczy'),
    (false, 16000.0, '2021-07-12 09:45:00', '2021-07-12 09:45:00', 'Platforma', '6x2.5x3.5', 'Pojazd z otwartą platformą', 'Dźwig HDS'),
    (false, 5000.0, '2021-07-13 11:00:00', '2021-07-13 11:00:00', 'Pickup', '2x1.8x1.5', 'Mały pojazd terenowy', 'Napęd 4x4'),
    (false, 1000.0, '2021-07-14 12:15:00', '2021-07-14 12:15:00', 'Motocykl', '2x0.8x1.2', 'Dwuślad', 'Kufry boczne'),
    (false, 18000.0, '2021-07-15 13:30:00', '2021-07-15 13:30:00', 'Naczepa chłodnicza', '6x2.5x3.5', 'Naczepa z kontrolowaną temperaturą', 'Podwójna izolacja');
INSERT INTO vehicles (capacity, is_deleted, mileage, year, created_at, last_service_date, next_service_due, purchase_date, updated_at, vehicle_type_id, registration_number, make, model, status)
VALUES
    (20000.0, false, 150000, 2015, '2021-07-01 08:00:00', '2021-06-01 08:00:00', '2021-12-01 08:00:00', '2015-01-15 08:00:00', '2021-07-01 08:00:00', 1, 'WX12345', 'Mercedes', 'Actros', 'AVAILABLE'),
    (10000.0, false, 80000, 2016, '2021-07-02 09:15:00', '2021-06-02 09:15:00', '2021-12-02 09:15:00', '2016-02-16 09:15:00', '2021-07-02 09:15:00', 2, 'WX12346', 'MAN', 'TGL', 'IN_SERVICE'),
    (15000.0, false, 120000, 2014, '2021-07-03 10:30:00', '2021-06-03 10:30:00', '2021-12-03 10:30:00', '2014-03-17 10:30:00', '2021-07-03 10:30:00', 3, 'WX12347', 'Volvo', 'FH16', 'MAINTENANCE'),
    (8000.0, false, 60000, 2017, '2021-07-04 11:45:00', '2021-06-04 11:45:00', '2021-12-04 11:45:00', '2017-04-18 11:45:00', '2021-07-04 11:45:00', 4, 'WX12348', 'DAF', 'LF', 'AVAILABLE'),
    (12000.0, false, 90000, 2015, '2021-07-05 13:00:00', '2021-06-05 13:00:00', '2021-12-05 13:00:00', '2015-05-19 13:00:00', '2021-07-05 13:00:00', 5, 'WX12349', 'Scania', 'R450', 'IN_SERVICE'),
    (9000.0, false, 50000, 2018, '2021-07-06 14:15:00', '2021-06-06 14:15:00', '2021-12-06 14:15:00', '2018-06-20 14:15:00', '2021-07-06 14:15:00', 6, 'WX12350', 'Iveco', 'Eurocargo', 'AVAILABLE'),
    (7000.0, false, 30000, 2019, '2021-07-07 15:30:00', '2021-06-07 15:30:00', '2021-12-07 15:30:00', '2019-07-21 15:30:00', '2021-07-07 15:30:00', 7, 'WX12351', 'Renault', 'Master', 'ASSIGNED'),
    (11000.0, false, 110000, 2016, '2021-07-08 16:45:00', '2021-06-08 16:45:00', '2021-12-08 16:45:00', '2016-08-22 16:45:00', '2021-07-08 16:45:00', 8, 'WX12352', 'Mercedes', 'Sprinter', 'IN_SERVICE'),
    (13000.0, false, 130000, 2014, '2021-07-09 18:00:00', '2021-06-09 18:00:00', '2021-12-09 18:00:00', '2014-09-23 18:00:00', '2021-07-09 18:00:00', 9, 'WX12353', 'Volvo', 'FMX', 'MAINTENANCE'),
    (6000.0, false, 25000, 2020, '2021-07-10 19:15:00', '2021-06-10 19:15:00', '2021-12-10 19:15:00', '2020-10-24 19:15:00', '2021-07-10 19:15:00', 10, 'WX12354', 'Ford', 'Transit', 'AVAILABLE'),
    (14000.0, false, 140000, 2013, '2021-07-11 08:30:00', '2021-06-11 08:30:00', '2021-12-11 08:30:00', '2013-11-25 08:30:00', '2021-07-11 08:30:00', 11, 'WX12355', 'MAN', 'TGM', 'IN_SERVICE'),
    (16000.0, false, 160000, 2012, '2021-07-12 09:45:00', '2021-06-12 09:45:00', '2021-12-12 09:45:00', '2012-12-26 09:45:00', '2021-07-12 09:45:00', 12, 'WX12356', 'DAF', 'XF', 'AVAILABLE'),
    (5000.0, false, 20000, 2020, '2021-07-13 11:00:00', '2021-06-13 11:00:00', '2021-12-13 11:00:00', '2020-01-27 11:00:00', '2021-07-13 11:00:00', 13, 'WX12357', 'Toyota', 'Hilux', 'ASSIGNED'),
    (1000.0, false, 5000, 2021, '2021-07-14 12:15:00', '2021-06-14 12:15:00', '2021-12-14 12:15:00', '2021-02-28 12:15:00', '2021-07-14 12:15:00', 14, 'WX12358', 'Honda', 'CB500', 'AVAILABLE'),
    (18000.0, false, 180000, 2011, '2021-07-15 13:30:00', '2021-06-15 13:30:00', '2021-12-15 13:30:00', '2011-03-01 13:30:00', '2021-07-15 13:30:00', 15, 'WX12359', 'Scania', 'R500', 'MAINTENANCE');
INSERT INTO driver_vehicles (is_deleted, assigned_date, created_at, driver_id, end_date, updated_at, vehicle_id, notes, status)
VALUES
    (false, '2021-08-01 08:00:00', '2021-08-01 08:00:00', 1, '2021-08-31 17:00:00', '2021-08-01 08:00:00', 1, 'Przydzielony do trasy A', 'NEW'),
    (false, '2021-08-02 09:15:00', '2021-08-02 09:15:00', 2, '2021-08-31 17:00:00', '2021-08-02 09:15:00', 2, 'Przydzielony do trasy B', 'NEW'),
    (false, '2021-08-03 10:30:00', '2021-08-03 10:30:00', 3, '2021-08-31 17:00:00', '2021-08-03 10:30:00', 3, 'Przydzielony do trasy C', 'NEW'),
    (false, '2021-08-04 11:45:00', '2021-08-04 11:45:00', 4, '2021-08-31 17:00:00', '2021-08-04 11:45:00', 4, 'Przydzielony do trasy D', 'NEW'),
    (false, '2021-08-05 13:00:00', '2021-08-05 13:00:00', 5, '2021-08-31 17:00:00', '2021-08-05 13:00:00', 5, 'Przydzielony do trasy E', 'NEW'),
    (false, '2021-08-06 14:15:00', '2021-08-06 14:15:00', 6, '2021-08-31 17:00:00', '2021-08-06 14:15:00', 6, 'Przydzielony do trasy F', 'NEW'),
    (false, '2021-08-07 15:30:00', '2021-08-07 15:30:00', 7, '2021-08-31 17:00:00', '2021-08-07 15:30:00', 7, 'Przydzielony do trasy G', 'NEW'),
    (false, '2021-08-08 16:45:00', '2021-08-08 16:45:00', 1, '2021-09-30 17:00:00', '2021-08-08 16:45:00', 8, 'Przydzielony do trasy H', 'NEW'),
    (false, '2021-08-09 18:00:00', '2021-08-09 18:00:00', 2, '2021-09-30 17:00:00', '2021-08-09 18:00:00', 9, 'Przydzielony do trasy I', 'NEW'),
    (false, '2021-08-10 19:15:00', '2021-08-10 19:15:00', 3, '2021-09-30 17:00:00', '2021-08-10 19:15:00', 10, 'Przydzielony do trasy J', 'NEW'),
    (false, '2021-08-11 08:30:00', '2021-08-11 08:30:00', 4, '2021-09-30 17:00:00', '2021-08-11 08:30:00', 11, 'Przydzielony do trasy K', 'NEW'),
    (false, '2021-08-12 09:45:00', '2021-08-12 09:45:00', 5, '2021-09-30 17:00:00', '2021-08-12 09:45:00', 12, 'Przydzielony do trasy L', 'NEW'),
    (false, '2021-08-13 11:00:00', '2021-08-13 11:00:00', 6, '2021-09-30 17:00:00', '2021-08-13 11:00:00', 13, 'Przydzielony do trasy M', 'NEW'),
    (false, '2021-08-14 12:15:00', '2021-08-14 12:15:00', 7, '2021-09-30 17:00:00', '2021-08-14 12:15:00', 14, 'Przydzielony do trasy N', 'NEW'),
    (false, '2021-08-15 13:30:00', '2021-08-15 13:30:00', 1, '2021-10-31 17:00:00', '2021-08-15 13:30:00', 15, 'Przydzielony do trasy O', 'NEW');
INSERT INTO incidents (is_deleted, created_at, date, driver_id, reported_by, updated_at, vehicle_id, incident_type, description, status)
VALUES
    (false, '2021-09-01 08:00:00', '2021-08-31 14:00:00', 1, 2, '2021-09-01 08:00:00', 1, 'Wypadek', 'Stłuczka z innym pojazdem na skrzyżowaniu.', 'NEW'),
    (false, '2021-09-02 09:15:00', '2021-09-01 15:30:00', 2, 3, '2021-09-02 09:15:00', 2, 'Awaria', 'Uszkodzenie układu hamulcowego podczas jazdy.', 'NEW'),
    (false, '2021-09-03 10:30:00', '2021-09-02 16:45:00', 3, 4, '2021-09-03 10:30:00', 3, 'Kradzież', 'Kradzież paliwa z baku pojazdu.', 'NEW'),
    (false, '2021-09-04 11:45:00', '2021-09-03 18:00:00', 4, 5, '2021-09-04 11:45:00', 4, 'Uszkodzenie', 'Pęknięcie opony podczas jazdy.', 'NEW'),
    (false, '2021-09-05 13:00:00', '2021-09-04 19:15:00', 5, 6, '2021-09-05 13:00:00', 5, 'Wypadek', 'Kolizja z pieszym na przejściu.', 'NEW'),
    (false, '2021-09-06 14:15:00', '2021-09-05 20:30:00', 6, 7, '2021-09-06 14:15:00', 6, 'Awaria', 'Problemy z silnikiem, pojazd nie odpala.', 'NEW'),
    (false, '2021-09-07 15:30:00', '2021-09-06 21:45:00', 7, 8, '2021-09-07 15:30:00', 7, 'Uszkodzenie', 'Zarysowanie karoserii podczas parkowania.', 'NEW'),
    (false, '2021-09-08 16:45:00', '2021-09-07 23:00:00', 1, 2, '2021-09-08 16:45:00', 8, 'Kradzież', 'Kradzież ładunku z naczepy.', 'NEW'),
    (false, '2021-09-09 18:00:00', '2021-09-08 08:15:00', 2, 3, '2021-09-09 18:00:00', 9, 'Awaria', 'Niedziałające światła tylne.', 'NEW'),
    (false, '2021-09-10 19:15:00', '2021-09-09 09:30:00', 3, 4, '2021-09-10 19:15:00', 10, 'Wypadek', 'Zderzenie z przeszkodą na drodze.', 'NEW'),
    (false, '2021-09-11 08:30:00', '2021-09-10 10:45:00', 4, 5, '2021-09-11 08:30:00', 11, 'Uszkodzenie', 'Pęknięcie szyby przedniej.', 'NEW'),
    (false, '2021-09-12 09:45:00', '2021-09-11 12:00:00', 5, 6, '2021-09-12 09:45:00', 12, 'Awaria', 'Problemy z układem elektrycznym.', 'NEW'),
    (false, '2021-09-13 11:00:00', '2021-09-12 13:15:00', 6, 7, '2021-09-13 11:00:00', 13, 'Kradzież', 'Zaginięcie narzędzi z kabiny.', 'NEW'),
    (false, '2021-09-14 12:15:00', '2021-09-13 14:30:00', 7, 8, '2021-09-14 12:15:00', 14, 'Wypadek', 'Kolizja z pojazdem na autostradzie.', 'NEW'),
    (false, '2021-09-15 13:30:00', '2021-09-14 15:45:00', 1, 2, '2021-09-15 13:30:00', 15, 'Uszkodzenie', 'Uszkodzenie lustra bocznego.', 'NEW');
INSERT INTO breakdowns (is_deleted, repair_cost, created_at, incident_id, repair_date, updated_at, description)
VALUES
    (false, 2000.00, '2021-09-02 08:00:00', 2, '2021-09-03 17:00:00', '2021-09-02 08:00:00', 'Wymiana układu hamulcowego.'),
    (false, 1500.50, '2021-09-03 09:15:00', 4, '2021-09-04 17:00:00', '2021-09-03 09:15:00', 'Naprawa pękniętej opony.'),
    (false, 2500.75, '2021-09-04 10:30:00', 6, '2021-09-05 17:00:00', '2021-09-04 10:30:00', 'Naprawa silnika.'),
    (false, 1000.00, '2021-09-05 11:45:00', 9, '2021-09-06 17:00:00', '2021-09-05 11:45:00', 'Naprawa świateł tylnych.'),
    (false, 1800.25, '2021-09-06 13:00:00', 12, '2021-09-07 17:00:00', '2021-09-06 13:00:00', 'Naprawa układu elektrycznego.'),
    (false, 2200.00, '2021-09-07 14:15:00', 10, '2021-09-08 17:00:00', '2021-09-07 14:15:00', 'Naprawa uszkodzeń po zderzeniu.'),
    (false, 900.50, '2021-09-08 15:30:00', 11, '2021-09-09 17:00:00', '2021-09-08 15:30:00', 'Wymiana szyby przedniej.'),
    (false, 750.75, '2021-09-09 16:45:00', 15, '2021-09-10 17:00:00', '2021-09-09 16:45:00', 'Naprawa lustra bocznego.'),
    (false, 3000.00, '2021-09-10 18:00:00', 14, '2021-09-11 17:00:00', '2021-09-10 18:00:00', 'Naprawa poważnych uszkodzeń po kolizji.'),
    (false, 1600.00, '2021-09-12 08:30:00', 3, '2021-09-13 17:00:00', '2021-09-12 08:30:00', 'Wymiana zamków po kradzieży.'),
    (false, 1100.50, '2021-09-13 09:45:00', 5, '2021-09-14 17:00:00', '2021-09-13 09:45:00', 'Naprawa uszkodzeń po kolizji z pieszym.'),
    (false, 800.75, '2021-09-14 11:00:00', 7, '2021-09-15 17:00:00', '2021-09-14 11:00:00', 'Lakierowanie zarysowanej karoserii.'),
    (false, 1400.00, '2021-09-15 12:15:00', 8, '2021-09-16 17:00:00', '2021-09-15 12:15:00', 'Naprawa drzwi po włamaniu.'),
    (false, 2000.25, '2021-09-16 13:30:00', 13, '2021-09-17 17:00:00', '2021-09-16 13:30:00', 'Wymiana ukradzionych narzędzi.');
INSERT INTO damages (insurance_claim, is_deleted, repair_cost, created_at, damage_id, incident_id, updated_at, claim_number, description)
VALUES
    (true, false, 7500.50, '2021-10-02 09:15:00', DEFAULT, 2, '2021-10-02 09:15:00', 'CLM-1002', 'Uszkodzenie silnika, naprawa pokryta z ubezpieczenia.'),
    (false, false, 2500.75, '2021-10-03 10:30:00', DEFAULT, 3, '2021-10-03 10:30:00', NULL, 'Wymiana zbitej szyby przedniej.'),
    (true, false, 4000.00, '2021-10-04 11:45:00', DEFAULT, 4, '2021-10-04 11:45:00', 'CLM-1003', 'Naprawa po kradzieży części z pojazdu.'),
    (false, false, 3000.25, '2021-10-05 13:00:00', DEFAULT, 5, '2021-10-05 13:00:00', NULL, 'Uszkodzenie zawieszenia na nierównej drodze.'),
    (true, false, 6000.00, '2021-10-06 14:15:00', DEFAULT, 6, '2021-10-06 14:15:00', 'CLM-1004', 'Szkoda całkowita, pojazd skasowany.'),
    (false, false, 1500.50, '2021-10-07 15:30:00', DEFAULT, 7, '2021-10-07 15:30:00', NULL, 'Naprawa drobnych wgnieceń po gradobiciu.'),
    (true, false, 3500.75, '2021-10-08 16:45:00', DEFAULT, 8, '2021-10-08 16:45:00', 'CLM-1005', 'Uszkodzenie spowodowane zalaniem silnika.'),
    (false, false, 2000.00, '2021-10-09 18:00:00', DEFAULT, 9, '2021-10-09 18:00:00', NULL, 'Wymiana uszkodzonego układu wydechowego.'),
    (true, false, 4500.25, '2021-10-10 19:15:00', DEFAULT, 10, '2021-10-10 19:15:00', 'CLM-1006', 'Naprawa po poważnym wypadku drogowym.'),
    (false, false, 1000.00, '2021-10-11 08:30:00', DEFAULT, 11, '2021-10-11 08:30:00', NULL, 'Uszkodzenie lusterka bocznego.'),
    (true, false, 5500.50, '2021-10-12 09:45:00', DEFAULT, 12, '2021-10-12 09:45:00', 'CLM-1007', 'Kradzież pojazdu, odszkodowanie z ubezpieczenia.'),
    (false, false, 1800.75, '2021-10-13 11:00:00', DEFAULT, 13, '2021-10-13 11:00:00', NULL, 'Uszkodzenie opon na skutek złej nawierzchni.'),
    (true, false, 3200.00, '2021-10-14 12:15:00', DEFAULT, 14, '2021-10-14 12:15:00', 'CLM-1008', 'Naprawa po kolizji z udziałem zwierzęcia.'),
    (false, false, 2200.25, '2021-10-15 13:30:00', DEFAULT, 15, '2021-10-15 13:30:00', NULL, 'Wymiana uszkodzonego reflektora.');
INSERT INTO service_schedules (is_deleted, created_at, schedule_id, scheduled_date, updated_at, vehicle_id, service_type, status)
VALUES
    (false, '2021-11-01 08:00:00', DEFAULT, '2021-11-10 08:00:00', '2021-11-01 08:00:00', 1, 'Przegląd okresowy', 'NEW'),
    (false, '2021-11-02 09:15:00', DEFAULT, '2021-11-11 09:15:00', '2021-11-02 09:15:00', 2, 'Wymiana oleju', 'NEW'),
    (false, '2021-11-03 10:30:00', DEFAULT, '2021-11-12 10:30:00', '2021-11-03 10:30:00', 3, 'Kontrola hamulców', 'NEW'),
    (false, '2021-11-04 11:45:00', DEFAULT, '2021-11-13 11:45:00', '2021-11-04 11:45:00', 4, 'Wymiana filtrów', 'NEW'),
    (false, '2021-11-05 13:00:00', DEFAULT, '2021-11-14 13:00:00', '2021-11-05 13:00:00', 5, 'Diagnostyka silnika', 'NEW'),
    (false, '2021-11-06 14:15:00', DEFAULT, '2021-11-15 14:15:00', '2021-11-06 14:15:00', 6, 'Sprawdzenie zawieszenia', 'NEW'),
    (false, '2021-11-07 15:30:00', DEFAULT, '2021-11-16 15:30:00', '2021-11-07 15:30:00', 7, 'Regulacja układu kierowniczego', 'NEW'),
    (false, '2021-11-08 16:45:00', DEFAULT, '2021-11-17 16:45:00', '2021-11-08 16:45:00', 8, 'Przegląd techniczny', 'NEW'),
    (false, '2021-11-09 18:00:00', DEFAULT, '2021-11-18 18:00:00', '2021-11-09 18:00:00', 9, 'Wymiana klocków hamulcowych', 'NEW'),
    (false, '2021-11-10 19:15:00', DEFAULT, '2021-11-19 19:15:00', '2021-11-10 19:15:00', 10, 'Kontrola układu elektrycznego', 'NEW'),
    (false, '2021-11-11 08:30:00', DEFAULT, '2021-11-20 08:30:00', '2021-11-11 08:30:00', 11, 'Wymiana płynu chłodniczego', 'NEW'),
    (false, '2021-11-12 09:45:00', DEFAULT, '2021-11-21 09:45:00', '2021-11-12 09:45:00', 12, 'Sprawdzenie układu wydechowego', 'NEW'),
    (false, '2021-11-13 11:00:00', DEFAULT, '2021-11-22 11:00:00', '2021-11-13 11:00:00', 13, 'Kalibracja tachografu', 'NEW'),
    (false, '2021-11-14 12:15:00', DEFAULT, '2021-11-23 12:15:00', '2021-11-14 12:15:00', 14, 'Wymiana opon', 'NEW'),
    (false, '2021-11-15 13:30:00', DEFAULT, '2021-11-24 13:30:00', '2021-11-15 13:30:00', 15, 'Kontrola klimatyzacji', 'NEW');
INSERT INTO technical_inspections (is_deleted, created_at, inspection_date, next_inspection_date, updated_at, vehicle_id, inspector_name, comments, result)
VALUES
    (false, '2021-12-01 08:00:00', '2021-12-01 08:00:00', '2022-12-01 08:00:00', '2021-12-01 08:00:00', 1, 'Jan Nowak', 'Brak uwag, pojazd w dobrym stanie.', 'PASSED'),
    (false, '2021-12-02 09:15:00', '2021-12-02 09:15:00', '2022-12-02 09:15:00', '2021-12-02 09:15:00', 2, 'Anna Kowalska', 'Zalecana wymiana klocków hamulcowych.', 'PASSED'),
    (false, '2021-12-03 10:30:00', '2021-12-03 10:30:00', '2022-12-03 10:30:00', '2021-12-03 10:30:00', 3, 'Piotr Wiśniewski', 'Wykryto drobne wycieki oleju.', 'PASSED'),
    (false, '2021-12-04 11:45:00', '2021-12-04 11:45:00', '2022-12-04 11:45:00', '2021-12-04 11:45:00', 4, 'Katarzyna Wójcik', 'Uszkodzone światło tylne.', 'FAILED'),
    (false, '2021-12-05 13:00:00', '2021-12-05 13:00:00', '2022-12-05 13:00:00', '2021-12-05 13:00:00', 5, 'Michał Kowalczyk', 'Pojazd w idealnym stanie.', 'PASSED'),
    (false, '2021-12-06 14:15:00', '2021-12-06 14:15:00', '2022-12-06 14:15:00', '2021-12-06 14:15:00', 6, 'Agnieszka Kamińska', 'Konieczna regulacja układu kierowniczego.', 'PASSED'),
    (false, '2021-12-07 15:30:00', '2021-12-07 15:30:00', '2022-12-07 15:30:00', '2021-12-07 15:30:00', 7, 'Tomasz Lewandowski', 'Zużyte opony, wymiana niezbędna.', 'FAILED'),
    (false, '2021-12-08 16:45:00', '2021-12-08 16:45:00', '2022-12-08 16:45:00', '2021-12-08 16:45:00', 8, 'Monika Zielińska', 'Brak uwag, wszystko sprawne.', 'PASSED'),
    (false, '2021-12-09 18:00:00', '2021-12-09 18:00:00', '2022-12-09 18:00:00', '2021-12-09 18:00:00', 9, 'Andrzej Szymański', 'Drobne rysy na karoserii.', 'PASSED'),
    (false, '2021-12-10 19:15:00', '2021-12-10 19:15:00', '2022-12-10 19:15:00', '2021-12-10 19:15:00', 10, 'Magdalena Woźniak', 'Problemy z układem wydechowym.', 'FAILED'),
    (false, '2021-12-11 08:30:00', '2021-12-11 08:30:00', '2022-12-11 08:30:00', '2021-12-11 08:30:00', 11, 'Krzysztof Dąbrowski', 'Konieczna wymiana płynu hamulcowego.', 'PASSED'),
    (false, '2021-12-12 09:45:00', '2021-12-12 09:45:00', '2022-12-12 09:45:00', '2021-12-12 09:45:00', 12, 'Sylwia Pawłowska', 'Nieprawidłowe działanie klimatyzacji.', 'PASSED'),
    (false, '2021-12-13 11:00:00', '2021-12-13 11:00:00', '2022-12-13 11:00:00', '2021-12-13 11:00:00', 13, 'Adam Michalski', 'Brak uwag, pojazd sprawny.', 'PASSED'),
    (false, '2021-12-14 12:15:00', '2021-12-14 12:15:00', '2022-12-14 12:15:00', '2021-12-14 12:15:00', 14, 'Ewa Wróbel', 'Uszkodzone lusterko boczne.', 'FAILED'),
    (false, '2021-12-15 13:30:00', '2021-12-15 13:30:00', '2022-12-15 13:30:00', '2021-12-15 13:30:00', 15, 'Paweł Jankowski', 'Wszystkie systemy działają prawidłowo.', 'PASSED');