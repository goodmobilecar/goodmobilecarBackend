-- V4: 어드민 계정 비밀번호 해시 수정 (demo1234!)
UPDATE admins SET password_hash = '$2a$10$n6txaNMI89Z8qD79Rc6AluWdkDsihq8G840jWj.zSRLlB5xmcVHx2'
WHERE email IN ('super@demo.test', 'admin@demo.test', 'viewer@demo.test');
