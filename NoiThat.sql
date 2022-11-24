use master
go

if exists(select * from sys.databases where name = 'NoiThat_Shop')
	drop database NoiThat_Shop

create database NoiThat_Shop

go
use NoiThat_Shop

go

create table Roles(
	Id nvarchar(10) not null,
	Name nvarchar(50) not null,
	primary key (Id)
)

go

create table Accounts(
	Username varchar(50) not null,
	Password varchar(150) not null,
	Email varchar(50) not null,
	Fullname nvarchar(50) not null,
	Photo varchar(50),
	Phone varchar(50),
	Address nvarchar(150),
	checked bit,
	verification_code varchar(20),
	primary key (Username)
)

go

create table Authorities(
	Id int identity(1,1),
	Role_Id nvarchar(10) not null,
	constraint FK_Authorities_Roles
	foreign key (Role_Id) references Roles (Id),
	Username varchar(50) not null,
	constraint FK_Authorities_Users
	foreign key (Username) references Accounts (Username),
	primary key (Id)
)

go

create table Categories(
	Id varchar(10) not null,
	Name nvarchar(40) not null,
	Icon varchar(40),
	primary key (Id)
)

go

create table Subcategories(
	Id varchar(10) not null,
	Name nvarchar(40) not null,
	Icon varchar(40),
	Category_Id varchar(10) not null,
	constraint FK_Subcategories_Categories
	foreign key (Category_Id) references Categories (Id),
	primary key (Id)
)

go

create table Products(
	Id int identity(1,1),
	Name nvarchar(50) not null,
	Image1 nvarchar(50),
	Price float not null,
	Quantity int not null,
	Discount int,
	Description nvarchar(200),
	Available bit not null,
	Category_Id varchar(10) not null,
	constraint FK_Products_Categories
	foreign key (Category_Id) references Categories(Id),
	Subcategory_Id varchar(10) not null,
	constraint FK_Products_Subcategories
	foreign key (Subcategory_Id) references Subcategories(Id),
	primary key (Id)
)

go

create table Orders_Status(
	Id varchar(5),
	Names nvarchar(50),
	primary Key(Id),
)

go
create table Orders(
	Id int identity(1,1),
	Username varchar(50) not null,
	constraint FK_Orders_Users
	foreign key (Username) references Accounts (Username),
	order_status_id varchar(5)
	constraint FK_Orders_OrderStatus
	foreign key (order_status_id) references Orders_Status (Id),
	Create_Date date,
	Address nvarchar(100),
	description nvarchar(300),
	phone varchar(50),
	primary key (Id)
)

go

create table Orders_Detail(
	Id int identity(1,1),
	Order_Id int not null,
	constraint FK_OrdersDetail_Orders
	foreign key (Order_Id) references Orders (Id),
	Product_Id int not null,
	constraint FK_OrdersDetail_Products
	foreign key (Product_Id) references Products (Id),
	Quantity int not null,
	Price float not null,
	primary key (Id)
)


insert into Roles(id, name)
values('ADMIN','Administrators'),
	  ('USER','Users')



insert into Accounts(username, password, email, fullname, Photo, Phone, Address)
values('khangps15054','$2a$12$GhM3.0eKiqjO2hK/3jJFZezsHdn6t.Xu1xTwehKxXHqI2qKl3FdqK','khangtgps15054@fpt.edu.vn',N'Trần Gia Khang','avatar1.png','0337429181',N'Bình thuận'), /* mk 123456*/
	  ('anps15011','$2a$12$E8YhPTebpzjlGu9lxNXfT.WEclyviMbXqkEcfWEpaxLGSBrLpjdvu','anthtps15011@fpt.edu.vn',N'Trịnh Hữu Thiện Ân','avatar2.png','0912312314',N'Trà vinh'),/* mk 12345*/
	  ('dangps14887','123456','danglbps14887@fpt.edu.vn',N'Lê Bảo Đăng','avatar3.png','0912312314',N'Trà vinh'),
	  ('thinhps14930','123456','thinhnmps14930@fpt.edu.vn',N'Nguyễn Minh Thịnh','avatar4.png','0912312314',N'Trà vinh'),
	  ('vups14942','123456','vuvnps14942@fpt.edu.vn',N'Võ Nguyên Vũ','avatar5.png','0912312314',N'Trà vinh'),
	  ('phucps15061','$2a$12$Ms.4chL5gIX2yAzAP7VYqOxKS5hQ6OYdhMhIehSttWpaFgfigkg56','phuctlhps15061@fpt.edu.vn',N'Trần Lê Hồng Phúc','avatar6.png','0912312314',N'Trà vinh'),/* mk 123*/
	  ('nhips15064','123456','nhinhps15064@fpt.edu.vn',N'Nguyễn Hoàng Nhi','avatar7.png','0912312314',N'Trà vinh'),
	    ('nhi','123','nhinhps15064@fpt.edu.vn',N'Nguyễn Hoàng Nhi','avatar7.png','0912312314',N'Trà vinh')		

