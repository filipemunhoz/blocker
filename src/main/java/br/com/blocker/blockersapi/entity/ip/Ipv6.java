package br.com.blocker.blockersapi.entity.ip;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Ipv6 {

    public Ipv6(final String address, final String origin) {
		super();
		this.address = address;
		this.origin = origin;
	}

	@Id
    private Long id;
    
    @NotBlank(message = "IpV6 address required")
    @Size(min=4, max=39, message = "Invalid ipv6 size")
    private String address;
    
    @NotBlank(message = "Origin required")
    private String origin;
    
    public boolean hasId() {
        return id != null;
    }
}
