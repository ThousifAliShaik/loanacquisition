CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Capitalized ENUMS
CREATE TYPE USER_ROLES AS ENUM (
    'LOAN_OFFICER', 
    'UNDERWRITER', 
    'MANAGER', 
    'SENIOR_MANAGER', 
    'RISK_ANALYST', 
    'SYSTEM_ADMINISTRATOR', 
    'COMPLIANCE_OFFICER'
);

CREATE TYPE PERMISSION_NAME AS ENUM (
    'VIEW_REPORTS', 
    'EDIT_REPORTS', 
    'APPROVE_LOANS', 
    'MANAGE_USERS', 
    'ACCESS_SYSTEM_SETTINGS', 
    'CONDUCT_RISK_ANALYSIS'
);

CREATE TYPE LOAN_TYPE AS ENUM ('CONVENTIONAL', 'FHA', 'VA');
CREATE TYPE APPLICATION_STATUS AS ENUM ('SUBMITTED', 'UNDER_REVIEW', 'APPROVED', 'REJECTED', 'CLOSED');
CREATE TYPE RISK_LEVEL AS ENUM ('LOW', 'MEDIUM', 'HIGH');
CREATE TYPE APPROVAL_STATUS AS ENUM ('PENDING', 'APPROVED', 'REJECTED');
CREATE TYPE DOCUMENT_TYPE AS ENUM ('CREDIT_SCORE', 'INCOME_VERIFICATION', 'LOAN_REQUEST', 'PROPERTY_APPRAISAL', 'INSURANCE_PROOF');
CREATE TYPE RISK_CATEGORY AS ENUM ('LOW', 'MEDIUM', 'HIGH');
CREATE TYPE COMPLIANCE_STATUS AS ENUM ('PENDING', 'APPROVED', 'REJECTED');
CREATE TYPE INCOME_VERIFICATION_STATUS AS ENUM ('VERIFIED', 'UNVERIFIED', 'PENDING');
CREATE TYPE ASSESSMENT_OUTCOME AS ENUM ('APPROVE', 'REJECT', 'FURTHER_REVIEW');
CREATE TYPE NOTIFICATION_TYPE AS ENUM ('LOAN_APPLICATION_UPDATE', 'RISK_ASSESSMENT', 'APPROVAL_STATUS', 'DOCUMENT_SUBMISSION', 'GENERAL');
CREATE TYPE REPORT_TYPE AS ENUM ('LOAN_METRICS', 'RISK_ASSESSMENT_SUMMARY', 'APPROVAL_STATUS_OVERVIEW', 'MONTHLY_LOAN_REPORT');
CREATE TYPE AUDIT_ACTION AS ENUM ('CREATE', 'UPDATE', 'DELETE');
CREATE TYPE ENTITY_TYPE AS ENUM ('LOAN_APPLICATION', 'DOCUMENT', 'RISK_ASSESSMENT', 'REPORT');

-- Create Roles Table
CREATE TABLE roles (
    role_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_name USER_ROLES UNIQUE NOT NULL  -- Using updated USER_ROLES ENUM
);

-- Create Users Table with username as a foreign key
CREATE TABLE users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    secret_key VARCHAR(255) NOT NULL,
    last_login TIMESTAMP,
    role USER_ROLES NOT NULL,  -- Using the updated USER_ROLES ENUM
    is_active BOOLEAN DEFAULT FALSE,

    -- Foreign key to ensure one-to-one relationship with user_profiles using username
    FOREIGN KEY (username) REFERENCES user_profiles(username) ON DELETE CASCADE
);

-- Create user_profiles Table
CREATE TABLE user_profiles (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) UNIQUE NOT NULL,  -- This will map to username in the users table
    email VARCHAR(255) UNIQUE NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign key to roles table (Many-to-One relationship)
    role_name USER_ROLES NOT NULL,  -- Link to role_name instead of role_id
    FOREIGN KEY (role_name) REFERENCES roles(role_name) ON DELETE SET NULL  -- Link to role_name in the roles table
);

-- Create Permissions Table
CREATE TABLE permissions (
    permission_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    permission_name PERMISSION_NAME UNIQUE NOT NULL
);

-- Create RolePermissions Table
CREATE TABLE role_permissions (
    role_permission_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_id UUID NOT NULL,
    permission_name PERMISSION_NAME NOT NULL,  -- Use ENUM type directly
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    UNIQUE (role_id, permission_name)
);

