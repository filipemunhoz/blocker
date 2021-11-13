package br.com.blocker.blockersapi.entity.ip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "ip")
public class Ipv4 {

    @Id
    private Long id;
	private Integer address;
    private String origin;
}