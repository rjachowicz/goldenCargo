create table vehicle_services
(
    cost             double precision,
    is_deleted       boolean      not null,
    created_at       timestamp(6) not null,
    next_service_due timestamp(6),
    service_date     timestamp(6),
    service_id       bigint generated by default as identity
        primary key,
    updated_at       timestamp(6) not null,
    vehicle_id       bigint       not null,
    service_type     varchar(50),
    service_center   varchar(100),
    description      varchar(255)
);

alter table vehicle_services
    owner to postgres;

create table clients
(
    is_deleted     boolean      not null,
    client_id      bigint generated by default as identity
        primary key,
    created_at     timestamp(6) not null,
    updated_at     timestamp(6) not null,
    nip            varchar(20)  not null,
    phone          varchar(20),
    contact_person varchar(100),
    email          varchar(100) not null,
    name           varchar(100) not null,
    address        varchar(255) not null
);

alter table clients
    owner to postgres;

create table client_orders
(
    is_deleted      boolean      not null,
    total_amount    double precision,
    client_id       bigint
        constraint fksvtx3xcntbrypx5rib5qq0ed6
            references clients,
    client_order_id bigint generated by default as identity
        primary key,
    created_at      timestamp(6) not null,
    order_date      timestamp(6),
    updated_at      timestamp(6) not null,
    payment_status  varchar(255) not null
        constraint client_orders_payment_status_check
            check ((payment_status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'PAID'::character varying, 'UNPAID'::character varying, 'PARTIAL'::character varying])::text[])),
    status          varchar(255) not null
        constraint client_orders_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'PENDING'::character varying, 'CONFIRMED'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying, 'CLOSED'::character varying])::text[]))
);

alter table client_orders
    owner to postgres;

create table client_invoices
(
    is_deleted      boolean          not null,
    total_amount    double precision not null,
    client_id       bigint           not null
        constraint fkfaki7doww8rveci8277p6o72d
            references clients,
    client_order_id bigint           not null
        constraint fk586j36w7ydi23hn3o2fugjjsu
            references client_orders,
    created_at      timestamp(6)     not null,
    date_issued     timestamp(6)     not null,
    due_date        timestamp(6)     not null,
    invoice_id      bigint generated by default as identity
        primary key,
    updated_at      timestamp(6)     not null,
    invoice_number  varchar(50)      not null
        unique,
    payment_status  varchar(255)     not null
        constraint client_invoices_payment_status_check
            check ((payment_status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'PAID'::character varying, 'UNPAID'::character varying, 'PARTIAL'::character varying])::text[]))
);

alter table client_invoices
    owner to postgres;

create table goods
(
    is_deleted                    boolean          not null,
    weight                        double precision not null,
    client_order_id               bigint           not null
        constraint fk15cvsrmrdaff31tl6vhnn00tx
            references client_orders,
    created_at                    timestamp(6)     not null,
    goods_id                      bigint generated by default as identity
        primary key,
    updated_at                    timestamp(6)     not null,
    dimensions                    varchar(100),
    name                          varchar(100)     not null,
    description                   varchar(255),
    special_handling_instructions varchar(255)     not null
);

alter table goods
    owner to postgres;

create table invoices
(
    is_deleted     boolean          not null,
    total_amount   double precision not null,
    created_at     timestamp(6)     not null,
    date_issued    timestamp(6)     not null,
    due_date       timestamp(6)     not null,
    invoice_id     bigint generated by default as identity
        primary key,
    related_id     bigint,
    updated_at     timestamp(6)     not null,
    invoice_number varchar(50)      not null
        unique,
    invoice_type   varchar(255)     not null
        constraint invoices_invoice_type_check
            check ((invoice_type)::text = ANY
                   ((ARRAY ['CLIENT'::character varying, 'SUBCONTRACTOR'::character varying, 'SUPPLIER'::character varying, 'OTHER'::character varying])::text[])),
    payment_status varchar(255)     not null
        constraint invoices_payment_status_check
            check ((payment_status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'PAID'::character varying, 'UNPAID'::character varying, 'PARTIAL'::character varying])::text[]))
);

alter table invoices
    owner to postgres;

create table locations
(
    is_deleted  boolean      not null,
    latitude    double precision,
    longitude   double precision,
    created_at  timestamp(6) not null,
    location_id bigint generated by default as identity
        primary key,
    updated_at  timestamp(6) not null,
    postal_code varchar(20),
    city        varchar(50),
    country     varchar(50),
    state       varchar(50),
    name        varchar(100),
    address     varchar(255)
);

alter table locations
    owner to postgres;

create table roles
(
    is_deleted  boolean      not null,
    created_at  timestamp(6) not null,
    role_id     bigint generated by default as identity
        primary key,
    updated_at  timestamp(6) not null,
    name        varchar(50)  not null
        unique,
    description varchar(255)
);

