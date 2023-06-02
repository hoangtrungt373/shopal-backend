alter table shop.CUSTOMER alter column ID_ORIGIN INT NULL
update shop.CUSTOMER
set ID_ORIGIN = CUSTOMER_ID
alter table shop.CUSTOMER alter column ID_ORIGIN INT NOT NULL

alter table shop.ENTERPRISE alter column ID_ORIGIN INT NULL
update shop.ENTERPRISE
set ID_ORIGIN = ENTERPRISE_ID
alter table shop.ENTERPRISE alter column ID_ORIGIN INT NOT NULL


alter table shop.MEMBERSHIP alter column ID_ORIGIN INT NULL
update shop.MEMBERSHIP
set ID_ORIGIN = MEMBERSHIP_ID
alter table shop.MEMBERSHIP alter column ID_ORIGIN INT NOT NULL

alter table shop.COOPERATION_CONTRACT drop column if exists UPDATE_DESCRIPTION;
alter table shop.PRODUCT_POINT drop column if exists UPDATE_DESCRIPTION;