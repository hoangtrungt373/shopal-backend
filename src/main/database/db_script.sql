﻿--create database SHOPAL
--go
--use SHOPAL
--go

-- for authentication
drop table if exists auth.USER_ACCOUNT_TOKEN;
drop table if exists auth.USER_ACCOUNT;

-- for shop
drop table if exists shop.PRODUCT_CATALOG;
drop table if exists shop.COOPERATION_CONTRACT;
drop table if exists shop.PURCHASE_ORDER_DETAIL;
drop table if exists shop.ACCOUNTING;
drop table if exists shop.PRODUCT_POINT;
drop table if exists shop.PURCHASE_ORDER;
drop table if exists shop.MEMBERSHIP;
drop table if exists shop.PRODUCT_IMAGE;
drop table if exists shop.PRODUCT;
drop table if exists shop.ENTERPRISE;
drop table if exists shop.CUSTOMER;
drop table if exists shop.CATALOG;

-- for multilingal
drop table if exists config.DELIVERY_STATUS_LAN;
drop table if exists config.GENDER_LAN;
drop table if exists config.PAYMENT_METHOD_LAN;
drop table if exists config.PRODUCT_SOURCE_LAN;
drop table if exists config.SYS_LANGUAGE;
drop table if exists config.PRODUCT_TYPE_LAN;

drop schema if exists auth;
drop schema if exists shop;
drop schema if exists config;
-----------------------------------------------

go
create schema auth
go
create schema shop
go
create schema config
go

CREATE TABLE auth.USER_ACCOUNT (
	USER_ACCOUNT_ID				INT				NOT NULL	IDENTITY,
	USERNAME					VARCHAR(255),
	EMAIL						VARCHAR(255)	NOT NULL,
	PASSWORD					VARCHAR(255)	NOT NULL,
	ROLE						VARCHAR(20)		NOT NULL,

	CONSTRAINT PK_USER_ACCOUNT PRIMARY KEY (USER_ACCOUNT_ID)
)

CREATE TABLE auth.USER_ACCOUNT_TOKEN (
	USER_ACCOUNT_TOKEN_ID		INT				NOT NULL	IDENTITY,
	USER_ACCOUNT_ID				INT				NOT NULL,
	TOKEN						VARCHAR(255)	NOT NULL,
	TYPE						VARCHAR(20)		NOT NULL,
	REVOKED						BIT				NOT NULL,
	EXPIRED						BIT				NOT NULL,

	CONSTRAINT PK_USER_ACCOUNT_TOKEN PRIMARY KEY (USER_ACCOUNT_TOKEN_ID),

	CONSTRAINT FK_USER_ACCOUNT_TOKEN_USER_ACCOUNT FOREIGN KEY (USER_ACCOUNT_ID) 
		REFERENCES auth.USER_ACCOUNT (USER_ACCOUNT_ID)
)

CREATE TABLE shop.CUSTOMER (
    CUSTOMER_ID					INT				NOT NULL	IDENTITY,
    USER_ACCOUNT_ID				INT				NOT NULL,
	FULL_NAME					NVARCHAR(50),
	PHONE_NUMBER				VARCHAR(12),
	GENDER						VARCHAR(10),
	BIRTHDATE					DATE,
	AVATAR_URL					VARCHAR(255),

    USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_CUSTOMER PRIMARY KEY (CUSTOMER_ID)
);

CREATE TABLE shop.ENTERPRISE (
    ENTERPRISE_ID				INT				NOT NULL	IDENTITY,
    USER_ACCOUNT_ID				INT				NOT NULL,
	ENTERPRISE_NAME				NVARCHAR(50)	NOT NULL,
	PHONE_NUMBER				VARCHAR(12)		NOT NULL,
	ADRESS						VARCHAR(255)	NOT NULL,
	WEBSITE_URL					VARCHAR(255)	NOT NULL,
	AVATAR_URL					VARCHAR(255)	NOT NULL,

    USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_ENTERPRISE PRIMARY KEY (ENTERPRISE_ID)
)

