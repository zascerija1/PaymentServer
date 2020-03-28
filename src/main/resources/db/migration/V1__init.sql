CREATE TABLE IF NOT EXISTS questions
(
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    description text COLLATE pg_catalog."default",
    title character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT questions_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS  answers
(
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    text text COLLATE pg_catalog."default",
    question_id bigint NOT NULL,
    CONSTRAINT answers_pkey PRIMARY KEY (id),
    CONSTRAINT fk3erw1a3t0r78st8ty27x6v3g1 FOREIGN KEY (question_id)
        REFERENCES questions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS roles
(
    id bigint NOT NULL DEFAULT nextval('roles_id_seq'::regclass),
    name character varying(60) COLLATE pg_catalog."default",
    CONSTRAINT roles_pkey PRIMARY KEY (id),
    CONSTRAINT uk_nb4h0p6txrmfc0xbrd1kglp9t UNIQUE (name)
);

CREATE TABLE user_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id)
        REFERENCES roles (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhm3fc8664fichr5tu1u9566il FOREIGN KEY (user_id)
        REFERENCES application_users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE IF NOT EXISTS application_users
(
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    first_name text COLLATE pg_catalog."default",
    last_name text COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default",
    answer_id bigint NOT NULL,
    CONSTRAINT application_users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_ha8ehjd5vlqolvals08ssh7o0 UNIQUE (email),
    CONSTRAINT uk_ortc4unvsh9wn07pt9d3nv2n4 UNIQUE (answer_id),
    CONSTRAINT fk8k1bh0dvfnphvin7adchxs1fm FOREIGN KEY (answer_id)
        REFERENCES answers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.bank_accounts
(
    id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    account_owner text COLLATE pg_catalog."default",
    bank_name text COLLATE pg_catalog."default",
    card_number text COLLATE pg_catalog."default",
    cvc text COLLATE pg_catalog."default",
    expiry_date timestamp without time zone NOT NULL,
    CONSTRAINT bank_accounts_pkey PRIMARY KEY (id)
);



INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN') ON CONFLICT ON CONSTRAINT roles_pkey DO NOTHING;
INSERT INTO roles (id, name) VALUES (2, 'ROLE_USER') ON CONFLICT ON CONSTRAINT roles_pkey DO NOTHING;

INSERT INTO bank_accounts (id, created_at, updated_at, account_owner,bank_name, card_number, cvc, expiry_date)
VALUES (1, new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),
        'Dzan Tabakovic', '1234567898767895' ) ON CONFLICT ON CONSTRAINT bank_accounts_pkey DO NOTHING;

