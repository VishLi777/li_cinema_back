INSERT INTO public.ci_users(id, email, name, password)VALUES (1, 'admin@admin.com', 'admin', '$2a$12$Fluf3YXsZkoN1Mkjv5ezP.ukirQUhapiFJ.zMK/RPM8hzMwatkj3m');
INSERT INTO public.ci_users(id, email, name, password)VALUES (2, 'testuser@testuser.com', 'testuser', '$2a$12$3pvSpTo1BvFQecP5Rp2sh.XDNJJ7Vc/u2HJzUFdZWedD4VWhqqqXW');
INSERT INTO ci_role(id, "role") VALUES (1, 'ADMIN');
INSERT INTO ci_role(id, "role") VALUES (2, 'USER');
INSERT INTO ci_roles_user_entities(role_id, user_id) VALUES (1, 1);
INSERT INTO ci_roles_user_entities(role_id, user_id) VALUES (2, 2);




select * from ci_role;
select * from ci_users;
select * from ci_roles_user_entities;
