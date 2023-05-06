BEGIN TRY
BEGIN TRANSACTION


---------------------------------------------------------------------------------------------------------------------
DECLARE @log_user VARCHAR(20) = 'admin_1'

delete from config.SYS_LANGUAGE;
delete from config.PRODUCT_TYPE_LAN;
delete from config.GENDER_LAN;
delete from config.ORDER_STATUS_LAN;
delete from config.PAYMENT_STATUS_LAN;
delete from config.CONTRACT_STATUS_LAN;
delete from config.PRODUCT_STATUS_LAN;

delete from shop.COOPERATION_CONTRACT;
delete from shop.ACCOUNTING;
delete from shop.MEMBERSHIP;
delete from shop.PURCHASE_ORDER_DETAIL;
delete from shop.PURCHASE_ORDER;
delete from shop.PRODUCT_CART;
delete from shop.PRODUCT_IMAGE;
delete from shop.PRODUCT_POINT;
delete from shop.PRODUCT_CATALOG;
delete from shop.PRODUCT;
delete from shop.ENTERPRISE;
delete from shop.CUSTOMER;
delete from shop.CATALOG;

delete from auth.USER_ACCOUNT_TOKEN;
delete from auth.USER_ACCOUNT --where ROLE = 'ENTERPRISE_MANAGER';

delete from staging.STAG_CUSTOMER;

-----------------------------------------------------------
--[SYS_LANGUAGE]
SET IDENTITY_INSERT [config].[SYS_LANGUAGE] ON; 
INSERT INTO [config].[SYS_LANGUAGE]
	([SYS_LANGUAGE_ID]
	,[CODE]
	,[DESCRIPTION]
	,[ACTIVE])
VALUES 
	(1, 'VN', 'Vietnamese', 1),
	(2, 'EN', 'English', 0);
SET IDENTITY_INSERT [config].[SYS_LANGUAGE] OFF; 

-----------------------------------------------------------
--[PRODUCT_TYPE_LAN]
SET IDENTITY_INSERT [config].[PRODUCT_TYPE_LAN] ON; 
INSERT INTO [config].[PRODUCT_TYPE_LAN]
	([PRODUCT_TYPE_LAN_ID]
	,[CODE]
	,[LAN]
	,[DESCRIPTION])
VALUES
	(1,'ELECTRONIC_DEVICES','VN',N'Thiết bị điện tử'),
	(2,'TV_AND_HOME_APPLIANCES','VN',N'TV và thiết bị điện gia dụng'),
	(3,'HEALTH_AND_BEAUTY','VN',N'Sức khỏe và làm đẹp'),
	(4,'BABIES_AND_TOYS','VN',N'Hàng mẹ, bé và đồ chơi'),
	(5,'GROCERIES_AND_PETS','VN',N'Siêu thị tạp hóa'),
	(6,'HOME_AND_LIFESTYLE','VN',N'Hàng gia dụng và đời sống'),
	(7,'WOMEN_FASHION_AND_ACCESSORIES','VN',N'Thời trang và phụ kiện nữ'),
	(8,'MEN_FASHION_AND_ACCESSORIES','VN',N'Thời trang và phụ kiện nam'),
	(9,'SPORTS_AND_TRAVEL','VN',N'Thể thao và du lịch'),
	(10,'AUTOMOTIVE_AND_MOTORCYCLES','VN',N'Ô tô xe máy và thiết bị định vị'),
	(11,'ELECTRONIC_DEVICES','EN',N'Electronic Devices'),
	(12,'TV_AND_HOME_APPLIANCES','EN',N'TV & Home Appliances'),
	(13,'HEALTH_AND_BEAUTY','EN',N'Health & Beauty'),
	(14,'BABIES_AND_TOYS','EN',N'Babies & Toys'),
	(15,'GROCERIES_AND_PETS','EN',N'Groceries & Pets'),
	(16,'HOME_AND_LIFESTYLE','EN',N'Home & LifeStyle'),
	(17,'WOMEN_FASHION_AND_ACCESSORIES','EN',N'Women Fashion & Accessories'),
	(18,'MEN_FASHION_AND_ACCESSORIES','EN',N'Mem Fashion & Accessories'),
	(19,'SPORTS_AND_TRAVEL','EN',N'Sports & Travel'),
	(20,'AUTOMOTIVE_AND_MOTORCYCLES','EN',N'Automotive & Motorcycles'),
	(21,'MOBILES','VN',N'Điện thoại di động'),
	(22,'TABLETS','VN',N'Máy tính bảng'),
	(23,'LAPTOPS','VN',N'Laptop'),
	(24,'AUDIO_AND_MOTORCYCLES','VN',N'Âm thanh'),
	(25,'CONSOLE_GAMING','VN',N'Console gaming'),
	(26,'MOBILES','EN',N'Mobiles'),
	(27,'TABLETS','EN',N'Tablets'),
	(28,'LAPTOPS','EN',N'Laptops'),
	(29,'AUDIO_AND_MOTORCYCLES','EN',N'Audio & Motorcycles'),
	(30,'CONSOLE_GAMING','EN',N'Console gaming');

