package se.sundsvall.datawarehousereader.api.converter;

import static org.zalando.problem.Status.BAD_REQUEST;
import static se.sundsvall.datawarehousereader.Constants.INVALID_PARAMETER_CUSTOMER_TYPE;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import se.sundsvall.datawarehousereader.api.model.CustomerType;

@Component
public class CustomerTypeConverter implements Converter<String, CustomerType> {

	@Override
	public CustomerType convert(String source) {
		return CustomerType.fromValue(source, BAD_REQUEST, INVALID_PARAMETER_CUSTOMER_TYPE);
	}
}