CREATE TABLE shop.MEMBERSHIP (
	MEMBERSHIP_ID				INT				NOT NULL,
	CUSTOMER_ID					INT				NOT NULL,
	ENTERPRISE_ID				INT				NOT NULL,
	AVAILABLE_POINT				DECIMAL(17,2)	NOT NULL,
	
	USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_MEMBERSHIP PRIMARY KEY (MEMBERSHIP_ID),

	CONSTRAINT FK_MEMBERSHIP_CUSTOMER
		FOREIGN KEY (CUSTOMER_ID) REFERENCES shop.CUSTOMER (CUSTOMER_ID),

	CONSTRAINT FK_MEMBERSHIP_ENTERPRISE
		FOREIGN KEY (ENTERPRISE_ID) REFERENCES shop.ENTERPRISE (ENTERPRISE_ID)
);

CREATE TABLE shop.CATALOG (
	CATALOG_ID					INT				NOT NULL	IDENTITY,
	PRODUCT_TYPE				VARCHAR(50)		NOT NULL,
	LOGO_URL					VARCHAR(255)	NOT NULL,
	LEVEL						INT				NOT NULL,

    USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_CATALOG PRIMARY KEY (CATALOG_ID)
);

CREATE TABLE shop.PRODUCT (
	PRODUCT_ID					INT				NOT NULL	IDENTITY,
	SKU							VARCHAR(50),
	PRODUCT_NAME				NVARCHAR(255)	NOT NULL,
	QUANTITY_IN_STOCK			INT				NOT NULL,
	DESCRIPTION_CONTENT_URL		VARCHAR(MAX),
	ACTIVE						BIT				NOT NULL,

	USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_PRODUCT PRIMARY KEY (PRODUCT_ID),
);

CREATE TABLE shop.PRODUCT_IMAGE (
	PRODUCT_IMAGE_ID			INT				NOT NULL	IDENTITY,
	PRODUCT_ID					INT				NOT NULL,
	IMAGE_URL					VARCHAR(255)	NOT NULL,
	IS_MAIN_IMG					BIT				NOT NULL,

    USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_PRODUCT_IMAGE PRIMARY KEY (PRODUCT_IMAGE_ID),

	CONSTRAINT FK_PRODUCT_IMAGE_PRODUCT
		FOREIGN KEY (PRODUCT_ID) REFERENCES shop.PRODUCT(PRODUCT_ID)
);

CREATE TABLE shop.PRODUCT_CATALOG (
	PRODUCT_CATALOG_ID			INT				NOT NULL	IDENTITY,
	PRODUCT_ID					INT				NOT NULL,
	CATALOG_ID					INT				NOT NULL,

    USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_PRODUCT_CATALOG PRIMARY KEY (PRODUCT_CATALOG_ID),

	CONSTRAINT FK_PRODUCT_CATALOG_PRODUCT
		FOREIGN KEY (PRODUCT_ID) REFERENCES shop.PRODUCT (PRODUCT_ID),
		
	CONSTRAINT FK_PRODUCT_CATALOG_CATALOG
		FOREIGN KEY (CATALOG_ID) REFERENCES shop.CATALOG (CATALOG_ID)
);

CREATE TABLE shop.PRODUCT_POINT (
	PRODUCT_POINT_ID			INT				NOT NULL	IDENTITY,
	ENTERPRISE_ID				INT,
	PRODUCT_ID					INT				NOT NULL,
	INITIAL_CASH				DECIMAL(17,2)	NOT NULL,
	POINT_EXCHANGE				DECIMAL(17,2),
	POINT_NAME					NVARCHAR(255),
	SOURCE						VARCHAR(20)		NOT NULL,
	ID_ORIGIN					INT				NOT NULL,
	UPDATE_DESCRIPTION			NVARCHAR(4000),
	ACTIVE						BIT				NOT NULL,

    USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_PRODUCT_POINT PRIMARY KEY (PRODUCT_POINT_ID),

	CONSTRAINT FK_PRODUCT_POINT_ENTERPRISE
		FOREIGN KEY (ENTERPRISE_ID) REFERENCES shop.ENTERPRISE (ENTERPRISE_ID),

	CONSTRAINT FK_PRODUCT_POINT_PRODUCT
		FOREIGN KEY (PRODUCT_ID) REFERENCES shop.PRODUCT (PRODUCT_ID)
);