SET IDENTITY_INSERT [config].[PRODUCT_TYPE_LAN] OFF; 

-----------------------------------------------------------
--[GENDER_LAN]
SET IDENTITY_INSERT [config].[GENDER_LAN] ON; 
INSERT INTO [config].[GENDER_LAN]
	([GENDER_LAN_ID]
	,[CODE]
	,[LAN]
	,[DESCRIPTION])
VALUES
	(1,'MALE','VN',N'Nam'),
	(2,'FEMALE','VN',N'Nữ'),
	(3,'MALE','EN',N'Male'),
	(4,'FEMALE','EN',N'Female');
SET IDENTITY_INSERT [config].[GENDER_LAN] OFF; 

-----------------------------------------------------------
--[ORDER_STATUS_LAN]
SET IDENTITY_INSERT [config].[ORDER_STATUS_LAN] ON; 
INSERT INTO [config].[ORDER_STATUS_LAN]
	([ORDER_STATUS_LAN_ID]
	,[CODE]
	,[LAN]
	,[DESCRIPTION])
VALUES
	(1,'OPEN','VN',N'Đã đặt hàng'),
	(2,'PROCESSING','VN',N'Đang xử lý'),
	(3,'IN_TRANSIT','VN',N'Đang vận chuyển'),
	(4,'DELIVERED','VN',N'Đã giao'),
	(5,'CANCELLED','VN',N'Đã hủy'),
	(6,'OPEN','EN',N'Open'),
	(7,'PROCESSING','EN',N'Processing'),
	(8,'IN_TRANSIT','EN',N'In transit'),
	(9,'DELIVERED','EN',N'Delivered'),
	(10,'CANCELLED','EN',N'Cancelled');
SET IDENTITY_INSERT [config].[ORDER_STATUS_LAN] OFF; 

-----------------------------------------------------------
--[PAYMENT_STATUS_LAN]
SET IDENTITY_INSERT [config].[PAYMENT_STATUS_LAN] ON; 
INSERT INTO [config].[PAYMENT_STATUS_LAN]
	([PAYMENT_STATUS_LAN_ID]
	,[CODE]
	,[LAN]
	,[DESCRIPTION])
VALUES
	(1,'UNPAID','VN',N'Chưa thanh toán'),
	(2,'PAID','VN',N'Đã thanh toán'),
	(3,'UNPAID','EN',N'Un paid'),
	(4,'PAID','EN',N'Paid');
SET IDENTITY_INSERT [config].[PAYMENT_STATUS_LAN] OFF; 

