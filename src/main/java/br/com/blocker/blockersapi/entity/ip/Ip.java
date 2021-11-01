package br.com.blocker.blockersapi.entity.ip;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ip {

    @Id
    private Long id;
    
    private String address;
    
    private String origin;
    
    public boolean hasId() {
        return id != null;
    }
}
