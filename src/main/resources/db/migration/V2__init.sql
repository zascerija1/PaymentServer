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
    bank_name     text,
    card_number   text,
    cvc           text,
    expiry_date   timestamp        not null
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



INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (1, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Dzan Tabakovic',
        'UniCredit Bank', '1111111111111111', '111', '2023-03-25 14:45:36.674000', '1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (2, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Aisa Hajradinovic',
        'UniCredit Bank', '1111111111111112', '112', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (3, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Amra Dadic',
        'UniCredit Bank', '1111111111111113', '113', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (4, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Dzan Tabakovic',
        'UniCredit Bank', '1111111111111114', '114', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (5, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Dzan Tabakovic',
        'UniCredit Bank', '1111111111111115', '115', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (6, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Aisa Hajradinovic',
        'UniCredit Bank', '1111111111111116', '116', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (7, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Aisa Hajradinovic',
        'Raiffeisen Bank', '1111111111111117', '117', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (8, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Kenan Halilovic',
        'Raiffeisen Bank', '1111111111111118', '118', '2023-03-25 14:45:36.674000','100.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (9, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Kenan Halilovic',
        'Raiffeisen Bank', '1111111111111119', '119', '2023-03-25 14:45:36.674000','10000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (10, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Kenan Halilovic',
        'Addiko Bank', '1111111111111121', '121', '2023-03-25 14:45:36.674000','100.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (11, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Zerina Ascerija',
        'Raiffeisen Bank', '1111111111111122', '122', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (12, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Amila Lakovic',
        'Addiko Bank', '1111111111111123', '123', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (13, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Dzenan Devedzic',
        'Sparkasse Bank', '1111111111111124', '124', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (14, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Amra Music',
        'Sparkasse Bank', '1111111111111125', '125', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (15, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Damad Butkovic',
        'Sberbank BH', '1111111111111126', '126', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (16, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Delila Smajlovic',
        'Sberbank BH', '1111111111111127', '127', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (17, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Naida Rascic',
        'Sberbank BH', '1111111111111128', '128', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (18, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Donald Trump',
        'ZiraatBank BH', '1111111111111129', '129', '2023-03-25 14:45:36.674000','10.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (19, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Donald Trump',
        'ZiraatBank BH', '1111111111111131', '131', '2023-03-25 14:45:36.674000','10000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (20, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Test Test',
        'UniCredit Bank', '1111111111111132', '132', '2023-03-25 14:45:36.674000','340.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (21, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Test Test',
        'ZiraatBank BH', '1111111111111133', '133', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (22, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Test Test',
        'UniCredit Bank', '1111111111111134', '134', '2023-03-25 14:45:36.674000','10.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (23, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'John Travolta',
        'ZiraatBank BH', '1111111111111135', '135', '2023-03-25 14:45:36.674000','50.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (24, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'John Travolta',
        'Addiko Bank', '1111111111111136', '136', '2023-03-25 14:45:36.674000','1000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (25, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Thomas Shelby',
        'Addiko Bank', '1111111111111137', '137', '2023-03-25 14:45:36.674000','3000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

INSERT INTO public.bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date, balance)
VALUES (26, '2020-03-25 14:45:36.674000', '2020-03-25 14:45:36.674000', 'Thomas Shelby',
        'Addiko Bank', '1111111111111138', '138', '2023-03-25 14:45:36.674000','4000.00') ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;
