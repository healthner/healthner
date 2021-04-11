insert into user (created_date, last_modified_date, email, password, name, phone_number)
values
       (now(), now(), 'admin@email.com', '1234', '관리자', '010-0000-0000'),
       (now(), now(), 'user1@email.com', '1234', '홍길동', '010-0000-0000'),
       (now(), now(), 'user2@email.com', '1234', '조건희', '010-0000-0000'),
       (now(), now(), 'user3@email.com', '1234', '최기현', '010-0000-0000'),
       (now(), now(), 'user4@email.com', '1234', '이선하', '010-0000-0000'),
       (now(), now(), 'user5@email.com', '1234', '신정은', '010-0000-0000');


insert into trainer (created_date, last_modified_date, user_id, career)
values
       (now(), now(), 2, '자격증 보유'),
       (now(), now(), 3, '자격증 보유');

insert into gym (created_date, last_modified_date, name, address, content, business_number, ceo_id)
values
       (now(), now(), '홍길동짐', '부천시', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 1);