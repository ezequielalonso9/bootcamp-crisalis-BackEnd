package com.crisalis.bootcamp.Services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class GarantiaService {

    //Afuturo se piensa en crear una tabla Garantia que dependa de tipo de Prestacion
    //Por eso una capa de servicio de garantia
    //valor pedido 2%
    private Float valorGarantia = 2F;

    public Float changeGarantia(Float newValorGarantia ){
        this.setValorGarantia(newValorGarantia);
        return valorGarantia;
    }

    public Float calculateGarantiaForCostoPrestacion(Integer yearsGarantia, Float costoPrestacion){
        return yearsGarantia * valorGarantia * costoPrestacion / 100;
    }
}