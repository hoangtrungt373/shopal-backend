alter table shop.ANNOUNCEMENT alter column INTERFACE VARCHAR(100) NOT NULL;

create sequence shop.ENTERPRISE_REGISTER_REQUEST start with 1 increment by 1 no maxvalue no cycle cache 20;