-----------------------------------------------------------
--[CONTRACT_STATUS_LAN]
SET IDENTITY_INSERT [config].[CONTRACT_STATUS_LAN] ON; 
INSERT INTO [config].[CONTRACT_STATUS_LAN]
	([CONTRACT_STATUS_LAN_ID]
	,[CODE]
	,[LAN]
	,[DESCRIPTION])
VALUES
	(1,'INACTIVE','VN',N'Hết hạn'),
	(2,'ACTIVE','VN',N'Đang áp dụng'),
	(3,'PENDING','VN', N'Chưa áp dụng'),
	(4,'INACTIVE','EN',N'Inactive'),
	(5,'ACTIVE','EN',N'Active'),
	(6,'PENDING','VN', N'Pending');
SET IDENTITY_INSERT [config].[CONTRACT_STATUS_LAN] OFF; 

--[PRODUCT_STATUS_LAN]
SET IDENTITY_INSERT [config].[PRODUCT_STATUS_LAN] ON; 
INSERT INTO [config].[PRODUCT_STATUS_LAN]
	([PRODUCT_STATUS_LAN_ID]
	,[CODE]
	,[LAN]
	,[DESCRIPTION])
VALUES
	(1,'INACTIVE','VN',N'Không được bán'),
	(2,'ACTIVE','VN',N'Đang được bán'),
	(3,'INACTIVE','EN',N'Inactive'),
	(4,'ACTIVE','EN',N'Active');
SET IDENTITY_INSERT [config].[PRODUCT_STATUS_LAN] OFF; 

-----------------------------------------------------------
--[USER_ACCOUNT]
SET IDENTITY_INSERT [auth].[USER_ACCOUNT] ON; 
INSERT INTO [auth].[USER_ACCOUNT]
    ([USER_ACCOUNT_ID]
	,[USERNAME]
    ,[EMAIL]
    ,[PASSWORD]
    ,[ROLE])
