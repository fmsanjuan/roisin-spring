package com.roisin.spring.converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.File;
import com.roisin.spring.services.FileService;

@Component
@Transactional
public class StringToFileConverter implements Converter<String, File> {

	/**
	 * File service
	 */
	@Autowired
	private transient FileService fileService;

	@Override
	public File convert(final String source) {
		File res;
		int id;

		try {
			if (StringUtils.isEmpty(source)) {
				res = null;
			} else {
				id = Integer.valueOf(source);
				res = fileService.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
