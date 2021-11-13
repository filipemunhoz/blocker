package br.com.blocker.blockersapi.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import br.com.blocker.blockersapi.entity.ip.Ipv4;

@Repository
public interface IpRepository extends ReactiveCrudRepository<Ipv4, Long> {

}
