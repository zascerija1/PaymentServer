create table if not exists public.questions
(
    id          bigint    not null
        constraint questions_pkey
            primary key,
    created_at  timestamp not null,
    updated_at  timestamp not null,
    description text,
    title       varchar(100)
);
create table if not exists public.roles
(
    id   bigserial not null
        constraint roles_pkey
            primary key,
    name varchar(60)
        constraint uk_nb4h0p6txrmfc0xbrd1kglp9t
            unique
);

create table if not exists public.answers
(
    id          bigint    not null
        constraint answers_pkey
            primary key,
    created_at  timestamp not null,
    updated_at  timestamp not null,
    text        text,
    question_id bigint    not null
        constraint fk3erw1a3t0r78st8ty27x6v3g1
            references questions
            on delete cascade
);


create table if not exists public.application_users
(
    id         bigint    not null
        constraint application_users_pkey
            primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    email      varchar(255)
        constraint uk_ha8ehjd5vlqolvals08ssh7o0
            unique,
    first_name text,
    last_name  text,
    password   varchar(100),
    username   varchar(255),
    answer_id  bigint    not null
        constraint uk_ortc4unvsh9wn07pt9d3nv2n4
            unique
        constraint fk8k1bh0dvfnphvin7adchxs1fm
            references answers
            on delete cascade
);

create table if not exists public.user_roles
(
    user_id bigint not null
        constraint fkhm3fc8664fichr5tu1u9566il
            references application_users,
    role_id bigint not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles,
    constraint user_roles_pkey
        primary key (user_id, role_id)
);



create table if not exists public.banks
(
    id        bigint not null
        constraint banks_pkey
            primary key,
    bank_name varchar(255)
);

create table if not exists public.bank_accounts
(
    id            bigint           not null
        constraint bank_accounts_pkey
            primary key,
    created_at    timestamp        not null,
    updated_at    timestamp        not null,
    account_owner text,
    balance       double precision not null
        constraint bank_accounts_balance_check
            check (balance >= (0)::double precision),
    card_number   text,
    cvc           text,
    expiry_date   timestamp        not null,
    bank_id       bigint           not null
        constraint fk8ngd2pjw12xdt5wasywldwjy3
            references banks
            on delete cascade
);

create table if not exists public.bank_account_users
(
    id                       bigint           not null
        constraint bank_account_users_pkey
            primary key,
    balance_lower_limit      double precision not null,
    monthly_limit            double precision not null,
    transaction_amount_limit double precision not null,
    application_user_id      bigint           not null
        constraint fkjvnkuxpowqf9445fnwobvub83
            references application_users
            on delete cascade,
    bank_account_id          bigint           not null
        constraint uk_emykmc2ulup7oy6tje2l4u1vx
            unique
        constraint fk74pijj9nly4jh4lu9pauqjwmy
            references bank_accounts
            on delete cascade
);

create table if not exists public.atms
(
    id        bigint           not null
        constraint atms_pkey
            primary key,
    balance   double precision not null,
    latitude  double precision,
    longitude double precision,
    bank_id   bigint           not null
        constraint fkkjoxqnmbgukc2qcyu4mhqwx2w
            references banks
            on delete cascade
);

create table if not exists public.merchants
(
    id              bigint not null
        constraint merchants_pkey
            primary key,
    merchant_name   text,
    bank_account_id bigint not null
        constraint uk_152q4yt6b8fl64q061cp8e666
            unique
        constraint fk3stbkqegk8a9q5qom2xwqhpvq
            references bank_accounts
            on delete cascade
);
create table if not exists public.transactions
(
    id                  uuid             not null
        constraint transactions_pkey
            primary key,
    created_at          timestamp        not null,
    updated_at          timestamp        not null,
    payment_status      varchar(255),
    receipt_id          text,
    service             text,
    total_price         double precision not null
        constraint transactions_total_price_check
            check (total_price >= (0)::double precision),
    application_user_id bigint           not null
        constraint fkrkh6i81pyos3sns019vukplpy
            references application_users
            on delete cascade,
    bank_account_id     bigint
        constraint fk59wy892j0r3ye2oxj71rrj02
            references bank_accounts
            on delete cascade,
    merchant_id         bigint           not null
        constraint fkosju61fahf0o80fnd5p59jch5
            references merchants
            on delete cascade
);