VALUES 
	(1001, 'customer1001', 'customer1001@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'CUSTOMER'), 	-- customer main testing
	(1002, 'customer1002', 'customer1002@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'CUSTOMER'),
	(1003, 'customer1003', 'customer1003@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'CUSTOMER'),
	(1004, 'customer1004', 'customer1004@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'CUSTOMER'),
	(1005, 'customer1005', 'customer1005@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'CUSTOMER'),
	(1006, 'customer1006', 'customer1006@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'CUSTOMER'),
	(1007, 'customer1007', 'customer1007@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'CUSTOMER'),
	(1008, 'customer1008', 'customer1008@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'CUSTOMER'),
	(1009, 'customer1009', 'customer1009@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'CUSTOMER'),
	(1010, 'customer1010', 'customer1010@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'CUSTOMER'),

	(2001, 'circlek2001', 'ciclek2001@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'ENTERPRISE_MANAGER'), 	-- enterprise main testing
	(2002, 'familymark2002', 'familymark2002@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'ENTERPRISE_MANAGER'),
	(2003, 'phuclong2003', 'phuclong2003@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'ENTERPRISE_MANAGER'),
	(2004, 'ministop2004', 'ministop2004@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'ENTERPRISE_MANAGER'),
	(2005, 'thegioididong2005', 'thegioididong2005@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'ENTERPRISE_MANAGER'),

	(3001, 'admin1', 'admin1@gmail.com', '$2a$10$v1Y4Bu6Nqa.2q22sFMPnu.d3Mp5gtXX9CpMq33raTZRtUi1xiFbNa', 'ADMIN'); --admin main testing

SET IDENTITY_INSERT [auth].[USER_ACCOUNT] OFF; 

-----------------------------------------------------------
--[CATALOG]
SET IDENTITY_INSERT [shop].[CATALOG] ON; 
INSERT INTO [shop].[CATALOG]
	([CATALOG_ID]
	,[PRODUCT_TYPE]
	,[LOGO_URL]
	,[LEVEL]
	,[PARENT_CATALOG_ID]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(1, 'ELECTRONIC_DEVICES', 'elctronic_devices.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2, 'TV_AND_HOME_APPLIANCES', 'tv_and_home_appliances.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(3, 'HEALTH_AND_BEAUTY', 'health_and_beauty.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(4, 'BABIES_AND_TOYS', 'babies_and_toys.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(5, 'GROCERIES_AND_PETS', 'groceries_and_pets.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(6, 'HOME_AND_LIFESTYLE', 'home_and_lifestyle.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(7, 'WOMEN_FASHION_AND_ACCESSORIES', 'women_fashion_and_accessories.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(8, 'MEN_FASHION_AND_ACCESSORIES', 'men_fashion_and_accessories.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(9, 'SPORTS_AND_TRAVEL', 'sports_and_travel.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(10, 'AUTOMOTIVE_AND_MOTORCYCLES', 'automotive_and_motorcycles.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(11, 'MOBILES', 'mobiles.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(12, 'TABLETS', 'tablets.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(13, 'LAPTOPS', 'laptops.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(14, 'AUDIO_AND_MOTORCYCLES', 'audio_and_motorcycles.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(15, 'CONSOLE_GAMING', 'console_gaming.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
SET IDENTITY_INSERT [shop].[CATALOG] OFF; 

SET IDENTITY_INSERT [shop].[CUSTOMER] ON; 
INSERT INTO [shop].[CUSTOMER]
	([CUSTOMER_ID]
	,[USER_ACCOUNT_ID]
	,[FULL_NAME]
	,[PHONE_NUMBER]
	,[GENDER]
	,[BIRTHDATE]
	,[AVATAR_URL]
	,[ADDRESS]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(1001, 1001, N'Phạm Như Quyền', '0359561001', 'MALE','1999-01-01', '1001.png',N'64 Trương Định, Phường 7, Võ Thị Sáu, Quận 3, Thành phố Hồ Chí Minh', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(1002, 1002, N'Quân Nguyễn', '0359561002', 'MALE','1998-02-01', '1002.png',N'64 Trương Định, Phường 7, Võ Thị Sáu, Quận 3, Thành phố Hồ Chí Minh', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(1003, 1003, N'Minh Quân', '0359561003', 'MALE','1997-03-01', '1003.png',N'64 Trương Định, Phường 7, Võ Thị Sáu, Quận 3, Thành phố Hồ Chí Minh', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(1004, 1004, N'Duy Nguyễn', '0359561004', 'MALE','1996-04-01', '1004.png',N'64 Trương Định, Phường 7, Võ Thị Sáu, Quận 3, Thành phố Hồ Chí Minh', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(1005, 1005, N'Tiến Lê', '0359561005', 'MALE','1995-05-01', '1005.png',N'64 Trương Định, Phường 7, Võ Thị Sáu, Quận 3, Thành phố Hồ Chí Minh', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(1006, 1006, N'Minh Khánh', '0359561006', 'MALE','1999-06-01', '1006.png',N'64 Trương Định, Phường 7, Võ Thị Sáu, Quận 3, Thành phố Hồ Chí Minh', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(1007, 1007, N'Minh Trí', '0359561007', 'MALE','1998-07-01', '1007.png',N'64 Trương Định, Phường 7, Võ Thị Sáu, Quận 3, Thành phố Hồ Chí Minh', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(1008, 1008, N'Gia Hồng', '0359561008', 'FEMALE','1997-08-01', '1008.png',N'64 Trương Định, Phường 7, Võ Thị Sáu, Quận 3, Thành phố Hồ Chí Minh', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(1009, 1009, N'Lê Đào Thảo Tiên', '0359561009', 'FEMALE','1996-09-01', '1009.png',N'64 Trương Định, Phường 7, Võ Thị Sáu, Quận 3, Thành phố Hồ Chí Minh', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(1010, 1010, N'Khắc Trí', '0359561010', 'MALE','1995-10-01', '1010.png',N'64 Trương Định, Phường 7, Võ Thị Sáu, Quận 3, Thành phố Hồ Chí Minh', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
SET IDENTITY_INSERT [shop].[CUSTOMER] OFF;

-----------------------------------------------------------
--[ENTERPRISE]
SET IDENTITY_INSERT [shop].[ENTERPRISE] ON; 
INSERT INTO [shop].[ENTERPRISE]
	([ENTERPRISE_ID]
	,[USER_ACCOUNT_ID]
	,[ENTERPRISE_NAME]
	,[PHONE_NUMBER]
	,[ADRESS]
	,[WEBSITE_URL]
	,[LOGO_URL]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(2001, 2001, N'Circle K', '0359562001', N'60 Bùi Thị Xuân, Phường Phạm Ngũ Lão, Quận 1, Tp.Hồ Chí Minh, Việt Nam', 'https://www.circlek.com.vn/vi/', 'circlek.png', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 2002, N'Family Mark', '0359562002', N'Tầng 8, Tòa nhà An Khánh, số 63 Phạm Ngọc Thạch, Phường Võ Thị Sáu, Quận 3, Tp. Hồ Chí Minh', 'https://www.famima.vn/', 'familymart.png', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 2003, N'Phúc Long', '0359562003', N'Phòng 702, Tầng 7, Tòa nhà Central Plaza, số 17 Lê Duẩn, phường Bến Nghé, quận 1, Hồ Chí Minh', 'https://phuclong.com.vn/', 'phuclong.png', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 2004, N'Ministop', '0359562004', N'215 Điện Biên Phủ, Phường 15, Quận Bình Thạnh, Thành phố Hồ Chí Minh, Việt Nam', 'https://www.ministop.vn/', 'ministop.png', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 2005, N'Thế Giới Di Động', '0359562005', N'128 Trần Quang Khải, P. Tân Định, Q.1, TP.Hồ Chí Minh', 'https://www.thegioididong.com/', 'thegioididong.png', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
SET IDENTITY_INSERT [shop].[ENTERPRISE] OFF

-----------------------------------------------------------
--[PRODUCT]
SET IDENTITY_INSERT [shop].[PRODUCT] ON; 
INSERT INTO [shop].[PRODUCT]
	([PRODUCT_ID]
	,[SKU]
	,[PRODUCT_NAME]
	,[QUANTITY_IN_STOCK]
	,[DESCRIPTION_CONTENT_URL]
	,[PRODUCT_STATUS]
	,[INITIAL_CASH]
	,[RATING]
	,[INPUT_DATE]
	,[EXPIRATION_DATE]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(1,'M000001', N'Máy xay sinh tố Bear LLJ-D04A1', 
		100, '1.html', 'ACTIVE', '100000', '3.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2,'M000002', N'Giày thể thao nam G2 kiểu sneaker trắng cổ mid bằng da microfiber cao cấp chống nhăn độn đế tăng chiều cao', 
		200, '2.html', 'ACTIVE', '150000', '4', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(3,'M000003', N'Chậu cây cảnh giả cây thông bonsai xanh mát giống thật trang trí bàn kệ tủ tiểu cảnh Anzzar-02', 
		300, '3.html', 'ACTIVE', '160000', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(4,'M000004', N'Đèn Ngủ Silicon Hình Chú Vịt Dễ Thương Dùng Trang Trí Phòng Ngủ', 
		400, '4.html', 'ACTIVE', '70000', '5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(5,'M000005', N'Quạt mini, quạt mini để bàn cực mát xoay 720 độ kẹp bàn chắc chắn cho dân văn phòng, kẹp xe đẩy cho bé', 
		500, '5.html', 'ACTIVE', '80000', '3', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

SET IDENTITY_INSERT [shop].[PRODUCT] OFF; 

-----------------------------------------------------------
--[PRODUCT_CATALOG]
SET IDENTITY_INSERT [shop].[PRODUCT_CATALOG] ON; 
INSERT INTO [shop].[PRODUCT_CATALOG]
	([PRODUCT_CATALOG_ID]
	,[PRODUCT_ID]
	,[CATALOG_ID]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(1, 1, 11, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2, 2, 12, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(3, 3, 13, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(4, 4, 14, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(5, 5, 15, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
SET IDENTITY_INSERT [shop].[PRODUCT_CATALOG] OFF; 

-----------------------------------------------------------
--[PRODUCT_POINT]
SET IDENTITY_INSERT [shop].[PRODUCT_POINT] ON; 
INSERT INTO [shop].[PRODUCT_POINT]
	([PRODUCT_POINT_ID]
	,[ENTERPRISE_ID]
	,[PRODUCT_ID]
	,[INITIAL_CASH]
	,[POINT_EXCHANGE]
	,[POINT_NAME]
	,[ID_ORIGIN]
	,[UPDATE_DESCRIPTION]
	,[ACTIVE]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(1,  2001, 1, '10000', '100', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2,  2002, 1, '20000', '200', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(3,  2003, 1, '30000', '300', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(4,  2004, 1, '40000', '400', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(5,  2005, 1, '50000', '500', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(6,  2001, 2, '11000', '110', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(7,  2002, 2, '21000', '210', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(8,  2003, 2, '31000', '310', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(9,  2004, 2, '41000', '410', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(10, 2005, 2, '51000', '510', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(11, 2001, 3, '12000', '120', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(12, 2002, 3, '22000', '220', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(13, 2003, 3, '32000', '320', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(14, 2004, 3, '42000', '420', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(15, 2005, 3, '52000', '520', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(16, 2001, 4, '13000', '130', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(17, 2002, 4, '23000', '230', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(18, 2003, 4, '33000', '330', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(19, 2004, 4, '43000', '430', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(20, 2005, 4, '53000', '530', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(21, 2001, 5, '14000', '140', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(22, 2002, 5, '24000', '240', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(23, 2003, 5, '34000', '340', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(24, 2004, 5, '44000', '440', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(25, 2005, 5, '54000', '540', null, 'SYSTEM', 1, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
SET IDENTITY_INSERT [shop].[PRODUCT_POINT] OFF; 

-----------------------------------------------------------
--[PRODUCT_IMAGE]
SET IDENTITY_INSERT [shop].[PRODUCT_IMAGE] ON; 
INSERT INTO [shop].[PRODUCT_IMAGE]
	([PRODUCT_IMAGE_ID]
	,[PRODUCT_ID]
	,[IMAGE_URL]
	,[IS_MAIN_IMG]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(1,	 1, '1_1.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2,	 1, '1_2.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(3,	 1, '1_3.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(4,	 1, '1_4.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(5,	 1, '1_5.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(6,	 2, '2_1.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(7,	 2, '2_2.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(8,	 2, '2_3.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(9,	 2, '2_4.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(10, 2, '2_5.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(11, 3, '3_1.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(12, 3, '3_2.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(13, 3, '3_3.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(14, 3, '3_4.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(15, 3, '3_5.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(16, 4, '4_1.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(17, 4, '4_2.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(18, 4, '4_3.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(19, 4, '4_4.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(20, 4, '4_5.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(21, 5, '5_1.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(22, 5, '5_2.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(23, 5, '5_3.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(24, 5, '5_4.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(25, 5, '5_5.png', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
SET IDENTITY_INSERT [shop].[PRODUCT_IMAGE] OFF; 

-----------------------------------------------------------
--[MEMBERSHIP]
SET IDENTITY_INSERT [shop].[MEMBERSHIP] ON; 
INSERT INTO [shop].[MEMBERSHIP]
	([MEMBERSHIP_ID]
	,[CUSTOMER_ID]
	,[ENTERPRISE_ID]
	,[AVAILABLE_POINT]
	,[REGISTER_EMAIL]
	,[REGISTER_PHONE_NUMBER]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	-- customer main testing
	(1, 1001, 2001, '1000', 'customer1001@gmail.com', '0359561001', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(2, 1001, 2002, '2000', 'customer1002@gmail.com', '0359561002', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(3, 1001, 2003, '3000', 'customer1003@gmail.com', '0359561003',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(4, 1001, 2004, '4000', 'customer1004@gmail.com', '0359561004',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),

	-- enterprise main testing
	(5, 1002, 2001, '4000', 'customer1005@gmail.com', '0359561005',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(6, 1003, 2001, '5000', 'customer1006@gmail.com', '0359561006',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(7, 1004, 2001, '6000', 'customer1007@gmail.com', '0359561007',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(8, 1005, 2001, '7000', 'customer1008@gmail.com', '0359561008',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(9, 1006, 2001, '8000', 'customer1009@gmail.com', '0359561009',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(10, 1007, 2001, '9000', 'customer1010@gmail.com', '0359561010',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(11, 1008, 2001, '11000', 'customer1011@gmail.com', '0359561011',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(12, 1009, 2001, '12000', 'customer1012@gmail.com', '0359561012',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(13, 1010, 2001, '13000', 'customer1013@gmail.com', '0359561013',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
SET IDENTITY_INSERT [shop].[MEMBERSHIP] OFF; 

SET IDENTITY_INSERT [shop].[COOPERATION_CONTRACT] ON; 
INSERT INTO [shop].[COOPERATION_CONTRACT]
	([COOPERATION_CONTRACT_ID]
	,[ENTERPRISE_ID]
	,[ACCOUNTING_ID]
	,[START_DATE]
	,[END_DATE]
	,[COMMISSION_RATE]
	,[CASH_PER_POINT]
	,[ID_ORIGIN]
	,[UPDATE_DESCRIPTION]
	,[CONTRACT_STATUS]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(1, 2001, NULL, '2023-03-01', '2023-03-31', '5', '1500', 1, 'Initial', 'INACTIVE',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(2, 2001, NULL, '2023-04-01', '2023-04-30', '6', '1600', 2, 'Initial', 'INACTIVE',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(3, 2001, NULL, '2023-05-01', '2023-05-31', '7', '1700', 3, 'Initial', 'INACTIVE',  @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
SET IDENTITY_INSERT [shop].[COOPERATION_CONTRACT] OFF; 

SET IDENTITY_INSERT [shop].[ACCOUNTING] ON; 
INSERT INTO [shop].[ACCOUNTING]
	([ACCOUNTING_ID]
	,[ENTERPRISE_ID]
	,[START_DATE]
	,[END_DATE]
	,[TOTAL_INCOME]
	,[TOTAL_COMMISSION]
	,[PAYMENT_STATUS]
	,[PAYMENT_DATE]
	,[PAYMENT_METHOD]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(1, 2001, '2023-03-01', '2023-03-31', '3544000', '177200', 'UNPAID', NULL, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(2, 2001, '2023-04-01', '2023-04-30', '6713000', '402708', 'UNPAID', NULL, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(3, 2001, '2023-05-01', '2023-05-31', '4946000', '346220', 'UNPAID', NULL, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
SET IDENTITY_INSERT [shop].[ACCOUNTING] OFF; 

---------------------------------------------------------------------------------------------------------------------
COMMIT TRANSACTION
END TRY
BEGIN CATCH
    IF @@TRANCOUNT > 0
        ROLLBACK TRAN
    DECLARE @ErrorMessage NVARCHAR(4000);
	DECLARE @ErrorSeverity INT;
	DECLARE @ErrorState INT;

	SELECT 
		@ErrorMessage = ERROR_MESSAGE() + ' occurred at Line_Number: ' + CAST(ERROR_LINE() AS VARCHAR(50)),
		@ErrorSeverity = ERROR_SEVERITY(),
		@ErrorState = ERROR_STATE();

	RAISERROR (@ErrorMessage,
		@ErrorSeverity,
		@ErrorState
	);
END CATCH