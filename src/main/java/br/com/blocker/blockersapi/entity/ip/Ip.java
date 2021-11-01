package br.com.blocker.blockersapi.entity.ip;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table
public class Ip {

    @Id
    private Long id;
    
    @NotBlank(message = "Ip address required")
    @Size(min=7, max=39, message = "Invalid ip size")
    private String address;
    
    @NotBlank(message = "Origin required")
    private String origin;
    
    public boolean hasId() {
        return id != null;
    }
}