alter table roles
    owner to postgres;

create table routes
(
    distance          double precision,
    estimated_time    double precision,
    is_deleted        boolean      not null,
    created_at        timestamp(6) not null,
    end_location_id   bigint       not null
        constraint fktqxldvq6vfex3mna2wxpuw9an
            references locations,
    route_id          bigint generated by default as identity
        primary key,
    start_location_id bigint       not null
        constraint fkja5uqif3mnj3ff3idvepoifar
            references locations,
    updated_at        timestamp(6) not null,
    preferred_route   varchar(255)
);

alter table routes
    owner to postgres;

create table users
(
    is_deleted   boolean      not null,
    created_at   timestamp(6) not null,
    updated_at   timestamp(6) not null,
    user_id      bigint generated by default as identity
        primary key,
    phone_number varchar(20),
    first_name   varchar(50),
    last_name    varchar(50),
    username     varchar(50)  not null
        unique,
    email        varchar(100) not null
        unique,
    address      varchar(255),
    password     varchar(255) not null,
    status       varchar(255) not null
        constraint users_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'ACTIVE'::character varying, 'INACTIVE'::character varying, 'BANED'::character varying])::text[]))
);

alter table users
    owner to postgres;

create table drivers
(
    is_deleted                 boolean      not null,
    created_at                 timestamp(6) not null,
    date_of_birth              timestamp(6),
    driver_id                  bigint generated by default as identity
        primary key,
    hire_date                  timestamp(6) not null,
    medical_certificate_expiry timestamp(6) not null,
    updated_at                 timestamp(6) not null,
    user_id                    bigint
        unique
        constraint fkfscpnjt46gco44xh86l99rxh7
            references users,
    license_category           varchar(10),
    license_number             varchar(50)  not null
        unique,
    driver_status              varchar(255) not null
        constraint drivers_driver_status_check
            check ((driver_status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'ACTIVE'::character varying, 'INACTIVE'::character varying])::text[]))
);

alter table drivers
    owner to postgres;

create table driver_reports
(
    is_deleted       boolean      not null,
    created_at       timestamp(6) not null,
    date             timestamp(6) not null,
    driver_id        bigint       not null
        constraint fk8h9orxv55m1wvo5si3o8976h8
            references drivers,
    driver_report_id bigint generated by default as identity
        primary key,
    updated_at       timestamp(6) not null,
    content          varchar(255)
);

alter table driver_reports
    owner to postgres;

create table logisticians
(
    is_deleted   boolean      not null,
    created_at   timestamp(6) not null,
    logistics_id bigint generated by default as identity
        primary key,
    updated_at   timestamp(6) not null,
    user_id      bigint       not null
        unique
        constraint fk18ep9u36sfxsotw3uyh6knpmd
            references users,
    department   varchar(50)
);

alter table logisticians
    owner to postgres;

create table messages
(
    is_deleted  boolean      not null,
    read_status boolean      not null,
    created_at  timestamp(6) not null,
    date_sent   timestamp(6) not null,
    message_id  bigint generated by default as identity
        primary key,
    receiver_id bigint       not null
        constraint fkt05r0b6n0iis8u7dfna4xdh73
            references users,
    sender_id   bigint       not null
        constraint fk4ui4nnwntodh6wjvck53dbk9m
            references users,
    updated_at  timestamp(6) not null,
    content     varchar(255) not null,
    subject     varchar(255) not null
);

alter table messages
    owner to postgres;

create table news
(
    is_deleted  boolean      not null,
    created_at  timestamp(6) not null,
    date_posted timestamp(6) not null,
    news_id     bigint generated by default as identity
        primary key,
    posted_by   bigint       not null
        constraint fkakrgw7fj4kjo8k64r92w4oed
            references users,
    updated_at  timestamp(6) not null,
    content     varchar(255) not null,
    title       varchar(255) not null
);

alter table news
    owner to postgres;

create table notifications
(
    is_deleted      boolean      not null,
    read_status     boolean      not null,
    created_at      timestamp(6) not null,
    date_sent       timestamp(6) not null,
    notification_id bigint generated by default as identity
        primary key,
    updated_at      timestamp(6) not null,
    user_id         bigint       not null
        constraint fk9y21adhxn0ayjhfocscqox7bh
            references users,
    message         varchar(255)
);

alter table notifications
    owner to postgres;

create table reports
(
    is_deleted     boolean      not null,
    created_at     timestamp(6) not null,
    date_generated timestamp(6) not null,
    generated_by   bigint       not null
        constraint fk6oup43skcuxmgopql1obft8lo
            references users,
    report_id      bigint generated by default as identity
        primary key,
    updated_at     timestamp(6) not null,
    report_type    varchar(50),
    content        varchar(255)
);

