
/****** Object:  Table [dbo].[action]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[action](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[owner_state_id] [int] NOT NULL,
	[transition_state_id] [int] NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[description] [nvarchar](max) NULL,
 CONSTRAINT [PK_action] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[activity]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[activity](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[activity_type] [nvarchar](50) NOT NULL,
	[action_id] [int] NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[description] [nvarchar](max) NULL,
	[content] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_activity] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[application]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[application](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[workflow_id] [int] NOT NULL,
	[profile_id] [int] NOT NULL,
	[responsible_user_id] [int] NOT NULL,
	[current_state_id] [int] NULL,
	[completed] [bit] NOT NULL,
	[created_user_id] [int] NOT NULL,
	[created_date] [datetime2](7) NOT NULL,
 CONSTRAINT [PK_process] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[application_attachment]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[application_attachment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[application_id] [int] NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[content_type] [nvarchar](255) NOT NULL,
	[path] [nvarchar](4000) NOT NULL,
	[created_date] [datetime2](7) NOT NULL,
	[created_user_id] [int] NOT NULL,
 CONSTRAINT [PK_application_attachment] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_application_attachment_application_id_name] UNIQUE NONCLUSTERED 
(
	[application_id] ASC,
	[name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[application_field_value]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[application_field_value](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[application_id] [int] NOT NULL,
	[workflow_field_id] [int] NOT NULL,
	[value] [nvarchar](max) NULL,
 CONSTRAINT [PK_process_field_value] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[application_log]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[application_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[application_id] [int] NOT NULL,
	[from_state_id] [int] NULL,
	[to_state_id] [int] NOT NULL,
	[note] [nvarchar](max) NULL,
	[created_date] [datetime2](7) NOT NULL,
	[created_user_id] [int] NOT NULL,
 CONSTRAINT [PK_application_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[application_state_permission]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[application_state_permission](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[application_id] [int] NOT NULL,
	[state_id] [int] NOT NULL,
	[type] [nvarchar](50) NOT NULL,
	[reference_id] [int] NOT NULL,
 CONSTRAINT [PK_application_state_permission] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[event]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[event](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](500) NOT NULL,
	[content] [nvarchar](max) NOT NULL,
	[start_date] [datetime2](7) NOT NULL,
	[end_date] [datetime2](7) NOT NULL,
	[created_user_id] [int] NOT NULL,
	[created_date] [datetime2](7) NOT NULL,
	[all_day] [bit] NOT NULL,
 CONSTRAINT [PK_event] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[event_participants]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[event_participants](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[event_id] [int] NOT NULL,
	[type] [nvarchar](50) NOT NULL,
	[reference_id] [int] NOT NULL,
 CONSTRAINT [PK_event_participants] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[notification]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[notification](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[application_id] [int] NOT NULL,
	[created_user_id] [int] NOT NULL,
	[created_date] [datetime2](7) NOT NULL,
	[notification_date] [datetime2](7) NOT NULL,
	[message] [nvarchar](max) NOT NULL,
	[done] [bit] NOT NULL,
 CONSTRAINT [PK_notification] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[profile]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[profile](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [nvarchar](50) NOT NULL,
	[first_name] [nvarchar](255) NOT NULL,
	[last_name] [nvarchar](255) NOT NULL,
	[email] [nvarchar](50) NULL,
	[mobile_phone] [nvarchar](50) NULL,
	[gender] [nvarchar](20) NOT NULL,
	[created_date] [datetime2](7) NOT NULL,
 CONSTRAINT [PK_profile] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_profile_code] UNIQUE NONCLUSTERED 
(
	[code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[role]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[code] [nvarchar](50) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_role] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_role_code] UNIQUE NONCLUSTERED 
(
	[code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[role_permission]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role_permission](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_id] [int] NOT NULL,
	[permission] [nvarchar](255) NOT NULL,
 CONSTRAINT [PK_role_permission] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[state]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[state](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[workflow_id] [int] NOT NULL,
	[state_type] [nvarchar](50) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[description] [nvarchar](max) NULL,
 CONSTRAINT [PK_state] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[state_field]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[state_field](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[state_id] [int] NOT NULL,
	[field_id] [int] NOT NULL,
	[read_only] [bit] NOT NULL,
 CONSTRAINT [PK_state_field] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[user]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](255) NOT NULL,
	[password] [nvarchar](255) NOT NULL,
	[first_name] [nvarchar](255) NOT NULL,
	[last_name] [nvarchar](255) NOT NULL,
	[role_id] [int] NOT NULL,
	[email] [nvarchar](50) NULL,
	[mobile_phone] [nvarchar](50) NULL,
	[enabled] [bit] NOT NULL CONSTRAINT [DF_user_deleted]  DEFAULT ((1)),
	[phone_number] [nvarchar](50) NULL,
	[address] [nvarchar](500) NULL,
	[current_occupation] [nvarchar](255) NULL,
	[citizenship] [nvarchar](255) NULL,
	[spoken_languages] [nvarchar](255) NULL,
	[specialization] [nvarchar](255) NULL,
	[availability] [nvarchar](255) NULL,
	[already_volunteered] [nvarchar](255) NULL,
	[support] [nvarchar](255) NULL,
	[support_other] [nvarchar](255) NULL,
	[support_promoters] [nvarchar](255) NULL,
	[support_promoters_other] [nvarchar](255) NULL,
	[preferred_working_languages] [nvarchar](255) NULL,
	[street] [nvarchar](500) NULL,
	[postal_code] [nvarchar](500) NULL,
	[photo_name] [nvarchar](255)  NULL,
	[photo_content_type] [nvarchar](255)  NULL,
	[photo_path] [nvarchar](4000)  NULL,
 CONSTRAINT [PK_user] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO




/****** Object:  Table [dbo].[reset_user_password_token]    Script Date: 17-May-17 3:06:50 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[reset_user_password_token](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NOT NULL,
	[token] [nvarchar](max) NOT NULL,
	[expire_date] [datetime2](7) NOT NULL,
	[create_date] [datetime2](7) NOT NULL,
 CONSTRAINT [PK_reset_user_password_token] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

ALTER TABLE [dbo].[reset_user_password_token]  WITH CHECK ADD  CONSTRAINT [FK_reset_user_password_token_user] FOREIGN KEY([user_id])
REFERENCES [dbo].[user] ([id])
GO

ALTER TABLE [dbo].[reset_user_password_token] CHECK CONSTRAINT [FK_reset_user_password_token_user]
GO


/****** Object:  Table [dbo].[work_log]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[work_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[application_id] [int] NOT NULL,
	[spent_hours] [decimal](18,2) NOT NULL,
	[entered_date] [datetime2](7) NOT NULL,
	[created_date] [datetime2](7) NOT NULL,
	[created_user_id] [int] NOT NULL,
	[note] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_work_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[workflow]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[workflow](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[description] [nvarchar](max) NULL,
	[is_default] [bit] NOT NULL,
 CONSTRAINT [PK_workflow] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[workflow_field]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[workflow_field](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[section_id] [int] NOT NULL,
	[field_type] [nvarchar](50) NOT NULL,
	[caption] [nvarchar](255) NOT NULL,
	[unique] [bit] NOT NULL,
	[mandatory] [bit] NOT NULL,
	[order] [int] NOT NULL,
	[extra] [nvarchar](max) NULL,
 CONSTRAINT [PK_workflow_field] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[workflow_field_section]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[workflow_field_section](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[workflow_id] [int] NOT NULL,
	[caption] [nvarchar](50) NOT NULL,
	[order] [int] NOT NULL,
 CONSTRAINT [PK_workflow_field_section] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[workflow_state_permission]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[workflow_state_permission](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[state_id] [int] NOT NULL,
	[type] [nvarchar](50) NOT NULL,
	[reference_id] [int] NOT NULL,
 CONSTRAINT [PK_state_permission] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [dbo].[email_template]    Script Date: 03-May-17 3:55:31 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[email_template](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[type] [nvarchar](50) NOT NULL,
	[name] [nvarchar](max) NOT NULL,
	[title] [nvarchar](500) NOT NULL,
	[content] [nvarchar](max) NOT NULL,
 CONSTRAINT [PK_email_template] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  View [dbo].[user_profile_view]    Script Date: 20-Apr-17 11:25:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW user_profile_view
AS
SELECT
	t.id [entity_id],
	t.first_name,
	t.last_name,
	t.full_name,
	t.is_user,
	t.email,
	 ROW_NUMBER() OVER(ORDER BY id, is_user ASC) AS id
FROM (SELECT
	p.first_name,
	p.last_name,
	p.id,
	p.email,
	CAST(0 AS BIT) AS is_user,
	CONCAT(p.first_name, CONCAT(' ', p.last_name)) AS full_name
FROM  dbo.[profile] p
UNION
SELECT
	u.first_name,
	u.last_name,
	u.id,
	u.email,
	CAST(1 AS BIT) AS is_user,
	CONCAT(u.first_name, CONCAT(' ', u.last_name)) AS full_name
FROM
dbo.[user] u
WHERE        u.enabled = 1) t

GO
/****** Object:  Table [dbo].[notification_history]    Script Date: 05-Jul-17 05:28:26 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[notification_history](
[id] [int] IDENTITY(1,1) NOT NULL,
[created_date] [datetime2](7) NOT NULL,
[created_by] [nvarchar](max) NOT NULL,
[title] [nvarchar](max) NOT NULL,
[content] [nvarchar](max) NOT NULL,
[recipients] [nvarchar](max) NOT NULL,
[notification_type] [nvarchar](100) NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


GO
ALTER TABLE [dbo].[action]  WITH CHECK ADD  CONSTRAINT [FK_action_state_owner] FOREIGN KEY([owner_state_id])
REFERENCES [dbo].[state] ([id])
GO
ALTER TABLE [dbo].[action] CHECK CONSTRAINT [FK_action_state_owner]
GO
ALTER TABLE [dbo].[action]  WITH CHECK ADD  CONSTRAINT [FK_action_state_transition] FOREIGN KEY([transition_state_id])
REFERENCES [dbo].[state] ([id])
GO
ALTER TABLE [dbo].[action] CHECK CONSTRAINT [FK_action_state_transition]
GO
ALTER TABLE [dbo].[activity]  WITH CHECK ADD  CONSTRAINT [FK_activity_action] FOREIGN KEY([action_id])
REFERENCES [dbo].[action] ([id])
GO
ALTER TABLE [dbo].[activity] CHECK CONSTRAINT [FK_activity_action]
GO
ALTER TABLE [dbo].[application]  WITH CHECK ADD  CONSTRAINT [FK_application_profile] FOREIGN KEY([profile_id])
REFERENCES [dbo].[profile] ([id])
GO
ALTER TABLE [dbo].[application] CHECK CONSTRAINT [FK_application_profile]
GO
ALTER TABLE [dbo].[application]  WITH CHECK ADD  CONSTRAINT [FK_application_state] FOREIGN KEY([current_state_id])
REFERENCES [dbo].[state] ([id])
GO
ALTER TABLE [dbo].[application] CHECK CONSTRAINT [FK_application_state]
GO
ALTER TABLE [dbo].[application]  WITH CHECK ADD  CONSTRAINT [FK_application_user] FOREIGN KEY([created_user_id])
REFERENCES [dbo].[user] ([id])
GO
ALTER TABLE [dbo].[application] CHECK CONSTRAINT [FK_application_user]
GO
ALTER TABLE [dbo].[application]  WITH CHECK ADD  CONSTRAINT [FK_application_user_responsible] FOREIGN KEY([responsible_user_id])
REFERENCES [dbo].[user] ([id])
GO
ALTER TABLE [dbo].[application] CHECK CONSTRAINT [FK_application_user_responsible]
GO
ALTER TABLE [dbo].[application]  WITH CHECK ADD  CONSTRAINT [FK_application_workflow] FOREIGN KEY([workflow_id])
REFERENCES [dbo].[workflow] ([id])
GO
ALTER TABLE [dbo].[application] CHECK CONSTRAINT [FK_application_workflow]
GO
ALTER TABLE [dbo].[application_attachment]  WITH CHECK ADD  CONSTRAINT [FK_application_attachment_application] FOREIGN KEY([application_id])
REFERENCES [dbo].[application] ([id])
GO
ALTER TABLE [dbo].[application_attachment] CHECK CONSTRAINT [FK_application_attachment_application]
GO
ALTER TABLE [dbo].[application_attachment]  WITH CHECK ADD  CONSTRAINT [FK_application_attachment_user] FOREIGN KEY([created_user_id])
REFERENCES [dbo].[user] ([id])
GO
ALTER TABLE [dbo].[application_attachment] CHECK CONSTRAINT [FK_application_attachment_user]
GO
ALTER TABLE [dbo].[application_field_value]  WITH CHECK ADD  CONSTRAINT [FK_application_field_value_application] FOREIGN KEY([application_id])
REFERENCES [dbo].[application] ([id])
GO
ALTER TABLE [dbo].[application_field_value] CHECK CONSTRAINT [FK_application_field_value_application]
GO
ALTER TABLE [dbo].[application_field_value]  WITH CHECK ADD  CONSTRAINT [FK_application_field_value_workflow_field] FOREIGN KEY([workflow_field_id])
REFERENCES [dbo].[workflow_field] ([id])
GO
ALTER TABLE [dbo].[application_field_value] CHECK CONSTRAINT [FK_application_field_value_workflow_field]
GO
ALTER TABLE [dbo].[application_log]  WITH CHECK ADD  CONSTRAINT [FK_application_log_application] FOREIGN KEY([application_id])
REFERENCES [dbo].[application] ([id])
GO
ALTER TABLE [dbo].[application_log] CHECK CONSTRAINT [FK_application_log_application]
GO
ALTER TABLE [dbo].[application_log]  WITH CHECK ADD  CONSTRAINT [FK_application_log_state_from] FOREIGN KEY([from_state_id])
REFERENCES [dbo].[state] ([id])
GO
ALTER TABLE [dbo].[application_log] CHECK CONSTRAINT [FK_application_log_state_from]
GO
ALTER TABLE [dbo].[application_log]  WITH CHECK ADD  CONSTRAINT [FK_application_log_state_to] FOREIGN KEY([to_state_id])
REFERENCES [dbo].[state] ([id])
GO
ALTER TABLE [dbo].[application_log] CHECK CONSTRAINT [FK_application_log_state_to]
GO
ALTER TABLE [dbo].[application_log]  WITH CHECK ADD  CONSTRAINT [FK_application_log_user] FOREIGN KEY([created_user_id])
REFERENCES [dbo].[user] ([id])
GO
ALTER TABLE [dbo].[application_log] CHECK CONSTRAINT [FK_application_log_user]
GO
ALTER TABLE [dbo].[application_state_permission]  WITH CHECK ADD  CONSTRAINT [FK_application_state_permission_application] FOREIGN KEY([application_id])
REFERENCES [dbo].[application] ([id])
GO
ALTER TABLE [dbo].[application_state_permission] CHECK CONSTRAINT [FK_application_state_permission_application]
GO
ALTER TABLE [dbo].[application_state_permission]  WITH CHECK ADD  CONSTRAINT [FK_application_state_permission_state] FOREIGN KEY([state_id])
REFERENCES [dbo].[state] ([id])
GO
ALTER TABLE [dbo].[application_state_permission] CHECK CONSTRAINT [FK_application_state_permission_state]
GO
ALTER TABLE [dbo].[event]  WITH CHECK ADD  CONSTRAINT [FK_event_user] FOREIGN KEY([created_user_id])
REFERENCES [dbo].[user] ([id])
GO
ALTER TABLE [dbo].[event] CHECK CONSTRAINT [FK_event_user]
GO
ALTER TABLE [dbo].[event_participants]  WITH CHECK ADD  CONSTRAINT [FK_event_participants_event] FOREIGN KEY([event_id])
REFERENCES [dbo].[event] ([id])
GO
ALTER TABLE [dbo].[event_participants] CHECK CONSTRAINT [FK_event_participants_event]
GO
ALTER TABLE [dbo].[notification]  WITH CHECK ADD  CONSTRAINT [FK_notification_application] FOREIGN KEY([application_id])
REFERENCES [dbo].[application] ([id])
GO
ALTER TABLE [dbo].[notification] CHECK CONSTRAINT [FK_notification_application]
GO
ALTER TABLE [dbo].[notification]  WITH CHECK ADD  CONSTRAINT [FK_notification_user_created] FOREIGN KEY([created_user_id])
REFERENCES [dbo].[user] ([id])
GO
ALTER TABLE [dbo].[notification] CHECK CONSTRAINT [FK_notification_user_created]
GO
ALTER TABLE [dbo].[role_permission]  WITH CHECK ADD  CONSTRAINT [FK_role_permission_role] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([id])
GO
ALTER TABLE [dbo].[role_permission] CHECK CONSTRAINT [FK_role_permission_role]
GO
ALTER TABLE [dbo].[state]  WITH CHECK ADD  CONSTRAINT [FK_state_workflow] FOREIGN KEY([workflow_id])
REFERENCES [dbo].[workflow] ([id])
GO
ALTER TABLE [dbo].[state] CHECK CONSTRAINT [FK_state_workflow]
GO
ALTER TABLE [dbo].[state_field]  WITH CHECK ADD  CONSTRAINT [FK_state_field_state] FOREIGN KEY([state_id])
REFERENCES [dbo].[state] ([id])
GO
ALTER TABLE [dbo].[state_field] CHECK CONSTRAINT [FK_state_field_state]
GO
ALTER TABLE [dbo].[state_field]  WITH CHECK ADD  CONSTRAINT [FK_state_field_workflow_field] FOREIGN KEY([field_id])
REFERENCES [dbo].[workflow_field] ([id])
GO
ALTER TABLE [dbo].[state_field] CHECK CONSTRAINT [FK_state_field_workflow_field]
GO
ALTER TABLE [dbo].[user]  WITH CHECK ADD  CONSTRAINT [FK_user_role] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([id])
GO
ALTER TABLE [dbo].[user] CHECK CONSTRAINT [FK_user_role]
GO
ALTER TABLE [dbo].[work_log]  WITH CHECK ADD  CONSTRAINT [FK_work_log_application] FOREIGN KEY([application_id])
REFERENCES [dbo].[application] ([id])
GO
ALTER TABLE [dbo].[work_log] CHECK CONSTRAINT [FK_work_log_application]
GO
ALTER TABLE [dbo].[work_log]  WITH CHECK ADD  CONSTRAINT [FK_work_log_user] FOREIGN KEY([created_user_id])
REFERENCES [dbo].[user] ([id])
GO
ALTER TABLE [dbo].[work_log] CHECK CONSTRAINT [FK_work_log_user]
GO
ALTER TABLE [dbo].[workflow_field]  WITH CHECK ADD  CONSTRAINT [FK_workflow_field_workflow_field_section] FOREIGN KEY([section_id])
REFERENCES [dbo].[workflow_field_section] ([id])
GO
ALTER TABLE [dbo].[workflow_field] CHECK CONSTRAINT [FK_workflow_field_workflow_field_section]
GO
ALTER TABLE [dbo].[workflow_field_section]  WITH CHECK ADD  CONSTRAINT [FK_workflow_field_section_workflow] FOREIGN KEY([workflow_id])
REFERENCES [dbo].[workflow] ([id])
GO
ALTER TABLE [dbo].[workflow_field_section] CHECK CONSTRAINT [FK_workflow_field_section_workflow]
GO
ALTER TABLE [dbo].[workflow_state_permission]  WITH CHECK ADD  CONSTRAINT [FK_state_permission_state] FOREIGN KEY([state_id])
REFERENCES [dbo].[state] ([id])
GO
ALTER TABLE [dbo].[workflow_state_permission] CHECK CONSTRAINT [FK_state_permission_state]
GO
