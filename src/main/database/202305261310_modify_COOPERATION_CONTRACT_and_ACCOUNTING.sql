--drop ACCOUNTING_ID in COOPERATION_CONTRACT
alter table shop.COOPERATION_CONTRACT drop constraint if exists FK_COOPERATION_CONTRACT_ACCOUNTING;
alter table shop.COOPERATION_CONTRACT drop column if exists ACCOUNTING_ID;

--add COOPERATION_CONTRACT_ID in ACCOUNTING
alter table shop.ACCOUNTING
    add COOPERATION_CONTRACT_ID INT;
go
alter table shop.ACCOUNTING
    add constraint FK_ACCOUNTING_COOPERATION_CONTRACT
        foreign key (COOPERATION_CONTRACT_ID) references shop.COOPERATION_CONTRACT (COOPERATION_CONTRACT_ID);

update shop.ACCOUNTING
set COOPERATION_CONTRACT_ID = (select top(1) COOPERATION_CONTRACT_ID from shop.COOPERATION_CONTRACT)
where COOPERATION_CONTRACT_ID is null;

alter table shop.ACCOUNTING alter column COOPERATION_CONTRACT_ID INT NOT NULL;
