package br.com.blocker.blockersapi.entity.ip;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Ip {
	
    public Ip(final Integer address, final String origin) {
		super();
		this.address = address;
		this.origin = origin;
	}

	@Id
    private Long id;
	private Integer address;
    private String origin;
}