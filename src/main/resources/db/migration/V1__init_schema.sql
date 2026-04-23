-- =============================================
-- V1: 초기 스키마 생성
-- =============================================

-- admins (관리자)
CREATE TABLE admins (
    id              BIGSERIAL PRIMARY KEY,
    email           VARCHAR(255) UNIQUE NOT NULL,
    password_hash   VARCHAR(255) NOT NULL,
    name            VARCHAR(100) NOT NULL,
    phone           VARCHAR(20),
    title           VARCHAR(50),
    role            VARCHAR(20) DEFAULT 'ADMIN',
    is_must_receive BOOLEAN DEFAULT FALSE,
    is_live         BOOLEAN DEFAULT TRUE,
    order_priority  INTEGER,
    is_active       BOOLEAN DEFAULT TRUE,
    last_login_at   TIMESTAMPTZ,
    created_at      TIMESTAMPTZ DEFAULT NOW(),
    updated_at      TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_admins_active ON admins(is_active) WHERE is_active = TRUE;
CREATE INDEX idx_admins_priority ON admins(order_priority) WHERE is_live = TRUE;

-- leads (리드)
CREATE TABLE leads (
    id                  BIGSERIAL PRIMARY KEY,

    -- 고객 입력
    customer_name       VARCHAR(100) NOT NULL,
    phone               VARCHAR(20) NOT NULL,
    car_model           VARCHAR(100) NOT NULL,
    lease_period        INTEGER NOT NULL,
    income_type         VARCHAR(30) NOT NULL,

    -- 자동 수집
    channel             VARCHAR(30),
    device_type         VARCHAR(20),
    ip_address          VARCHAR(45),
    user_agent          TEXT,
    referrer            TEXT,
    utm_source          VARCHAR(100),
    utm_medium          VARCHAR(100),
    utm_campaign        VARCHAR(100),
    utm_term            VARCHAR(100),
    utm_content         VARCHAR(100),

    -- 폼 위치 추적
    conversion_page     VARCHAR(50),
    conversion_position VARCHAR(50),

    -- 방문자 식별
    visitor_id          VARCHAR(100),
    session_id          VARCHAR(100),

    -- 헤더 추적
    original_url        TEXT,
    previous_referrer   TEXT,

    -- 업무 처리
    assignee_id         BIGINT REFERENCES admins(id),
    status              VARCHAR(20) DEFAULT 'NEW',
    memo                TEXT,
    contacted_at        TIMESTAMPTZ,
    converted_at        TIMESTAMPTZ,

    -- 소프트 삭제
    is_deleted          BOOLEAN DEFAULT FALSE,
    deleted_at          TIMESTAMPTZ,
    deleted_by          BIGINT REFERENCES admins(id),

    created_at          TIMESTAMPTZ DEFAULT NOW(),
    updated_at          TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_leads_created_at ON leads(created_at DESC);
CREATE INDEX idx_leads_assignee ON leads(assignee_id);
CREATE INDEX idx_leads_channel ON leads(channel);
CREATE INDEX idx_leads_status ON leads(status);
CREATE INDEX idx_leads_phone ON leads(phone);
CREATE INDEX idx_leads_deleted ON leads(is_deleted);
CREATE INDEX idx_leads_visitor ON leads(visitor_id);
CREATE INDEX idx_leads_conversion ON leads(conversion_page, conversion_position);

-- consents (동의 기록)
CREATE TABLE consents (
    id                  BIGSERIAL PRIMARY KEY,
    lead_id             BIGINT REFERENCES leads(id) ON DELETE CASCADE,
    privacy_consent     BOOLEAN NOT NULL,
    third_party_consent BOOLEAN NOT NULL,
    marketing_consent   BOOLEAN DEFAULT FALSE,
    policy_version      VARCHAR(20) NOT NULL,
    ip_address          VARCHAR(45) NOT NULL,
    user_agent          TEXT NOT NULL,
    agreed_at           TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    revoked_at          TIMESTAMPTZ,
    revoke_reason       TEXT
);

CREATE INDEX idx_consents_lead_id ON consents(lead_id);

-- lead_access_logs (리드 접근 감사 로그)
CREATE TABLE lead_access_logs (
    id          BIGSERIAL PRIMARY KEY,
    admin_id    BIGINT REFERENCES admins(id),
    lead_id     BIGINT REFERENCES leads(id),
    action      VARCHAR(30) NOT NULL,
    details     JSONB,
    ip_address  VARCHAR(45),
    user_agent  TEXT,
    created_at  TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_lead_logs_admin ON lead_access_logs(admin_id);
CREATE INDEX idx_lead_logs_lead ON lead_access_logs(lead_id);
CREATE INDEX idx_lead_logs_created ON lead_access_logs(created_at DESC);

-- admin_activity_logs (관리자 활동 로그)
CREATE TABLE admin_activity_logs (
    id          BIGSERIAL PRIMARY KEY,
    admin_id    BIGINT REFERENCES admins(id),
    action      VARCHAR(50) NOT NULL,
    ip_address  VARCHAR(45),
    user_agent  TEXT,
    success     BOOLEAN DEFAULT TRUE,
    created_at  TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_admin_logs_admin ON admin_activity_logs(admin_id);
CREATE INDEX idx_admin_logs_created ON admin_activity_logs(created_at DESC);

-- policy_versions (약관 버전)
CREATE TABLE policy_versions (
    id              BIGSERIAL PRIMARY KEY,
    version         VARCHAR(20) UNIQUE NOT NULL,
    policy_type     VARCHAR(30) NOT NULL,
    content         TEXT NOT NULL,
    effective_from  TIMESTAMPTZ NOT NULL,
    effective_to    TIMESTAMPTZ,
    created_at      TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_policy_type_effective ON policy_versions(policy_type, effective_from DESC);

-- capital_partners (제휴 캐피탈사)
CREATE TABLE capital_partners (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    logo_url        VARCHAR(500),
    display_order   INTEGER NOT NULL,
    is_active       BOOLEAN DEFAULT TRUE,
    created_at      TIMESTAMPTZ DEFAULT NOW(),
    updated_at      TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_capital_active_order ON capital_partners(is_active, display_order) WHERE is_active = TRUE;

-- completion_tokens (완료 페이지 일회성 토큰)
CREATE TABLE completion_tokens (
    id          BIGSERIAL PRIMARY KEY,
    token       VARCHAR(100) UNIQUE NOT NULL,
    lead_id     BIGINT REFERENCES leads(id),
    consumed    BOOLEAN DEFAULT FALSE,
    consumed_at TIMESTAMPTZ,
    expires_at  TIMESTAMPTZ NOT NULL,
    created_at  TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_completion_tokens_token ON completion_tokens(token);
CREATE INDEX idx_completion_tokens_expires ON completion_tokens(expires_at);
