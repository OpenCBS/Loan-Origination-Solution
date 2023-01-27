alter table workflow_fields
    add column parent_show boolean;

delete
from workflow_fields
where section_id = 3
  and caption = 'Guarantor list';


insert into workflow_fields(caption, extra, field_type, is_mandatory, sort_order, is_unique, section_id, parent_show)
values ('Guarantor', 'YES | NO', 'LIST', true, 1, false, 3, true);


insert into workflow_fields(caption, extra, field_type, is_mandatory, sort_order, is_unique, section_id)
values ('Gender', 'Madam | Sir', 'LIST', true, 2, false, 3),
       ('First Name', null, 'TEXT', true, 3, false, 3),
       ('Last Name', null, 'TEXT', true, 4, false, 3),
       ('Phone Number', null, 'NUMERIC', true, 5, false, 3),
       ('Email', null, 'TEXT', false, 6, false, 3),
       ('Address : Street and n°', null, 'TEXT', false, 7, false, 3),
       ('Postal Code', null, 'TEXT', false, 8, false, 3),
       ('City', null, 'TEXT', false, 9, false, 3),
       ('Country','All countries of the world', 'LIST', true, 10, false, 3),
       ('Main language', 'English | French | German | Luxembourgish | Other', 'LIST', true, 11, false, 3),
       ('Other languages', null, 'TEXT', false, 12, false, 3),
       ('Date of Birth', null, 'DATE', true, 13, false, 3),
       ('Place of Birth (country)', null, 'TEXT', true, 14, false, 3),
       ('Citizenship', 'All countries of the world', 'LIST', false, 15, false, 3),
       ('Family Status',
        'Married | Married separation of property | Civil union (PACS) | Partnership | Single | Divorced | Widower | Other',
        'LIST', true, 16, false, 3),
       ('Professionnel situation',
        'Full time employed | Part-time employed | Unemployed (with benefits) | Unemployed (without benefits) | Independent | Revis | Retired | Student | Other',
        'LIST', true, 17, false, 3),
       ('Committed amount', null, 'NUMERIC', true, 18, false, 3),
       ('Other credits', 'YES | NO', 'LIST', true, 19, false, 3),
       ('Already guarantor for someone else?', 'YES | NO', 'LIST', true, 20, false, 3),
       ('Comment', null, 'TEXT', false, 21, false, 3),
       ('Installment amount acceptable?', 'YES | NO', 'TEXT', true, 22, false, 3),
       ('Trust in the project', 'Good | Average | Bad', 'LIST', true, 23, false, 3),
       ('Trust repayment', 'Good | Average | Bad', 'LIST', true, 24, false, 3),
       ('Relationship with client',
        'Parents / Grandparents | Brother / Sister / Brother-in-law / Sister-in-law | Children / Grandchildren | Friends | Other Family | Other',
        'LIST', true, 25, false, 3),
       ('Notes', null, 'TEXT', false, 26, false, 3);

delete
from workflow_fields
where section_id = 8
  and caption = 'NACE Code';
insert into workflow_fields(caption, extra, field_type, is_mandatory, sort_order, is_unique, section_id)
values ('NACE Code', null, 'NUMERIC', false, 8, false, 8);


update workflow_fields
set is_mandatory = false
where caption = 'Coaching needs'
  and section_id = 2;
update workflow_fields
set is_mandatory = true
where caption = 'Company Created'
  and section_id = 4;
update workflow_fields
set field_type = 'NUMERIC'
where caption = 'Telefon'
  and section_id = 4;
update workflow_fields
set field_type = 'Luxembourg | Belgium | Other'
where caption = 'Country'
  and section_id = 4;
update workflow_fields
set is_mandatory = true
where caption = 'Bank 1'
  and section_id = 6;
update workflow_fields
set field_type = 'TEXT'
where caption = 'City'
  and section_id = 4;
update workflow_fields
set field_type = 'LIST'
where caption = 'Country'
  and section_id = 4;
update workflow_fields
set extra = 'Luxembourg | Belgium | Other'
where caption = 'Country'
  and section_id = 4;

  delete
from workflow_fields
where section_id = 7
  and caption = 'Monthly family budget-Resources';
delete
from workflow_fields
where section_id = 7
  and caption = 'Monthly family budget-Expenses';
delete
from workflow_fields
where section_id = 8
  and caption = 'Financial Plan-Needs';
delete
from workflow_fields
where section_id = 8
  and caption = 'Financial Plan-Ressources';
delete
from workflow_fields
where section_id = 8
  and caption = 'Financial Plan-Total fix costs';
delete
from workflow_fields
where section_id = 8
  and caption = 'Financial Plan-Total Revenus';


  insert into workflow_field_sections(id,caption, sort_order, workflow_id)
values (10,'Monthly family budget-Resources', 10, 1),
       (11,'Monthly family budget-Expenses', 11, 1),
       (12,'Other loans', 12, 1),
       (13,'Financial Plan-Needs', 13, 1),
       (14,'Financial Plan-Ressources', 14, 1),
       (15,'Financial Plan-Total fix costs', 15, 1),
       (16,'Financial Plan-Total Revenus', 16, 1),
       (17,'Date of meeting', 17, 1);







-- insert into workflow_fields(caption, extra, field_type, is_mandatory, sort_order, is_unique, section_id)
--                                 select 'Gender', 'Madam | Sir', 'LIST', true, 2, false, 3
-- where exists (select parent_show from workflow_fields wf where caption = 'Guarantor' and section_id = 3 and parent_show = true);

