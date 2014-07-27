package com.roisin.spring.converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Process;
import com.roisin.spring.repositories.ProcessRepository;

@Component
@Transactional
public class StringToProcessConverter implements Converter<String, Process> {

	@Autowired
	private ProcessRepository processRepository;

	@Override
	public Process convert(String source) {
		Process res;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				res = null;
			else {
				id = Integer.valueOf(source);
				res = processRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
