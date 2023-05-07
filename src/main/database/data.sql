BEGIN TRY
BEGIN TRANSACTION


---------------------------------------------------------------------------------------------------------------------
DECLARE @log_user VARCHAR(20) = 'admin_1'

delete from config.SYS_LANGUAGE;
delete from config.GENDER_LAN;
delete from config.ORDER_STATUS_LAN;
delete from config.PAYMENT_STATUS_LAN;
delete from config.PAYMENT_METHOD_LAN;
delete from config.CONTRACT_STATUS_LAN;
delete from config.PRODUCT_STATUS_LAN;
delete from config.CONTRACT_STATUS_LAN;
delete from config.DELIVERY_STATUS_LAN;
delete from config.PRODUCT_TYPE_LAN;


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

-----------------------------------------------------------
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
--[CATALOG_STATUS_LAN]
SET IDENTITY_INSERT [config].[CATALOG_STATUS_LAN] ON; 
INSERT INTO [config].[CATALOG_STATUS_LAN]
	([CATALOG_STATUS_LAN_ID]
	,[CODE]
	,[LAN]
	,[DESCRIPTION])
VALUES
	(1,'INACTIVE','VN',N'Không áp dụng'),
	(2,'ACTIVE','VN',N'Đang áp dụng'),
	(3,'INACTIVE','EN',N'Inactive'),
	(4,'ACTIVE','EN',N'Active');