INSERT INTO public.roles (id, name) VALUES (1, 'ROLE_ADMIN') ON CONFLICT ON CONSTRAINT roles_pkey DO NOTHING;
INSERT INTO public.roles (id, name) VALUES (2, 'ROLE_USER') ON CONFLICT ON CONSTRAINT roles_pkey DO NOTHING;



INSERT INTO public.questions (id, created_at, updated_at, description,title) VALUES
(1, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', 'House number and street name',
 'What was the house number and street name you lived in as a child?') ON CONFLICT ON CONSTRAINT questions_pkey DO NOTHING;

INSERT INTO public.questions (id, created_at, updated_at, description,title) VALUES
(2, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', 'Childhood telephone number',
 'What were the last four digits of your childhood telephone number?') ON CONFLICT ON CONSTRAINT questions_pkey DO NOTHING;

INSERT INTO public.questions (id, created_at, updated_at, description,title) VALUES
(3, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', 'Spouse/partner meet',
 'In what town or city did you meet your spouse/partner?') ON CONFLICT ON CONSTRAINT questions_pkey DO NOTHING;

INSERT INTO public.questions (id, created_at, updated_at, description,title) VALUES
(4, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', 'First full time job',
 'In what town or city was your first full time job?') ON CONFLICT ON CONSTRAINT questions_pkey DO NOTHING;

INSERT INTO public.questions (id, created_at, updated_at, description,title) VALUES
(5, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', 'Driving license',
 'What are the last five digits of your drivers licence number?') ON CONFLICT ON CONSTRAINT questions_pkey DO NOTHING;

INSERT INTO public.questions (id, created_at, updated_at, description,title) VALUES
(6, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', 'Grandmothers maiden name',
 'What is your grandmothers (on your mothers side) maiden name?') ON CONFLICT ON CONSTRAINT questions_pkey DO NOTHING;

INSERT INTO public.questions (id, created_at, updated_at, description,title) VALUES
(7, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', 'Spouses mother maiden name',
 'What is your spouse or partners mothers maiden name?') ON CONFLICT ON CONSTRAINT questions_pkey DO NOTHING;

INSERT INTO public.questions (id, created_at, updated_at, description,title) VALUES
(8, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', 'Time of birth',
 'What time of the day were you born? (hh:mm)') ON CONFLICT ON CONSTRAINT questions_pkey DO NOTHING;

INSERT INTO public.questions (id, created_at, updated_at, description,title) VALUES
(9, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', 'Time of birth of the first child',
 'What time of the day was your first child born? (hh:mm)') ON CONFLICT ON CONSTRAINT questions_pkey DO NOTHING;


INSERT INTO public.banks (id, bank_name) VALUES (1, 'UniCredit Bank') ON CONFLICT ON CONSTRAINT banks_pkey DO NOTHING;
INSERT INTO public.banks (id, bank_name) VALUES (2, 'Raiffeisen Bank') ON CONFLICT ON CONSTRAINT banks_pkey DO NOTHING;
INSERT INTO public.banks (id, bank_name) VALUES (3, 'Sparkasse Bank') ON CONFLICT ON CONSTRAINT banks_pkey DO NOTHING;
INSERT INTO public.banks (id, bank_name) VALUES (4, 'Sberbank BH') ON CONFLICT ON CONSTRAINT banks_pkey DO NOTHING;
INSERT INTO public.banks (id, bank_name) VALUES (5, 'ZiraatBank BH') ON CONFLICT ON CONSTRAINT banks_pkey DO NOTHING;




INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (1, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Dzan Tabakovic',
        '1111111111111111', '$2a$10$ZNYHai/hEuQGQJn.vr3mnOuDglwuerhTILotfBrrMMVIPPuoBbdG.', '2023-03-25 14:45:36.674000', '1000.00',1) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (2, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Ajsa Hajradinovic',
        '1111111111111112', '$2a$10$PycFlXZh/yp4Jg6x1OwLUOvTHcs82NBlfHNqvQXy7Lfoe9GzgrqEK', '2023-03-25 14:45:36.674000','50.00',5) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (3, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Amra Dadic',
        '1111111111111113', '$2a$10$sPIx1v0Ms32cHXL8m1P/T.BA1t0gnUNIcrXQMIhsdKf9YlfJVWDIa', '2023-03-25 14:45:36.674000','1000.00',1) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (4, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Dzan Tabakovic',
        '1111111111111114', '$2a$10$D2XnnWW7fkfKwaw5ba0EHefytGhaWomdv6YyI8zIisON4KfaoFBXO', '2023-03-25 14:45:36.674000','1000.00',2) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (5, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Dzan Tabakovic',
        '1111111111111115', '$2a$10$p2XKdo8hMNwWKEuKivl7IeSlF.jLnckSir3AurFH06FgmqMOnvXpC', '2023-03-25 14:45:36.674000','1000.00',3) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (6, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Ajsa Hajradinovic',
        '1111111111111116', '$2a$10$cGPm7ux/MaY0CG.pRYKh9eImCcNVk8mg0q5v4LRb8/znzVqce4rFC', '2023-03-25 14:45:36.674000','100.00',4) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (7, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Ajsa Hajradinovic',
        '1111111111111117', '$2a$10$uqB7CgAjyOb7BjuICKLRVegARrDfb8bU849E3mlkdXg4qZ2ASPNO.', '2023-03-25 14:45:36.674000','1000.00',5) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (8, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Kenan Halilovic',
        '1111111111111118', '$2a$10$oE6keGdK.wyW86EFzpMvL.D.ILyLvdT41BByKzK.gzCaEV0uv/f6K', '2023-03-25 14:45:36.674000','100.00',1) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (9, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Kenan Halilovic',
        '1111111111111119', '$2a$10$T7oRSpxVMDWjVvsNlXrLFe9DGm1Jw7fN0OUyufhlNn2nsld41sLIa', '2023-03-25 14:45:36.674000','10000.00', 2) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (10, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Kenan Halilovic',
        '1111111111111121', '$2a$10$cqcfb5ub.u6MPez5ZGTRjulsl.jkxppyMNrERS7bFXoxWyVRfjnRu', '2023-03-25 14:45:36.674000','100.00',3) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (11, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Zerina Ascerija',
        '1111111111111122', '$2a$10$8RAOXDRJCYhniNqEr8V/NuPkDBV83gF/NuTDGH9q5L7b1T0oaX1sy', '2023-03-25 14:45:36.674000','1000.00',4) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (12, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Amila Lakovic',
        '1111111111111123', '$2a$10$.VFlWoqFO0cQuY93UrXOMODwzndGC6PEEyxnnuOkHsPbVt6cQVy/.', '2023-03-25 14:45:36.674000','1000.00',5) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (13, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Dzenan Devedzic',
        '1111111111111124', '$2a$10$jPqVAb3lZsZ5xBT6MMnPfOkos4l7mPKBIpxzpPCgp2q/L4RD09n0m', '2023-03-25 14:45:36.674000','1000.00',1) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (14, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Amra Music',
        '1111111111111125', '$2a$10$mBtLTHeUVl2xPCJlnFPCaO4ZwyniuYw16z9VMmqTryyR1dzoevRfq', '2023-03-25 14:45:36.674000','1000.00',2) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (15, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Damad Butkovic',
        '1111111111111126', '$2a$10$FRLWM3HF4iPsixsB7ReBceVd5HZdfeSHPLURVkBLwkoc0Y4OMPlVC', '2023-03-25 14:45:36.674000','1000.00',3) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (16, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Delila Smajlovic',
        '1111111111111127', '$2a$10$B3tcuJ4M1IOe/b9eAM3FM.2WYggOkEopn3G.eYtj/aGA2pC1buuCe', '2023-03-25 14:45:36.674000','1000.00',4) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (17, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Naida Rascic',
        '1111111111111128', '$2a$10$mtpUecp0ktyniDZEbXRMz.vY3uXpFGLFCyRnvCK0htSb43hv4a4D.', '2023-03-25 14:45:36.674000','1000.00',5) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (18, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Donald Trump',
        '1111111111111129', '$2a$10$gxpT/pKh649nkZfByzIoROjxwWqNA2iS5cLHfrEwpETGngZtBSX6e', '2023-03-25 14:45:36.674000','10.00',1) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (19, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Donald Trump',
        '1111111111111131', '$2a$10$mlD0C7/f84JxuqlyLsi9UeUOonpJSzKQ4O0g5tbSPAe8fIje3MW.y', '2023-03-25 14:45:36.674000','10000.00',2) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (20, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Test Test',
        '1111111111111132', '$2a$10$iE03wYnqYFa8cupSzd4Lt.kb.U2OgGCAxTcQ3Fb0ccEuYLkjFiPXK', '2023-03-25 14:45:36.674000','340.00',3) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (21, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Test Test',
        '1111111111111133', '$2a$10$tnTqr3oZXDy0gigS6VC0ceKqQyzm2ZrnvNtnV5FSrjnfz.cfN4hEe', '2023-03-25 14:45:36.674000','1000.00',4) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (22, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Test Test',
        '1111111111111134', '$2a$10$EMASQFISsncbTPpGYEVXIufY9lqwbThGmhMY.zACHQPyRYqBWS3ha', '2023-03-25 14:45:36.674000','10.00',5) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (23, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'John Travolta',
        '1111111111111135', '$2a$10$4cOMxVJlYliKY8GPvarerebvZ0z4jrjwofL1rreB.g9K4EHF4vGPC', '2023-03-25 14:45:36.674000','50.00',1) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (24, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'John Travolta',
        '1111111111111136', '$2a$10$QLwcmJoLlw9rLnQGohoRaOtVuQnVnE47psVrJbVmMCoWhIgUQXg7u', '2023-03-25 14:45:36.674000','1000.00',2) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (25, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Thomas Shelby'
       , '1111111111111137', '$2a$10$RbutP5XK5ONh/AtDYfImRe8WcBZSFL0YE.hOh7Q7U9tLjQctSKx/q', '2023-03-25 14:45:36.674000','3000.00',4) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (26, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Thomas Shelby',
        '1111111111111138', '$2a$10$2b47JKq/xLj3O.fYc4mUG.jzzX3WyBSNbfGQp59TD/5Ur73Gn.WiK', '2023-03-25 14:45:36.674000','4000.00',3) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

