SET IDENTITY_INSERT dbo.workflow ON
INSERT dbo.workflow (id, name, description) VALUES (1, N'General workflow', N'The workflow that covers most of the requests.')
SET IDENTITY_INSERT dbo.workflow OFF

SET IDENTITY_INSERT dbo.state ON
INSERT dbo.state (id, workflow_id, state_type, name, description) VALUES (1, 1, N'START', N'CO Review', NULL)
INSERT dbo.state (id, workflow_id, state_type, name, description) VALUES (2, 1, N'NORMAL', N'BM Review', NULL)
INSERT dbo.state (id, workflow_id, state_type, name, description) VALUES (3, 1, N'NORMAL', N'GM Review', NULL)
INSERT dbo.state (id, workflow_id, state_type, name, description) VALUES (4, 1, N'COMPLETE', N'Complete', NULL)
INSERT dbo.state (id, workflow_id, state_type, name, description) VALUES (5, 1, N'DENIED', N'Rejected', NULL)
SET IDENTITY_INSERT dbo.state OFF

SET IDENTITY_INSERT dbo.action ON
INSERT dbo.action (id, owner_state_id, transition_state_id, name, description) VALUES (1, 1, 2, N'Approve', N'Send to branch manager review')
INSERT dbo.action (id, owner_state_id, transition_state_id, name, description) VALUES (2, 1, 5, N'Reject', NULL)
INSERT dbo.action (id, owner_state_id, transition_state_id, name, description) VALUES (3, 2, 3, N'Approve', N'Send to general manager review')
INSERT dbo.action (id, owner_state_id, transition_state_id, name, description) VALUES (4, 2, 5, N'Reject', NULL)
INSERT dbo.action (id, owner_state_id, transition_state_id, name, description) VALUES (5, 2, 1, N'Return to CO', N'Send back to credit officer review')
INSERT dbo.action (id, owner_state_id, transition_state_id, name, description) VALUES (6, 3, 4, N'Approve', N'Complete application')
INSERT dbo.action (id, owner_state_id, transition_state_id, name, description) VALUES (7, 3, 5, N'Reject', NULL)
SET IDENTITY_INSERT dbo.action OFF

SET IDENTITY_INSERT dbo.role ON
INSERT dbo.role (id, code, name) VALUES (1, N'ADMIN', N'Administrator')
INSERT dbo.role (id, code, name) VALUES (2, N'CO', N'Client officer')
INSERT dbo.role (id, code, name) VALUES (3, N'BM', N'Branch manager')
INSERT dbo.role (id, code, name) VALUES (4, N'GM', N'General manager')
SET IDENTITY_INSERT dbo.role OFF

SET IDENTITY_INSERT dbo.role_permission ON
INSERT dbo.role_permission (id, role_id, permission) VALUES (1, 1, N'ALL')
SET IDENTITY_INSERT dbo.role_permission OFF

SET IDENTITY_INSERT dbo.[user] ON
INSERT dbo.[user] (id, username, password, first_name, last_name, role_id, email, mobile_phone, enabled) VALUES (1, N'admin', N'$2a$10$GuXYYBO6EGYXvOxfQjMP7u2v0L7VJxE85KEOzrcMVunF2P13UxLhG', N'Admin', N'Admin', 1, NULL, NULL, 1)
INSERT dbo.[user] (id, username, password, first_name, last_name, role_id, email, mobile_phone, enabled) VALUES (2, N'officer', N'$2a$10$GuXYYBO6EGYXvOxfQjMP7u2v0L7VJxE85KEOzrcMVunF2P13UxLhG', N'Elza', N'Lebo', 2, N'elza@gmail.com', NULL, 1)
INSERT dbo.[user] (id, username, password, first_name, last_name, role_id, email, mobile_phone, enabled) VALUES (3, N'bmanager', N'$2a$10$GuXYYBO6EGYXvOxfQjMP7u2v0L7VJxE85KEOzrcMVunF2P13UxLhG', N'Trevor', N'Piller', 3, N'trevor@gmail.com', NULL, 1)
INSERT dbo.[user] (id, username, password, first_name, last_name, role_id, email, mobile_phone, enabled) VALUES (4, N'gmanager', N'$2a$10$GuXYYBO6EGYXvOxfQjMP7u2v0L7VJxE85KEOzrcMVunF2P13UxLhG', N'Ivan', N'Fowlkes', 4, N'ivan@gmail.com', NULL, 1)
SET IDENTITY_INSERT dbo.[user] OFF

