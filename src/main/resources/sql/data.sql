INSERT INTO tb_user (id, name, join_date, password, ssn)
VALUES
(nextval('hibernate_sequence'), 'dev1', current_timestamp, '1234', '12345-56789'),
(nextval('hibernate_sequence'), 'dev2', current_timestamp, '1234', '12345-56789'),
(nextval('hibernate_sequence'), 'dev3', current_timestamp, '1234', '12345-56789')
;

INSERT INTO tb_post (id, description, user_id)
VALUES
(nextval('hibernate_sequence'), 'post 1', 1),
(nextval('hibernate_sequence'), 'post 2', 2),
(nextval('hibernate_sequence'), 'post 3', 1)
;
