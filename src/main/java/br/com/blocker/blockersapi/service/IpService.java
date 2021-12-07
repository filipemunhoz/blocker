package br.com.blocker.blockersapi.service;

import br.com.blocker.blockersapi.entity.ip.Ipv4;
import br.com.blocker.blockersapi.entity.ip.Ipv6;
import br.com.blocker.blockersapi.repository.IpRepository;
import br.com.blocker.blockersapi.repository.Ipv6Repository;
import br.com.blocker.blockersapi.repository.RedisRepository;
import br.com.blocker.blockersapi.request.IpRequest;
import br.com.blocker.blockersapi.sql.SQLHelper;
import com.google.common.net.InetAddresses;
import io.r2dbc.spi.ConnectionFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.time.Duration;

@Service
public class IpService {
	
	@Autowired private IpRepository ipRepository;
	@Autowired private Ipv6Repository ipv6Repository;
	@Autowired private RedisRepository redisRepository;
	@Autowired private ConnectionFactory connectionFactory;
	@Autowired private TransactionManager transactionManager;

	private final InetAddressValidator validator = InetAddressValidator.getInstance();
	
	public Mono<String> save(@RequestBody IpRequest request) {

    	if(validator.isValidInet4Address(request.getAddress())) {
    		
    		final InetAddress addr = InetAddresses.forString(request.getAddress());
    		final Integer address = InetAddresses.coerceToInteger(addr);
    		
        	final Ipv4 ipv4 = Ipv4.builder()
        				.address(address)
        				.origin(request.getOrigin())
        				.build();
        	
        	ipRepository.save(ipv4).subscribe();
        	return Mono.just(String.format("IpV4: %s - Saved", request.getAddress()));
    	}
    	
    	if(validator.isValidInet6Address(request.getAddress())) {
    		
    		byte[] addr = Base64.decodeBase64(request.getAddress());
    		final Ipv6 ipv6 = Ipv6.builder()
    					.address(addr)
    					.origin(request.getOrigin())
    					.build();
    		ipv6Repository.save(ipv6).subscribe();
    		return Mono.just(String.format("IpV6: %s - Saved", request.getAddress()));
    	}
    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Invalid ip: %s", request.getAddress()));
    }

	public Mono<String> publishInternalsIpv4(){

		Mono.from(connectionFactory.create())
				.flatMapMany(connection ->
						Flux.from(connection.createBatch()
								.add(SQLHelper.generateBulkInsert())
								.execute())
						).subscribe();
		return Mono.just("Published IpV4");
	}
    
    public Mono<Ipv4> findByIdIpv4(Long id) {
        return ipRepository.findById(id);
    }

    public Mono<Ipv6> findByIdIpv6(Long id) {
        return ipv6Repository.findById(id);
    }
    
    public Mono<String> findByIdOnCache(String ip) {
    	return redisRepository.getOperations().opsForValue().get(ip).timeout(Duration.ofMillis(100));
    }
    
    public Flux<Ipv4> findAll() {
        return ipRepository.findAll();
    }
    
    public Mono<Ipv4> deleteById(Long id) {
        return this.ipRepository
                .findById(id)
                .flatMap(ip -> this.ipRepository.deleteById(ip.getId()).thenReturn(ip));
    }    
    
    public Mono<String> loadDataToRedis(){

    	redisRepository.getFactory().getReactiveConnection().serverCommands().flushAll().thenMany(
    			ipRepository.findAll()
    						.flatMap(ips -> redisRepository.getOperations().opsForValue().set(InetAddresses.fromInteger(ips.getAddress()).getHostAddress(), ips.getOrigin())))
    						.subscribe();

    	redisRepository.getFactory().getReactiveConnection().serverCommands().save().thenMany(
    			ipv6Repository.findAll()
							.flatMap(ipv6 -> redisRepository.getOperations().opsForValue().set(Base64.encodeBase64String(ipv6.getAddress()), ipv6.getOrigin())))
    			.subscribe();
    	
    	return Mono.just("Loaded data to Redis");
    }
}