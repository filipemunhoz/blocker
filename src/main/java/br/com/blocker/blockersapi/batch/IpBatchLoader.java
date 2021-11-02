package br.com.blocker.blockersapi.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.blocker.blockersapi.service.IpService;

@Component
public class IpBatchLoader {
	
	@Autowired
	final IpService service = null;

	
	@Scheduled(fixedRate = 5000, initialDelay = 5 * 1000)
	public void loadBlockedIpsToRedis() {
		System.out.println("################ LOADING");
		service.loadDataToRedis();
	}
	
}
