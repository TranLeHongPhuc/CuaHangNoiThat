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
	Address varchar(150),
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


create table Categories(
	Id varchar(10) not null,
	Name nvarchar(40) not null,
	primary key (Id)
)

create table Subcategories(
	Id varchar(10) not null,
	Name nvarchar(40) not null,
	Category_Id varchar(10) not null,
	constraint FK_Subcategories_Categories
	foreign key (Category_Id) references Categories (Id),
	primary key (Id)
)

create table Products(
	Id int identity(1,1),
	Name nvarchar(50) not null,
	Image1 nvarchar(50),
	Image2 nvarchar(50),
	Image3 nvarchar(50),
	Image4 nvarchar(50),
	Price float not null,
	Quantity int not null,
	Discount int,
	Description nvarchar(200),
	CreateDate date not null,
	Available bit not null,
	Category_Id varchar(10) not null,
	constraint FK_Products_Categories
	foreign key (Category_Id) references Categories(Id),
	Subcategory_Id varchar(10) not null,
	constraint FK_Products_Subcategories
	foreign key (Subcategory_Id) references Subcategories(Id),
	primary key (Id)
)

create table Orders(
	Id int identity(1,1),
	Username varchar(50) not null,
	constraint FK_Orders_Users
	foreign key (Username) references Accounts (Username),
	Create_Date date,
	Address nvarchar(100),
	primary key (Id)
)

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
values('khangps15054','$2a$12$GhM3.0eKiqjO2hK/3jJFZezsHdn6t.Xu1xTwehKxXHqI2qKl3FdqK','khangtgps15054@fpt.edu.vn',N'Trần Gia Khang','avatar1.png','0912312314',N'Trà vinh'), /* mk 123456*/
	  ('anps15011','$2a$12$E8YhPTebpzjlGu9lxNXfT.WEclyviMbXqkEcfWEpaxLGSBrLpjdvu','anthtps15011@fpt.edu.vn',N'Trịnh Hữu Thiện Ân','avatar2.png','0912312314',N'Trà vinh'),/* mk 12345*/
	  ('dangps14887','123456','danglbps14887@fpt.edu.vn',N'Lê Bảo Đăng','avatar3.png','0912312314',N'Trà vinh'),
	  ('thinhps14930','123456','thinhnmps14930@fpt.edu.vn',N'Nguyễn Minh Thịnh','avatar4.png','0912312314',N'Trà vinh'),
	  ('vups14942','123456','vuvnps14942@fpt.edu.vn',N'Võ Nguyên Vũ','avatar5.png','0912312314',N'Trà vinh'),
	  ('phucps15061','$2a$12$Ms.4chL5gIX2yAzAP7VYqOxKS5hQ6OYdhMhIehSttWpaFgfigkg56','phuctlhps15061@fpt.edu.vn',N'Trần Lê Hồng Phúc','avatar6.png','0912312314',N'Trà vinh'),/* mk 123*/
	  ('nhips15064','123456','nhinhps15064@fpt.edu.vn',N'Nguyễn Hoàng Nhi','avatar7.png','0912312314',N'Trà vinh')	

insert into Authorities(username, Role_Id)
values('khangps15054','ADMIN'),
	  ('anps15011','ADMIN'),
	  ('dangps14887','ADMIN'),
	  ('thinhps14930','ADMIN'),
	  ('vups14942','ADMIN'),
	  ('phucps15061','ADMIN'),
	  ('nhips15064','ADMIN')

insert into Categories(id, name)
values
		('TABLE',N'Bàn'),
		('CHAIR',N'Ghế'),
		('CHEST',N'Tủ'),
		('MIRROR', N'Gương'),
		('BED', N'Giường'),
		('MATTRESS', N'Nệm')

