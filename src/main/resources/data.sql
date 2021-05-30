insert into user (created_date, last_modified_date, email, password, name, phone_number)
values (now(), now(), 'admin@email.com', '1234', '관리자', '00000000000'),
       (now(), now(), 'jmtkdsh@nate.com', '1234', '이선하', '00000000001'),
       (now(), now(), 'gym1@email.com', '1234', '조건희', '00000000002'),
       (now(), now(), 'dev.hyeonic@gmail.com', '1234', 'hyeonic', '01057713566'),
       (now(), now(), 'user1@email.com', '1234', '헬트너', '11111111111'),
       (now(), now(), 'user2@email.com', '1234', '최기현', '11111111112'),
       (now(), now(), 'user3@email.com', '1234', '이선하', '11111111113'),
       (now(), now(), 'user4@email.com', '1234', '신정은', '11111111114'),
       (now(), now(), 'user5@email.com', '1234', '신정은', '11111111115'),
       (now(), now(), 'gh1719@daum.net', '1234', '조건희', '22222222221'),
       (now(), now(), 'gym2@email.com', '1234', '혁거세', '22222222222'),
       (now(), now(), 'gym3@email.com', '1234', '이사부', '22222222223'),
       (now(), now(), 'gym4@email.com', '1234', '의자왕', '22222222224'),
       (now(), now(), 'gym5@email.com', '1234', '계백', '22222222225'),
       (now(), now(), 'gym6@email.com', '1234', '원효대사', '22222222226'),
       (now(), now(), 'gym7@email.com', '1234', '서희', '22222222227'),
       (now(), now(), 'trainer1@email.com', '1234', '트레이너 최기현', '33333333331'),
       (now(), now(), 'trainer2@email.com', '1234', '대조영', '33333333332'),
       (now(), now(), 'trainer3@email.com', '1234', '강감찬', '33333333333'),
       (now(), now(), 'trainer4@email.com', '1234', '김부식', '33333333334');


insert into gym (created_date, last_modified_date, name, address, content, business_number, ceo_id)
values (now(), now(), '홍길동짐', '부천시 중동로 165번길 22', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 4),
       (now(), now(), '고투 부천역점', '부천시 부천로 7', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 12),
       (now(), now(), '리버사이드 스포츠', '서울특별시 광진구 아차산로78길 44', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 13),
       (now(), now(), '고릴라멀티짐 중계점', '서울특별시 노원구 한글비석로 232 유경데파트 4층', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 14),
       (now(), now(), '스포애니 상동점', '경기도 부천시 부일로 293', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 15),
       (now(), now(), '크로스핏거츠', '경기도 부천시 부일로 226', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 16),
       (now(), now(), '멋짐', '서울특별시 구로구 연동로 320', '넓은 평수, 다수의 기구 보유', '010-0000-0000', 10);

insert into trainer (created_date, last_modified_date, user_id, gym_id, career)
values (now(), now(), 4, 1, '홍길동짐 트레이너 5년차'),
       (now(), now(), 18, 7, '자격증 보유'),
       (now(), now(), 19, 7, '자격증 보유'),
       (now(), now(), 20, 7, '나는 김부식 역사 공부 중');

insert into product (created_date, last_modified_date, content, count, delete_status, name, period, price, type, gym_id,
                     trainer_id)
