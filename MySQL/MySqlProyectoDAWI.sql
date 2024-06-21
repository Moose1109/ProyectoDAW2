-- borra la bd si existe
DROP DATABASE IF EXISTS proyectodawi;
-- creamos la bd
CREATE DATABASE proyectodawi;
-- activamos la bd
USE proyectodawi;

Create table tb_cliente(
idcliente varchar(7) primary key ,
nomcliente varchar(50),
apecliente varchar(50),
dni varchar (8),
direccion varchar (50),
celular  varchar(9),
puesto varchar(4),
estado int
);

Create table tb_categoria(
idcategoria int primary key auto_increment ,
nomcategoria varchar(50)
);
Create table tb_productos(
codproducto varchar(7) primary key ,
nomproducto varchar(50) not null,
idcategoria int,
precio double ,
stock int ,
estado int,
constraint fk_categoria foreign key (idcategoria) references tb_categoria(idcategoria)
);

Create table tb_empleados(
idempleado varchar(7) primary key ,
nomempleado varchar(50),
apeempleado varchar(50),
dni varchar(8),
fechanacimiento date ,
usuario varchar (50),
contraseña varchar(50),
estado int
);

Create table tb_venta(
idventas int primary key auto_increment,
nroserie varchar(20),
idcliente varchar(7),
idempleado varchar(7),
monto double,
fecha date,
constraint fk_cliente foreign key(idcliente) references   tb_cliente (idcliente),
constraint fk_empleado foreign key(idempleado) references  tb_empleados (idempleado)
);

Create table tb_detalle_venta( 
iddetalleventa int primary key auto_increment ,
idventas int,
codproducto varchar(7),
cantidad int,
precioVenta double,
constraint fk_ventas foreign key(idventas) references   tb_venta (idventas),
constraint fk_productos foreign key(codproducto) references  tb_productos  (codproducto)
);

insert into tb_categoria values (1,'Arroces');
insert into tb_categoria values (2,'Limpieza');
insert into tb_categoria values (3,'Maices');
insert into tb_categoria values (4,'Azucar');
insert into tb_categoria values (5,'Papeles');
insert into tb_categoria values (6,'Bebidas');
insert into tb_categoria values (7,'Aceites');
insert into tb_categoria values (8,'Fideos');
insert into tb_categoria values (9,'Descartables');
insert into tb_categoria values (10,'Mascotas');
insert into tb_categoria values (11,'Dulces');


//GENERARDOR DE CÓDIGO Producto(PRO *)
delimiter $
create trigger GenerarCodigoProducto before insert on tb_productos for each row
begin
	declare siguiente_CodigoProducto int;
    set siguiente_CodigoProducto = (Select ifnull(max(convert(substring(codproducto, 5), signed integer)),0) from tb_productos) +1;
    set new.codproducto = concat('PRO ', LPAD(siguiente_CodigoProducto,3,'0'));
end $

delimiter $
create trigger GenerarCodigoCliente before insert on tb_cliente for each row
begin
	declare siguiente_idcliente int;
    set siguiente_idcliente = (Select ifnull(max(convert(substring(idcliente, 5), signed integer)),0) from tb_cliente) +1;
    set new.idcliente = concat('CLI ', LPAD(siguiente_idcliente,3,'0'));
end $




 -- GENERAR CODIGO AUTOGENERADO
//GENERARDOR DE CÓDIGO EMPLEADO (tb_empleadosE00*)
delimiter $
create trigger Generar_idEmpleado before insert on tb_empleados for each row
begin
	declare siguiente_id int;
    set siguiente_id = (Select ifnull(max(convert(substring(idempleado, 5), signed integer)),0) from tb_empleados) +1;
    set new.idempleado = concat('EMP ', LPAD(siguiente_id,3,'0'));
end $

-- un registro para tenerlo como login ---------------

