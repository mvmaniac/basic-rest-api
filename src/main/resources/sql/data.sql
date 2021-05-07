insert into tb_user (name, join_date, password, ssn)
values
('dev1', current_timestamp, '1234', '12345-56789'),
('dev2', current_timestamp, '1234', '12345-56789'),
('dev3', current_timestamp, '1234', '12345-56789')
;

insert into tb_post (description, user_id)
values
('post 1', 1),
('post 2', 2),
('post 3', 1)
;