SET IDENTITY_INSERT dbo.profile ON
INSERT dbo.profile (id, code, first_name, last_name, email, mobile_phone, gender, created_date) VALUES (1, N'noah-robinson', N'Noah', N'Robinson', N'noah.robinson68@example.com', N'(602)-654-4432', N'MALE', N'2017-02-08 10:36:53.4100000')
INSERT dbo.profile (id, code, first_name, last_name, email, mobile_phone, gender, created_date) VALUES (2, N'pppppp', N'Tara', N'Rivera', N'tara.rivera35@example.com', N'(429)-358-1119', N'FEMALE', N'2017-02-08 10:36:53.4100000')
INSERT dbo.profile (id, code, first_name, last_name, email, mobile_phone, gender, created_date) VALUES (3, N'lexmark', N'Kristin', N'Scott', N'kristin.scott58@example.com', N'(656)-178-2013', N'FEMALE', N'2017-02-08 10:36:53.4100000')
SET IDENTITY_INSERT dbo.profile OFF

SET IDENTITY_INSERT dbo.workflow_field_section ON
INSERT INTO [dbo].[workflow_field_section] (id, [workflow_id], [caption], [order]) VALUES(1, 1, 'General data', 1)
INSERT INTO [dbo].[workflow_field_section] (id, [workflow_id], [caption], [order]) VALUES(2, 1, 'Comments', 2)
SET IDENTITY_INSERT dbo.workflow_field_section OFF

SET IDENTITY_INSERT dbo.workflow_field ON
INSERT dbo.workflow_field (id, section_id, field_type, caption, [unique], mandatory, [order], extra) VALUES (1, 1, N'TEXT', N'Loan purpose', 0, 1, 1, NULL)
INSERT dbo.workflow_field (id, section_id, field_type, caption, [unique], mandatory, [order], extra) VALUES (2, 1, N'NUMERIC', N'Loan amount', 0, 1, 2, NULL)
INSERT dbo.workflow_field (id, section_id, field_type, caption, [unique], mandatory, [order], extra) VALUES (3, 1, N'LIST', N'Maturity', 0, 1, 3, N'6|7|8|9|10|11|12')
INSERT dbo.workflow_field (id, section_id, field_type, caption, [unique], mandatory, [order], extra) VALUES (4, 2, N'TEXT_AREA', N'CO Comment', 0, 0, 1, NULL)
INSERT dbo.workflow_field (id, section_id, field_type, caption, [unique], mandatory, [order], extra) VALUES (5, 2, N'TEXT_AREA', N'BM Comment', 0, 0, 2, NULL)
INSERT dbo.workflow_field (id, section_id, field_type, caption, [unique], mandatory, [order], extra) VALUES (6, 2, N'TEXT_AREA', N'GM Comment', 0, 0, 3, NULL)
SET IDENTITY_INSERT dbo.workflow_field OFF

SET IDENTITY_INSERT dbo.workflow_state_permission ON
INSERT INTO dbo.workflow_state_permission (id, state_id, [type], reference_id) VALUES (1, 1, N'Role', 2)
INSERT INTO dbo.workflow_state_permission (id, state_id, [type], reference_id) VALUES (2, 2, N'Role', 3)
INSERT INTO dbo.workflow_state_permission (id, state_id, [type], reference_id) VALUES (3, 3, N'Role', 4)
SET IDENTITY_INSERT dbo.workflow_state_permission OFF

SET IDENTITY_INSERT dbo.state_field ON
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (1, 1, 1, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (2, 1, 2, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (3, 1, 3, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (4, 1, 4, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (5, 2, 1, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (6, 2, 2, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (7, 2, 3, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (8, 2, 4, 1)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (9, 2, 5, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (10, 3, 1, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (11, 3, 2, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (12, 3, 3, 0)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (13, 3, 4, 1)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (14, 3, 5, 1)
INSERT INTO dbo.state_field (id, state_id, field_id, read_only) VALUES (15, 3, 6, 0)
SET IDENTITY_INSERT dbo.state_field OFF


