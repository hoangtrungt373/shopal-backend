alter table shop.PURCHASE_ORDER drop column if exists CANCEL_REASON;
alter table shop.PURCHASE_ORDER
    add CANCEL_CODE VARCHAR(20)