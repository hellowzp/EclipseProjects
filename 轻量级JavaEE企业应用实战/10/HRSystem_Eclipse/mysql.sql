DROP database IF exists hrSystem;

CREATE database hrSystem;

use hrSystem;

CREATE TABLE  type_table(
type_id integer auto_increment,
type_name varchar(50) not null,
amerce_amount double not null,
primary key(type_id));

CREATE TABLE  mgr_table(
mgr_id integer auto_increment,
dept_name varchar(50) not null,
primary key(mgr_id));

CREATE TABLE  emp_table(
emp_id integer auto_increment,
emp_name varchar(50) not null,
emp_pass varchar(50) not null,
emp_salary double not null,
mgr_id integer,
primary key(emp_id),
unique key(emp_name),
foreign key(mgr_id) references mgr_table(mgr_id)
);

CREATE TABLE attend_table(
attend_id integer auto_increment,
duty_day varchar(50) not null,
punch_time datetime,
is_come boolean not null,
type_id integer not null,
emp_id integer not null,
primary key(attend_id),
foreign key(type_id) references type_table(type_id),
foreign key(emp_id) references emp_table(emp_id)
);

CREATE TABLE app_table(
app_id integer auto_increment,
attend_id integer not null,
app_reason varchar(255),
app_result boolean,
type_id integer not null,
primary key(app_id),
foreign key(type_id) references type_table(type_id),
foreign key(attend_id) references attend_table(attend_id)
);

CREATE TABLE  pay_table(
pay_id integer auto_increment,
pay_month varchar(50) not null,
pay_amount double not null,
emp_id integer not null,
primary key(pay_id),
foreign key(emp_id) references emp_table(emp_id)
);

CREATE TABLE  check_table(
check_id integer auto_increment,
app_id integer not null,
check_result boolean not null,
check_reason varchar(255),
mgr_id integer not null,
primary key(check_id),
foreign key(app_id) references app_table(app_id),
foreign key(mgr_id) references mgr_table(mgr_id)
);

INSERT INTO type_table ( type_name , amerce_amount)
	VALUES ( '正常', 0);
INSERT INTO type_table ( type_name , amerce_amount)
	VALUES ( '事假', -20);
INSERT INTO type_table ( type_name , amerce_amount)
	VALUES ( '病假', -10);
INSERT INTO type_table ( type_name , amerce_amount)
	VALUES ( '迟到', -10);
INSERT INTO type_table ( type_name , amerce_amount)
	VALUES ( '早退', -10);
INSERT INTO type_table ( type_name , amerce_amount)
	VALUES ( '旷工', -30);
INSERT INTO type_table ( type_name , amerce_amount)
	VALUES ( '出差', 10);


# 插入经理
INSERT INTO mgr_table ( dept_name)
	VALUES ( 'DB部');
INSERT INTO mgr_table ( dept_name)
	VALUES ( 'Server部');

# 插入员工
INSERT INTO emp_table ( emp_name , emp_pass , emp_salary , mgr_id)
	VALUES ( 'oracle', 'oracle' , 5000 , null);
INSERT INTO emp_table ( emp_name , emp_pass , emp_salary , mgr_id)
	VALUES ( 'weblogic', 'weblogic' , 6000 , null);

INSERT INTO emp_table ( emp_name , emp_pass , emp_salary , mgr_id)
	VALUES ( 'mysql', 'mysql' , 3000 , 1);
INSERT INTO emp_table ( emp_name , emp_pass , emp_salary , mgr_id)
	VALUES ( 'hsql', 'hsql' , 3200 , 1);
INSERT INTO emp_table ( emp_name , emp_pass , emp_salary , mgr_id)
	VALUES ( 'tomcat', 'tomcat' , 2800 , 2);
INSERT INTO emp_table ( emp_name , emp_pass , emp_salary , mgr_id)
	VALUES ( 'jetty', 'jetty' , 2560 , 2);
