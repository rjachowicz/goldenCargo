-- Insert into clients
INSERT INTO public.clients (is_deleted, created_at, updated_at, nip, phone, contact_person, email, name, address)
VALUES (false, NOW(), NOW(), '1234567890', '123-456-7890', 'John Doe', 'client1@example.com', 'Client One',
        '123 Main St, City A'),
       (false, NOW(), NOW(), '0987654321', '098-765-4321', 'Jane Smith', 'client2@example.com', 'Client Two',
        '456 Elm St, City B');

-- Insert into client_orders
INSERT INTO public.client_orders (is_deleted, total_amount, client_id, created_at, order_date, updated_at,
                                  payment_status, status)
VALUES (false, 1000.00, 1, NOW(), NOW(), NOW(), 'NEW', 'NEW'),
       (false, 2000.00, 2, NOW(), NOW(), NOW(), 'PAID', 'CONFIRMED');

-- Insert into client_invoices
INSERT INTO public.client_invoices (is_deleted, total_amount, client_id, client_order_id, created_at, date_issued,
                                    due_date, updated_at, invoice_number, payment_status)
VALUES (false, 1000.00, 1, 1, NOW(), NOW(), NOW() + INTERVAL '30 days', NOW(), 'INV-0001', 'UNPAID'),
       (false, 2000.00, 2, 2, NOW(), NOW(), NOW() + INTERVAL '30 days', NOW(), 'INV-0002', 'PAID');

-- Insert into goods
INSERT INTO public.goods (is_deleted, weight, client_order_id, created_at, updated_at, dimensions, name, description,
                          special_handling_instructions)
VALUES (false, 500.0, 1, NOW(), NOW(), '2x2x2', 'Goods One', 'Description for goods one', 'Handle with care'),
       (false, 750.0, 2, NOW(), NOW(), '3x3x3', 'Goods Two', 'Description for goods two', 'Keep upright');

-- Insert into locations
INSERT INTO public.locations (is_deleted, latitude, longitude, created_at, updated_at, postal_code, city, country,
                              state, name, address)
VALUES (false, 40.7128, -74.0060, NOW(), NOW(), '10001', 'New York', 'USA', 'NY', 'Location One',
        '789 Broadway, New York, NY'),
       (false, 34.0522, -118.2437, NOW(), NOW(), '90001', 'Los Angeles', 'USA', 'CA', 'Location Two',
        '123 Hollywood Blvd, Los Angeles, CA');

-- Insert into routes
INSERT INTO public.routes (distance, estimated_time, is_deleted, created_at, end_location_id, start_location_id,
                           updated_at, preferred_route)
VALUES (4500.0, 48.0, false, NOW(), 2, 1, NOW(), 'Interstate 80'),
       (4500.0, 48.0, false, NOW(), 1, 2, NOW(), 'Interstate 40');

-- Insert into vehicle_types
INSERT INTO public.vehicle_types (is_deleted, maximum_load, created_at, updated_at, name, dimensions, description,
                                  special_features)
VALUES (false, 10000.0, NOW(), NOW(), 'Truck Type A', '10x2x3', 'Standard truck', 'GPS'),
       (false, 20000.0, NOW(), NOW(), 'Truck Type B', '12x3x4', 'Heavy duty truck', 'Refrigeration unit');

-- Insert into vehicles
INSERT INTO public.vehicles (capacity, is_deleted, mileage, year, created_at, last_service_date, next_service_due,
                             purchase_date, updated_at, vehicle_type_id, registration_number, make, model, status)
VALUES (10000.0, false, 50000, 2018, NOW(), NOW() - INTERVAL '6 months', NOW() + INTERVAL '6 months',
        NOW() - INTERVAL '2 years', NOW(), 1, 'REG123', 'MakeA', 'ModelA', 'AVAILABLE'),
       (20000.0, false, 100000, 2016, NOW(), NOW() - INTERVAL '1 year', NOW() + INTERVAL '1 year',
        NOW() - INTERVAL '4 years', NOW(), 2, 'REG456', 'MakeB', 'ModelB', 'MAINTENANCE');

-- Insert into vehicle_services
INSERT INTO public.vehicle_services (cost, is_deleted, created_at, next_service_due, service_date, updated_at,
                                     vehicle_id, service_type, service_center, description)
VALUES (500.00, false, NOW(), NOW() + INTERVAL '1 year', NOW(), NOW(), 1, 'Oil Change', 'Service Center A',
        'Regular maintenance'),
       (1000.00, false, NOW(), NOW() + INTERVAL '6 months', NOW(), NOW(), 2, 'Brake Replacement', 'Service Center B',
        'Replaced brake pads');

-- Insert into technical_inspections
INSERT INTO public.technical_inspections (is_deleted, created_at, inspection_date, next_inspection_date, updated_at,
                                          vehicle_id, inspector_name, comments, result)
