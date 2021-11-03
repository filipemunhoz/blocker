package br.com.blocker.blockersapi.service;

import java.net.InetAddress;

import org.apache.commons.codec.binary.Base64;
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
import br.com.blocker.blockersapi.entity.ip.Ipv6;
import br.com.blocker.blockersapi.repository.IpRepository;
import br.com.blocker.blockersapi.repository.Ipv6Repository;
import br.com.blocker.blockersapi.request.IpRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IpService {
	
	@Autowired private IpRepository ipRepository;
	@Autowired private Ipv6Repository ipv6Repository;
	
	private final ReactiveRedisConnectionFactory factory;
	private final ReactiveRedisOperations<String, String> operations;

	public IpService(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, String> operations) {
		this.factory = factory;
		this.operations = operations;
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
        	return Mono.just(String.format("IpV4: %s - Saved", request.getAddress()));
    	}
    	
    	if(InetAddressValidator.getInstance().isValidInet6Address(request.getAddress())) {
    		
    		byte[] addr = Base64.decodeBase64(request.getAddress());
    		final Ipv6 ipv6 = Ipv6.builder()
    					.address(addr)
    					.origin(request.getOrigin())
    					.build();
    		ipv6Repository.save(ipv6).subscribe();
    		return Mono.just(String.format("IpV6: %s - Saved", request.getAddress()));
    	}
    	
    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Invalid ip: ", request.getAddress()));
    }
    
    public Mono<Ip> findById(Long id) {
        return ipRepository.findById(id);
    }

    public Mono<Ipv6> findByIdIpv6(Long id) {
        return ipv6Repository.findById(id);
    }
    
    public Mono<String> findByIdOnCache(String ip) {
    	return operations.opsForValue().get(ip);
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

    	factory.getReactiveConnection().serverCommands().flushAll().thenMany(
    			ipRepository.findAll()
    						.flatMap(ips -> operations.opsForValue().set(InetAddresses.fromInteger(ips.getAddress()).getHostAddress(), ips.getOrigin())))
    						.subscribe();

    	factory.getReactiveConnection().serverCommands().save().thenMany(
    			ipv6Repository.findAll()
							.flatMap(ipv6 -> operations.opsForValue().set(Base64.encodeBase64String(ipv6.getAddress()), ipv6.getOrigin())))
    			.subscribe();
    	
    	return Mono.just("Loaded data to Redis");
    }
}
