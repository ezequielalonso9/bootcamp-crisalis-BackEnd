package com.crisalis.bootcamp.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CuentasPedido {
    //reformatear poner los dos parametros en una clase CuentasLinea
    private Float cargoAdicionalGarantia = 0F; //para linea
    private Float costoTotalLiena = 0F; //para linea
    private Float impuestosIva = 0F;
    private Float impuestosIbb = 0F;
    private Float otrosImpuestos = 0F;
    private Float descuentos = 0F;
    private Float subTotal = 0F;
    private Float totalPedido = 0F;

}
