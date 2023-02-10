package com.crisalis.bootcamp.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CuentasLinea {

    private Float costoUnitarioGarantia = 0F;
    private Float costoUnitarioSoporte = 0F;
    private Float totalAdicionalGarantia = 0F;
    private Float totalAdicionalSoporte = 0F; //para linea
    private Float costoTotalLiena = 0F; //para linea
}
