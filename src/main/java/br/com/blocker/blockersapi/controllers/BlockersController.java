package br.com.blocker.blockersapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.blocker.blockersapi.entity.ip.Ipv6;
import br.com.blocker.blockersapi.request.IpRequest;
import br.com.blocker.blockersapi.service.IpService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class BlockersController {
	
	@Autowired
	IpService ipService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Mono<String>> createCustomer(@Valid @RequestBody IpRequest request) {
    	
    	return ResponseEntity
    			.status(HttpStatus.CREATED)
    			.header("X-Blocker-Message", "Ip blocked")
    			.body(ipService.save(request)); 
    }
    
    @GetMapping("{ip}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> getIp(@PathVariable String ip) {    	
    	return ipService.findByIdOnCache(ip);
    }
    
    @GetMapping("/ipv6/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Ipv6> getIpv6(@PathVariable String id) {    	
    	return ipService.findByIdIpv6(Long.valueOf(id));
    }    
}