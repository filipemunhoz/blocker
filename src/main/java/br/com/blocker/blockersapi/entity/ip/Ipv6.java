package br.com.blocker.blockersapi.entity.ip;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Table
public class Ipv6 {

	@Id
    private Long id;
    private byte[] address;
    private String origin;
}
