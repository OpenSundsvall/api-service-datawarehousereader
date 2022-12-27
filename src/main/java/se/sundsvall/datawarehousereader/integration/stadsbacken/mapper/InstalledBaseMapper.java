package se.sundsvall.datawarehousereader.integration.stadsbacken.mapper;

import static java.util.Objects.isNull;

import org.springframework.data.domain.Example;

import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.installedbase.InstalledBaseItemEntity;
import se.sundsvall.datawarehousereader.service.util.ServiceUtil;

public class InstalledBaseMapper {

	private InstalledBaseMapper() {}

	/**
	 * Method for building a query by example object based on the installed base entity
	 * @param parameters object containing values to match
	 * @return example to match against when performing database query
	 */
	public static Example<InstalledBaseItemEntity> toExample(InstalledBaseParameters parameters) {
		if (isNull(parameters)) {
			return Example.of(InstalledBaseItemEntity.create());
		}

		return Example.of(InstalledBaseItemEntity.create()
			.withCareOf(parameters.getCareOf())
			.withCity(parameters.getCity())
			.withCompany(parameters.getCompany())
			.withCustomerId(ServiceUtil.toInteger(parameters.getCustomerNumber()))
			.withFacilityId(parameters.getFacilityId())
			.withPostCode(parameters.getPostCode())
			.withHouseName(parameters.getPropertyDesignation())
			.withStreet(parameters.getStreet())
			.withType(parameters.getType()));
	}
}
