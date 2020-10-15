package com.sk.jdp.common.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseMessage extends BaseObject {

		private String message;
		
		public ResponseMessage(String message) {
			this.message=message;
		}
}
