package br.com.blocker.blockersapi.service;

import java.net.InetAddress;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.google.common.net.InetAddresses;

import br.com.blocker.blockersapi.entity.ip.Ip;
import br.com.blocker.blockersapi.repository.IpRepository;
import br.com.blocker.blockersapi.request.IpRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IpService {
	
	@Autowired
	private IpRepository ipRepository;
	
	  private final ReactiveRedisConnectionFactory factory;
	  private final ReactiveRedisOperations<String, String> coffeeOps;

	  public IpService(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, String> coffeeOps) {
	    this.factory = factory;
	    this.coffeeOps = coffeeOps;
	  }
	
    public Mono<String> save(@RequestBody IpRequest request) {

    	if(InetAddressValidator.getInstance().isValidInet4Address(request.getAddress())) {
    		
    		final InetAddress addr = InetAddresses.forString(request.getAddress());
    		final Integer address = InetAddresses.coerceToInteger(addr);
    		
        	final Ip ipv4 = Ip.builder()
        				.address(address)
        				.origin(request.getOrigin())
        				.build();
        	
        	ipRepository.save(ipv4).subscribe();
        	return Mono.just("OK");
    	}
    	
       	final Boolean isValidIp = InetAddressValidator.getInstance().isValid(request.getAddress());    	
    	if(!isValidIp) {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Invalid ip: ", request.getAddress()));
    	}    	
    	return Mono.just("OK");
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
    
    public Mono<String> loadDataToRedis(){

//    	factory.getReactiveConnection().serverCommands().flushAll().thenMany(
//    			ipRepository.findAll()
//    						.flatMap(ips -> coffeeOps.opsForValue().set(ips.getAddress(), ips.getAddress())))
//    						.subscribe();
    	
    	// TODO implementar
    	return Mono.just("OK");
    }
}
