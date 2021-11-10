package br.com.blocker.blockersapi.entity.ip;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "ip")
public class Ipv4 {

    public Ipv4(Integer address, String origin) {
        this.address = address;
        this.origin = origin;
    }

    @Id
    private Long id;
	private Integer address;
    private String origin;
}