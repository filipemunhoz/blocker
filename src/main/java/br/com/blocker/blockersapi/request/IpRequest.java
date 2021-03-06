package br.com.blocker.blockersapi.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IpRequest {

	@NotBlank(message = "Ip address required")
	@Size(min = 7, max = 39, message = "Invalid ip size")
	private String address;
	
	@NotBlank(message = "Origin required")
	private String origin;
}