/*-----------------------------------------
  MERCHANTS BANK ACCOUNTS
  ----------------------------------------
*/
INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (27, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Amko',
        '1111111111111139', '$2a$10$sq0EtMcAyxtUwWHLpjiNIetDaAg..k3Tw8Y3mZO.4XhD0n3Df.cJG', '2023-03-25 14:45:36.674000','4000.00',2) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (28, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'BINGO'
       , '1111111111111141', '$2a$10$IRvYXeoMPSpEHLa5V0mzwuejsjsxE1vK7jwqlkR.LIZkmwc6lLRmC', '2023-03-25 14:45:36.674000','3000.00',4) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (29, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Konzum',
        '1111111111111142', '$2a$10$O5adw/HgpTipEkfHKKToBe0Bgyvb47q0lnM4DOWXMFj6D3t678NVi', '2023-03-25 14:45:36.674000','4000.00',5) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;
INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (30, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Aspek',
        '1111111111111143', '$2a$10$kCPH8LzbEWC5SZ2oqBEoveHXWM/ohpn4jo3LlnCRQ.1xg78/.i/6m', '2023-03-25 14:45:36.674000','4000.00',1) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (31, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Montana',
        '1111111111111144', '$2a$10$C1Y9u.O8802zLcnWhc5qouR57r2TADUzyDctq7Si4GAqFDtHmivUG', '2023-03-25 14:45:36.674000','4000.00',2) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (32, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Reuff',
        '1111111111111145', '$2a$10$kjqwmcAvdEC8gslYNTOqCOgNzGT/D51uaU1TRWvrBVzyJ5o.TjFBe', '2023-03-25 14:45:36.674000','4000.00',3) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (33, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Chipas',
        '1111111111111146', '$2a$10$y8TMINdL0vga9Sfo/P0SzOnepY9gVmqT7SZAqT5gXVBRYSqW1iEJq', '2023-03-25 14:45:36.674000','4000.00',4) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner, card_number, cvc, expiry_date, balance, bank_id)
