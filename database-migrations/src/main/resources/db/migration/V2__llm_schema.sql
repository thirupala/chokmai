
-- LLM Providers
CREATE TABLE llm_providers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    provider_key VARCHAR(50) UNIQUE NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT true,
    supports_json_schema BOOLEAN DEFAULT true,
    supports_streaming BOOLEAN DEFAULT false,
    base_url VARCHAR(255),
    api_version VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- LLM Models
CREATE TABLE llm_models (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    provider_id UUID REFERENCES llm_providers(id) ON DELETE CASCADE,
    model_key VARCHAR(100) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    description TEXT,
    context_window INTEGER,
    max_output_tokens INTEGER,
    supports_vision BOOLEAN DEFAULT false,
    supports_function_calling BOOLEAN DEFAULT false,
    cost_per_1k_input_tokens DECIMAL(10, 6),
    cost_per_1k_output_tokens DECIMAL(10, 6),
    is_active BOOLEAN DEFAULT true,
    capabilities JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(provider_id, model_key)
);

-- Tenant LLM Preferences
CREATE TABLE tenant_llm_preferences (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID REFERENCES tenants(id) ON DELETE CASCADE,
    default_provider_id UUID REFERENCES llm_providers(id) ON DELETE SET NULL,
    default_model_id UUID REFERENCES llm_models(id) ON DELETE SET NULL,
    fallback_model_id UUID REFERENCES llm_models(id) ON DELETE SET NULL,
    budget_limit_monthly DECIMAL(10, 2),
    current_month_spend DECIMAL(10, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(tenant_id)
);

-- Add LLM columns to processors table
ALTER TABLE processors ADD COLUMN IF NOT EXISTS llm_provider_id UUID REFERENCES llm_providers(id) ON DELETE SET NULL;
ALTER TABLE processors ADD COLUMN IF NOT EXISTS llm_model_id UUID REFERENCES llm_models(id) ON DELETE SET NULL;
ALTER TABLE processors ADD COLUMN IF NOT EXISTS llm_temperature DECIMAL(3, 2) DEFAULT 0.0;
ALTER TABLE processors ADD COLUMN IF NOT EXISTS llm_max_tokens INTEGER DEFAULT 4096;

-- Add LLM columns to jobs table
ALTER TABLE jobs ADD COLUMN IF NOT EXISTS llm_provider_id UUID REFERENCES llm_providers(id) ON DELETE SET NULL;
ALTER TABLE jobs ADD COLUMN IF NOT EXISTS llm_model_id UUID REFERENCES llm_models(id) ON DELETE SET NULL;
ALTER TABLE jobs ADD COLUMN IF NOT EXISTS llm_input_tokens INTEGER;
ALTER TABLE jobs ADD COLUMN IF NOT EXISTS llm_output_tokens INTEGER;
ALTER TABLE jobs ADD COLUMN IF NOT EXISTS llm_cost DECIMAL(10, 6);

-- LLM Usage Logs
CREATE TABLE llm_usage_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID REFERENCES tenants(id) ON DELETE CASCADE,
    job_id UUID REFERENCES jobs(id) ON DELETE CASCADE,
    provider_id UUID REFERENCES llm_providers(id) ON DELETE SET NULL,
    model_id UUID REFERENCES llm_models(id) ON DELETE SET NULL,
    input_tokens INTEGER,
    output_tokens INTEGER,
    total_cost DECIMAL(10, 6),
    request_time_ms INTEGER,
    status VARCHAR(20),
    error_message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes AFTER table creation (not inside CREATE TABLE)
CREATE INDEX idx_llm_usage_tenant ON llm_usage_logs(tenant_id, created_at);
CREATE INDEX idx_llm_usage_provider ON llm_usage_logs(provider_id, created_at);
CREATE INDEX idx_llm_usage_job ON llm_usage_logs(job_id);
CREATE INDEX idx_llm_models_provider ON llm_models(provider_id);
CREATE INDEX idx_llm_models_active ON llm_models(is_active) WHERE is_active = true;
CREATE INDEX idx_processors_llm_model ON processors(llm_model_id);
CREATE INDEX idx_jobs_llm_model ON jobs(llm_model_id);

-- Insert default LLM providers
INSERT INTO llm_providers (provider_key, display_name, description, supports_json_schema) VALUES
    ('openai', 'OpenAI', 'GPT models from OpenAI', true),
    ('anthropic', 'Anthropic', 'Claude models from Anthropic', true),
    ('google', 'Google', 'Gemini models from Google', true),
    ('mistral', 'Mistral AI', 'Open-source Mistral models', true),
    ('tensorlake', 'TensorLake', 'TensorLake managed models', true)
    ON CONFLICT (provider_key) DO NOTHING;

-- Insert default LLM models
INSERT INTO llm_models (provider_id, model_key, display_name, context_window, max_output_tokens, supports_vision, supports_function_calling, cost_per_1k_input_tokens, cost_per_1k_output_tokens)
VALUES
-- OpenAI models
((SELECT id FROM llm_providers WHERE provider_key = 'openai'), 'gpt-4o', 'GPT-4 Optimized', 128000, 16384, true, true, 0.0025, 0.010),
((SELECT id FROM llm_providers WHERE provider_key = 'openai'), 'gpt-4-turbo', 'GPT-4 Turbo', 128000, 4096, true, true, 0.010, 0.030),
((SELECT id FROM llm_providers WHERE provider_key = 'openai'), 'gpt-4', 'GPT-4', 8192, 4096, false, true, 0.030, 0.060),
((SELECT id FROM llm_providers WHERE provider_key = 'openai'), 'gpt-3.5-turbo', 'GPT-3.5 Turbo', 16385, 4096, false, true, 0.0005, 0.0015),

-- Anthropic models
((SELECT id FROM llm_providers WHERE provider_key = 'anthropic'), 'claude-3-opus-20240229', 'Claude 3 Opus', 200000, 4096, true, true, 0.015, 0.075),
((SELECT id FROM llm_providers WHERE provider_key = 'anthropic'), 'claude-3-sonnet-20240229', 'Claude 3 Sonnet', 200000, 4096, true, true, 0.003, 0.015),
((SELECT id FROM llm_providers WHERE provider_key = 'anthropic'), 'claude-3-haiku-20240307', 'Claude 3 Haiku', 200000, 4096, true, true, 0.00025, 0.00125),

-- Google models
((SELECT id FROM llm_providers WHERE provider_key = 'google'), 'gemini-1.5-pro', 'Gemini 1.5 Pro', 1000000, 8192, true, true, 0.00125, 0.005),
((SELECT id FROM llm_providers WHERE provider_key = 'google'), 'gemini-1.5-flash', 'Gemini 1.5 Flash', 1000000, 8192, true, true, 0.000075, 0.0003),
((SELECT id FROM llm_providers WHERE provider_key = 'google'), 'gemini-pro', 'Gemini Pro', 32768, 8192, false, true, 0.00025, 0.0005),

-- Mistral models
((SELECT id FROM llm_providers WHERE provider_key = 'mistral'), 'mistral-large-latest', 'Mistral Large', 32768, 4096, false, true, 0.004, 0.012),
((SELECT id FROM llm_providers WHERE provider_key = 'mistral'), 'mistral-medium-latest', 'Mistral Medium', 32768, 4096, false, true, 0.0027, 0.0081)

ON CONFLICT (provider_id, model_key) DO NOTHING;