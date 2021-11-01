package br.com.blocker.blockersapi.repository.ip;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import br.com.blocker.blockersapi.entity.ip.Ip;

public interface IpRepository extends ReactiveCrudRepository<Ip, Long> {

}