-- Insert roles into the Roles table
INSERT INTO roles (role_id, role_name) VALUES
    (gen_random_uuid(), 'LOAN_OFFICER'),
    (gen_random_uuid(), 'UNDERWRITER'),
    (gen_random_uuid(), 'MANAGER'),
    (gen_random_uuid(), 'SENIOR_MANAGER'),
    (gen_random_uuid(), 'RISK_ANALYST'),
    (gen_random_uuid(), 'SYSTEM_ADMINISTRATOR'),
    (gen_random_uuid(), 'COMPLIANCE_OFFICER');
	
-- Insert test data into user_profiles with capitalized enums
INSERT INTO user_profiles (
    user_id, username, email, full_name, phone_number, created_at, updated_at, role_name
) VALUES
    (
        gen_random_uuid(), 'loan_officer', 'loan_officer@example.com', 'Loan Officer', '123-456-7890', 
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'LOAN_OFFICER'
    ),
    (
        gen_random_uuid(), 'underwriter', 'underwriter@example.com', 'Underwriter', '123-456-7891', 
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'UNDERWRITER'
    ),
    (
        gen_random_uuid(), 'manager', 'manager@example.com', 'Manager', '123-456-7892', 
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'MANAGER'
    ),
    (
        gen_random_uuid(), 'senior_manager', 'senior_manager@example.com', 'Senior Manager', '123-456-7893', 
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SENIOR_MANAGER'
    ),
    (
        gen_random_uuid(), 'risk_analyst', 'risk_analyst@example.com', 'Risk Analyst', '123-456-7894', 
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'RISK_ANALYST'
    ),
    (
        gen_random_uuid(), 'system_admin', 'system_admin@example.com', 'System Administrator', '123-456-7895', 
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SYSTEM_ADMINISTRATOR'
    ),
    (
        gen_random_uuid(), 'compliance_officer', 'compliance_officer@example.com', 'Compliance Officer', '123-456-7896', 
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'COMPLIANCE_OFFICER'
    );

-- Insert test data into the users table
INSERT INTO users (
    user_id, username, password, secret_key, last_login, role, is_active
) VALUES
    (
        gen_random_uuid(), 'loan_officer', 'password123', 'secret_key_1', CURRENT_TIMESTAMP, 'LOAN_OFFICER', TRUE
    ),
    (
        gen_random_uuid(), 'underwriter', 'password123', 'secret_key_2', CURRENT_TIMESTAMP, 'UNDERWRITER', TRUE
    ),
    (
        gen_random_uuid(), 'manager', 'password123', 'secret_key_3', CURRENT_TIMESTAMP, 'MANAGER', TRUE
    ),
    (
        gen_random_uuid(), 'senior_manager', 'password123', 'secret_key_4', CURRENT_TIMESTAMP, 'SENIOR_MANAGER', TRUE
    ),
    (
        gen_random_uuid(), 'risk_analyst', 'password123', 'secret_key_5', CURRENT_TIMESTAMP, 'RISK_ANALYST', TRUE
    ),
    (
        gen_random_uuid(), 'system_admin', 'password123', 'secret_key_6', CURRENT_TIMESTAMP, 'SYSTEM_ADMINISTRATOR', TRUE
    ),
    (
        gen_random_uuid(), 'compliance_officer', 'password123', 'secret_key_7', CURRENT_TIMESTAMP, 'COMPLIANCE_OFFICER', TRUE
    );
	
-- Insert permissions into the Permissions table
INSERT INTO permissions (permission_id, permission_name) VALUES
    (gen_random_uuid(), 'VIEW_REPORTS'),
    (gen_random_uuid(), 'EDIT_REPORTS'),
    (gen_random_uuid(), 'APPROVE_LOANS'),
    (gen_random_uuid(), 'MANAGE_USERS'),
    (gen_random_uuid(), 'ACCESS_SYSTEM_SETTINGS'),
    (gen_random_uuid(), 'CONDUCT_RISK_ANALYSIS');

