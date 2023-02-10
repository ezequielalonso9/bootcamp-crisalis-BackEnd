package com.crisalis.bootcamp.model.entities;

import com.crisalis.bootcamp.helper.CuentasPedido;
import com.crisalis.bootcamp.model.dto.ClienteDto;
import com.crisalis.bootcamp.model.dto.LineaPedidoDto;
import com.crisalis.bootcamp.model.dto.PedidoDto;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_pedido")
    private Date fechaPedido;
    @Column(name = "fehca_modificacion")
    private Date fehcaUltimaModificacion;
    private Float subTotalPedido;
    private Float totalImpuestoIva;
    private Float totalImpuestoIbb;
    private Float totalOtrosImpuestos;
    private Float descuentoTotal;
    private Float totalPedido;

    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @ToString.Exclude
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<LineaPedido> lineasPedido = new HashSet<>();



    public void addLineaPedido(LineaPedido lineaPedido) {
        if( this.getLineasPedido() == null ){
            this.lineasPedido = new HashSet<>();
            this.lineasPedido.add(lineaPedido);
        }else {
            this.getLineasPedido().add(lineaPedido);
        }
    }

    public String getTipoCliente(){
        if(this.cliente.getEmpresa() == null ){
            return "Persona";
        }
        return "Empresa";
    }


    public PedidoDto toDto(){

        Set<LineaPedido> lineas = this.getLineasPedido();
        Set< LineaPedidoDto> lineasDto = new HashSet<>();
        if ( lineas.size() > 0 ) {
            lineas.forEach( linea -> {
                lineasDto.add( linea.toDto() );
            });
        }

        return PedidoDto
                .builder()
                .id(this.id)
                .idCliente(this.cliente.getId())
                .tipoCliente(this.getTipoCliente())
                .lineas(lineasDto)
                .fechaPedido(this.fechaPedido)
                .fechaUltimaModificacion(this.fehcaUltimaModificacion)
                .estado(this.estado)
                .subTotalPedido(this.subTotalPedido)
                .totalImpuestoIva(this.totalImpuestoIva)
                .totalImpuestoIbb(this.totalImpuestoIbb)
                .totalOtrosImpuestos(this.totalOtrosImpuestos)
                .descuentoTotal(this.descuentoTotal)
                .totalPedido(this.totalPedido)
                .cliente(new ClienteDto(this.cliente))
                .build();
    }

    public void setMontosPedido(CuentasPedido cuentas){
        this.setSubTotalPedido(cuentas.getSubTotal());
        this.setTotalImpuestoIva(cuentas.getImpuestosIva());
        this.setTotalImpuestoIbb(cuentas.getImpuestosIbb());
        this.setTotalOtrosImpuestos(cuentas.getOtrosImpuestos());
        this.setDescuentoTotal(cuentas.getDescuentos());
        this.setTotalPedido(cuentas.getTotalPedido());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Pedido pedido = (Pedido) o;
        return id != null && Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