VALUES (false, NOW(), NOW(), NOW() + INTERVAL '1 year', NOW(), 1, 'Inspector A', 'All good', 'PASSED'),
       (false, NOW(), NOW(), NOW() + INTERVAL '6 months', NOW(), 2, 'Inspector B', 'Requires attention', 'FAILED');

-- Insert into service_schedules
INSERT INTO public.service_schedules (is_deleted, created_at, scheduled_date, updated_at, vehicle_id, service_type,
                                      status)
VALUES (false, NOW(), NOW() + INTERVAL '1 month', NOW(), 1, 'Tire Rotation', 'CONFIRMED'),
       (false, NOW(), NOW() + INTERVAL '2 months', NOW(), 2, 'Engine Check', 'PENDING');

-- Insert into users
INSERT INTO public.users (is_deleted, created_at, updated_at, phone_number, first_name, last_name, username, email,
                          address, password, status)
VALUES (false, NOW(), NOW(), '555-1234', 'Alice', 'Anderson', 'alice', 'alice@example.com', '123 Main St', 'password1',
        'ACTIVE'),
       (false, NOW(), NOW(), '555-5678', 'Bob', 'Brown', 'bob', 'bob@example.com', '456 Elm St', 'password2', 'ACTIVE'),
       (false, NOW(), NOW(), '555-8765', 'Charlie', 'Clark', 'charlie', 'charlie@example.com', '789 Oak St',
        'password3', 'ACTIVE'),
       (false, NOW(), NOW(), '555-4321', 'Diana', 'Davis', 'diana', 'diana@example.com', '321 Pine St', 'password4',
        'ACTIVE');

-- Insert into roles
INSERT INTO public.roles (is_deleted, created_at, updated_at, name, description)
VALUES (false, NOW(), NOW(), 'Driver', 'Driver role'),
       (false, NOW(), NOW(), 'Logistician', 'Logistician role');

-- Insert into user_roles
INSERT INTO public.user_roles (assigned_at, role_id, user_id)
VALUES (NOW(), 1, 1), -- Alice as Driver
       (NOW(), 1, 3), -- Charlie as Driver
       (NOW(), 2, 2), -- Bob as Logistician
       (NOW(), 2, 4);
-- Diana as Logistician

-- Insert into drivers
INSERT INTO public.drivers (is_deleted, created_at, date_of_birth, hire_date, medical_certificate_expiry, updated_at,
                            user_id, license_category, license_number, driver_status)
VALUES (false, NOW(), '1985-01-01', NOW() - INTERVAL '5 years', NOW() + INTERVAL '1 year', NOW(), 1, 'B', 'LIC123',
        'ACTIVE'),
       (false, NOW(), '1990-02-02', NOW() - INTERVAL '3 years', NOW() + INTERVAL '2 years', NOW(), 3, 'C', 'LIC456',
        'ACTIVE');

-- Insert into logisticians
INSERT INTO public.logisticians (is_deleted, created_at, updated_at, user_id, department)
VALUES (false, NOW(), NOW(), 2, 'Logistics Department A'),
       (false, NOW(), NOW(), 4, 'Logistics Department B');

-- Insert into driver_reports
INSERT INTO public.driver_reports (is_deleted, created_at, date, driver_id, updated_at, content)
VALUES (false, NOW(), NOW(), 1, NOW(), 'Report from driver Alice'),
       (false, NOW(), NOW(), 2, NOW(), 'Report from driver Charlie');

-- Insert into messages
INSERT INTO public.messages (is_deleted, read_status, created_at, date_sent, sender_id, receiver_id, updated_at,
                             content, subject)
VALUES (false, false, NOW(), NOW(), 1, 2, NOW(), 'Hello Bob', 'Greetings'),
       (false, false, NOW(), NOW(), 3, 4, NOW(), 'Hi Diana', 'Update');

-- Insert into news
INSERT INTO public.news (is_deleted, created_at, date_posted, posted_by, updated_at, content, title)
VALUES (false, NOW(), NOW(), 2, NOW(), 'Company News Content', 'Company News'),
       (false, NOW(), NOW(), 4, NOW(), 'Logistics Update', 'Logistics News');

-- Insert into notifications
INSERT INTO public.notifications (is_deleted, read_status, created_at, date_sent, updated_at, user_id, message)
VALUES (false, false, NOW(), NOW(), NOW(), 1, 'Notification for Alice'),
       (false, false, NOW(), NOW(), NOW(), 3, 'Notification for Charlie');

-- Insert into reports
INSERT INTO public.reports (is_deleted, created_at, date_generated, generated_by, updated_at, report_type, content)
VALUES (false, NOW(), NOW(), 2, NOW(), 'Monthly', 'Monthly Report Content'),
       (false, NOW(), NOW(), 4, NOW(), 'Annual', 'Annual Report Content');

