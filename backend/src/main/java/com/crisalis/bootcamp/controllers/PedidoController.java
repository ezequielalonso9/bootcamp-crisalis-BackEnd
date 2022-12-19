package com.crisalis.bootcamp.controllers;

import com.crisalis.bootcamp.Services.PedidoService;
import com.crisalis.bootcamp.model.dto.PedidoDto;
import com.crisalis.bootcamp.model.entities.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
public class PedidoController {

    @Autowired
    PedidoService pedidoService;


    @PostMapping("/pedido")
    private ResponseEntity<Pedido> create(@RequestBody PedidoDto pedidoDto){
        Pedido newPedido = pedidoService.createAndSave(pedidoDto);
        return new ResponseEntity<Pedido>(newPedido, HttpStatus.OK);
    }
}
