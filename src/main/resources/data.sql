insert into user (created_date, last_modified_date, email, password, name, phone_number)
values
       (now(), now(), 'admin@email.com', '1234', '관리자', '010-0000-0000'),
       (now(), now(), 'user1@email.com', '1234', '홍길동', '010-0000-0000'),
       (now(), now(), 'user2@email.com', '1234', '조건희', '010-0000-0000'),
       (now(), now(), 'user3@email.com', '1234', '최기현', '010-0000-0000'),
       (now(), now(), 'user4@email.com', '1234', '이선하', '010-0000-0000'),
       (now(), now(), 'user5@email.com', '1234', '신정은', '010-0000-0000'),
       (now(), now(), 'user5@email.com', '1234', '신정은', '010-0000-0000'),
       (now(), now(), 'dev.hyeonic@gmail.com', '1234', 'hyeonic', '010-0000-0000');

insert into gym (created_date, last_modified_date, name, address, content, business_number, ceo_id)
values
(now(), now(), '홍길동짐', '부천시 중동로 165번길 22', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 1),
(now(), now(), '고투 부천역점', '부천시 부천로 7', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 1),
(now(), now(), '리버사이드 스포츠', '서울특별시 광진구 아차산로78길 44', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 1),
(now(), now(), '고릴라멀티짐 중계점', '서울특별시 노원구 한글비석로 232 유경데파트 4층', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 1),
(now(), now(), '스포애니 상동점', '경기도 부천시 부일로 293', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 1),
(now(), now(), '크로스핏거츠', '경기도 부천시 부일로 226', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 1),
(now(), now(), '멋짐', '서울특별시 구로구 연동로 320', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 1);

insert into trainer (created_date, last_modified_date, user_id, gym_id, career)
values
       (now(), now(), 2, 1, '자격증 보유'),
       (now(), now(), 3, 1, '자격증 보유'),
       (now(), now(), 8, 1, '자격증 보유');

insert into product (created_date, last_modified_date, content, count, delete_status, name, period, price, type, gym_id, trainer_id)
values
    (now(), now(), 'hyeonic의 pt상품 1 입니다', 6, false, 'hyeonic의 pt상품 1',null, 60000, 'PT', 1, 3),
    (now(), now(), 'hyeonic의 pt상품 2 입니다', 6, false, 'hyeonic의 pt상품 2', null, 60000, 'PT', 1, 3),
    (now(), now(), 'hyeonic의 pt상품 3 입니다', 6, false, 'hyeonic의 pt상품 3', null, 60000, 'PT', 1, 3),
    (now(), now(), 'hyeonic의 pt상품 4 입니다', 6, true, 'hyeonic의 pt상품 4', null, 60000, 'PT', 1, 3),
    (now(), now(), 'hyeonic의 pt상품 5 입니다', 6, true, 'hyeonic의 pt상품 5', null, 60000, 'PT', 1, 3),
    (now(), now(), 'pt상품1 입니다', 6, false, 'pt상품1', now(), 60000, 'PT', 1, 1),
    (now(), now(), 'pt상품2 입니다', 10, false, 'pt상품2', now(), 100000, 'NORMAL', 2, 2);


insert into purchase (created_date, last_modified_date, count , period, price , gym_id, product_id,trainer_id,user_id)
values
        (now(), now(),1,null,60000,1,1,1,1),
        (now(), now(),1,null,60000,2,1,1,2),
        (now(), now(),null,now(),60000,3,1,1,3),
        (now(), now(),null,now(),60000,4,1,1,4);

insert into reservation (created_date, last_modified_date, Date , end_time,start_time , purchase_id, trainer_id,user_id)
values
        (now(), now(),'2021-04-13','2021-04-13T10:15:30','2021-04-13T11:15:30',1,1,1),
        (now(), now(),'2021-04-22','2021-04-22T16:25:30','2021-04-22T16:25:30',2,1,6);