insert into Authorities(username, Role_Id)
values('khangps15054','ADMIN'),
	  ('anps15011','ADMIN'),
	  ('dangps14887','ADMIN'),
	  ('thinhps14930','ADMIN'),
	  ('vups14942','ADMIN'),
	  ('phucps15061','ADMIN'),
	  ('nhips15064','ADMIN')

insert into Categories(id, name, icon)
values
		('TABLE',N'Bàn','table.png'),
		('CHAIR',N'Ghế','chair.png'),
		('CABINET',N'Tủ','cabinet.png'),
		('MIRROR', N'Gương','mirror.png'),
		('BED', N'Giường','bed.png'),
		('MATTRESS', N'Nệm','mattress.png')

insert into Subcategories(id, name, Category_Id, Icon)
values
		('TABLE1', N'Bàn làm việc', 'TABLE','desk.png'),
		('TABLE2', N'Bàn ăn', 'TABLE','table.png'),
		('TABLE3', N'Bàn trà/sofa', 'TABLE','coffee-table.png'),
		('TABLE4', N'Bàn trang điểm', 'TABLE','dressing-table.png'),
		('CHAIR1', N'Ghế ăn', 'CHAIR','dining-chair.png'),
		('CHAIR2', N'Ghế làm việc', 'CHAIR','office-chair.png'),
		('CHAIR3', N'Ghế trang điểm', 'CHAIR','chair.png'),
		('CABINET1', N'Tủ kính', 'CABINET','cabinet.png'),
		('CABINET2', N'Tủ quần áo', 'CABINET','cabinetClothes.png'),
		('CABINET3', N'Tủ bát đĩa', 'CABINET','cabinetKitchen.png'),
		('MIRROR1', N'Gương đứng', 'MIRROR','mirror-standing.png'),
		('MIRROR2', N'Gương treo tường', 'MIRROR','mirror-onwall.png'),
		('MIRROR3', N'Gương dựa tường', 'MIRROR','mirror-wall.png'),
		('BED1', N'Giường 1,2m', 'BED','bed1m2.png'),
		('BED2', N'Giường 1,6m', 'BED','beb1m8.png'),
		('BED3', N'Giường 1,8m', 'BED','bed1m6.png'),
		('MATTRESS1', N'Nệm 1,2m', 'MATTRESS','air-mattress1.2.png'),
		('MATTRESS2', N'Nệm 1,6m', 'MATTRESS','air-mattress1.6.png'),
		('MATTRESS3', N'Nệm 1,8m', 'MATTRESS','air-mattress1.8.png')



