package com.bl.fundoo.requestdto;

import java.util.List;

import com.bl.fundoo.entity.LableEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LableRequest {
	
	private String lableName;
}
