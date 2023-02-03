package com.crisalis.bootcamp.controllers;

import com.crisalis.bootcamp.Services.PedidoService;
import com.crisalis.bootcamp.model.dto.PedidoDto;
import com.crisalis.bootcamp.model.entities.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PedidoController {

    @Autowired
    PedidoService pedidoService;


    @PostMapping("/pedido")
    private ResponseEntity<PedidoDto> create(@RequestBody PedidoDto pedidoDto){
        PedidoDto newPedido = pedidoService.save(pedidoDto);
        return new ResponseEntity<PedidoDto>(newPedido, HttpStatus.OK);
    }

    @PostMapping("/pedido/{id}/linea")
    private ResponseEntity<PedidoDto> addLineToPedido(@PathVariable(value = "id") Long id,
                                                      @RequestBody PedidoDto pedidoDto){

        PedidoDto pedido = pedidoService.saveLineByIdPedido(id,pedidoDto);

        return new ResponseEntity<PedidoDto>(pedido, HttpStatus.OK);
    }

    @GetMapping("/pedido")
    private ResponseEntity<PedidoDto> getPedido(){
        PedidoDto pedidoDto= new PedidoDto();
        return new ResponseEntity<PedidoDto>(pedidoDto,HttpStatus.OK);
    }

}
