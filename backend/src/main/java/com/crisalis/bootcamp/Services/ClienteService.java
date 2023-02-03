package com.crisalis.bootcamp.Services;

import com.crisalis.bootcamp.exceptions.custom.ClienteNotFoundException;
import com.crisalis.bootcamp.exceptions.custom.EmpresaException;
import com.crisalis.bootcamp.exceptions.custom.PersonaException;
import com.crisalis.bootcamp.model.dto.ClienteDto;
import com.crisalis.bootcamp.model.dto.EmpresaDto;
import com.crisalis.bootcamp.model.dto.PersonaDto;
import com.crisalis.bootcamp.model.entities.Cliente;
import com.crisalis.bootcamp.model.entities.Empresa;
import com.crisalis.bootcamp.model.entities.Persona;
import com.crisalis.bootcamp.repositories.ClienteRepository;
import com.crisalis.bootcamp.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PersonaRepository personaRepository;

    public Cliente findClienteByDniPersona(Integer dni) {
        return clienteRepository.findByPersonaDni(dni)
                .orElseThrow(
                        () -> new ClienteNotFoundException("Cliente with Persona with Dni: "
                                + dni + " not found.")
                );
    }

    public Cliente findClienteByCuitEmpresa(Long cuit) {
        return clienteRepository.findByEmpresaCuit(cuit)
                .orElseThrow(
                        () -> new ClienteNotFoundException("Cliente with Empresa with Cuit: "
                                + cuit + " not found.")
                );
    }

    public ClienteDto findClienteByCuit(Long cuit) {
        Cliente cliente = findClienteByCuitEmpresa(cuit);

        return new ClienteDto(cliente);
    }

    public ClienteDto findPersonaByDni(Integer dni) {
        Cliente cliente = findClienteByDniPersona(dni);

        return new ClienteDto(cliente);
    }

    public List<ClienteDto> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDto> clientesDto = new ArrayList<>();
        clientes.forEach(cliente -> clientesDto.add(new ClienteDto(cliente)));

        return clientesDto;
    }


    public void deletePersona(Integer dni) {

        Optional<Cliente> cliente = clienteRepository.findByPersonaDni(dni);

        if (cliente.isEmpty()) {
            throw new ClienteNotFoundException("Persona con dni " + dni + "no existe");
        }
        clienteRepository.delete(cliente.get());
    }

    public void deleteEmpresa(Long cuit) {

        Optional<Cliente> cliente = clienteRepository.findByEmpresaCuit(cuit);

        if (cliente.isEmpty()) {
            throw new ClienteNotFoundException("Empresa con cuit " + cuit + "no existe");
        }
        clienteRepository.delete(cliente.get());
    }


    public PersonaDto createPersona(PersonaDto personaDto) {

        Persona persona = validatePersona(personaDto);
        validateExistPersona(personaDto.getDni());

        Cliente cliente = Cliente
                .builder()
                .estado(true)
                .persona(persona)
                .build();

        Cliente clienteCreated = clienteRepository.save(cliente);
        return new PersonaDto(clienteCreated.getPersona());
    }


    public ClienteDto createEmpresa(ClienteDto clienteDto) {

        validatePersona(clienteDto.getPersona());
        validateEmpresa(clienteDto.getEmpresa());
        validateExistPersona(clienteDto.getPersona().getDni());
        validateExisteEmpresa(clienteDto.getEmpresa().getCuit());

        Cliente cliente = new Cliente(clienteDto);

        Cliente clienteCreated = clienteRepository.save(cliente);
        return new ClienteDto(clienteCreated);
    }

     private Boolean isAddEmpresaToPersona(ClienteDto clienteDto){
        return clienteDto.getEmpresa() != null;
    }


    public ClienteDto updateClienteByDni(Integer dni, ClienteDto clienteDto) {

        Cliente cliente = findClienteByDniPersona(dni);
        if ( clienteDto.getEstado() != null ){
            cliente.setEstado(clienteDto.getEstado());
        }

        boolean addEmpresaToPersona = isAddEmpresaToPersona(clienteDto);

        Persona personaNew = validatePersona(clienteDto.getPersona());
        Empresa empresaNew;
        if ( addEmpresaToPersona ){
            empresaNew = validateEmpresa(clienteDto.getEmpresa());
            cliente.setEmpresa(empresaNew);
        }

        cliente.getPersona().update(personaNew);

        Cliente clienteUpdate = clienteRepository.save(cliente);

        return new ClienteDto(clienteUpdate);
    }


    public ClienteDto updateClienteByCuit(Long cuit, ClienteDto clienteDto) {

        Cliente cliente = findClienteByCuitEmpresa(cuit);
        if ( clienteDto.getEstado() != null ){
            cliente.setEstado(clienteDto.getEstado());
        }

        Persona oldPerson = cliente.getPersona();

        Persona personaNew = validatePersona(clienteDto.getPersona());
        Empresa empresaNew = validateEmpresa(clienteDto.getEmpresa());

        boolean changePersona = isChangePersona(oldPerson, clienteDto.getPersona());
        boolean deleteOldPerson = false;

        if ( changePersona ){
            cliente.setPersona(personaNew);
            deleteOldPerson = true;
        }else {
            cliente.getPersona().update(personaNew);
        }

        cliente.getEmpresa().update(empresaNew);

        Cliente clienteUpdate = clienteRepository.save(cliente);
        if( deleteOldPerson ){
            personaRepository.delete(oldPerson);
        }

        return new ClienteDto(clienteUpdate);
    }

    private boolean isChangePersona(Persona persona, PersonaDto personaDto) {
        return !Objects.equals(persona.getDni(), personaDto.getDni());
    }

    public ClienteDto updateCliente(Cliente cliente, Cliente clienteNew) {

        Persona persona = cliente.getPersona();
        persona.update(clienteNew.getPersona());

        Empresa empresa = null;

        if (cliente.getEmpresa() != null) {
            empresa = cliente.getEmpresa(); // empresa tiene id
        }

        if (clienteNew.getEmpresa() != null) {
            //hay que actulizar la empresa
            if (empresa == null) {  //si es verdadero el cliente no tenia empresa
                empresa = clienteNew.getEmpresa(); // ahora le asignamos una empresa
            }else { // si es falso entonces el cliente tenia empresa y la actualizamos, sin cambiar el cuit
                empresa.update(clienteNew.getEmpresa());
            }

        }

        cliente.setEmpresa(empresa);
        cliente.setPersona(persona);
        Cliente clienteUpdated = clienteRepository.save(cliente);
        return new ClienteDto(clienteUpdated);
    }

    public Persona validatePersona(PersonaDto persona) {
        if (persona == null) throw new PersonaException("Persona is required");

        if (persona.getNombre() == null || persona.getNombre().isEmpty())
            throw new PersonaException("Nombre is required");

        if (persona.getApellido() == null || persona.getApellido().isEmpty())
            throw new PersonaException("Apellido is required");

        if (persona.getDni() == null) throw new PersonaException("Dni is required");

        return new Persona(persona);
    }

    public Empresa validateEmpresa(EmpresaDto empresa) {
        if (empresa == null) throw new EmpresaException("Empresa is required");

        if (empresa.getRazonSocial() == null || empresa.getRazonSocial().isEmpty())
            throw new EmpresaException("Razon social is required");

        if (empresa.getFechaInicioActividad() == null)
            throw new EmpresaException("Fecha inicio actividad is required");

        if (empresa.getCuit() == null) throw new EmpresaException("Cuit is required");

        return new Empresa(empresa);

    }

    public Cliente validateCliente(ClienteDto clienteDto, Boolean isEmpresa) {

        if (clienteDto.getPersona() == null) {
            throw new PersonaException("Persona is required");
        }
        validatePersona(clienteDto.getPersona());

        if (isEmpresa) {
            if (clienteDto.getEmpresa() == null) {
                throw new EmpresaException("Empresa is required");
            }
            validateEmpresa(clienteDto.getEmpresa());
        } else {
            if (clienteDto.getEmpresa() != null) {
                validateEmpresa(clienteDto.getEmpresa());
            }
        }

        return new Cliente(clienteDto);
    }

    public void validateExistPersona(Integer dni) {

        boolean clientePersonaExist = clienteRepository.findByPersonaDni(dni).isPresent();
        if (clientePersonaExist) {
            throw new PersonaException("La persona con dni "
                    + dni
                    + " ya existe en Db");
        }
    }

    public void validateExisteEmpresa(Long cuit) {
        boolean clienteExist = clienteRepository.findByEmpresaCuit(cuit).isPresent();
        if (clienteExist) {
            throw new EmpresaException("La empresa con cuit "
                    + cuit
                    + " ya existe en Db");
        }
    }


}
