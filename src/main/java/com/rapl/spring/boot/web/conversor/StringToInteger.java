package com.rapl.spring.boot.web.conversor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToInteger implements Converter<String, Integer> {

	// Se o número não for numérico, retorna null e o bean validador do Spring irá retornar a mensagem
	@Override
	public Integer convert(String text) {
		
		text = text.trim();
		
		if (text.matches("[0-9]+")) {
			return Integer.valueOf(text);
		}
		
		return null;
	}
}
