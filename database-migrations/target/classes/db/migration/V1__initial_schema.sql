-- =========================
-- USERS & IDENTITY
-- =========================
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT,
    status TEXT NOT NULL CHECK (status IN ('active','inactive','suspended','deleted')),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
    );

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_status ON users(status);

CREATE TABLE IF NOT EXISTS user_identities (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    provider TEXT NOT NULL,
    external_user_id TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    UNIQUE (provider, external_user_id)
    );

-- =========================
-- ORGANIZATIONS & TENANTS
-- =========================
CREATE TABLE IF NOT EXISTS organizations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
    );

CREATE TABLE IF NOT EXISTS tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    name TEXT NOT NULL,
    status TEXT NOT NULL CHECK (status IN ('active','inactive','suspended','trial')),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
    );

CREATE TABLE IF NOT EXISTS tenant_users (
    tenant_id UUID REFERENCES tenants(id) ON DELETE CASCADE,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    role TEXT NOT NULL CHECK (role IN ('owner','admin','member','viewer')),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (tenant_id, user_id)
    );

-- =========================
-- PROCESSORS & JOBS
-- =========================
CREATE TABLE IF NOT EXISTS processors (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    status TEXT NOT NULL,
    json_schema JSONB,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
    );

CREATE TABLE IF NOT EXISTS jobs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    project_id UUID,
    processor_id UUID NOT NULL REFERENCES processors(id),
    status TEXT NOT NULL,
    submission_mode TEXT NOT NULL,
    external_job_id TEXT,
    extraction_file_id TEXT,
    extraction_job_id TEXT,
    retry_count INT DEFAULT 0,
    error_message TEXT,
    estimated_credits INT,
    actual_credits INT,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    completed_at TIMESTAMP
    );

-- =========================
-- CREDIT LEDGER (TEMP REAL)
-- =========================
    CREATE TABLE IF NOT EXISTS credit_ledger (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    job_id UUID REFERENCES jobs(id),
    type TEXT NOT NULL,
    credits INT NOT NULL,
    balance_after INT NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT now()
    );

-- =========================
-- AUDIT LOGS
-- =========================
CREATE TABLE IF NOT EXISTS audit_logs (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    actor TEXT NOT NULL,
    tenant_id UUID REFERENCES tenants(id),
    action TEXT NOT NULL,
    resource TEXT NOT NULL,
    outcome TEXT NOT NULL,
    metadata JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT now()
    );

-- =========================
-- FUNCTIONS & TRIGGERS
-- =========================
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS update_jobs_updated_at ON jobs;
CREATE TRIGGER update_jobs_updated_at
    BEFORE UPDATE ON jobs
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
