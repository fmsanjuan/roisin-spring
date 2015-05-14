package com.roisin.spring.converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.roisin.spring.model.Results;
import com.roisin.spring.services.ResultsService;

@Component
@Transactional
public class StringToResultsConverter implements Converter<String, Results> {

	/**
	 * Results service
	 */
	@Autowired
	private transient ResultsService resultsService;

	@Override
	public Results convert(final String source) {
		Results res;
		int id;

		try {
			if (StringUtils.isEmpty(source)) {
				res = null;
			} else {
				id = Integer.valueOf(source);
				res = resultsService.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return res;
	}

}