CREATE TABLE shop.PURCHASE_ORDER (
	PURCHASE_ORDER_ID			INT				NOT NULL	IDENTITY,
	CUSTOMER_ID					INT				NOT NULL,
	ENTERPRISE_ID				INT				NOT NULL,
	ORDER_TOTAL_POINT_EXHANGE	DECIMAL(17,2)	NOT NULL,
	PAYMENT_METHOD				VARCHAR(20)		NOT NULL,
	ORDER_DATE					DATETIME		NOT NULL,
	DELIVERY_STATUS				VARCHAR(20)		NOT NULL,
	DELIVERY_DATE				DATETIME,

    USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_PURCHASE_ORDER PRIMARY KEY (PURCHASE_ORDER_ID),

	CONSTRAINT FK_PURCHASE_ORDER_CUSTOMER
		FOREIGN KEY (CUSTOMER_ID) REFERENCES shop.CUSTOMER (CUSTOMER_ID),
		
	CONSTRAINT FK_PURCHASE_ORDER_ENTERPRISE
		FOREIGN KEY (ENTERPRISE_ID) REFERENCES shop.ENTERPRISE (ENTERPRISE_ID)
);

CREATE TABLE shop.PURCHASE_ORDER_DETAIL (
	PURCHASE_ORDER_DETAIL_ID	INT				NOT NULL	IDENTITY,
	PURCHASE_ORDER_ID			INT				NOT NULL,
	PRODUCT_ID					INT				NOT NULL, 
	PRODUCT_POINT_ID			INT				NOT NULL,
	POINT_EXCHANGE				DECIMAL(17,2)	NOT NULL,
	AMOUNT						INT				NOT NULL,
	TOTAL_POINT_EXCHANGE		DECIMAL(17,2)	NOT NULL,
	
    USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_PURCHASE_ORDER_DETAIL PRIMARY KEY (PURCHASE_ORDER_DETAIL_ID),

	CONSTRAINT FK_PURCHASE_ORDER_DETAIL_PURCHASE_ORDER
		FOREIGN KEY (PURCHASE_ORDER_ID) REFERENCES shop.PURCHASE_ORDER (PURCHASE_ORDER_ID),

	CONSTRAINT FK_PURCHASE_ORDER_DETAIL_PRODUCT
		FOREIGN KEY (PRODUCT_ID) REFERENCES shop.PRODUCT (PRODUCT_ID),

	CONSTRAINT FK_PURCHASE_ORDER_DETAIL_PRODUCT_POINT
		FOREIGN KEY (PRODUCT_POINT_ID) REFERENCES shop.PRODUCT_POINT (PRODUCT_POINT_ID),
);


CREATE TABLE shop.ACCOUNTING (
	ACCOUNTING_ID				INT				NOT NULL	IDENTITY,
	ENTERPRISE_ID				INT				NOT NULL,
	START_DATE					DATE			NOT NULL,
	END_DATE					DATE			NOT NULL, 
	TOTAL_INCOME				DECIMAL(17,2)	NOT NULL,
	TOTAL_COMMISSION			DECIMAL(17,2)	NOT NULL,
	ALREADY_PAID				BIT				NOT NULL,
	PAYMENT_DATE				DATE,
	PAYMENT_METHOD				VARCHAR(20),

    USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_ACCOUNTTING PRIMARY KEY (ACCOUNTING_ID),

	CONSTRAINT FK_ACCOUNTING_ENTERPRISE
		FOREIGN KEY (ENTERPRISE_ID) REFERENCES shop.ENTERPRISE(ENTERPRISE_ID)
);