insert into tb_cliente values (null,'Jorge', 'Gonzales', '04149109', 'Jiron Canta 189, La Victoria', '987654321',"#007",1);
insert into tb_cliente values (null,'Juana', 'Cisneros', '04908709', 'Jiron Ilo 189, Cercado', '987654322',"#001",1);
insert into tb_cliente values (null,'Humberto', 'Salas', '09087109', 'Jiron Zepita 189, Cercado', '987654222',"#007",1);
insert into tb_cliente values (null,'Juliana', 'Lozano', '04123219', 'Jiron Cangallo 189, Barrios Altos', '987652222',"#001",1);
insert into tb_cliente values (null,'Nestor', 'Ayasta', '04908709', 'Jiron Camana 189, Pueblo Libre', '987622222',"#001",1);

insert into tb_empleados values (null,'Nestor', 'Ayasta', '04908709', '1996-06-25', 'el mago','dehoz',1);
insert into tb_empleados values (null,'Luana', 'Gutierrez', '04908709', '2002-06-25', 'lacangri','12345',1);
insert into tb_empleados values (null,'Honorata', 'Rojas', '04908709', '2001-06-25', 'lamensa','90865',1);
insert into tb_empleados values (null,'Jose', 'Huertas', '04908709', '1998-06-25', 'elgoat','87234',1);
insert into tb_empleados values (null,'Fernando', 'Rondon', '04908709', '2001-06-25', 'billbixby','24864',1);
insert into tb_empleados values (null,'Pedro', 'Paredes', '04908709', '2000-06-25', 'ironman','12567',1);

insert into tb_productos values (null,'Arroz Paisana 1kg', 7, 4.50, 200,1);
insert into tb_productos values (null,'Papel Higienico Paracas 2 und', 5, 2.50, 300,1);
insert into tb_productos values (null,'Spaghetti Molitalia 0.50 kg', 8, 5.00, 150,1);
insert into tb_productos values (null,'fideo don Victorio 1kg', 8, 6.00, 80,1);
insert into tb_productos values (null,'Mimaskot Gatos 1kg', 10, 14.50, 30,1);
insert into tb_productos values (null,'Chocolate Sublime 1unid', 11, 2.50, 90,1);

insert into tb_venta values (null,'00000001', 'CLI 001', 'EMP 003',12.0, '2023-11-15');
insert into tb_venta values (null,'00000002', 'CLI 001', 'EMP 003',19.50, '2023-10-28');
insert into tb_venta values (null,'00000003', 'CLI 002', 'EMP 001',2.50, '2023-10-28');
insert into tb_venta values (null,'00000004', 'CLI 003', 'EMP 002',43.50, '2023-10-28');
insert into tb_venta values (null,'00000005', 'CLI 005', 'EMP 001',11.50, '2023-09-12');
insert into tb_venta values (null,'00000006', 'CLI 001', 'EMP 002',13.50, '2023-09-12');
insert into tb_venta values (null,'00000007', 'CLI 004', 'EMP 001',6.0, '2023-08-20');
insert into tb_venta values (null,'00000008', 'CLI 002', 'EMP 004',12.0, '2023-08-20');
insert into tb_venta values (null,'00000009', 'CLI 004', 'EMP 005',2.0, '2023-08-20');

insert into tb_detalle_venta values (null, 1, 'PRO 004', 2, 12.0);
insert into tb_detalle_venta values (null, 2, 'PRO 005', 1, 14.50);
insert into tb_detalle_venta values (null, 2, 'PRO 002', 2, 5.50);
insert into tb_detalle_venta values (null, 3, 'PRO 002', 1, 2.50);
insert into tb_detalle_venta values (null, 4, 'PRO 005', 3, 43.50);
insert into tb_detalle_venta values (null, 5, 'PRO 006', 1, 2.50);
insert into tb_detalle_venta values (null, 5, 'PRO 001', 2, 9.0);
insert into tb_detalle_venta values (null, 6, 'PRO 001', 3, 13.50);
insert into tb_detalle_venta values (null, 7, 'PRO 004', 1, 6.0);
insert into tb_detalle_venta values (null, 8, 'PRO 004', 2, 12.0);
insert into tb_detalle_venta values (null, 9, 'PRO 002', 1, 2.50);










