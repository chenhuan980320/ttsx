drop database if exists youxian;


-- 创建库
create database youxian default character set UTF8 collate utf8_bin;

-- 切换库
use youxian;

-- 参加表
-- 管理员信息表 
create table if not exists admininfo(
	aid int(10) primary key auto_increment comment '管理员编号',
	tel int(11) not null unique comment '管理员手机号',
	aname varchar(100) not null unique comment '管理员姓名',
	pwd varchar(100) not null comment '密码',
	email varchar(15) unique not null comment '联系方式',
	status int(2) comment '状态(1管理员  100超级管理员  0禁)'
)engine = InnoDB auto_increment = 101 default charset=utf8 collate=utf8_bin;


-- 会员信息
create table if not exists memberinfo(
	mid int(11) primary key auto_increment comment '会员编号',
	nickName varchar(100) not null unique comment '昵称',
	pwd varchar(100) not null comment '密码',
	tel varchar(15) unique comment '联系方式',
	email varchar(100) not null unique comment '注册邮箱',
	photo varchar(100) comment '图像',
	status int(2) comment '状态(1 会员  2是商家  10,20禁用)'
)engine = InnoDB auto_increment = 1 default charset=utf8 collate=utf8_bin;

-- 类型表
create table if not exists typeinfo(
	tid int(5) primary key auto_increment comment '商品类型编号',
	tname varchar(100) not null unique comment '类型名称',
	status int(2) comment '类型状态'
)engine = InnoDB auto_increment = 1 default charset=utf8 collate=utf8_bin;

-- 店铺表
create table if not exists shopinfo(
	sid int(11) primary key auto_increment comment '店铺编号',
	mid int(11) comment '卖家编号',
	sname varchar(200) not null comment '店铺名称',
	intro varchar(500) comment '店铺简介',
	pics varchar(200) comment '店铺图片',
	tel varchar(20) comment '联系方式',
	license varchar(200) comment '营业执照',
	regDate datetime comment '注册日期',
	status int(2) comment '状态'
)engine = InnoDB auto_increment = 1 default charset=utf8 collate=utf8_bin;

-- 商品信息
create table if not exists goodsinfo(
	gid int(11) primary key auto_increment comment'商品编号',
	sid int(11) comment '所属店铺',
	gname varchar(100) not null comment '商品名称',
	pics varchar(200) not null comment '商品图片',
	dintro varchar(200) comment '商品详细',
	intro varchar(200) comment '商品简介',
	price decimal(10,2) not null comment '原价',
	constraint FK_goodsinfo_sid foreign key(sid) references shopinfo(sid)
)engine = InnoDB auto_increment = 1 default charset=utf8 collate=utf8_bin;


-- 商品活动表
create table if not exists activeinfo(
	acid int(11) primary key auto_increment comment '活动编号',
	gid int(11) comment '商品编号',
	activeName varchar(100) not null comment '活动名称',
	rebate decimal(2,0) not null comment '折扣',
	sdate date not null comment '活动开始时间',
	edate date not null comment '活动结束时间',
	status int(2) comment '活动状态',
	constraint FK_activeinfo_gid foreign key(gid) references goodsinfo(gid)
)engine = InnoDB auto_increment = 1 default charset=utf8 collate=utf8_bin;

-- 收货地址表
create table if not exists placeinfo(
	pid int(11) primary key auto_increment comment '地址编号',
    mid int(11) comment '会员编号',
    tel int(11) comment '电话',
	province varchar(100) not null comment '省份',
	city varchar(100) not null comment '城市',
	area varchar(100) not null comment '地区',
	addr varchar(200) comment '详细地址',
	constraint FK_memberinfo_mid foreign key(mid) references memberinfo(mid)
)engine = InnoDB auto_increment = 1 default charset=utf8 collate=utf8_bin;
-- 订单表
create table if not exists orderinfo(
	oid int(11) primary key auto_increment comment '订单编号',
	mid int(11) comment '会员编号',
	nickName varchar(100) not null unique comment '昵称',
	pid int(11) comment '地址编号',
	odate datetime comment '下单日期',
	status int(2) comment '收货状态',
	total decimal(10,2) not null comment '总价',
	constraint FK_orderinfo_mid foreign key(mid) references memberinfo(mid),
	constraint FK_orderinfo_pid foreign key(pid) references placeinfo(pid)
	)engine = InnoDB auto_increment = 202011 default charset=utf8 collate=utf8_bin;


-- 订单详细表
create table if not exists detailinfo(
	did int(11) primary key auto_increment comment '详细编号',
	oid int(11) comment '订单编号',
	gid int(11) comment '商品编号',
	pics varchar(200) not null comment '商品图片',
	price decimal(10,2) not null comment '商品单价',
	nums int(11) comment '购买数量',
	constraint FK_detailinfo_gid foreign key(gid) references goodsinfo(gid)	
)engine = InnoDB auto_increment = 1 default charset=utf8 collate=utf8_bin;



-- 购物车
create table if not exists cartinfo(
	oid int(11) primary key comment '订单编号',
	mid int(11) comment '会员编号',
	gid int(11) comment '商品编号',
	nums int(11) comment '购买数量',
	constraint FK_cartinfo_oid foreign key(oid) references orderinfo(oid),
	constraint FK_cartinfo_mid foreign key(mid) references memberinfo(mid),
	constraint FK_cartinfo_gid foreign key(gid) references goodsinfo(gid)
)default charset=utf8 collate=utf8_bin;


