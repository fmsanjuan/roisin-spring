package com.roisin.spring.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Results;

@Component
@Transactional
public class ResultsToStringConverter implements Converter<Results, String> {

	@Override
	public String convert(Results source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}

}
