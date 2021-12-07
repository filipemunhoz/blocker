package br.com.blocker.blockersapi.batch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class RedisPojo {
	
	private String key;
	private String value;
}