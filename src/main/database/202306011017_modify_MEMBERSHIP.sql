alter table shop.MEMBERSHIP alter column REGISTER_EMAIL VARCHAR(50) NULL
alter table shop.MEMBERSHIP alter column REGISTER_PHONE_NUMBER VARCHAR(50) NULL

alter table shop.ACCOUNTING
    add PAYMENT_DETAIL VARCHAR(4000);


alter table staging.STAG_CUSTOMER alter column REGISTER_EMAIL VARCHAR(255) NULL
alter table staging.STAG_CUSTOMER alter column REGISTER_PHONE_NUMBER VARCHAR(255) NULL
alter table staging.STAG_CUSTOMER alter column FULL_NAME VARCHAR(255) NULL