insert into Products(name, Image1, price, discount,quantity, Description, Available, category_id, subcategory_id)
values	(N'PICO TABLE', 'picotable1.png', 5380000,10,10, N'Tuyệt Vời', 0, 'TABLE', 'TABLE3'),
		(N'B TABLE NATURAL', 'btablenatural1.png', 1290000,10,10, N'Vip', 0, 'TABLE', 'TABLE3'),
		(N'B TABLE WHITE', 'btablewhite1.png', 1290000,10,10, N'Vip', 0, 'TABLE', 'TABLE3'),
		(N'O - DORE TABLE NATURAL', 'odoretablenatural1.png', 1290000,10,10, N'Vip', 0, 'TABLE', 'TABLE3'),
		(N'O - PURE TABLE WHITE', 'opuretablewhite1.png', 1290000,10,10, N'Vip', 0, 'TABLE', 'TABLE3'),
		(N'O - PURE TABLE NATURAL', 'opuretablenatural1.png', 1290000,10,10, N'Vip', 0, 'TABLE', 'TABLE3'),
		(N'BEE TABLE CREAM', 'beetablecream1.png', 1290000,10,10, N'Vip', 0, 'TABLE', 'TABLE3'),
		(N'DRA LONG TABLE WHITE', 'dralongtable1.png', 1290000,10,10, N'Vip',  0, 'TABLE', 'TABLE1'),
		(N'BÀN LÀM VIỆC DRA LONG TABLE BROWN', 'dralongtablebrown1.png', 1290000,10,10, N'Vip',  0, 'TABLE', 'TABLE1'),
		(N'Odile Table', 'odiletable1.png', 1290000,10,10, N'Vip',  0, 'TABLE', 'TABLE1'),
		(N'BÀN ĂN BILBO', 'bananbilbo1.png', 1290000,10,10, N'Vip',  0, 'TABLE', 'TABLE2'),
		(N'BÀN ĂN VERONA', 'bananverona1.png', 1290000,10,10, N'Vip',0, 'TABLE', 'TABLE2'),
		(N'BED TRAY NATURAL', 'bedtraynatural1.png', 1290000,10,10, N'Vip', 0, 'TABLE', 'TABLE2'),
		(N'B CHARMING DRESSING TABLE WHITE', 'bcharmingdressingtablewhite1.png', 1290000,10,10, N'Vip', 0, 'TABLE', 'TABLE4'),
		(N'AIMEE DRESSING TABLE NATURAL', 'aimeedressingtablenatural1.png', 1290000,10,10, N'Vip', 0, 'TABLE', 'TABLE4'),
		(N'DAMBI DRESSING TABLE WHITE', 'dambidressingtablewhite1.png', 1290000,10,10, N'Vip',  0, 'TABLE', 'TABLE4'),
		(N'GHẾ ĂN NARI BENCH STOOL - 1M - BROWN', 'gheannaribenchstool1mbrow1.png', 1290000,10,10, N'Vip',  0, 'CHAIR', 'CHAIR1'),
		(N'GHẾ ĂN NARI BENCH STOOL - 1M - NATURAL', 'gheannaribenchstool1mnatural1.png',1290000,10,10, N'Vip',  0, 'CHAIR', 'CHAIR1'),
		(N'GHẾ ĂN NARI BENCH STOOL - 1M - WHITE NATURAL', 'gheannaribenchstool1mwhitenatural1.png',1290000,10,10, N'Vip', 0, 'CHAIR', 'CHAIR1'),
		(N'GHẾ LÀM VIỆC PIN STOOL BROWN', 'ghelamviecpinstoolbrown1.png', 1290000,10,10, N'Vip',0, 'CHAIR', 'CHAIR2'),
		(N'BE.HE CHAIR BLACK', 'behechairblack1.png',1290000,10,10, N'Vip', 0, 'CHAIR', 'CHAIR2'),
		(N'GHẾ LÀM VIỆC PIN STOOL WHITE', 'ghelamviecpinstoolwhite1.png', 1290000,10,10, N'Vip',  0, 'CHAIR', 'CHAIR2'),
		(N'GHẾ GỖ PODA DRESSING CHAIR', 'ghegopadasressingchair1.png', 1290000,10,10, N'Vip', 0, 'CHAIR', 'CHAIR3'),
		(N'TỦ KÍNH MOZART', 'tukinhmozart1.png', 1290000,10,10, N'Vip',0, 'CABINET', 'CABINET1'),
		(N'TỦ KÍNH BILBAO', 'tukinhbilbao1.png',  1290000,10,10, N'Vip', 0, 'CABINET', 'CABINET1'),
		(N'TỦ KÍNH CONNEMARA', 'tukinhconnemara1.png',  1290000,10,10, N'Vip',  0, 'CABINET', 'CABINET1'),
		(N'TỦ QUẦN ÁO ICONICO', 'tuquanaoiconico1.png',  1290000,10,10, N'Vip', 0, 'CABINET', 'CABINET2'),
		(N'TỦ QUẦN ÁO SUND', 'tuquanaosund1.png', 1290000,10,10, N'Vip', 0, 'CABINET', 'CABINET2'),
		(N'TỦ QUẦN ÁO CHESTER', 'tuquanaochester1.png', 1290000,10,10, N'Vip', 0, 'CABINET', 'CABINET2'),
		(N'TỦ BÁT ĐĨA PATERSON', 'tubatdiapaterson1.png',  1290000,10,10, N'Vip', 0, 'CABINET', 'CABINET3'),
		(N'GƯƠNG ĐỨNG MIRAMAR', 'guongdungmiramar1.png', 1290000,10,10, N'Vip', 0, 'MIRROR', 'MIRROR1'),
		(N'GƯƠNG TREO TƯỜNG MIRAMAR', 'guongtreotuongmiramar1.png', 1290000,10,10, N'Vip',  0, 'MIRROR', 'MIRROR2'),
		(N'MIA CIRCLE MIRROR GREY', 'miacirclemirrorgrey1.png', 1290000,10,10, N'Vip',  0, 'MIRROR', 'MIRROR2'),
		(N'MIA CIRCLE MIRROR D50 BLACK', 'miacirclemirrord50black1.png', 1290000,10,10, N'Vip', 0, 'MIRROR', 'MIRROR2'),
		(N'O - GIRLY MIRROR NATURAL', 'ogirlymirrornatural1.png',  1290000,10,10, N'Vip',  0, 'MIRROR', 'MIRROR3'),
		(N'GIƯỜNG MOZART', 'giuongmozart1.png',1290000,10,10, N'Vip',  0, 'BED', 'BED1'),
		(N'GIƯỜNG RALLY', 'giuongrally1.png', 1290000,10,10, N'Vip', 0, 'BED', 'BED1'),
		(N'GIƯỜNG KITKA', 'giuongkitka1.png',  1290000,10,10, N'Vip', 0, 'BED', 'BED1'),
		(N'GIƯỜNG CHESTER', 'giuongchester1.png',  1290000,10,10, N'Vip',  0, 'BED', 'BED2'),
		(N'GIƯỜNG CARINE', 'giuongcarine1.png',1290000,10,10, N'Vip', 0, 'BED', 'BED2'),
		(N'GIƯỜNG HARRIS', 'giuongharris1.png',  1290000,10,10, N'Vip', 0, 'BED', 'BED2'),
		(N'GIƯỜNG ALI', 'giuongali1.png',  1290000,10,10, N'Vip', 0, 'BED', 'BED3'),
		(N'GIƯỜNG KEIKO', 'giuongkeiko1.png',  1290000,10,10, N'Vip', 0, 'BED', 'BED3'),
		(N'GIƯỜNG ALBANY', 'giuongalbany1.png', 1290000,10,10, N'Vip', 0, 'BED', 'BED3'),
		(N'NỆM SELENE', 'nemselene1.png', 1290000,10,10, N'Vip',0, 'MATTRESS', 'MATTRESS1'),
		(N'NỆM SELENE', 'nemselene1.png', 1290000,10,10, N'Vip', 0, 'MATTRESS', 'MATTRESS2'),
		(N'NỆM SELENE', 'nemselene1.png',  1290000,10,10, N'Vip',  0, 'MATTRESS', 'MATTRESS3'),
		(N'NỆM SAPA', 'nemssapa1.png', 1290000,10,10, N'Vip',0, 'MATTRESS', 'MATTRESS1'),
		(N'NỆM SAPA', 'nemssapa1.png',  1290000,10,10, N'Vip',  0, 'MATTRESS', 'MATTRESS2'),
		(N'NỆM SAPA', 'nemssapa1.png', 1290000,10,10, N'Vip',  0, 'MATTRESS', 'MATTRESS3'),
		(N'NỆM GRAFFITI', 'nemgraffiti1.png',  1290000,10,10, N'Vip', 0, 'MATTRESS', 'MATTRESS1'),
		(N'NỆM GRAFFITI', 'nemgraffiti1.png', 1290000,10,10, N'Vip', 0, 'MATTRESS', 'MATTRESS2'),
		(N'NỆM GRAFFITI', 'nemgraffiti1.png',  1290000,10,10, N'Vip', 0, 'MATTRESS', 'MATTRESS3')

go
 INSERT INTO Orders_Status
     VALUES
           ('CXN','Cho xac nhan'),
		   ('CLH','Cho lay hang'),
		   ('DG','Dang giao '),
		   ('DGH','Da giao hang')

go
CREATE PROC sp_getTotalPricePerMonth
(
	@month varchar(2),
	@year  varchar(4)
)
AS BEGIN
	DECLARE @result varchar(20)
	SET @result = (SELECT 
						SUM(Orders_Detail.Price * Orders_Detail.Quantity)
					FROM
						orders INNER JOIN Orders_Detail
							ON Orders.id = Orders_Detail.Order_Id
					WHERE 
						MONTH(Orders.Create_Date) = @month
						AND YEAR(Orders.Create_Date) = @year)
	IF @result IS NULL BEGIN SET @result = '0' END
	SELECT @result
END	
		
		
