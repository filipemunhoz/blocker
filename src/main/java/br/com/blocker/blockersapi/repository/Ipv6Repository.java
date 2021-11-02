package br.com.blocker.blockersapi.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import br.com.blocker.blockersapi.entity.ip.Ipv6;

@Repository
public interface Ipv6Repository extends ReactiveCrudRepository<Ipv6, Long> {

}
