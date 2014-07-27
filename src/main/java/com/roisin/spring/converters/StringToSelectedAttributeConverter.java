package com.roisin.spring.converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.SelectedAttribute;
import com.roisin.spring.repositories.SelectedAttributeRepository;

@Component
@Transactional
public class StringToSelectedAttributeConverter implements Converter<String, SelectedAttribute> {

	@Autowired
	private SelectedAttributeRepository selectedAttributeRepository;

	@Override
	public SelectedAttribute convert(String source) {
		SelectedAttribute res;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				res = null;
			else {
				id = Integer.valueOf(source);
				res = selectedAttributeRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
