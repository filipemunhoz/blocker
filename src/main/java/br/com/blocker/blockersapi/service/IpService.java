package br.com.blocker.blockersapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.blocker.blockersapi.entity.ip.Ip;
import br.com.blocker.blockersapi.repository.ip.IpRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IpService {
	
	@Autowired
	IpRepository ipRepository;
	
    public Mono<Ip> save(@RequestBody Ip ip) {
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