VALUES (34, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Mercator',
        '1111111111111147', '$2a$10$XFAzK/.ZhqF/N5J87zeXz.LPdfZi/zuX7K0T7gJHKDIlS/gYs0XCy', '2023-03-25 14:45:36.674000','4000.00',5) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.merchants (id, merchant_name, bank_account_id) VALUES (1, 'Amko',27) ON CONFLICT ON CONSTRAINT merchants_pkey DO NOTHING;
INSERT INTO public.merchants (id, merchant_name,bank_account_id) VALUES (2, 'BINGO',28) ON CONFLICT ON CONSTRAINT merchants_pkey DO NOTHING;
INSERT INTO public.merchants (id, merchant_name,bank_account_id) VALUES (3, 'Konzum',29) ON CONFLICT ON CONSTRAINT merchants_pkey DO NOTHING;
INSERT INTO public.merchants (id, merchant_name,bank_account_id) VALUES (4, 'Aspek',30) ON CONFLICT ON CONSTRAINT merchants_pkey DO NOTHING;
INSERT INTO public.merchants (id, merchant_name,bank_account_id) VALUES (5, 'Montana',31) ON CONFLICT ON CONSTRAINT merchants_pkey DO NOTHING;
INSERT INTO public.merchants (id, merchant_name,bank_account_id) VALUES (6, 'Reuff',32) ON CONFLICT ON CONSTRAINT merchants_pkey DO NOTHING;
INSERT INTO public.merchants (id, merchant_name,bank_account_id) VALUES (7, 'Chipas',33) ON CONFLICT ON CONSTRAINT merchants_pkey DO NOTHING;
INSERT INTO public.merchants (id, merchant_name,bank_account_id) VALUES (8, 'Mercator',34) ON CONFLICT ON CONSTRAINT merchants_pkey DO NOTHING;


/*--------------
  USER AJSA
 --------------*/

INSERT INTO public.answers (id, created_at, updated_at, text,question_id) VALUES
(1, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', '$2a$10$sl2y6mad0p1Hb9k9NBjNDum9Ab7bsDYVGBnWPm3p1QG4mf2K89gRK',
 6) ON CONFLICT ON CONSTRAINT answers_pkey DO NOTHING;

INSERT INTO public.application_users(id, created_at, updated_at, email, first_name, last_name, password, username, answer_id) VALUES
(1, '2020-03-25 14:45:36.674000','2020-03-25 14:45:36.674000', 'ajsa@gmail.com', 'Ajsa', 'Hajradinovic', '$2a$10$DDhUlIqLK6F/ybdz/cPZZ.JTP75vIaFaCedZ7qRUa76dMqCqiXwgm',
 'ajsa123',1) ON CONFLICT ON CONSTRAINT application_users_pkey DO NOTHING;

INSERT INTO public.user_roles (user_id, role_id) values (1,2) on conflict on constraint user_roles_pkey do nothing;

INSERT INTO public.bank_account_users (id, balance_lower_limit, monthly_limit, transaction_amount_limit, application_user_id, bank_account_id) VALUES (1,5.0, 1000.0, 500.0, 1,6) on conflict on constraint  bank_account_users_pkey do nothing;
INSERT INTO public.bank_account_users (id, balance_lower_limit, monthly_limit, transaction_amount_limit, application_user_id, bank_account_id) VALUES (2,5.0, 1000.0, 500.0, 1,7) on conflict on constraint  bank_account_users_pkey do nothing;
INSERT INTO public.bank_account_users (id, balance_lower_limit, monthly_limit, transaction_amount_limit, application_user_id, bank_account_id) VALUES (3,5.0, 1000.0, 500.0, 1,2) on conflict on constraint  bank_account_users_pkey do nothing;


INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('cd81a884-78d8-11ea-bc55-0242ac130003', '2020-03-25 17:45:36.674000','2020-03-25 14:45:36.674000','PAID','1-1-1-123456781', 'Franck green tea (1), Cake Havana (1)', 43.5,1,6,1) on conflict  on constraint transactions_pkey do nothing;


INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('36f2e7fe-78d6-11ea-bc55-0242ac130003', '2020-03-17 14:45:36.674000','2020-03-17 14:45:36.674000','PAID','1-1-1-123456782', 'Sendvic losos (1), Choco croissant (1)', 8.5,1,6,4) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('3fc62224-78d6-11ea-bc55-0242ac130003', '2020-03-08 14:45:36.674000','2020-03-08 14:45:36.674000','PAID','1-1-1-123456783', 'Potato pie (1), Pizza (2)', 4.5,1,7,4) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('4a7097b8-78d6-11ea-bc55-0242ac130003', '2020-03-22 09:45:36.674000','2020-03-22 14:45:36.674000','PAID','1-1-1-123456784', 'Tortilla Mexico (1), Chicken pie (2)', 11.0,1,7,5) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('558f9d4c-78d6-11ea-bc55-0242ac130003', '2020-04-04 16:45:36.674000','2020-04-04 14:45:36.674000','PAID','1-1-1-123456785', 'Pizza Al Tonno (1), Pizza Quattro Stagioni (2)', 11.0,1,7,5) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('5cafe050-78d6-11ea-bc55-0242ac130003', '2020-04-03 14:45:36.674000','2020-04-03 14:45:36.674000','PAID','1-1-1-123456786', 'Flour Klas (15), Oil Floriol (10)', 35.70,1,7,8) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('67df0cd0-78d6-11ea-bc55-0242ac130003', '2020-04-03 18:45:30.674000','2020-04-03 14:45:36.674000','PAID','1-1-1-123456787', 'Loyd tea chamomile (1), Milka chocolate (10)', 10.8,1,7,8) on conflict  on constraint transactions_pkey do nothing;



/*
 nove transakcije
 */

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('b64d78fc-798f-11ea-bc55-0242ac130003', '2020-04-03 17:15:36.674000','2020-04-03 14:45:36.674000','PAID','1-1-1-123456788', 'Steak tartare dish (1)', 25.0,1,2,6) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('b64d7d66-798f-11ea-bc55-0242ac130003', '2020-04-06 14:45:36.674000','2020-04-03 14:45:36.674000','PAID','1-1-1-123456789', 'Beef Wellington dish (1)', 30.0,1,2,6) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('b64d8004-798f-11ea-bc55-0242ac130003', '2020-04-03 10:18:36.674000','2020-04-03 14:45:36.674000','PAID','1-1-1-123456791', 'Chicken Alfredo (1)', 4.5,1,2,7) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('b64d8108-798f-11ea-bc55-0242ac130003', '2020-04-08 11:20:36.674000','2020-04-03 14:45:36.674000','PAID','1-1-1-123456792', 'Lip balm Rosal (1)', 5.5,1,7,8) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('b64d834c-798f-11ea-bc55-0242ac130003', '2020-04-08 13:29:36.674000','2020-04-03 14:45:36.674000','PAID','1-1-1-123456793', 'Tomato sauce (1), Pasta penne (1)', 9.5,1,2,2) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('b64d8446-798f-11ea-bc55-0242ac130003', '2020-04-03 18:02:36.674000','2020-04-03 14:45:36.674000','PAID','1-1-1-123456794', 'Puff pastry (3), Raspberry sauce (1), Vanilla sugar (1), Condensed milk (1)', 15.5,1,6,8) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('b64d866c-798f-11ea-bc55-0242ac130003', '2020-04-08 19:45:36.674000','2020-04-03 14:45:36.674000','PAID','1-1-1-123456795', 'Chicken soup (1), Chicken fried steak (1) ', 12.8,1,6,6) on conflict  on constraint transactions_pkey do nothing;

INSERT INTO public.transactions (id, created_at, updated_at, payment_status, receipt_id, service, total_price, application_user_id, bank_account_id, merchant_id) VALUES
('0ccb3fde-7990-11ea-bc55-0242ac130003', '2020-04-03 20:45:36.674000','2020-04-03 14:45:36.674000','PAID','1-1-1-123456796', 'Granny-smith apple (2), Potato ketchup chips (3), Peanut butter (2)', 15.5,1,7,3) on conflict  on constraint transactions_pkey do nothing;