-- Insert data into RolePermissions table
INSERT INTO role_permissions (role_permission_id, role_id, permission_name) VALUES
    (gen_random_uuid(), (SELECT role_id FROM roles WHERE role_name = 'LOAN_OFFICER'), 'APPROVE_LOANS'),
    (gen_random_uuid(), (SELECT role_id FROM roles WHERE role_name = 'UNDERWRITER'), 'VIEW_REPORTS'),
    (gen_random_uuid(), (SELECT role_id FROM roles WHERE role_name = 'MANAGER'), 'EDIT_REPORTS'),
    (gen_random_uuid(), (SELECT role_id FROM roles WHERE role_name = 'SENIOR_MANAGER'), 'MANAGE_USERS'),
    (gen_random_uuid(), (SELECT role_id FROM roles WHERE role_name = 'SYSTEM_ADMINISTRATOR'), 'ACCESS_SYSTEM_SETTINGS'),
    (gen_random_uuid(), (SELECT role_id FROM roles WHERE role_name = 'RISK_ANALYST'), 'CONDUCT_RISK_ANALYSIS');

-- Create the Loan Applications table
CREATE TABLE loan_applications (
    loan_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    applicant_id UUID NOT NULL,
    loan_amount DECIMAL(15, 2) NOT NULL,
    loan_type LOAN_TYPE NOT NULL,
    application_status APPLICATION_STATUS NOT NULL,
    risk_level RISK_LEVEL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    required_approval_matrix INT NOT NULL CHECK (required_approval_matrix BETWEEN 3 AND 5),
    final_approval_status APPROVAL_STATUS DEFAULT 'PENDING',
    final_approver UUID,
    final_approval_timestamp TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,

    -- Foreign key constraint
    FOREIGN KEY (applicant_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert test data into the loan_applications table
INSERT INTO loan_applications (
    loan_id, applicant_id, loan_amount, loan_type, application_status, risk_level, 
    required_approval_matrix, final_approval_status, final_approver, final_approval_timestamp,
    created_at, updated_at, is_active
) VALUES
    (
        gen_random_uuid(), 
        (SELECT user_id FROM users WHERE username = 'loan_officer'), 
        250000.00, 
        'CONVENTIONAL', 
        'SUBMITTED', 
        'MEDIUM',
        3,  
        'PENDING', 
        NULL, 
        NULL, 
        CURRENT_TIMESTAMP, 
        CURRENT_TIMESTAMP,
        TRUE
    ),
    (
        gen_random_uuid(), 
        (SELECT user_id FROM users WHERE username = 'underwriter'), 
        180000.00, 
        'FHA', 
        'UNDER_REVIEW', 
        'HIGH',
        4,  
        'APPROVED', 
        (SELECT user_id FROM users WHERE username = 'system_admin'), 
        CURRENT_TIMESTAMP, 
        CURRENT_TIMESTAMP, 
        CURRENT_TIMESTAMP,
        TRUE
    ),
    (
        gen_random_uuid(), 
        (SELECT user_id FROM users WHERE username = 'manager'), 
        300000.00, 
        'VA', 
        'APPROVED', 
        'LOW',
        5,  
        'REJECTED', 
        (SELECT user_id FROM users WHERE username = 'senior_manager'), 
        CURRENT_TIMESTAMP, 
        CURRENT_TIMESTAMP, 
        CURRENT_TIMESTAMP,
        TRUE
    );


-- Create Loan Documents Table
CREATE TABLE loan_documents (
    document_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    loan_id UUID NOT NULL,
    document_name VARCHAR(255) NOT NULL,
    document_type DOCUMENT_TYPE NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    uploaded_by UUID NOT NULL,
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Foreign key constraints
    FOREIGN KEY (loan_id) REFERENCES loan_applications(loan_id) ON DELETE CASCADE,
    FOREIGN KEY (uploaded_by) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert test data into the Loan Documents table
INSERT INTO loan_documents (
    document_id, loan_id, document_name, document_type, file_path, uploaded_by, uploaded_at
) VALUES
    (
        gen_random_uuid(),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 250000.00),
        'CREDIT SCORE REPORT',
        'CREDIT_SCORE',
        '/files/credit_score_loan_officer.pdf',
        (SELECT user_id FROM users WHERE username = 'loan_officer'),
        CURRENT_TIMESTAMP
    ),
    (
        gen_random_uuid(),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 180000.00),
        'INCOME VERIFICATION DOCUMENT',
        'INCOME_VERIFICATION',
        '/files/income_verification_underwriter.pdf',
        (SELECT user_id FROM users WHERE username = 'underwriter'),
        CURRENT_TIMESTAMP
    );

-- Create Risk Assessments Table
CREATE TABLE risk_assessments (
    assessment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    loan_id UUID NOT NULL,
    underwriter_id UUID NOT NULL,
    debt_to_income_ratio DECIMAL(5, 2) NOT NULL,
    credit_score INT NOT NULL,
    risk_category RISK_CATEGORY NOT NULL,
    remarks TEXT,
    assessment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
    FOREIGN KEY (loan_id) REFERENCES loan_applications(loan_id) ON DELETE CASCADE,
    FOREIGN KEY (underwriter_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert test data into the Risk Assessments table
INSERT INTO risk_assessments (
    assessment_id, loan_id, underwriter_id, debt_to_income_ratio, credit_score, risk_category, remarks, assessment_date
) VALUES
    (
        gen_random_uuid(),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 250000.00),
        (SELECT user_id FROM users WHERE username = 'underwriter'),
        35.50,
        720,
        'MEDIUM',
        'Applicant has a stable income but a relatively high debt ratio.',
        CURRENT_TIMESTAMP
    ),
    (
        gen_random_uuid(),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 180000.00),
        (SELECT user_id FROM users WHERE username = 'risk_analyst'),
        28.00,
        680,
        'HIGH',
        'Credit score is slightly below optimal levels.',
        CURRENT_TIMESTAMP
    );

-- Create Compliance Assessments Table
CREATE TABLE compliance_assessments (
    compliance_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    loan_id UUID NOT NULL,
    compliance_officer_id UUID NOT NULL,
    compliance_status COMPLIANCE_STATUS NOT NULL,
    remarks TEXT,
    assessment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
    FOREIGN KEY (loan_id) REFERENCES loan_applications(loan_id) ON DELETE CASCADE,
    FOREIGN KEY (compliance_officer_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert test data into the Compliance Assessments table
INSERT INTO compliance_assessments (
    compliance_id, loan_id, compliance_officer_id, compliance_status, remarks, assessment_date
) VALUES
    (
        gen_random_uuid(),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 250000.00),
        (SELECT user_id FROM users WHERE username = 'compliance_officer'),
        'APPROVED',
        'All compliance checks have been satisfied.',
        CURRENT_TIMESTAMP
    ),
    (
        gen_random_uuid(),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 180000.00),
        (SELECT user_id FROM users WHERE username = 'compliance_officer'),
        'PENDING',
        'Additional documentation required for compliance.',
        CURRENT_TIMESTAMP
    );

-- Create Underwriter Assessments Table
CREATE TABLE underwriter_assessments (
    underwriter_assessment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    loan_id UUID NOT NULL,
    underwriter_id UUID NOT NULL,
    loan_to_value_ratio DECIMAL(5, 2) NOT NULL,
    income_verification_status INCOME_VERIFICATION_STATUS NOT NULL,
    assessment_outcome ASSESSMENT_OUTCOME NOT NULL,
    remarks TEXT,
    assessment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
    FOREIGN KEY (loan_id) REFERENCES loan_applications(loan_id) ON DELETE CASCADE,
    FOREIGN KEY (underwriter_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert test data into the Underwriter Assessments table
INSERT INTO underwriter_assessments (
    underwriter_assessment_id, loan_id, underwriter_id, loan_to_value_ratio, income_verification_status, assessment_outcome, remarks, assessment_date
) VALUES
    (
        gen_random_uuid(),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 250000.00),
        (SELECT user_id FROM users WHERE username = 'underwriter'),
        80.00,
        'VERIFIED',
        'APPROVE',
        'The loan meets all the underwriting criteria.',
        CURRENT_TIMESTAMP
    ),
    (
        gen_random_uuid(),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 180000.00),
        (SELECT user_id FROM users WHERE username = 'underwriter'),
        90.00,
        'PENDING',
        'FURTHER_REVIEW',
        'Income verification is pending further documentation.',
        CURRENT_TIMESTAMP
    ),
    (
        gen_random_uuid(),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 300000.00),
        (SELECT user_id FROM users WHERE username = 'underwriter'),
        70.00,
        'UNVERIFIED',
        'REJECT',
        'The applicant’s income could not be verified.',
        CURRENT_TIMESTAMP
    );

