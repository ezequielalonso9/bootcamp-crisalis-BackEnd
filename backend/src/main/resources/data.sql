INSERT INTO persona(nombre,apellido,dni) VALUES('persona1','apellido1',40563321);
INSERT INTO persona(nombre,apellido,dni) VALUES('persona2','apellido2',222);
INSERT INTO persona(nombre,apellido,dni) VALUES('persona3','apellido3',333);
INSERT INTO persona(nombre,apellido,dni) VALUES('persona4','apellido4',444);
INSERT INTO persona(nombre,apellido,dni) VALUES('persona5','apellido5',555);


INSERT INTO empresa(razon_social, fecha_inicio_actividad, cuit) VALUES('empresa1', '01-01-2022', 111111);
INSERT INTO empresa(razon_social, fecha_inicio_actividad, cuit) VALUES('empresa2', '01-01-2022', 222222);

INSERT INTO cliente(estado,persona_dni) VALUES(1, 40563321);
INSERT INTO cliente(estado,persona_dni) VALUES(1, 222);
INSERT INTO cliente(estado,persona_dni) VALUES(1, 333);
INSERT INTO cliente(estado,persona_dni, empresa_cuit) VALUES(1, 444, 111111);
INSERT INTO cliente(estado,persona_dni, empresa_cuit) VALUES(1, 555, 222222);

INSERT INTO tipo_prestacion (tipo_producto, nombre) VALUES(1, 'Producto');
INSERT INTO tipo_prestacion (tipo_producto, nombre) VALUES(2, 'Servicio');

-- tipo_producto_id cambiar por => type completar con valor 1(Producto) 2(Servicio)
INSERT  INTO prestacion (nombre,costo,cargo_adicional_soporte,type, estado)
 VALUES('teclado', 20, NULL, 'Producto', 1);
INSERT  INTO prestacion (nombre,costo,cargo_adicional_soporte,type, estado)
 VALUES('mouse', 10, NULL, 'Producto', 1);
INSERT  INTO prestacion (nombre,costo,cargo_adicional_soporte,type, estado)
 VALUES('monitor', 100, NULL, 'Producto', 1);
INSERT  INTO prestacion (nombre,costo,cargo_adicional_soporte,type, estado)
 VALUES('movistar', 50, NULL, 'Servicio', 1);
INSERT  INTO prestacion (nombre,costo,cargo_adicional_soporte,type, estado)
 VALUES('pepitoServer', 50, 50, 'Servicio', 1);

 INSERT INTO impuesto (nombre_impuesto,valor_impuesto) VALUES('IVA', 21);
 INSERT INTO impuesto (nombre_impuesto,valor_impuesto) VALUES('IBB', 10);
 INSERT INTO impuesto (nombre_impuesto,valor_impuesto) VALUES('provincial Bs As', 17);
 INSERT INTO impuesto (nombre_impuesto,valor_impuesto) VALUES('municipal', 5);

 INSERT INTO prestacion_impuesto (prestacion_id,impuesto_id) VALUES(1, 1);
 INSERT INTO prestacion_impuesto (prestacion_id,impuesto_id) VALUES(2, 1);
 INSERT INTO prestacion_impuesto (prestacion_id,impuesto_id) VALUES(3, 1);
 INSERT INTO prestacion_impuesto (prestacion_id,impuesto_id) VALUES(4, 1);


