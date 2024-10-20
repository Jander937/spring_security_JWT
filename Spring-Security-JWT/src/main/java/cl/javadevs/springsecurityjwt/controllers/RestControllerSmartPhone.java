package cl.javadevs.springsecurityjwt.controllers;

import cl.javadevs.springsecurityjwt.dtos.DTOSmart;
import cl.javadevs.springsecurityjwt.models.SmartPhone;
import cl.javadevs.springsecurityjwt.models.Usuarios;
import cl.javadevs.springsecurityjwt.services.SmartPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/celular/")
public class RestControllerSmartPhone {
    private final SmartPhoneService phoneService;

    @Autowired
    public RestControllerSmartPhone(SmartPhoneService phoneService) {
        this.phoneService = phoneService;
    }

    // Petición para crear un celular
    @PostMapping("crear")
    public Optional<DTOSmart> save(@RequestBody DTOSmart smart) {

        Boolean result = phoneService.add(smart);

        if (result){
            return Optional.of(smart);
        }

        return Optional.empty();

    }

    // Petición para obtener todos los celulares en la BD
    @GetMapping(value = "listar", headers = "Accept=application/json")
    public List<SmartPhone> listarCelulares() {
        return phoneService.readAll();
    }

    // Petición para obtener celular mediante "ID"
    @GetMapping(value = "listarId/{id}", headers = "Accept=application/json")
    public ResponseEntity<?> obtenerCelularPorId(@PathVariable Long id) {
        Optional<SmartPhone> smartPhone = phoneService.readOne(id);
        if (smartPhone.isPresent()) {
            return ResponseEntity.ok(smartPhone.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Petición para actualizar un celular
    @PutMapping(value = "actualizar", headers = "Accept=application/json")
    public void actualizarCelular(@RequestBody SmartPhone smartPhone) {
        phoneService.update(smartPhone);
    }

    // Petición para eliminar un celular por "Id"
    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void eliminarCelular(@PathVariable Long id) {
        phoneService.delete(id);
    }
}
