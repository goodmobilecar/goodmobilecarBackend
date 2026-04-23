-- =============================================
-- V3: 데모용 시드 데이터
-- =============================================

-- 관리자 계정 3개 (비밀번호: demo1234! → BCrypt)
-- BCrypt hash of 'demo1234!'
INSERT INTO admins (email, password_hash, name, phone, title, role, is_must_receive, is_live, order_priority) VALUES
    ('super@demo.test',  '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '대표 관리자', '010-1000-0001', '대표', 'SUPER_ADMIN', TRUE,  TRUE,  1),
    ('admin@demo.test',  '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '일반 관리자', '010-1000-0002', '팀장',  'ADMIN',       FALSE, TRUE,  2),
    ('viewer@demo.test', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '조회 관리자', '010-1000-0003', '직원',  'VIEWER',      FALSE, FALSE, 3);

-- 더미 리드 40건 (채널·상태·날짜 다양하게)
INSERT INTO leads (customer_name, phone, car_model, lease_period, income_type, channel, status, assignee_id, created_at) VALUES
    ('김민준', '010-1234-0001', '쏘렌토',   36, 'INDIVIDUAL',       'KARROT',  'NEW',       1, NOW() - INTERVAL '0 days'),
    ('이서연', '010-1234-0002', '싼타페',   48, 'INDIVIDUAL',       'META',    'CONTACTED', 2, NOW() - INTERVAL '1 days'),
    ('박지호', '010-1234-0003', '카니발',   60, 'SOLE_PROPRIETOR',  'KARROT',  'CONVERTED', 1, NOW() - INTERVAL '1 days'),
    ('최수아', '010-1234-0004', '펠리세이드',36, 'CORPORATE',        'GOOGLE',  'NEW',       2, NOW() - INTERVAL '2 days'),
    ('정우진', '010-1234-0005', '그랜저',   24, 'INDIVIDUAL',       'KARROT',  'CONTACTED', 1, NOW() - INTERVAL '2 days'),
    ('강하은', '010-1234-0006', 'K8',       48, 'INDIVIDUAL',       'META',    'REJECTED',  2, NOW() - INTERVAL '3 days'),
    ('조현우', '010-1234-0007', 'G80',      60, 'SOLE_PROPRIETOR',  'KARROT',  'NEW',       1, NOW() - INTERVAL '3 days'),
    ('윤지민', '010-1234-0008', 'GV80',     36, 'INDIVIDUAL',       'ORGANIC', 'CONVERTED', 2, NOW() - INTERVAL '4 days'),
    ('임도현', '010-1234-0009', '소나타',   24, 'INDIVIDUAL',       'KARROT',  'NEW',       1, NOW() - INTERVAL '4 days'),
    ('한수빈', '010-1234-0010', 'K5',       48, 'CORPORATE',        'META',    'CONTACTED', 2, NOW() - INTERVAL '5 days'),
    ('오민서', '010-1234-0011', '쏘렌토',   36, 'INDIVIDUAL',       'KARROT',  'NEW',       1, NOW() - INTERVAL '5 days'),
    ('서지원', '010-1234-0012', '싼타페',   60, 'SOLE_PROPRIETOR',  'GOOGLE',  'CONTACTED', 2, NOW() - INTERVAL '6 days'),
    ('신예준', '010-1234-0013', '카니발',   48, 'INDIVIDUAL',       'KARROT',  'CONVERTED', 1, NOW() - INTERVAL '6 days'),
    ('권나연', '010-1234-0014', '펠리세이드',36, 'INDIVIDUAL',       'META',    'NEW',       2, NOW() - INTERVAL '7 days'),
    ('황태양', '010-1234-0015', '그랜저',   24, 'CORPORATE',        'KARROT',  'REJECTED',  1, NOW() - INTERVAL '7 days'),
    ('문소희', '010-1234-0016', 'K8',       60, 'INDIVIDUAL',       'ORGANIC', 'NEW',       2, NOW() - INTERVAL '8 days'),
    ('배성민', '010-1234-0017', 'G80',      48, 'SOLE_PROPRIETOR',  'KARROT',  'CONTACTED', 1, NOW() - INTERVAL '8 days'),
    ('류하린', '010-1234-0018', 'GV80',     36, 'INDIVIDUAL',       'META',    'CONVERTED', 2, NOW() - INTERVAL '9 days'),
    ('전준호', '010-1234-0019', '소나타',   24, 'INDIVIDUAL',       'KARROT',  'NEW',       1, NOW() - INTERVAL '9 days'),
    ('홍지유', '010-1234-0020', 'K5',       48, 'CORPORATE',        'GOOGLE',  'CONTACTED', 2, NOW() - INTERVAL '10 days'),
    ('고은채', '010-1234-0021', '수입차종', 60, 'INDIVIDUAL',       'META',    'NEW',       1, NOW() - INTERVAL '11 days'),
    ('남재현', '010-1234-0022', '쏘렌토',   36, 'SOLE_PROPRIETOR',  'KARROT',  'NEW',       2, NOW() - INTERVAL '11 days'),
    ('변수연', '010-1234-0023', '싼타페',   48, 'INDIVIDUAL',       'ORGANIC', 'REJECTED',  1, NOW() - INTERVAL '12 days'),
    ('안도윤', '010-1234-0024', '카니발',   24, 'CORPORATE',        'KARROT',  'CONVERTED', 2, NOW() - INTERVAL '12 days'),
    ('장미래', '010-1234-0025', '펠리세이드',60, 'INDIVIDUAL',       'META',    'NEW',       1, NOW() - INTERVAL '13 days'),
    ('엄태오', '010-1234-0026', '그랜저',   36, 'INDIVIDUAL',       'KARROT',  'CONTACTED', 2, NOW() - INTERVAL '13 days'),
    ('곽소율', '010-1234-0027', 'K8',       48, 'SOLE_PROPRIETOR',  'GOOGLE',  'NEW',       1, NOW() - INTERVAL '14 days'),
    ('방준서', '010-1234-0028', 'G80',      24, 'INDIVIDUAL',       'KARROT',  'NEW',       2, NOW() - INTERVAL '14 days'),
    ('노유나', '010-1234-0029', 'GV80',     60, 'CORPORATE',        'META',    'CONVERTED', 1, NOW() - INTERVAL '15 days'),
    ('도현석', '010-1234-0030', '소나타',   36, 'INDIVIDUAL',       'KARROT',  'CONTACTED', 2, NOW() - INTERVAL '15 days'),
    ('석지아', '010-1234-0031', 'K5',       48, 'INDIVIDUAL',       'ORGANIC', 'NEW',       1, NOW() - INTERVAL '16 days'),
    ('표승우', '010-1234-0032', '수입차종', 24, 'SOLE_PROPRIETOR',  'KARROT',  'REJECTED',  2, NOW() - INTERVAL '16 days'),
    ('나다은', '010-1234-0033', '쏘렌토',   60, 'INDIVIDUAL',       'META',    'NEW',       1, NOW() - INTERVAL '17 days'),
    ('마준영', '010-1234-0034', '싼타페',   36, 'CORPORATE',        'KARROT',  'CONTACTED', 2, NOW() - INTERVAL '17 days'),
    ('바하늘', '010-1234-0035', '카니발',   48, 'INDIVIDUAL',       'GOOGLE',  'CONVERTED', 1, NOW() - INTERVAL '18 days'),
    ('사지훈', '010-1234-0036', '펠리세이드',24, 'INDIVIDUAL',       'KARROT',  'NEW',       2, NOW() - INTERVAL '18 days'),
    ('아연수', '010-1234-0037', '그랜저',   60, 'SOLE_PROPRIETOR',  'META',    'NEW',       1, NOW() - INTERVAL '19 days'),
    ('자민아', '010-1234-0038', 'K8',       36, 'INDIVIDUAL',       'KARROT',  'REJECTED',  2, NOW() - INTERVAL '19 days'),
    ('차동현', '010-1234-0039', 'G80',      48, 'CORPORATE',        'ORGANIC', 'CONTACTED', 1, NOW() - INTERVAL '20 days'),
    ('카소윤', '010-1234-0040', 'GV80',     24, 'INDIVIDUAL',       'KARROT',  'CONVERTED', 2, NOW() - INTERVAL '20 days');