alter table reports
    owner to postgres;

create table user_roles
(
    assigned_at timestamp(6) not null,
    role_id     bigint       not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles,
    user_id     bigint       not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references users,
    primary key (role_id, user_id)
);

alter table user_roles
    owner to postgres;

create table vehicle_types
(
    is_deleted       boolean      not null,
    maximum_load     double precision,
    created_at       timestamp(6) not null,
    updated_at       timestamp(6) not null,
    vehicle_type_id  bigint generated by default as identity
        primary key,
    name             varchar(50)  not null
        unique,
    dimensions       varchar(100),
    description      varchar(255),
    special_features varchar(255)
);

alter table vehicle_types
    owner to postgres;

create table vehicles
(
    capacity            double precision,
    is_deleted          boolean      not null,
    mileage             integer,
    year                integer,
    created_at          timestamp(6) not null,
    last_service_date   timestamp(6),
    next_service_due    timestamp(6),
    purchase_date       timestamp(6),
    updated_at          timestamp(6) not null,
    vehicle_id          bigint generated by default as identity
        primary key,
    vehicle_type_id     bigint       not null
        constraint fkk3cs3vldlan30kg1x6b57i4fe
            references vehicle_types,
    registration_number varchar(20)  not null
        unique,
    make                varchar(50),
    model               varchar(50),
    status              varchar(255) not null
        constraint vehicles_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'AVAILABLE'::character varying, 'IN_SERVICE'::character varying, 'ASSIGNED'::character varying, 'MAINTENANCE'::character varying])::text[]))
);

alter table vehicles
    owner to postgres;

create table driver_vehicles
(
    is_deleted    boolean      not null,
    assigned_date timestamp(6) not null,
    created_at    timestamp(6) not null,
    driver_id     bigint       not null
        constraint fk8rps614yp73srgxojmrybnfwm
            references drivers,
    end_date      timestamp(6) not null,
    id            bigint generated by default as identity
        primary key,
    updated_at    timestamp(6) not null,
    vehicle_id    bigint       not null
        constraint fk2yua66st8rjcncc4ui8dc9fhd
            references vehicles,
    notes         varchar(255),
    status        varchar(255) not null
        constraint driver_vehicles_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'IN_REPAIR'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying])::text[]))
);

alter table driver_vehicles
    owner to postgres;

create table incidents
(
    is_deleted    boolean      not null,
    created_at    timestamp(6) not null,
    date          timestamp(6) not null,
    driver_id     bigint       not null
        constraint fkr7yjttywvopo5vhaqqnx9pdl6
            references drivers,
    incident_id   bigint generated by default as identity
        primary key,
    reported_by   bigint       not null
        constraint fklsdthe1tepxj95377b6saenkh
            references users,
    updated_at    timestamp(6) not null,
    vehicle_id    bigint       not null
        constraint fkgwu3v09iuhjes7jexumiocuhw
            references vehicles,
    incident_type varchar(50),
    description   varchar(255),
    status        varchar(255) not null
        constraint incidents_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'PENDING'::character varying, 'CONFIRMED'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying, 'CLOSED'::character varying])::text[]))
);

alter table incidents
    owner to postgres;

create table breakdowns
(
    is_deleted   boolean          not null,
    repair_cost  double precision not null,
    breakdown_id bigint generated by default as identity
        primary key,
    created_at   timestamp(6)     not null,
    incident_id  bigint           not null
        constraint fkd1f3eavr4r585jjakotakhw0e
            references incidents,
    repair_date  timestamp(6)     not null,
    updated_at   timestamp(6)     not null,
    description  varchar(255)
);

alter table breakdowns
    owner to postgres;

create table damages
(
    insurance_claim boolean,
    is_deleted      boolean      not null,
    repair_cost     double precision,
    created_at      timestamp(6) not null,
    damage_id       bigint generated by default as identity
        primary key,
    incident_id     bigint       not null
        constraint fklk8sh5cyg40l0ssv5tgshin0i
            references incidents,
    updated_at      timestamp(6) not null,
    claim_number    varchar(50),
    description     varchar(255)
);

alter table damages
    owner to postgres;

create table service_schedules
(
    is_deleted     boolean      not null,
    created_at     timestamp(6) not null,
    schedule_id    bigint generated by default as identity
        primary key,
    scheduled_date timestamp(6),
    updated_at     timestamp(6) not null,
    vehicle_id     bigint       not null
        constraint fkhsqb70ihyipsmx7ntdojxyusp
            references vehicles,
    service_type   varchar(50),
    status         varchar(255) not null
        constraint service_schedules_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'PENDING'::character varying, 'CONFIRMED'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying, 'CLOSED'::character varying])::text[]))
);