insert into Subcategories(id, name, Category_Id)
values
		('TABLE1', N'Bàn làm việc', 'TABLE'),
		('TABLE2', N'Bàn ăn', 'TABLE'),
		('TABLE3', N'Bàn trà/sofa', 'TABLE'),
		('TABLE4', N'Bàn trang điểm', 'TABLE'),
		('CHAIR1', N'Ghế ăn', 'CHAIR'),
		('CHAIR2', N'Ghế làm việc', 'CHAIR'),
		('CHAIR3', N'Ghế trang điểm', 'CHAIR'),
		('CHEST1', N'Tủ kính', 'CHEST'),
		('CHEST2', N'Tủ quần áo', 'CHEST'),
		('CHEST3', N'Tủ bát đĩa', 'CHEST'),
		('MIRROR1', N'Gương đứng', 'MIRROR'),
		('MIRROR2', N'Gương treo tường', 'MIRROR'),
		('MIRROR3', N'Gương dựa tường', 'MIRROR'),
		('BED1', N'Giường 1,2m', 'BED'),
		('BED2', N'Giường 1,6m', 'BED'),
		('BED3', N'Giường 1,8m', 'BED'),
		('MATTRESS1', N'Nệm 1,2m', 'MATTRESS'),
		('MATTRESS2', N'Nệm 1,6m', 'MATTRESS'),
		('MATTRESS3', N'Nệm 1,8m', 'MATTRESS')