-- Create Loan Approvals Table
CREATE TABLE loan_approvals (
    approval_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    loan_id UUID NOT NULL,
    approver_id UUID NOT NULL,
    approval_level INT NOT NULL,
    approval_status APPROVAL_STATUS NOT NULL DEFAULT 'PENDING',
    remarks TEXT,
    approval_date TIMESTAMP,
    SLA TIMESTAMP DEFAULT NULL,  

    -- Foreign key constraints
    FOREIGN KEY (loan_id) REFERENCES loan_applications(loan_id) ON DELETE CASCADE,
    FOREIGN KEY (approver_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert test data into the Loan Approvals table
INSERT INTO loan_approvals (
    approval_id, loan_id, approver_id, approval_level, approval_status, remarks, approval_date, SLA
) VALUES
    (
        gen_random_uuid(), 
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 250000.00), 
        (SELECT user_id FROM users WHERE username = 'underwriter'), 
        1, 'APPROVED', 'Approved by underwriter.', CURRENT_TIMESTAMP, NULL
    ),
    (
        gen_random_uuid(), 
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 180000.00), 
        (SELECT user_id FROM users WHERE username = 'manager'), 
        2, 'PENDING', NULL, NULL, NULL
    );

-- Create Notifications Table
CREATE TABLE notifications (
    notification_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    loan_id UUID,
    message TEXT NOT NULL,
    notification_type NOTIFICATION_TYPE NOT NULL,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (loan_id) REFERENCES loan_applications(loan_id) ON DELETE CASCADE
);

