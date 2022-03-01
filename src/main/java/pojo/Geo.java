package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Geo{

	@JsonProperty("lat")
	private String lat;
	@JsonProperty("lng")
	private String lng;

}