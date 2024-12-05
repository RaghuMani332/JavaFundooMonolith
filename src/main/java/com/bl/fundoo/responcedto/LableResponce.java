package com.bl.fundoo.responcedto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class LableResponce {
	
	private int id;
	private String lableName;
	private List<NotesResponce> notes;

}
