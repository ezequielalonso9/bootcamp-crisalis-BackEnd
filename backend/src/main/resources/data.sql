INSERT INTO persona(nombre,apellido,dni) VALUES('persona1','apellido1',111);
INSERT INTO persona(nombre,apellido,dni) VALUES('persona2','apellido2',111);
INSERT INTO persona(nombre,apellido,dni) VALUES('persona3','apellido3',111);
INSERT INTO persona(nombre,apellido,dni) VALUES('persona4','apellido4',111);
INSERT INTO persona(nombre,apellido,dni) VALUES('persona5','apellido5',111);

INSERT INTO cliente(estado,persona_id) VALUES(1, 1);
INSERT INTO cliente(estado,persona_id) VALUES(1, 2);
INSERT INTO cliente(estado,persona_id) VALUES(1, 3);
INSERT INTO cliente(estado,persona_id) VALUES(1, 4);
INSERT INTO cliente(estado,persona_id) VALUES(1, 5);

INSERT INTO tipo_producto (tipo_producto) VALUES('PRODUCTO');
INSERT INTO tipo_producto (tipo_producto) VALUES('SERVICIO');

INSERT  INTO producto (nombre,costo,cargo_adicional_soporte,tipo_producto_id)
 VALUES('teclado', 20, NULL, 1);
INSERT  INTO producto (nombre,costo,cargo_adicional_soporte,tipo_producto_id)
 VALUES('mouse', 10, NULL, 1);
INSERT  INTO producto (nombre,costo,cargo_adicional_soporte,tipo_producto_id)
 VALUES('monitor', 100, NULL, 1);
INSERT  INTO producto (nombre,costo,cargo_adicional_soporte,tipo_producto_id)
 VALUES('movistar', 50, NULL, 2);
INSERT  INTO producto (nombre,costo,cargo_adicional_soporte,tipo_producto_id)
 VALUES('pepitoServer', 50, 50, 2);

 INSERT INTO impuesto (nombre_impuesto,valor_impuesto) VALUES('IVA', 25);
 INSERT INTO impuesto (nombre_impuesto,valor_impuesto) VALUES('provincial Bs As', 17);
 INSERT INTO impuesto (nombre_impuesto,valor_impuesto) VALUES('municipal', 5);

