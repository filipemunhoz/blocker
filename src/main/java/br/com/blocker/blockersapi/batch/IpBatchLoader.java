package br.com.blocker.blockersapi.batch;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.blocker.blockersapi.service.IpService;

@Component
public class IpBatchLoader {
	
	@Autowired
	private static final IpService service = null;
	
	@Scheduled(fixedRate = 60 * 1000, initialDelay = 5 * 1000)
	public void loadBlockedIpsToRedis() {
		Optional.ofNullable(service).ifPresent(s -> s.loadDataToRedis().subscribe());
	}
}