insert into Products(name, Image1, Image2, Image3, Image4, price, discount,quantity, Description, CreateDate, Available, category_id, subcategory_id)
values	(N'PICO TABLE', 'picotable1.png', 'picotable2.png', 'picotable3.png', 'picotable4.png', 5380000,10,10, N'Tuyệt Vời', '2022/10/26', 0, 'TABLE', 'TABLE3'),
		(N'B TABLE NATURAL', 'btablenatural1.png', 'btablenatural2.png', 'btablenatural3.png', 'btablenatural4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE3'),
		(N'B TABLE WHITE', 'btablewhite1.png', 'btablewhite2.png', 'btablewhite3.png', 'btablewhite4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE3'),
		(N'O - DORE TABLE NATURAL', 'odoretablenatural1.png', 'odoretablenatural2.png', 'odoretablenatural3.png', 'odoretablenatural4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE3'),
		(N'O - PURE TABLE WHITE', 'opuretablewhite1.png', 'opuretablewhite2.png', 'opuretablewhite3.png', 'opuretablewhite4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE3'),
		(N'O - PURE TABLE NATURAL', 'opuretablenatural1.png', 'opuretablenatural2.png', 'opuretablenatural3.png', 'opuretablenatural4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE3'),
		(N'BEE TABLE CREAM', 'beetablecream1.png', 'beetablecream2.png', 'beetablecream3.png', 'beetablecream4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE3'),
		(N'DRA LONG TABLE WHITE', 'dralongtable1.png', 'dralongtable2.png', 'dralongtable3.png', 'dralongtable4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE1'),
		(N'BÀN LÀM VIỆC DRA LONG TABLE BROWN', 'dralongtablebrown1.png', 'dralongtablebrown2.png', 'dralongtablebrown3.png', 'dralongtablebrown4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE1'),
		(N'Odile Table', 'odiletable1.png', 'odiletable2.png', 'odiletable3.png', 'odiletable4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE1'),
		(N'BÀN ĂN BILBO', 'bananbilbo1.png', 'bananbilbo2.png', 'bananbilbo3.png', 'bananbilbo4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE2'),
		(N'BÀN ĂN VERONA', 'bananverona1.png', 'bananverona2.png', 'bananverona3.png', 'bbananverona4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE2'),
		(N'BED TRAY NATURAL', 'bedtraynatural1.png', 'bedtraynatural2.png', 'bedtraynatural3.png', 'bedtraynatural4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE2'),
		(N'B CHARMING DRESSING TABLE WHITE', 'bcharmingdressingtablewhite1.png', 'bcharmingdressingtablewhite2.png', 'bcharmingdressingtablewhite3.png', 'bcharmingdressingtablewhite4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE4'),
		(N'AIMEE DRESSING TABLE NATURAL', 'aimeedressingtablenatural1.png', 'aimeedressingtablenatural2.png', 'aimeedressingtablenatural3.png', 'aimeedressingtablenatural4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE4'),
		(N'DAMBI DRESSING TABLE WHITE', 'dambidressingtablewhite1.png', 'dambidressingtablewhite2.png', 'dambidressingtablewhite3.png', 'dambidressingtablewhite4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'TABLE', 'TABLE4'),
		(N'GHẾ ĂN NARI BENCH STOOL - 1M - BROWN', 'gheannaribenchstool1mbrow1.png', 'gheannaribenchstool1mbrow2.png', 'gheannaribenchstool1mbrow3.png', 'gheannaribenchstool1mbrow4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHAIR', 'CHAIR1'),
		(N'GHẾ ĂN NARI BENCH STOOL - 1M - NATURAL', 'gheannaribenchstool1mnatural1.png', 'gheannaribenchstool1mnatural2.png', 'gheannaribenchstool1mnatural3.png', 'gheannaribenchstool1mnatural.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHAIR', 'CHAIR1'),
		(N'GHẾ ĂN NARI BENCH STOOL - 1M - WHITE NATURAL', 'gheannaribenchstool1mwhitenatural1.png', 'gheannaribenchstool1mwhitenatural2.png', 'gheannaribenchstool1mwhitenatural3.png', 'gheannaribenchstool1mwhitenatural.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHAIR', 'CHAIR1'),
		(N'GHẾ LÀM VIỆC PIN STOOL BROWN', 'ghelamviecpinstoolbrown1.png', 'ghelamviecpinstoolbrown2.png', 'ghelamviecpinstoolbrown3.png', 'ghelamviecpinstoolbrown4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHAIR', 'CHAIR2'),
		(N'BE.HE CHAIR BLACK', 'behechairblack1.png', 'behechairblack2.png', 'behechairblack3.png', 'behechairblack4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHAIR', 'CHAIR2'),
		(N'GHẾ LÀM VIỆC PIN STOOL WHITE', 'ghelamviecpinstoolwhite1.png', 'ghelamviecpinstoolwhite2.png', 'ghelamviecpinstoolwhite3.png', 'ghelamviecpinstoolwhite4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHAIR', 'CHAIR2'),
		(N'GHẾ GỖ PODA DRESSING CHAIR', 'ghegopadasressingchair1.png', 'ghegopadasressingchair2.png', 'ghegopadasressingchair3.png', 'ghegopadasressingchair4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHAIR', 'CHAIR3'),
		(N'TỦ KÍNH MOZART', 'tukinhmozart1.png', 'tukinhmozart2.png', 'tukinhmozart3.png', 'tukinhmozart4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHEST', 'CHEST1'),
		(N'TỦ KÍNH BILBAO', 'tukinhbilbao1.png', 'tukinhbilbao2.png', 'tukinhbilbao3.png', 'tukinhbilbao4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHEST', 'CHEST1'),
		(N'TỦ KÍNH CONNEMARA', 'tukinhconnemara1.png', 'tukinhconnemara2.png', 'tukinhconnemara3.png', 'tukinhconnemara4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHEST', 'CHEST1'),
		(N'TỦ QUẦN ÁO ICONICO', 'tuquanaoiconico1.png', 'tuquanaoiconico2.png', 'tuquanaoiconico3.png', 'tuquanaoiconico4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHEST', 'CHEST2'),
		(N'TỦ QUẦN ÁO SUND', 'tuquanaosund1.png', 'tuquanaosund2.png', 'tuquanaosund3.png', 'tuquanaosund4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHEST', 'CHEST2'),
		(N'TỦ QUẦN ÁO CHESTER', 'tuquanaochester1.png', 'tuquanaochester2.png', 'tuquanaochester3.png', 'tuquanaochester4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHEST', 'CHEST2'),
		(N'TỦ BÁT ĐĨA PATERSON', 'tubatdiapaterson1.png', 'tubatdiapaterson2.png', 'tubatdiapaterson3.png', 'tubatdiapaterson4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'CHEST', 'CHEST3'),
		(N'GƯƠNG ĐỨNG MIRAMAR', 'guongdungmiramar1.png', 'guongdungmiramar2.png', 'guongdungmiramar3.png', 'guongdungmiramar4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MIRROR', 'MIRROR1'),
		(N'GƯƠNG TREO TƯỜNG MIRAMAR', 'guongtreotuongmiramar1.png', 'guongtreotuongmiramar2.png', 'guongtreotuongmiramar3.png', 'guongtreotuongmiramar4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MIRROR', 'MIRROR2'),
		(N'MIA CIRCLE MIRROR GREY', 'miacirclemirrorgrey1.png', 'miacirclemirrorgrey2.png', 'miacirclemirrorgrey3.png', 'miacirclemirrorgrey4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MIRROR', 'MIRROR2'),
		(N'MIA CIRCLE MIRROR D50 BLACK', 'miacirclemirrord50black1.png', 'miacirclemirrord50black2.png', 'miacirclemirrord50black3.png', 'miacirclemirrord50black4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MIRROR', 'MIRROR2'),
		(N'O - GIRLY MIRROR NATURAL', 'ogirlymirrornatural1.png', 'ogirlymirrornatural2.png', 'ogirlymirrornatural3.png', 'ogirlymirrornatural4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MIRROR', 'MIRROR3'),
		(N'GIƯỜNG MOZART', 'giuongmozart1.png', 'giuongmozart2.png', 'giuongmozart3.png', 'giuongmozart4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'BED', 'BED1'),
		(N'GIƯỜNG RALLY', 'giuongrally1.png', 'giuongrally2.png', 'giuongrally3.png', 'giuongrally4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'BED', 'BED1'),
		(N'GIƯỜNG KITKA', 'giuongkitka1.png', 'giuongkitka2.png', 'giuongkitka3.png', 'giuongkitka4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'BED', 'BED1'),
		(N'GIƯỜNG CHESTER', 'giuongchester1.png', 'giuongchester2.png', 'giuongchester3.png', 'giuongchester4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'BED', 'BED2'),
		(N'GIƯỜNG CARINE', 'giuongcarine1.png', 'giuongcarine2.png', 'giuongcarine3.png', 'giuongcarine4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'BED', 'BED2'),
		(N'GIƯỜNG HARRIS', 'giuongharris1.png', 'giuongharris2.png', 'giuongharris3.png', 'giuongharris4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'BED', 'BED2'),
		(N'GIƯỜNG ALI', 'giuongali1.png', 'giuongali2.png', 'giuongali3.png', 'giuongali4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'BED', 'BED3'),
		(N'GIƯỜNG KEIKO', 'giuongkeiko1.png', 'giuongkeiko2.png', 'giuongkeiko3.png', 'giuongkeiko4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'BED', 'BED3'),
		(N'GIƯỜNG ALBANY', 'giuongalbany1.png', 'giuongalbany2.png', 'giuongalbany3.png', 'giuongalbany4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'BED', 'BED3'),
		(N'NỆM SELENE', 'nemselene1.png', 'nemselene2.png', 'nemselene3.png', 'nemselene4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MATTRESS', 'MATTRESS1'),
		(N'NỆM SELENE', 'nemselene1.png', 'nemselene2.png', 'nemselene3.png', 'nemselene4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MATTRESS', 'MATTRESS2'),
		(N'NỆM SELENE', 'nemselene1.png', 'nemselene2.png', 'nemselene3.png', 'nemselene4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MATTRESS', 'MATTRESS3'),
		(N'NỆM SAPA', 'nemssapa1.png', 'nemssapa2.png', 'nemssapa3.png', 'nemssapa4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MATTRESS', 'MATTRESS1'),
		(N'NỆM SAPA', 'nemssapa1.png', 'nemssapa2.png', 'nemssapa3.png', 'nemssapa4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MATTRESS', 'MATTRESS2'),
		(N'NỆM SAPA', 'nemssapa1.png', 'nemssapa2.png', 'nemssapa3.png', 'nemssapa4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MATTRESS', 'MATTRESS3'),
		(N'NỆM GRAFFITI', 'nemgraffiti1.png', 'nemgraffiti2.png.png', 'nemgraffiti3.png', 'nemgraffiti4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MATTRESS', 'MATTRESS1'),
		(N'NỆM GRAFFITI', 'nemgraffiti1.png', 'nemgraffiti2.png.png', 'nemgraffiti3.png', 'nemgraffiti4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MATTRESS', 'MATTRESS2'),
		(N'NỆM GRAFFITI', 'nemgraffiti1.png', 'nemgraffiti2.png.png', 'nemgraffiti3.png', 'nemgraffiti4.png', 1290000,10,10, N'Vip', '2022/10/26', 0, 'MATTRESS', 'MATTRESS3')


		
		
