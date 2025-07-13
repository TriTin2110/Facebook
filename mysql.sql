create database facebook character set utf8mb4 collate utf8mb4_general_ci;
drop database facebook;

alter table announce add index to_user_index (to_user);
alter table friend_request add index from_user_index (from_user);

use facebook;
select * from messenger; 
truncate messenger;
select * from user;
select * from user_friend;
select * from user_information;
select * from user u join user_information ui on u.userId = ui.user_information_id where ui.fullName like '%Tín%';
select * from post;
select * from announce;

delete from user_information where user_information_id = 'de10763cfc025790ea59c6942660e551283fa22179d04de2fd49dcb63482be9c';
delete from announce where from_user = 'de10763cfc025790ea59c6942660e551283fa22179d04de2fd49dcb63482be9c';
delete from user where userId = 'de10763cfc025790ea59c6942660e551283fa22179d04de2fd49dcb63482be9c';

truncate friend_receive;
delete from announce where id = '3ad1803d0a41b8a77148954e75db7d6e4eec47f4b2e04bd49d1c899ba850213a'

update user set identifyStatus = 1 where userId = 'e7059c525a6f85be2dcfcb6292f1194ab51ea59ead56190ac5b825085734561d'


