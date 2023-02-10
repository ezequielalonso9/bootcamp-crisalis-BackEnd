package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.ServicioClienteException;
import com.crisalis.bootcamp.model.entities.ServiciosCliente;
import com.crisalis.bootcamp.repositories.ServicioRepository;
import com.crisalis.bootcamp.repositories.ServiciosClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServicioClienteService {

    @Autowired
    ServiciosClienteRepository repository;
    @Autowired
    private ServicioRepository servicioRepository;


    public ServiciosCliente findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ServicioClienteException("no se encuentra relacion Servicio Cliente")
                );
    }

    public void saveAll(Set<ServiciosCliente> serviciosClientes) {
        repository.saveAll(serviciosClientes);
    }

    public void altaById(Long id) {
        ServiciosCliente servicioCliente = findById(id);
        Boolean estadoPedido = servicioCliente.getPedido().getEstado();

        if (estadoPedido == null || !estadoPedido) {
            throw new ServicioClienteException(
                    "el servicio no se puede dar de alta" +
                            "porque el pedido no esta confirmado");
        }

        servicioCliente.setActivo(true);
        repository.save(servicioCliente);
    }

    public void bajaById(Long id) {
        ServiciosCliente servicioCliente = findById(id);
        servicioCliente.setActivo(false);
        repository.save(servicioCliente);
    }

    public boolean areThereServiceActivceByIdPedido(Long idPedido) {

        List<ServiciosCliente> servicios = repository.findByPedidoId(idPedido);
        List<ServiciosCliente> serviciosActivos = servicios
                .stream()
                .filter(ServiciosCliente::getActivo)
                .collect(Collectors.toList());

        return serviciosActivos.size() > 0;
    }

    public boolean areThereServiceActivceByIdCliente(Long idCliente) {

        List<ServiciosCliente> servicios = repository.findByClienteId(idCliente);
        List<ServiciosCliente> serviciosActivos = servicios
                .stream()
                .filter(ServiciosCliente::getActivo)
                .collect(Collectors.toList());

        return serviciosActivos.size() > 0;
    }

    public boolean haveDiscountByIdCliente(Long id) {
        List<ServiciosCliente> serviciosClientes = repository.findByClienteId(id);

        List<ServiciosCliente> servicioActivos = serviciosClientes
                .stream()
                .filter(ServiciosCliente::getActivo)
                .collect(Collectors.toList());

        return servicioActivos.size() > 0;
    }

    public void bajaServiciosByIdCliente(Long id) {

        List<ServiciosCliente> serviciosClientes = repository.findByClienteId(id);
        serviciosClientes.forEach( servicio -> servicio.setActivo(false));
        repository.saveAll(serviciosClientes);
    }
}