alter table service_schedules
    owner to postgres;

create table technical_inspections
(
    is_deleted           boolean      not null,
    created_at           timestamp(6) not null,
    inspection_date      timestamp(6),
    inspection_id        bigint generated by default as identity
        primary key,
    next_inspection_date timestamp(6),
    updated_at           timestamp(6) not null,
    vehicle_id           bigint       not null
        constraint fkn4po7guh4f3shc8f2xj6igmrr
            references vehicles,
    inspector_name       varchar(100),
    comments             varchar(255),
    result               varchar(255) not null
        constraint technical_inspections_result_check
            check ((result)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'PASSED'::character varying, 'FAILED'::character varying])::text[]))
);

alter table technical_inspections
    owner to postgres;

create table transport_orders
(
    is_deleted           boolean      not null,
    assigned_driver_id   bigint
        constraint fkpcccuk8x8wi2bwsd3joldijoe
            references drivers,
    assigned_vehicle_id  bigint
        constraint fkfsbal5h89r9rruw6axk29d1qc
            references vehicles,
    client_order_id      bigint       not null
        constraint fk1pcj9a3xnqgw51pkfinhyijth
            references client_orders,
    created_at           timestamp(6) not null,
    end_location_id      bigint
        constraint fk5f17x3qtp4o1lpt6abw13yx7j
            references locations,
    scheduled_arrival    timestamp(6),
    scheduled_departure  timestamp(6),
    start_location_id    bigint
        constraint fk2n5hb0xo0o404nne0ofa48b2a
            references locations,
    transport_order_id   bigint generated by default as identity
        primary key,
    updated_at           timestamp(6) not null,
    status               varchar(255) not null
        constraint transport_orders_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'PENDING'::character varying, 'CONFIRMED'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying, 'CLOSED'::character varying])::text[])),
    transport_order_name varchar(255)
);

alter table transport_orders
    owner to postgres;

create table orders
(
    is_deleted         boolean      not null,
    client_order_id    bigint
        constraint fkbi65cvfkpdwgvhgl2etg9vje
            references client_orders,
    created_at         timestamp(6) not null,
    order_id           bigint generated by default as identity
        primary key,
    transport_order_id bigint
        constraint fkrqvg8dm30y0opoqde0mqvdfw5
            references transport_orders,
    updated_at         timestamp(6) not null,
    order_type         varchar(255) not null
        constraint orders_order_type_check
            check ((order_type)::text = ANY
                   ((ARRAY ['CLIENT'::character varying, 'SUBCONTRACTOR'::character varying, 'SUPPLIER'::character varying, 'OTHER'::character varying])::text[])),
    status             varchar(255) not null
        constraint orders_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'PENDING'::character varying, 'CONFIRMED'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying, 'CLOSED'::character varying])::text[]))
);

alter table orders
    owner to postgres;

create table transports
(
    is_deleted         boolean      not null,
    actual_arrival     timestamp(6),
    actual_departure   timestamp(6),
    created_at         timestamp(6) not null,
    transport_id       bigint generated by default as identity
        primary key,
    transport_order_id bigint       not null
        unique
        constraint fk9v2lowcrba0vbqybbmb6cw0p8
            references transport_orders,
    updated_at         timestamp(6) not null,
    notes              varchar(255),
    status             varchar(255) not null
        constraint transports_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NEW'::character varying, 'PENDING'::character varying, 'CONFIRMED'::character varying, 'IN_PROGRESS'::character varying, 'COMPLETED'::character varying, 'CANCELLED'::character varying, 'CLOSED'::character varying])::text[]))
);

alter table transports
    owner to postgres;

create table shipping_documents
(
    is_deleted      boolean      not null,
    created_at      timestamp(6) not null,
    document_id     bigint generated by default as identity
        primary key,
    issue_date      timestamp(6) not null,
    transport_id    bigint       not null
        constraint fkjww5w6m4gfi0jsrmchufghxdu
            references transports,
    updated_at      timestamp(6) not null,
    document_number varchar(50),
    document_type   varchar(50),
    file_url        varchar(255)
);

alter table shipping_documents
    owner to postgres;

create table vehicle_repairs
(
    cost             double precision,
    is_deleted       boolean      not null,
    created_at       timestamp(6) not null,
    next_service_due timestamp(6),
    service_date     timestamp(6),
    service_id       bigint generated by default as identity
        primary key,
    updated_at       timestamp(6) not null,
    vehicle_id       bigint       not null
        constraint fkjoh9un41ythmens2mngp4lds9
            references vehicles,
    service_type     varchar(50),
    service_center   varchar(100),
    description      varchar(255)
);

alter table vehicle_repairs
    owner to postgres;

