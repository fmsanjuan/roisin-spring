package com.roisin.spring.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Process;

@Component
@Transactional
public class ProcessToStringConverter implements Converter<Process, String> {

	@Override
	public String convert(final Process source) {
		String result;

		if (source == null) {
			result = null;
		} else {
			result = String.valueOf(source.getId());
		}

		return result;
	}

}
