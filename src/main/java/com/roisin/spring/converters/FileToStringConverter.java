package com.roisin.spring.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.File;

@Component
@Transactional
public class FileToStringConverter implements Converter<File, String> {

	@Override
	public String convert(File source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}

}
