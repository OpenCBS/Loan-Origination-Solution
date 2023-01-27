INSERT INTO workflows (id, name, description, is_default) VALUES (1, N'General workflow', N'The workflow that covers most of the requests.', true);

INSERT INTO states (id, workflow_id, state_type, name, description) VALUES (1, 1, N'START', N'CO Review', NULL);
INSERT INTO states (id, workflow_id, state_type, name, description) VALUES (2, 1, N'NORMAL', N'BM Review', NULL);
INSERT INTO states (id, workflow_id, state_type, name, description) VALUES (3, 1, N'NORMAL', N'GM Review', NULL);
INSERT INTO states (id, workflow_id, state_type, name, description) VALUES (4, 1, N'COMPLETE', N'Complete', NULL);
INSERT INTO states (id, workflow_id, state_type, name, description) VALUES (5, 1, N'DENIED', N'Rejected', NULL);

INSERT INTO actions (id, owner_state_id, transition_state_id, name, description) VALUES (1, 1, 2, N'Approve', N'Send to branch manager review');
INSERT INTO actions (id, owner_state_id, transition_state_id, name, description) VALUES (2, 1, 5, N'Reject', NULL);
INSERT INTO actions (id, owner_state_id, transition_state_id, name, description) VALUES (3, 2, 3, N'Approve', N'Send to general manager review');
INSERT INTO actions (id, owner_state_id, transition_state_id, name, description) VALUES (4, 2, 5, N'Reject', NULL);
INSERT INTO actions (id, owner_state_id, transition_state_id, name, description) VALUES (5, 2, 1, N'Return to CO', N'Send back to credit officer review');
INSERT INTO actions (id, owner_state_id, transition_state_id, name, description) VALUES (6, 3, 4, N'Approve', N'Complete application');
INSERT INTO actions (id, owner_state_id, transition_state_id, name, description) VALUES (7, 3, 5, N'Reject', NULL);

INSERT INTO roles (id, code, name) VALUES (1, N'ADMIN', N'Administrator');
INSERT INTO roles (id, code, name) VALUES (2, N'CO', N'Client officer');
INSERT INTO roles (id, code, name) VALUES (3, N'BM', N'Branch manager');
INSERT INTO roles (id, code, name) VALUES (4, N'GM', N'General manager');

INSERT INTO roles_permissions (role_id, permission) VALUES (1, N'ALL');

INSERT INTO users (id, username, password, first_name, last_name, role_id, email, mobile_phone, enabled) VALUES (1, 'admin', '$2a$10$XmtWixcSIQVNuX.j3SY7ZegiojYcKp.yE1MtqgF7VAy6e1GclZITm', N'Admin', N'Admin', 1, NULL, NULL, true);
INSERT INTO users (id, username, password, first_name, last_name, role_id, email, mobile_phone, enabled) VALUES (2, N'officer', N'$2a$10$GuXYYBO6EGYXvOxfQjMP7u2v0L7VJxE85KEOzrcMVunF2P13UxLhG', N'Elza', N'Lebo', 2, N'elza@gmail.com', NULL, true);
INSERT INTO users (id, username, password, first_name, last_name, role_id, email, mobile_phone, enabled) VALUES (3, N'bmanager', N'$2a$10$GuXYYBO6EGYXvOxfQjMP7u2v0L7VJxE85KEOzrcMVunF2P13UxLhG', N'Trevor', N'Piller', 3, N'trevor@gmail.com', NULL, true);
INSERT INTO users (id, username, password, first_name, last_name, role_id, email, mobile_phone, enabled) VALUES (4, N'gmanager', N'$2a$10$GuXYYBO6EGYXvOxfQjMP7u2v0L7VJxE85KEOzrcMVunF2P13UxLhG', N'Ivan', N'Fowlkes', 4, N'ivan@gmail.com', NULL, true);

INSERT INTO profiles (id, first_name, last_name, email, mobile_phone, gender, created_date) VALUES (1, N'Noah', N'Robinson', N'noah.robinson68@example.com', N'(602)-654-4432', N'MALE', '2017-02-08 10:36:53.4100000'::timestamp);
INSERT INTO profiles (id, first_name, last_name, email, mobile_phone, gender, created_date) VALUES (2, N'Tara', N'Rivera', N'tara.rivera35@example.com', N'(429)-358-1119', N'FEMALE', '2017-02-08 10:36:53.4100000'::timestamp);
INSERT INTO profiles (id, first_name, last_name, email, mobile_phone, gender, created_date) VALUES (3, N'Kristin', N'Scott', N'kristin.scott58@example.com', N'(656)-178-2013', N'FEMALE', '2017-02-08 10:36:53.4100000'::timestamp);

INSERT INTO workflow_field_sections (id, workflow_id, caption, sort_order) VALUES(1, 1, 'General data', 1);
INSERT INTO workflow_field_sections (id, workflow_id, caption, sort_order) VALUES(2, 1, 'Comments', 2);

INSERT INTO workflow_fields (id, section_id, field_type, caption, is_unique, is_mandatory, sort_order, extra) VALUES (1, 1, N'TEXT', N'Loan purpose', false, true, 1, NULL);
INSERT INTO workflow_fields (id, section_id, field_type, caption, is_unique, is_mandatory, sort_order, extra) VALUES (2, 1, N'NUMERIC', N'Loan amount', false, true, 2, NULL);
INSERT INTO workflow_fields (id, section_id, field_type, caption, is_unique, is_mandatory, sort_order, extra) VALUES (3, 1, N'LIST', N'Maturity', false, true, 3, N'6|7|8|9|10|11|12');
INSERT INTO workflow_fields (id, section_id, field_type, caption, is_unique, is_mandatory, sort_order, extra) VALUES (4, 2, N'TEXT_AREA', N'CO Comment', false, false, 1, NULL);
INSERT INTO workflow_fields (id, section_id, field_type, caption, is_unique, is_mandatory, sort_order, extra) VALUES (5, 2, N'TEXT_AREA', N'BM Comment', false, false, 2, NULL);
INSERT INTO workflow_fields (id, section_id, field_type, caption, is_unique, is_mandatory, sort_order, extra) VALUES (6, 2, N'TEXT_AREA', N'GM Comment', false, false, 3, NULL);

INSERT INTO workflow_state_permissions (id, state_id, type, reference_id) VALUES (1, 1, N'Role', 2);
INSERT INTO workflow_state_permissions (id, state_id, type, reference_id) VALUES (2, 2, N'Role', 3);
INSERT INTO workflow_state_permissions (id, state_id, type, reference_id) VALUES (3, 3, N'Role', 4);

INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (1, 1, 1, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (2, 1, 2, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (3, 1, 3, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (4, 1, 4, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (5, 2, 1, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (6, 2, 2, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (7, 2, 3, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (8, 2, 4, true);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (9, 2, 5, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (10, 3, 1, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (11, 3, 2, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (12, 3, 3, false);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (13, 3, 4, true);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (14, 3, 5, true);
INSERT INTO state_fields (id, state_id, field_id, is_read_only) VALUES (15, 3, 6, false);