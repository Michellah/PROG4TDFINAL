/*CREATE TABLE employee (
    id serial PRIMARY KEY,
    serial_number VARCHAR(255) UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    birthdate DATE,
    gender VARCHAR(1) CHECK (gender IN ('H', 'F')),
    address VARCHAR(255),
    cin_number VARCHAR(255) UNIQUE,
    cin_issuance_date DATE,
    cin_issuance_place VARCHAR(255),
    position VARCHAR(255),
    dependent_children INT,
    hire_date DATE,
    departure_date DATE,
    salary DOUBLE PRECISION,
    benefits DOUBLE PRECISION,
    allowances DOUBLE PRECISION,
    social_security_contributions DOUBLE PRECISION,
    taxable_income DOUBLE PRECISION,
    tax_rate DOUBLE PRECISION,
    net_income DOUBLE PRECISION,
    cnaps VARCHAR(255) UNIQUE,
    photo TEXT,
    end_to_end_id INT,
    FOREIGN KEY (end_to_end_id) REFERENCES employee(id)
);

CREATE TABLE all_phone_numbers (
    employee_id INT,
    phone_numbers VARCHAR(255),
    UNIQUE (phone_numbers),
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE all_emails (
    employee_id INT,
    emails VARCHAR(255),
    UNIQUE (emails),
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

--ALTER sequence employee_id_seq restart WITH 21;
*/