SET IDENTITY_INSERT [config].[CATALOG_STATUS_LAN] OFF; 

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
	,[CATALOG_NAME]
	,[CATALOG_STATUS]
	,[LOGO_URL]
	,[LEVEL]
	,[PARENT_CATALOG_ID]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(1, N'Thiết Bị Số - Phụ Kiện Số', 'ACTIVE', '1.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2, N'Voucher - Dịch vụ', 'ACTIVE', '2.png', 1, NULL, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(3, N'Phụ Kiện Điện Thoại và Máy Tính Bảng', 'ACTIVE', '3.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(4, N'Phụ kiện Máy tính và Laptop', 'ACTIVE', '4.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(5, N'Thiết Bị Âm Thanh và Phụ Kiện', 'ACTIVE', '5.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(6, N'Thiết Bị Thông Minh và Linh Kiện Điện Tử', 'ACTIVE', '6.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(7, N'Thiết Bị Đeo Thông Minh và Phụ Kiện', 'ACTIVE', '7.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(8, N'Thiết Bị Chơi Game và Phụ Kiện', 'ACTIVE', '8.png', 2, 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(9, N'Thanh toán hóa đơn', 'ACTIVE', '9.png', 2, 2, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(10, N'Khóa học', 'ACTIVE', '10.png', 2, 2, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(11, N'Du lịch - Khách sạn', 'ACTIVE', '11.png', 2, 2, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(12, N'Spa & Làm đẹp', 'ACTIVE', '12.png', 2, 2, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(13, N'Dịch vụ khác', 'ACTIVE', '13.png', 2, 2, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(14, N'Nhà hàng - Ăn uống', 'ACTIVE', '14.png', 2, 2, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(15, N'Nha khoa - Sức khỏe', 'ACTIVE', '15.png', 2, 2, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(16, N'Sự kiện - Giải trí', 'ACTIVE', '16.png', 2, 2, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(17, N'Sim số - Thẻ cào - Thẻ game', 'ACTIVE', '17.png', 2, 2, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(18, N'Phiếu quà tặng', 'ACTIVE', '18.png', 2, 2, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
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
	,[PRODUCT_TYPE]
	,[QUANTITY_IN_STOCK]
	,[DESCRIPTION_CONTENT_URL]
	,[PRODUCT_STATUS]
	,[AMOUNT_SOLD]
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
	(1,'M000001', N'Cáp Sạc Nhanh 20W PD Cho iPhone, iPad - Dây Sạc Hoco X14 1 Đầu Type-C, 1 Đầu Tới iPhone - Siêu Nhanh Siêu Bền - HÀNG CHÍNH HÃNG', 'NORMAL',
		100, '1.html', 'ACTIVE', 100, '99000', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2,'M000002', N'Củ sạc nhanh chuẩn kép PD và QC3.0 Remax RP-U88 công suất 20W - Hàng chính hãng',  'NORMAL',
		200, '2.html', 'ACTIVE', 200,  '229000', '4', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(3,'M000003', N'Giá đỡ laptop nhôm có thể gấp gọn dành cho Macbook Ipad Surface và máy tính xách tay',  'NORMAL',
		300, '3.html', 'ACTIVE', 300,  '249000 ', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(4,'M000004', N'Chuột không dây Logitech M220 Silent - Hàng chính hãng', 'NORMAL',
		400, '4.html', 'ACTIVE', 400,  '226000', '4.6', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(5,'M000005', N'Tai nghe bluetooth nhét tai chống ồn cao cấp V5.0 chính hãng dùng cho iPhone Samsung OPPO VIVO HUAWEI XIAOMI tai nghe không dây - Hàng Chính Hãng PKCB', 'NORMAL',
		500, '5.html', 'ACTIVE', 500,  '290000', '3.9', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(6,'M000006', N'Tai nghe chụp tai không dây Remax RB-660HB - Hàng chính hãng', 'NORMAL', 
		100, '1.html', 'ACTIVE', 100, '590000', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(7,'M000007', N'ADAPTER DC 12V 2A CÓ ĐÈN BÁO', 'NORMAL',
		200, '2.html', 'ACTIVE', 200,  '229000', '4', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(8,'M000008', N'Loa thông minh OLLI MAIKA - Đen Nguyên Bản - Hàng Chính Hãng', 'NORMAL',
		300, '3.html', 'ACTIVE', 300,  '249000 ', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(9,'M000009', N'Đồng Hồ Thông Minh Trẻ em AMA Q12S Model MỚI nhất ra mắt tháng 12/2021 - Hàng nhập khẩu', 'NORMAL', 
		400, '4.html', 'ACTIVE', 400,  '579000', '4.6', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(10,'M000010', N'Vòng đeo tay HUAWEI Band 7 | Thiết kế siêu mỏng | SpO2 tự động | Pin đến 2 tuần | Hàng Chính Hãng', 'NORMAL', 
		500, '5.html', 'ACTIVE', 500,  '250000', '3.9', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(11,'M000011', N'Máy chơi game điện tử 4 nút tay cầm không dây GAME STICK 4K ULTRA_HD Joystick 360 - 2 người chơi - kết nối TV 4K - Thẻ SD 32G +3000 games -Game console thiết bị game mượt ( HDMI ) - Tặng cáp chuyển HDMI', 'NORMAL', 
		100, '11.html', 'ACTIVE', 100, '619000', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(12,'M000012', N'Ghế Gaming, Ghế Game Thủ Có Gác Chân Và Massage Lưng Dành Cho Các Gamer, Ghế Chơi Game Kết Hợp Ghế Lười Xoay Văn Phòng Và Làm Việc Thư Giãn Kèm Gối Tựa Lưng Đầu - Hàng Chính Hãng', 'NORMAL',
		200, '12.html', 'ACTIVE', 200,  '1164000', '4', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(13,'M000013', N'Voucher Gói học ELSA Pro thời hạn 6 tháng từ ELSA SPEAK - Học phát âm tiếng Anh chuẩn bản xứ', 'VOUCHER',
		300, '13.html', 'ACTIVE', 300,  '545000 ', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(14,'M000014', N'[MÃ GIẢM GIÁ LÊN ĐẾN 50K] Ứng dụng Tiếng Anh số 1 cho trẻ mới bắt đầu (0-10 tuổi) - Gói Monkey Junior trọn đời', 'VOUCHER', 
		400, '14.html', 'ACTIVE', 400,  '579000', '4.6', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(15,'M000015', N'Vé Thuyền Rồng Sông Hàn Đà Nẵng Về Đêm', 'VOUCHER',
		500, '15.html', 'ACTIVE', 500,  '250000', '3.9', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(16,'M000016', N'TTC Resort Ninh Thuận 4*- Gồm Bữa Sáng, Miễn Phí Vui Chơi Cổng Công Viên Nước', 'VOUCHER', 
		100, '16.html', 'ACTIVE', 100, '760000', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(17,'M000017', N'VOUCHER CHĂM SÓC NHỨC MÕI CỔ, VAI, GÁY',  'VOUCHER',
		200, '17.html', 'ACTIVE', 200,  '1164000', '4', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(18,'M000018', N'VOUCHER MASSAGE CHĂM SÓC BODY ĐÁ NÓNG', 'VOUCHER',
		300, '18.html', 'ACTIVE', 300,  '349000 ', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(19,'M000019', N'[12 tháng] Voiz FM - Voucher nghe Sách Nói', 'VOUCHER',
		400, '19.html', 'ACTIVE', 400,  '640000', '4.6', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(20,'M000020', N'Giftpop_Ưu Đãi 500K Cho Đơn Hàng GrabMart', 'VOUCHER',
		500, '20.html', 'ACTIVE', 500,  '500000', '3.9', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(21,'M000021', N'Voucher Buffet Chay Buổi Trưa tại Bông Sen Hotel Nhà Hàng Cỏ Nội với Hơn 40 Món Ăn Đặc Sắc', 'VOUCHER',
		100, '21.html', 'ACTIVE', 100, '319000', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(22,'M000022', N'Voucher Buffet Tối tại Nhà Hàng Gánh Bông Sen, Tinh Hoa Ẩm Thực Sài Thành',  'VOUCHER',
		200, '22.html', 'ACTIVE', 200,  '359000', '4', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(23,'M000023', N'FPT Play - 06-12 tháng Gói iZi, 13 tháng gói MAX/VIP - Tài khoản dịch vụ phổ biến xem truyền hình, thể thao, phim truyện và giải trí, bóng đá trực tiếp và độc quyền UEFA cup C1 C2 C3', 'VOUCHER',
		300, '23.html', 'ACTIVE', 300,  '290000 ', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(24,'M000024', N'[tiNiWorld] - Vé điện tử vào cổng tất cả TRUNG TÂM tiNiWorld trên toàn quốc TW180', 'VOUCHER', 
		400, '24.html', 'ACTIVE', 400,  '190000', '4.6', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(25,'M000025', N'Lấy Cao Răng (Cạo vôi răng) Và Đánh Bóng Công Nghệ Siêu Âm Mới - Nha khoa BeDental (5 chi nhánh)', 'VOUCHER', 
		500, '25.html', 'ACTIVE', 500,  '99000', '3.9', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(26,'M000026', N'Voucher Nhổ răng sữa em bé + Ngừa sâu răng - Nha khoa BeDental', 'VOUCHER', 
		100, '26.html', 'ACTIVE', 100, '760000', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(27,'M000027', N'Phiếu Quà Tặng Việt Talents 200.000đ', 'VOUCHER', 
		200, '27.html', 'ACTIVE', 200,  '200000', '4', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(28,'M000028', N'Phiếu Quà Tặng Tiki 1.000.000đ', 'VOUCHER', 
		300, '28.html', 'ACTIVE', 300,  '1000000 ', '4.5', '2020-01-01', '2024-01-01', @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

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
	(1, 1, 3, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2, 2, 3, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(3, 3, 4, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(4, 4, 4, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(5, 5, 5, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(6, 6, 5, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(7, 7, 6, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(8, 8, 6, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(9, 9, 7, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(10, 10, 7, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(11, 11, 8, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(12, 12, 8, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(13, 13, 10, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(14, 14, 10, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(15, 15, 11, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(16, 16, 11, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(17, 17, 12, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(18, 18, 12, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(19, 19, 13, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(20, 20, 13, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(21, 21, 14, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(22, 22, 14, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(23, 23, 15, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(24, 24, 15, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(25, 25, 16, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(26, 26, 16, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(27, 27, 17, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(28, 28, 17, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
SET IDENTITY_INSERT [shop].[PRODUCT_CATALOG] OFF; 

-----------------------------------------------------------
--[PRODUCT_POINT]
INSERT INTO [shop].[PRODUCT_POINT]
	([ENTERPRISE_ID]
	,[PRODUCT_ID]
	,[POINT_EXCHANGE]
	,[ID_ORIGIN]
	,[UPDATE_DESCRIPTION]
	,[ACTIVE]
	,[USR_LOG_I]
	,[USR_LOG_U]
	,[DATE_LOG_I]
	,[DATE_LOG_U]
	,[VERSION])
VALUES 
	(2001, 1, '100', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 2, '200', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 3, '300', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 4, '400', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 5, '500', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2001, 6, '110', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 7, '210', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 8, '310', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 9, '410', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 10, '510', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2001, 11, '120', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 12, '220', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 13, '320', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 14, '420', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 15, '520', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2001, 16, '130', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 17, '230', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 18, '330', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 19, '430', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 20, '530', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2001, 21, '140', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 22, '240', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 23, '340', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 24, '440', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 25, '540', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 26, '540', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 27, '540', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2001, 2, '100', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 3, '200', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 4, '300', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 5, '400', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 6, '500', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2001, 7, '110', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 8, '210', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 9, '310', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 10, '410', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 11, '510', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2001, 12, '120', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 13, '220', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 14, '320', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 15, '420', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 16, '520', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2001, 17, '130', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 18, '230', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 19, '330', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 20, '430', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 21, '530', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2001, 22, '140', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2002, 23, '240', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2003, 24, '340', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2004, 25, '440', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 26, '540', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2005, 27, '540', 1, 'Initial', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

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
	(1,	 1, '1_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(2,	 1, '1_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(3,	 1, '1_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(4,	 1, '1_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(5,	 1, '1_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(6,	 2, '2_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(7,	 2, '2_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(8,	 2, '2_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(9,	 2, '2_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(10, 2, '2_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(11, 3, '3_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(12, 3, '3_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(13, 3, '3_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(14, 3, '3_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(15, 3, '3_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(16, 4, '4_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(17, 4, '4_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(18, 4, '4_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(19, 4, '4_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(20, 4, '4_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(21, 5, '5_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(22, 5, '5_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(23, 5, '5_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(24, 5, '5_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(25, 5, '5_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(26, 6, '6_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(27, 6, '6_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(28, 6, '6_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(29, 6, '6_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(30, 6, '6_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(31, 7, '7_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(32, 7, '7_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(33, 7, '7_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(34, 7, '7_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(35, 7, '7_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(36, 8, '8_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(37, 8, '8_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(38, 8, '8_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(39, 8, '8_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(40, 8, '8_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(41, 9, '9_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(42, 9, '9_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(43, 9, '9_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(44, 9, '9_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(45, 9, '9_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(46, 10, '10_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(47, 10, '10_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(48, 10, '10_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(49, 10, '10_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(50, 10, '10_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(51, 11, '11_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(52, 11, '11_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(53, 11, '11_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(54, 11, '11_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(55, 11, '11_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(56, 12, '12_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(57, 12, '12_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(58, 12, '12_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(59, 12, '12_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(60, 12, '12_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(61, 13, '13_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(62, 13, '13_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(63, 13, '13_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(64, 13, '13_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(65, 13, '13_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(66, 14, '14_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(67, 14, '14_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(68, 14, '14_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(69, 14, '14_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(70, 14, '14_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(71, 15, '15_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(72, 15, '15_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(73, 15, '15_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(74, 15, '15_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(75, 15, '15_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(76, 16, '16_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(77, 16, '16_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(78, 16, '16_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(79, 16, '16_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(80, 16, '16_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(81, 17, '17_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(82, 17, '17_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(83, 17, '17_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(84, 17, '17_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(85, 17, '17_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(86, 18, '18_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(87, 18, '18_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(88, 18, '18_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(89, 18, '18_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(90, 18, '18_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(91, 19, '19_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(92, 19, '19_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(93, 19, '19_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(94, 19, '19_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(95, 19, '19_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(96, 20, '20_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(97, 20, '20_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(98, 20, '20_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(99, 20, '20_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(100, 20, '20_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(101, 21, '21_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(102, 21, '21_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(103, 21, '21_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(104, 21, '21_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(105, 21, '21_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(106, 22, '22_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(107, 22, '22_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(108, 22, '22_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(109, 22, '22_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(110, 22, '22_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(111, 23, '23_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(112, 23, '23_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(113, 23, '23_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(114, 23, '23_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(115, 23, '23_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(116, 24, '24_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(117, 24, '24_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(118, 24, '24_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(119, 24, '24_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(120, 24, '24_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(121, 25, '25_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(122, 25, '25_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(123, 25, '25_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(124, 25, '25_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(125, 25, '25_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(126, 26, '26_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(127, 26, '26_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(128, 26, '26_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(129, 26, '26_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(130, 26, '26_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(131, 27, '27_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(132, 27, '27_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(133, 27, '27_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(134, 27, '27_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(135, 27, '27_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(136, 28, '28_1.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	(137, 28, '28_2.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(138, 28, '28_3.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(139, 28, '28_4.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
	(140, 28, '28_5.webp', 1, @log_user, @log_user, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
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