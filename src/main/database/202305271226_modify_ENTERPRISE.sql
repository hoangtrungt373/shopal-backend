alter table shop.ENTERPRISE
    add CONTACT_EMAIL VARCHAR(50);
go
update shop.ENTERPRISE
set CONTACT_EMAIL = 'hoangtrungt373@gmail.com';
alter table shop.ENTERPRISE alter column CONTACT_EMAIL VARCHAR(50) NOT NULL;


alter table shop.ENTERPRISE
    add TAX_ID VARCHAR(50);
go
update shop.ENTERPRISE
set TAX_ID = '261513380';
alter table shop.ENTERPRISE alter column TAX_ID VARCHAR(50) NOT NULL;

alter table shop.ENTERPRISE alter column LOGO_URL VARCHAR(50) NULL;