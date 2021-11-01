package br.com.blocker.blockersapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.blocker.blockersapi.entity.ip.Ip;
import br.com.blocker.blockersapi.service.IpService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class BlockersController {
	
	@Autowired
	final IpService ipService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Ip> createCustomer(@RequestBody Ip ip) {
        return ipService.save(ip);
    }
    
    @GetMapping("/ip")
    public Mono<Ip> getCustomer(Long id) {
        return ipService.findById(id);
    }

    @GetMapping("/")
    public Flux<Ip> getAll() {
        return ipService.findAll();
    }
    
    @DeleteMapping("/")
    public Mono<Ip> deleteById(Long id) {
        return this.ipService.deleteById(id);
    }
}
