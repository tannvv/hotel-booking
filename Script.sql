USE [master]
GO
/****** Object:  Database [Booking_Hotel]    Script Date: 10/29/2021 9:25:40 PM ******/
CREATE DATABASE [Booking_Hotel]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Booking_Hotel', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\Booking_Hotel.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Booking_Hotel_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\Booking_Hotel_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Booking_Hotel] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Booking_Hotel].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Booking_Hotel] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Booking_Hotel] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Booking_Hotel] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Booking_Hotel] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Booking_Hotel] SET ARITHABORT OFF 
GO
ALTER DATABASE [Booking_Hotel] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Booking_Hotel] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Booking_Hotel] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Booking_Hotel] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Booking_Hotel] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Booking_Hotel] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Booking_Hotel] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Booking_Hotel] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Booking_Hotel] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Booking_Hotel] SET  ENABLE_BROKER 
GO
ALTER DATABASE [Booking_Hotel] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Booking_Hotel] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Booking_Hotel] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Booking_Hotel] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Booking_Hotel] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Booking_Hotel] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Booking_Hotel] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Booking_Hotel] SET RECOVERY FULL 
GO
ALTER DATABASE [Booking_Hotel] SET  MULTI_USER 
GO
ALTER DATABASE [Booking_Hotel] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Booking_Hotel] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Booking_Hotel] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Booking_Hotel] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Booking_Hotel] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Booking_Hotel] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'Booking_Hotel', N'ON'
GO
ALTER DATABASE [Booking_Hotel] SET QUERY_STORE = OFF
GO
USE [Booking_Hotel]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 10/29/2021 9:25:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[email] [varchar](30) NOT NULL,
	[password] [varchar](100) NULL,
	[role] [int] NULL,
	[status] [int] NULL,
	[createDate] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Hotel]    Script Date: 10/29/2021 9:25:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Hotel](
	[hotelID] [int] IDENTITY(1,1) NOT NULL,
	[hotelName] [nvarchar](50) NULL,
	[address] [nvarchar](100) NULL,
	[image] [varchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[hotelID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[InforUser]    Script Date: 10/29/2021 9:25:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[InforUser](
	[email] [varchar](30) NOT NULL,
	[name] [nvarchar](50) NULL,
	[phone] [varchar](15) NULL,
	[address] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[OrderDetail]    Script Date: 10/29/2021 9:25:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[OrderDetail](
	[roomID] [int] NOT NULL,
	[orderID] [int] NOT NULL,
	[quantity] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[roomID] ASC,
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 10/29/2021 9:25:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[orderID] [int] IDENTITY(1,1) NOT NULL,
	[hotelID] [int] NULL,
	[userID] [varchar](30) NULL,
	[totalCost] [int] NULL,
	[createDate] [varchar](60) NULL,
	[checkinDate] [date] NULL,
	[checkOutDate] [date] NULL,
	[discount] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[orderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Room]    Script Date: 10/29/2021 9:25:40 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Room](
	[roomID] [int] IDENTITY(1,1) NOT NULL,
	[hotelID] [int] NULL,
	[type] [int] NULL,
	[amount] [int] NULL,
	[price] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[roomID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Account] ([email], [password], [role], [status], [createDate]) VALUES (N'huhu@gmail.com', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 1, 1, CAST(N'2021-10-16' AS Date))
INSERT [dbo].[Account] ([email], [password], [role], [status], [createDate]) VALUES (N'khanhkt@fpt.edu.vn', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 1, 1, CAST(N'2021-10-17' AS Date))
INSERT [dbo].[Account] ([email], [password], [role], [status], [createDate]) VALUES (N'sasa@gmail.com', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 1, 1, CAST(N'2021-10-28' AS Date))
INSERT [dbo].[Account] ([email], [password], [role], [status], [createDate]) VALUES (N'tannn@gmail.com', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 1, 1, CAST(N'2021-10-16' AS Date))
INSERT [dbo].[Account] ([email], [password], [role], [status], [createDate]) VALUES (N'tannv@gmail.com', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 1, 1, CAST(N'2021-10-13' AS Date))
INSERT [dbo].[Account] ([email], [password], [role], [status], [createDate]) VALUES (N'tannvse140923@fpt.edu.vn', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 1, 1, CAST(N'2021-10-19' AS Date))
INSERT [dbo].[Account] ([email], [password], [role], [status], [createDate]) VALUES (N'tantest@gmail.com', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 1, 1, CAST(N'2021-10-20' AS Date))
INSERT [dbo].[Account] ([email], [password], [role], [status], [createDate]) VALUES (N'tantinhte@gmail.com', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 1, 1, CAST(N'2021-10-17' AS Date))
INSERT [dbo].[Account] ([email], [password], [role], [status], [createDate]) VALUES (N'tram@gmail.com', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 1, 1, CAST(N'2021-10-14' AS Date))
INSERT [dbo].[Account] ([email], [password], [role], [status], [createDate]) VALUES (N'tramtram@gmail.com', N'5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 1, 1, CAST(N'2021-10-17' AS Date))
GO
SET IDENTITY_INSERT [dbo].[Hotel] ON 

INSERT [dbo].[Hotel] ([hotelID], [hotelName], [address], [image]) VALUES (1, N'Anh trang', N'Phu yen', N'hotel1.jpg')
INSERT [dbo].[Hotel] ([hotelID], [hotelName], [address], [image]) VALUES (2, N'Hoang hon', N'Nha trang', N'hotel2.jpg')
SET IDENTITY_INSERT [dbo].[Hotel] OFF
GO
INSERT [dbo].[InforUser] ([email], [name], [phone], [address]) VALUES (N'huhu@gmail.com', N'123456', N'123456', N'123456')
INSERT [dbo].[InforUser] ([email], [name], [phone], [address]) VALUES (N'sasa@gmail.com', N'tagege', N'123456', N'123456')
INSERT [dbo].[InforUser] ([email], [name], [phone], [address]) VALUES (N'tannn@gmail.com', N'123456', N'123456', N'123456')
INSERT [dbo].[InforUser] ([email], [name], [phone], [address]) VALUES (N'tannvse140923@fpt.edu.vn', N'atnate', N'123456', N'123456')
INSERT [dbo].[InforUser] ([email], [name], [phone], [address]) VALUES (N'tantest@gmail.com', N'tannnn', N'123456', N'123465')
INSERT [dbo].[InforUser] ([email], [name], [phone], [address]) VALUES (N'tantinhte@gmail.com', N'tannnn', N'123456', N'123465')
INSERT [dbo].[InforUser] ([email], [name], [phone], [address]) VALUES (N'tram@gmail.com', N'ttrammm', N'123456', N'phu yen')
INSERT [dbo].[InforUser] ([email], [name], [phone], [address]) VALUES (N'tramtram@gmail.com', N'12345', N'12345', N'12345')
GO
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (1, 85, 20)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (1, 87, 1)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (1, 88, 20)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (1, 90, 1)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (2, 85, 20)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (2, 88, 20)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (2, 90, 1)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (3, 90, 1)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (4, 89, 20)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (4, 91, 1)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (5, 86, 20)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (5, 91, 1)
INSERT [dbo].[OrderDetail] ([roomID], [orderID], [quantity]) VALUES (6, 91, 1)
GO
SET IDENTITY_INSERT [dbo].[Orders] ON 

INSERT [dbo].[Orders] ([orderID], [hotelID], [userID], [totalCost], [createDate], [checkinDate], [checkOutDate], [discount]) VALUES (85, 1, N'tannvse140923@fpt.edu.vn', 25000, N'2021-10-28T20:55:47.110975300', CAST(N'2021-12-15' AS Date), CAST(N'2021-12-20' AS Date), 0)
INSERT [dbo].[Orders] ([orderID], [hotelID], [userID], [totalCost], [createDate], [checkinDate], [checkOutDate], [discount]) VALUES (86, 2, N'tannvse140923@fpt.edu.vn', 25000, N'2021-10-28T20:55:47.118941500', CAST(N'2021-12-15' AS Date), CAST(N'2021-12-20' AS Date), 0)
INSERT [dbo].[Orders] ([orderID], [hotelID], [userID], [totalCost], [createDate], [checkinDate], [checkOutDate], [discount]) VALUES (87, 1, N'tannvse140923@fpt.edu.vn', 200, N'2021-10-28T20:58:31.802256500', CAST(N'2021-12-21' AS Date), CAST(N'2021-12-23' AS Date), 0)
INSERT [dbo].[Orders] ([orderID], [hotelID], [userID], [totalCost], [createDate], [checkinDate], [checkOutDate], [discount]) VALUES (88, 1, N'tannvse140923@fpt.edu.vn', 25000, N'2021-10-29T10:29:05.885753400', CAST(N'2021-10-31' AS Date), CAST(N'2021-11-05' AS Date), 0)
INSERT [dbo].[Orders] ([orderID], [hotelID], [userID], [totalCost], [createDate], [checkinDate], [checkOutDate], [discount]) VALUES (89, 2, N'tannvse140923@fpt.edu.vn', 20000, N'2021-10-29T10:29:05.906708800', CAST(N'2021-10-31' AS Date), CAST(N'2021-11-05' AS Date), 0)
INSERT [dbo].[Orders] ([orderID], [hotelID], [userID], [totalCost], [createDate], [checkinDate], [checkOutDate], [discount]) VALUES (90, 1, N'tannvse140923@fpt.edu.vn', 1000, N'2021-10-29T10:38:55.175823300', CAST(N'2021-11-06' AS Date), CAST(N'2021-11-08' AS Date), 0)
INSERT [dbo].[Orders] ([orderID], [hotelID], [userID], [totalCost], [createDate], [checkinDate], [checkOutDate], [discount]) VALUES (91, 2, N'tannvse140923@fpt.edu.vn', 1500, N'2021-10-29T10:38:55.185823400', CAST(N'2021-11-06' AS Date), CAST(N'2021-11-08' AS Date), 0)
SET IDENTITY_INSERT [dbo].[Orders] OFF
GO
SET IDENTITY_INSERT [dbo].[Room] ON 

INSERT [dbo].[Room] ([roomID], [hotelID], [type], [amount], [price]) VALUES (1, 1, 1, 20, 100)
INSERT [dbo].[Room] ([roomID], [hotelID], [type], [amount], [price]) VALUES (2, 1, 2, 20, 150)
INSERT [dbo].[Room] ([roomID], [hotelID], [type], [amount], [price]) VALUES (3, 1, 3, 20, 250)
INSERT [dbo].[Room] ([roomID], [hotelID], [type], [amount], [price]) VALUES (4, 2, 1, 20, 200)
INSERT [dbo].[Room] ([roomID], [hotelID], [type], [amount], [price]) VALUES (5, 2, 2, 20, 250)
INSERT [dbo].[Room] ([roomID], [hotelID], [type], [amount], [price]) VALUES (6, 2, 3, 20, 300)
SET IDENTITY_INSERT [dbo].[Room] OFF
GO
ALTER TABLE [dbo].[InforUser]  WITH CHECK ADD FOREIGN KEY([email])
REFERENCES [dbo].[Account] ([email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD FOREIGN KEY([orderID])
REFERENCES [dbo].[Orders] ([orderID])
GO
ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD FOREIGN KEY([roomID])
REFERENCES [dbo].[Room] ([roomID])
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([userID])
REFERENCES [dbo].[Account] ([email])
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD FOREIGN KEY([hotelID])
REFERENCES [dbo].[Hotel] ([hotelID])
GO
ALTER TABLE [dbo].[Room]  WITH CHECK ADD FOREIGN KEY([hotelID])
REFERENCES [dbo].[Hotel] ([hotelID])
GO
USE [master]
GO
ALTER DATABASE [Booking_Hotel] SET  READ_WRITE 
GO