-- Insert test data into the Notifications table
INSERT INTO notifications (
    notification_id, user_id, loan_id, message, notification_type, is_read, created_at
) VALUES
    (
        gen_random_uuid(),
        (SELECT user_id FROM users WHERE username = 'loan_officer'),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 250000.00),
        'Your loan application is now under review.',
        'LOAN_APPLICATION_UPDATE',
        FALSE,
        CURRENT_TIMESTAMP
    ),
    (
        gen_random_uuid(),
        (SELECT user_id FROM users WHERE username = 'underwriter'),
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 180000.00),
        'Risk assessment for the loan has been completed.',
        'RISK_ASSESSMENT',
        FALSE,
        CURRENT_TIMESTAMP
    );

-- Create Reports Table
CREATE TABLE reports (
    report_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    generated_by UUID NOT NULL,
    report_type REPORT_TYPE NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    generated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
    FOREIGN KEY (generated_by) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert test data into the Reports table
INSERT INTO reports (
    report_id, generated_by, report_type, file_path, generated_at
) VALUES
    (
        gen_random_uuid(),
        (SELECT user_id FROM users WHERE username = 'manager'),
        'LOAN_METRICS',
        '/reports/loan_metrics_report_manager.pdf',
        CURRENT_TIMESTAMP
    ),
    (
        gen_random_uuid(),
        (SELECT user_id FROM users WHERE username = 'risk_analyst'),
        'RISK_ASSESSMENT_SUMMARY',
        '/reports/risk_assessment_summary_risk_analyst.pdf',
        CURRENT_TIMESTAMP
    );

-- Create Audit Logs Table
CREATE TABLE audit_logs (
    log_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    action AUDIT_ACTION NOT NULL,
    entity_type ENTITY_TYPE NOT NULL,
    entity_id UUID NOT NULL,
    change_details JSON,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert test data into the Audit Logs table
INSERT INTO audit_logs (
    log_id, user_id, action, entity_type, entity_id, change_details, timestamp
) VALUES
    (
        gen_random_uuid(),
        (SELECT user_id FROM users WHERE username = 'loan_officer'),
        'CREATE',
        'LOAN_APPLICATION',
        (SELECT loan_id FROM loan_applications WHERE loan_amount = 250000.00),
        '{"loan_amount": 250000, "loan_type": "CONVENTIONAL", "status": "SUBMITTED"}',
        CURRENT_TIMESTAMP
    ),
    (
        gen_random_uuid(),
        (SELECT user_id FROM users WHERE username = 'underwriter'),
        'UPDATE',
        'DOCUMENT',
        (SELECT document_id FROM loan_documents WHERE document_name = 'CREDIT SCORE REPORT'),
        '{"previous_file_path": "/files/old_credit_score_report.pdf", "new_file_path": "/files/credit_score_loan_officer.pdf"}',
        CURRENT_TIMESTAMP
    );
