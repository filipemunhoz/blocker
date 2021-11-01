package br.com.blocker.blockersapi.service;

import java.util.Optional;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import br.com.blocker.blockersapi.entity.ip.Ip;
import br.com.blocker.blockersapi.exceptions.ip.InvalidIpException;
import br.com.blocker.blockersapi.repository.IpRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IpService {
	
	@Autowired
	IpRepository ipRepository;
	
    public Mono<Ip> save(@RequestBody Ip ip) {
    	
    	final Boolean isValidIp = InetAddressValidator.getInstance().isValid(ip.getAddress());
    	
    	if(!isValidIp) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Invalid ip: ", ip.getAddress()));
    	}    	
    	return ipRepository.save(ip);
    }
    
    public Mono<Ip> findById(Long id) {
        return ipRepository.findById(id);
    }

    public Flux<Ip> findAll() {
        return ipRepository.findAll();
    }
    
    public Mono<Ip> deleteById(Long id) {
        return this.ipRepository
                .findById(id)
                .flatMap(ip -> this.ipRepository.deleteById(ip.getId()).thenReturn(ip));
    }    
}
