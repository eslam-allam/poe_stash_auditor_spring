INSERT INTO user_role(id, role_code, description) VALUES 
(1 ,'SUPER_MAIN_ADMIN', 'Super user used by this application to access services') ON CONFLICT (id) DO NOTHING;

INSERT INTO user_role(id, role_code, description) VALUES (2 ,'ADMIN', 'Admin User with global access')
ON CONFLICT (id) DO NOTHING;

INSERT INTO user_role(id, role_code, description) VALUES (3 ,'BASE_USER', 'Base user with minimum permissions')
ON CONFLICT (id) DO NOTHING;