-- 프로시저 생성 코드

DELIMITER ;;
CREATE DEFINER=`jongun`@`%` PROCEDURE `createShip`(IN uid int, IN gid tinyint)
BEGIN
	DECLARE i INT;
	SET i = 0;
	
		INSERT INTO user values (uid, gid);
		
		WHILE i<10 DO
			insert into ship values (null, uid, gid, FLOOR(RAND()*96) +5);
			SET i = i+1;
		END WHILE;
		
END;;
DELIMITER ;

insert into `galaxy` values (0, "QQQ", 100000), (2, "WWW", 100000);

desc `galaxy`;

select * from ship;



select s.* from ship s inner join user u on s.UID = u.UID where s.UID = 38;

-- 공격 코드

