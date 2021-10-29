-- lấy tên của khách sạn, và tổng số phòng khách sạn đó cho thuê từ ngày đến ngày
select roomID ,SUM(quantity) as 'sumQuantity',ord.hotelID as hotelID from orders ord inner join OrderDetail od on ord.orderID = od.orderID
	where ('2021-12-12' between ord.checkinDate and ord.checkOutDate) OR ('2021-12-16' between ord.checkinDate and ord.checkOutDate)
	group by roomID,ord.hotelID

-- lấy ID của khách sạn, và tổng số phòng khách sạn đó cho thuê từ ngày đến ngày, nhóm lại cái những cái room
select hotelID, SUM(sumQuanity) as 'rented' from (select roomID ,SUM(quantity) as 'sumQuanity',ord.hotelID as hotelID from orders ord inner join OrderDetail od on ord.orderID = od.orderID
	where ('2021-12-12' between ord.checkinDate and ord.checkOutDate) OR ('2021-12-16' between ord.checkinDate and ord.checkOutDate)
	group by roomID,ord.hotelID
) AS Caculate
group by hotelID
-- lấy tổng số phòng khách sạn đang có
select hotelID, SUM(amount) as 'exist' from Room
	group by hotelID
-- lấy 2 cái số lượng đang có trừ số lượng đang cho thuê và thêm vào số lượng tìm kiếm của khách hàng
select RENT.hotelID from (select hotelID, SUM(sumQuanity) as 'rented' from (select roomID ,SUM(quantity) as 'sumQuanity',ord.hotelID as hotelID
		from orders ord inner join OrderDetail od on ord.orderID = od.orderID
		where ('2021-11-15' between ord.checkinDate and ord.checkOutDate) OR ('2021-11-15' between ord.checkinDate and ord.checkOutDate)
		group by roomID,ord.hotelID
		) AS Caculate
		group by hotelID) AS RENT inner join (select hotelID, SUM(amount) as 'exist' from Room
												group by hotelID) AS EXIST
		on RENT.hotelID = EXIST.hotelID and (EXIST.exist - RENT.rented) >= 60
-- chọn ra những cái khách sạn hợp lệ
select * from Hotel where hotelID IN (select RENT.hotelID from (select hotelID, SUM(sumQuanity) as 'rented' from (select roomID ,SUM(quantity) as 'sumQuanity',ord.hotelID as hotelID
		from orders ord inner join OrderDetail od on ord.orderID = od.orderID
		where ('2021-11-15' between ord.checkinDate and ord.checkOutDate) OR ('2021-11-15' between ord.checkinDate and ord.checkOutDate)
		group by roomID,ord.hotelID
		) AS Caculate
		group by hotelID) AS RENT inner join (select hotelID, SUM(amount) as 'exist' from Room
												group by hotelID) AS EXIST
		on RENT.hotelID = EXIST.hotelID and (EXIST.exist - RENT.rented) >= 39)
		Order by hotelName
		OFFSET 0 ROWS FETCH NEXT 20 ROWS ONLY


-- số lượng từng loại phòng đã cho thuê
select roomID ,SUM(quantity) as 'sumQuantity',ord.hotelID as hotelID from orders ord inner join OrderDetail od on ord.orderID = od.orderID
	where ('2021-12-12' between ord.checkinDate and ord.checkOutDate) OR ('2021-12-16' between ord.checkinDate and ord.checkOutDate)
	group by roomID,ord.hotelID
-- số lượng từng loại phòng mà khách sạn đang có
select roomID, amount from Room
-- lấy 2 số lượng phòng trừ lại với nhau, sau đó cho thêm điều kiện là cái số phòng mà ng dùng muốn đặt từ ngày đến ngày
select * from (select roomID ,SUM(quantity) as 'sumQuantity',ord.hotelID as hotelID from orders ord inner join OrderDetail od on ord.orderID = od.orderID
	where ('2021-12-12' between ord.checkinDate and ord.checkOutDate) OR ('2021-12-16' between ord.checkinDate and ord.checkOutDate)
	group by roomID,ord.hotelID) AS RENT INNER JOIN (select roomID, amount from Room) AS EXIST
	on RENT.roomID = EXIST.roomID
	where (EXIST.amount - RENT.sumQuantity) > 7


-- số lượng phòng đã cho thuê với loại roomId từ ngày đến ngày
select SUM(quantity) as 'sumQuantity' from orders ord inner join OrderDetail od on ord.orderID = od.orderID
	where (('2021-12-12' between ord.checkinDate and ord.checkOutDate) OR ('2021-12-16' between ord.checkinDate and ord.checkOutDate))
		AND roomID = 2

																	
 

