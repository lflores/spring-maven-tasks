-- @created triad
--Para precargar datos de prueba
select * from tasks;
insert into tasks (description,status) values('Define scope of images','todo');
insert into tasks (description,status) values('Deployment Diagram','resolved');
insert into tasks (description,status,image) values('Review Diagram','in-progress','http://localhost:8080/downloadFile/diagram.png');
insert into tasks (description,status) values('Image File Upload','todo');
insert into tasks (description,status) values('Deploy','todo');
insert into tasks (description,status) values('Continuous Integration','todo');
