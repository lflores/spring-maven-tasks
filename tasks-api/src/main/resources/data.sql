-- @created triad
--Para precargar datos de prueba
select * from tasks;
insert into tasks (description,status,image) values('Task1','todo', 'images/myimage.gif');
insert into tasks (description,status,image) values('Task2', 'resolved', null);
insert into tasks (description,status,image) values('Task3','todo', 'images/my-default.gif');
insert into tasks (description,status,image) values('Task4','todo', 'default1.gif');
insert into tasks (description,status,image) values('Task5','todo', null);
insert into tasks (description,status,image) values('Task6','todo', 'default2.gif');