-- Corrected Insert into driver_vehicles
INSERT INTO public.driver_vehicles (is_deleted, assigned_date, created_at, driver_id, end_date, updated_at, vehicle_id,
                                    notes, status)
VALUES (false, NOW(), NOW(), 1, NOW() + INTERVAL '1 month', NOW(), 1, 'Assigned for route A', 'NEW'),
       (false, NOW(), NOW(), 2, NOW() + INTERVAL '2 months', NOW(), 2, 'Assigned for route B', 'NEW');

-- Insert into incidents
INSERT INTO public.incidents (is_deleted, created_at, date, driver_id, reported_by, updated_at, vehicle_id,
                              incident_type, description, status)
VALUES (false, NOW(), NOW(), 1, 2, NOW(), 1, 'Minor Accident', 'Scratches on the side', 'PENDING'),
       (false, NOW(), NOW(), 2, 4, NOW(), 2, 'Mechanical Failure', 'Engine malfunction', 'IN_PROGRESS');

-- Insert into breakdowns
INSERT INTO public.breakdowns (is_deleted, repair_cost, created_at, incident_id, repair_date, updated_at, description)
VALUES (false, 500.00, NOW(), 1, NOW() + INTERVAL '1 day', NOW(), 'Repaired scratches'),
       (false, 1500.00, NOW(), 2, NOW() + INTERVAL '3 days', NOW(), 'Engine repaired');

-- Insert into damages
INSERT INTO public.damages (insurance_claim, is_deleted, repair_cost, created_at, incident_id, updated_at, claim_number,
                            description)
VALUES (true, false, 500.00, NOW(), 1, NOW(), 'CLM123', 'Damage to vehicle side'),
       (false, false, 1000.00, NOW(), 2, NOW(), NULL, 'Engine damage');

-- Insert into transport_orders
INSERT INTO public.transport_orders (is_deleted, assigned_driver_id, assigned_vehicle_id, client_order_id, created_at,
                                     end_location_id, scheduled_arrival, scheduled_departure, start_location_id,
                                     updated_at, status)
VALUES (false, 1, 1, 1, NOW(), 2, NOW() + INTERVAL '1 day', NOW(), 1, NOW(), 'CONFIRMED'),
       (false, 2, 2, 2, NOW(), 1, NOW() + INTERVAL '2 days', NOW(), 2, NOW(), 'PENDING');

-- Insert into orders
INSERT INTO public.orders (is_deleted, client_order_id, created_at, transport_order_id, updated_at, order_type, status)
VALUES (false, 1, NOW(), 1, NOW(), 'CLIENT', 'CONFIRMED'),
       (false, 2, NOW(), 2, NOW(), 'CLIENT', 'IN_PROGRESS');

-- Insert into transports
INSERT INTO public.transports (is_deleted, actual_arrival, actual_departure, created_at, transport_order_id, updated_at,
                               notes, status)
VALUES (false, NOW() + INTERVAL '1 day', NOW(), NOW(), 1, NOW(), 'On schedule', 'IN_PROGRESS'),
       (false, NULL, NOW(), NOW(), 2, NOW(), 'Delayed', 'PENDING');

-- Insert into shipping_documents
INSERT INTO public.shipping_documents (is_deleted, created_at, issue_date, transport_id, updated_at, document_number,
                                       document_type, content)
VALUES (false, NOW(), NOW(), 1, NOW(), 'DOC123', 'Bill of Lading', NULL),
       (false, NOW(), NOW(), 2, NOW(), 'DOC456', 'Shipping Manifest', NULL);

-- Insert into invoices
INSERT INTO public.invoices (is_deleted, total_amount, created_at, date_issued, due_date, updated_at, related_id,
                             invoice_number, invoice_type, payment_status)
VALUES (false, 1000.00, NOW(), NOW(), NOW() + INTERVAL '30 days', NOW(), 1, 'INV-1001', 'CLIENT', 'UNPAID'),
       (false, 2000.00, NOW(), NOW(), NOW() + INTERVAL '30 days', NOW(), 2, 'INV-1002', 'SUPPLIER', 'PAID');

-- Insert into vehicle_repairs
INSERT INTO public.vehicle_repairs (cost, is_deleted, created_at, next_service_due, service_date, updated_at,
                                    vehicle_id, service_type, service_center, description)
VALUES (1200.00, false, NOW(), NOW() + INTERVAL '1 year', NOW(), NOW(), 1, 'Transmission Repair', 'Repair Shop A',
        'Repaired transmission'),
       (800.00, false, NOW(), NOW() + INTERVAL '6 months', NOW(), NOW(), 2, 'Brake System Repair', 'Repair Shop B',
        'Repaired brake system');
