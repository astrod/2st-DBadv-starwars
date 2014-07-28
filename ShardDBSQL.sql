show databases;

use ShardDB;

show tables;

-- shard table 생성 쿼리
-----------------------

create table db (
	DBID TINYINT(4) primary key,
	DBNAME CHAR(3),
	IP CHAR(15)
)


create table user2db (
	UID INT(11) primary key AUTO_INCREMENT,
	GID TINYINT(4),
	DBID TINYINT(4),
	foreign key(DBID) REFERENCES db(DBID)
)
-----------------------

-- 회원가입 프로시저
drop PROCEDURE if EXISTS adduser
DELIMITER $$

create PROCEDURE adduser(OUT ruid int, OUT rdbid int, OUT rgid tinyint)
BEGIN
	insert into user2db values ();
	set ruid = LAST_INSERT_ID();
	set rgid = ruid % 4;
	set rdbid = rgid % 2;

	start TRANSACTION;
		
	update user2db set dbid = rdbid, gid = rgid where uid = ruid;
	COMMIT;
	
	END $$
DELIMITER ;
	
-----------------------
call adduser(@ruid, @rdbid, @rgid);

select * from user2db;
select * from db;

------------------------

-- 유저의 IP를 찾는 쿼리. UID에 인자를 넣어서, 이 번호 유저를 찾기 위해 접속해야 하는 로컬DB의 IP를 찾는다.
select d.IP from db d inner join user2db u on d.DBID = u.DBID where u.`UID` = 2;


-- 1) 아까 생성한 유저의 ip를 가져온다(위의 inner join 쿼리) // 여기까지 오면 uid, gid랑 ip를 갖고 있게 된다.
-- 2) 그 ip를 가져와서 java단에서 새로운 커넥션을 구성해서 로컬 DB에 접속한다.

--------------------------------------------

-- 3) 그 로컬 서버에 user 테이블에 데이터를 insert한다. (갤럭시 테이블에 먼저 데이터를 삽입한 상태여야 한다.)
-- 4) ship에 데이터를 삽입한다. UID, GID는 가져온 걸 사용하고, ATK는 랜덤으로 삽입한다 ( 5 - 100). SID는 AUTO_INCREMENT이다.

--------------

-- 회원가입 프로시저2 (ip가져오기)
drop PROCEDURE if EXISTS getIP
DELIMITER $$

create PROCEDURE getIP(IN uid int, IN gid tinyint, OUT tempIP char(15))
BEGIN
	start TRANSACTION;
	
	select d.IP into tempIP from db d inner join user2db u on d.DBID = u.DBID where u.`UID` = uid;
	
	COMMIT;
	
	END $$
DELIMITER ;

------

-- 공격
drop PROCEDURE if EXISTS selectUser
DELIMITER $$

create PROCEDURE selectUser(out tIP char(15), out tuid int, out tgid int)
BEGIN

	DECLARE totalPlayer int;
	
	select count(*) into totalPlayer from user2db;
	
	select FLOOR(RAND()*(totalPlayer+1)) into tuid;
		
	select d.IP into tIP from db d inner join user2db u on d.DBID = u.DBID where u.`UID` = tuid;
	select GID into tgid from user2db where UID = tuid;	
			
	END $$
DELIMITER ;

desc user2db;

call selectUser(@a, @b, @c);
select @a;
select @b;
select @c;