values (now(), now(), 'hyeonic의 pt상품 1 입니다', 6, false, 'hyeonic의 pt상품 1', null, 60000, 'PT', 1, 3),
       (now(), now(), 'hyeonic의 pt상품 2 입니다', 6, false, 'hyeonic의 pt상품 2', null, 60000, 'PT', 1, 3),
       (now(), now(), 'hyeonic의 pt상품 3 입니다', 6, false, 'hyeonic의 pt상품 3', null, 60000, 'PT', 1, 3),
       (now(), now(), 'hyeonic의 pt상품 4 입니다', 6, true, 'hyeonic의 pt상품 4', null, 60000, 'PT', 1, 3),
       (now(), now(), 'hyeonic의 pt상품 5 입니다', 6, true, 'hyeonic의 pt상품 5', null, 60000, 'PT', 1, 3),
       (now(), now(), '홍길동짐의 회원권 1 입니다', null, false, '홍길동짐의 회원권 1', 3, 100000, 'NORMAL', 1, null),
       (now(), now(), '홍길동짐의 회원권 2 입니다', null, false, '홍길동짐의 회원권 2', 6, 180000, 'NORMAL', 1, null),
       (now(), now(), 'pt상품2 입니다', 10, false, 'pt상품2', 3, 100000, 'NORMAL', 2, null),
       (now(), now(), '4달회원권 입니다', null, false, '4달회원권', 4, 110000, 'NORMAL', 1, null),
       (now(), now(), '테스트 1달회원권 입니다', null, false, '1달회원권', 1, 10000, 'NORMAL', 7, null),
       (now(), now(), '테스트 3달회원권 입니다', null, false, '3달회원권', 3, 30000, 'NORMAL', 7, null),
       (now(), now(), '테스트 6달회원권 입니다', null, false, '6달회원권', 6, 60000, 'NORMAL', 7, null),
       (now(), now(), '테스트 12달회원권 입니다', null, false, '12달회원권', 12, 120000, 'NORMAL', 7, null),
       (now(), now(), '테스트 1 회 PT', 1, false, '1 회 pt', null, 100000, 'PT', 7, 1),
       (now(), now(), '테스트 10 회 PT', 10, false, '10 회 pt', null, 1000000, 'PT', 7, 1),
       (now(), now(), '테스트 30 회 PT', 30, false, '30 회 pt', null, 3000000, 'PT', 7, 1);


insert into purchase (created_date, last_modified_date, count, period, price, gym_id, product_id, trainer_id, user_id)
values (now(), now(), null, now(), 10000, 7, 10, null, 5),
       (now(), now(), null, now(), 10000, 7, 10, null, 6),
       (now(), now(), null, now(), 10000, 7, 10, null, 7),
       (now(), now(), null, now(), 10000, 7, 10, null, 8),
       (now(), now(), null, now(), 10000, 7, 10, null, 9),
       (now(), now(), 10, null, 1000000, 7, 15, 1, 5),
       (now(), now(), 10, null, 1000000, 7, 15, 1, 6),
       (now(), now(), 10, null, 1000000, 7, 15, 1, 7),
       (now(), now(), 10, null, 1000000, 7, 15, 1, 8),
       (now(), now(), 10, null, 1000000, 7, 15, 1, 9);

insert into remain (created_date, last_modified_date, remain_count, remain_period, purchase_id, user_id)
values (now(), now(), null, now(), 1, 5),
       (now(), now(), null, now(), 2, 6),
       (now(), now(), null, now(), 3, 7),
       (now(), now(), null, now(), 4, 8),
       (now(), now(), null, now(), 5, 9),
       (now(), now(), 10, null, 6, 5),
       (now(), now(), 10, null, 7, 6),
       (now(), now(), 10, null, 8, 7),
       (now(), now(), 10, null, 9, 8),
       (now(), now(), 10, null, 10, 9);

insert into reservation (created_date, last_modified_date, Date, end_time, start_time, purchase_id, trainer_id, user_id)
values (now(), now(), '2021-04-13', '2021-04-13T10:15:30', '2021-04-13T11:15:30', 1, 1, 1),
       (now(), now(), '2021-04-22', '2021-04-22T16:25:30', '2021-04-22T16:25:30', 2, 1, 6);

insert into check_list (created_date, last_modified_date, status, gym_id, user_id)
values (now(), now(), 'OUT', 1, 4),
       (now(), now(), 'OUT', 7, 6),
       (now(), now(), 'OUT', 7, 7),
       (now(), now(), 'OUT', 7, 8),
       (now(), now(), 'OUT', 7, 9);