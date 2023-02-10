package com.crisalis.bootcamp.controllers;

import com.crisalis.bootcamp.Services.PedidoService;
import com.crisalis.bootcamp.model.dto.ClienteDto;
import com.crisalis.bootcamp.model.dto.LineaPedidoDto;
import com.crisalis.bootcamp.model.dto.PedidoDto;
import com.crisalis.bootcamp.model.dto.requestDto.RequestPedido;
import com.crisalis.bootcamp.model.entities.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class PedidoController {

    @Autowired
    PedidoService pedidoService;


    @PostMapping("/pedido")
    private ResponseEntity<PedidoDto> create(@RequestBody RequestPedido pedidoRequest) {
        PedidoDto newPedido = pedidoService.save(pedidoRequest);
        return new ResponseEntity<PedidoDto>(newPedido, HttpStatus.OK);
    }

    @PostMapping("/pedido/{id}/linea")
    private ResponseEntity<PedidoDto> addLineToPedido(@PathVariable(value = "id") Long id,
                                                      @RequestBody RequestPedido pedidoRequest) {

        PedidoDto pedido = pedidoService.saveLineByIdPedido(id, pedidoRequest);

        return new ResponseEntity<PedidoDto>(pedido, HttpStatus.OK);
    }

    @PostMapping("/pedido/{id}")
    private ResponseEntity<PedidoDto> confirmation(@PathVariable(value = "id") Long id) {

        PedidoDto pedidoDto = pedidoService.confirmationPedidoByIdPedido(id);

        return new ResponseEntity<PedidoDto>(pedidoDto, HttpStatus.OK);
    }

    @DeleteMapping("/pedido/{id}")
    private ResponseEntity<Object> delete(@PathVariable(value = "id") Long id) {
        boolean isDelete = pedidoService.deleteById(id);

        if (isDelete) {
            return new ResponseEntity<>("pedido borrado", HttpStatus.OK);
        }

        return new ResponseEntity<>("baja logica", HttpStatus.ACCEPTED);
    }

    @PutMapping("/pedido/{pedidoId}/linea/{lineaId}")
    private ResponseEntity<PedidoDto> updateLinea(@PathVariable(value = "pedidoId") Long pedidoId,
                                                  @PathVariable(value = "lineaId") Long lineaId,
                                                  @RequestBody LineaPedidoDto lineaPedidoDto) {

        PedidoDto pedidoActualizado = pedidoService
                .updateLineaPedido(
                        pedidoId,
                        lineaId,
                        lineaPedidoDto);

        return new ResponseEntity<PedidoDto>(pedidoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/pedido/{pedidoId}/linea/{lineaId}")
    private ResponseEntity<Object> deleteLinea(@PathVariable(value = "pedidoId") Long pedidoId,
                                               @PathVariable(value = "lineaId") Long lineaId
    ) {
        boolean isDelete = pedidoService
                .deleteLineaByIdPedidoAndIdLinea(pedidoId, lineaId);

        if (isDelete) {
            return new ResponseEntity<>("linea borrado", HttpStatus.OK);
        }

        return new ResponseEntity<>("El pedido ya esta confirmado no se puede borrar",
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/pedidos")
    private ResponseEntity<List<PedidoDto>> getPedidos() {

        List<PedidoDto> pedidos = pedidoService.getAll();

        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<PedidoDto> getPedidoById(@PathVariable(value = "id") Long id){

        Pedido pedido = pedidoService.findPedidoById(id);

        return new ResponseEntity<>(pedido.toDto(), HttpStatus.OK);
    }

}