CREATE TABLE shop.COOPERATION_CONTRACT (
	COOPERATION_CONTRACT_ID		INT				NOT NULL	IDENTITY,
	ENTERPRISE_ID				INT				NOT NULL,
	ACCOUNTING_ID				INT,
	START_DATE					DATE			NOT NULL,
	END_DATE					DATE			NOT NULL,
	COMMISSION_RATE				DECIMAL(17,2)	NOT NULL,
	CASH_PER_POINT				DECIMAL(17,2)	NOT NULL,
	POINT_NAME					NVARCHAR(255)	NOT NULL,
	ID_ORIGIN					INT				NOT NULL,
	UPDATE_DESCRIPTION			NVARCHAR(4000),
	ACTIVE						BIT				NOT NULL,

    USR_LOG_I					VARCHAR(128)	NOT NULL,
    USR_LOG_U					VARCHAR(128)	NOT NULL,
    DATE_LOG_I					DATETIME2		NOT NULL,
    DATE_LOG_U					DATETIME2		NOT NULL,
    VERSION						INT				NOT NULL,

	CONSTRAINT PK_COOPERATION_CONTRACT PRIMARY KEY (COOPERATION_CONTRACT_ID),

	CONSTRAINT FK_COOPERATION_CONTRACT_ENTERPRISE
		FOREIGN KEY (ENTERPRISE_ID) REFERENCES shop.ENTERPRISE(ENTERPRISE_ID),
		
	CONSTRAINT FK_COOPERATION_CONTRACT_ACCOUNTING
		FOREIGN KEY (ACCOUNTING_ID) REFERENCES shop.ACCOUNTING (ACCOUNTING_ID)
);

CREATE TABLE config.GENDER_LAN (
	GENDER_LAN_ID				INT				NOT NULL	IDENTITY,
	CODE						VARCHAR(50)		NOT NULL,
	LAN							VARCHAR(4)		NOT NULL,
	DESCRIPTION					NVARCHAR(255)	NOT NULL

	CONSTRAINT PK_GENDER_LAN PRIMARY KEY (GENDER_LAN_ID),
);

CREATE TABLE config.DELIVERY_STATUS_LAN (
	DELIVERY_STATUS_LAN_ID		INT				NOT NULL	IDENTITY,
	CODE						VARCHAR(50)		NOT NULL,
	LAN							VARCHAR(4)		NOT NULL,
	DESCRIPTION					NVARCHAR(255)	NOT NULL

	CONSTRAINT PK_DELIVERY_STATUS_LAN PRIMARY KEY (DELIVERY_STATUS_LAN_ID),
);

CREATE TABLE config.PAYMENT_METHOD_LAN (
	PAYMENT_METHOD_LAN_ID		INT				NOT NULL	IDENTITY,
	CODE						VARCHAR(50)		NOT NULL,
	LAN							VARCHAR(4)		NOT NULL,
	DESCRIPTION					NVARCHAR(255)	NOT NULL

	CONSTRAINT PK_PAYMENT_METHOD_LAN PRIMARY KEY (PAYMENT_METHOD_LAN_ID),
);

CREATE TABLE config.PRODUCT_SOURCE_LAN (
	PRODUCT_SOURCE_LAN_ID		INT				NOT NULL	IDENTITY,
	CODE						VARCHAR(50)		NOT NULL,
	LAN							VARCHAR(4)		NOT NULL,
	DESCRIPTION					NVARCHAR(255)	NOT NULL

	CONSTRAINT PK_PRODUCT_SOURCE_LAN PRIMARY KEY (PRODUCT_SOURCE_LAN_ID),
);

CREATE TABLE config.PRODUCT_TYPE_LAN (
	PRODUCT_TYPE_LAN_ID			INT				NOT NULL	IDENTITY,
	CODE						VARCHAR(50)		NOT NULL,
	LAN							VARCHAR(4)		NOT NULL,
	DESCRIPTION					NVARCHAR(255)	NOT NULL

	CONSTRAINT PK_PRODUCT_TYPE_LAN PRIMARY KEY (PRODUCT_TYPE_LAN_ID),
);

CREATE TABLE config.SYS_LANGUAGE (
	SYS_LANGUAGE_ID				INT				NOT NULL	IDENTITY,
	CODE						VARCHAR(50)		NOT NULL,
	DESCRIPTION					NVARCHAR(255)	NOT NULL,
	ACTIVE						BIT				NOT NULL,

	CONSTRAINT PK_SYS_LANGUAGE PRIMARY KEY (SYS_LANGUAGE_ID),
);






