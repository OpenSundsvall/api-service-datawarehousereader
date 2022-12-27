package se.sundsvall.datawarehousereader.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static se.sundsvall.datawarehousereader.api.model.Category.ELECTRICITY;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.DAY;
import static se.sundsvall.datawarehousereader.api.model.measurement.Aggregation.MONTH;
import static se.sundsvall.datawarehousereader.service.mapper.MeasurementMapper.toMeasurementResponse;
import static se.sundsvall.datawarehousereader.service.mapper.MeasurementMapper.toMeasurements;
import static se.sundsvall.dept44.util.DateUtils.toOffsetDateTimeWithLocalOffset;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort.Direction;

import se.sundsvall.datawarehousereader.api.model.Category;
import se.sundsvall.datawarehousereader.api.model.measurement.Aggregation;
import se.sundsvall.datawarehousereader.api.model.measurement.Measurement;
import se.sundsvall.datawarehousereader.api.model.measurement.MeasurementParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement.MeasurementElectricityMonthEntity;

class MeasurementMapperTest {
	private static final Category CATEGORY_ELECTRICITY = ELECTRICITY;
	private static final Aggregation AGGREGATION_MONTH = MONTH;
	private static final String FACILITY_ID = "facilityId";
	private static final String UNIT = "unit";
	private static final LocalDateTime TIME_STAMP_LOCAL_DATE_TIME = LocalDateTime.now();
	private static final String CUSTOMER_ORG_ID = "customerOrgId";
	private static final String FEED_TYPE = "feedType";
	private static final Integer INTERPOLATION = 543210;
	private static final BigDecimal USAGE = BigDecimal.valueOf(123.456);
	private static final String UUID = "uuid";

	@Test
	void toMeasurementsWithNull() {
		assertThat(toMeasurements(null, null, null, null)).isEmpty();
	}

	@Test
	void toMeasurementsWithEmptyList() {
		assertThat(toMeasurements(Collections.emptyList(), MeasurementParameters.create(), DAY, ELECTRICITY)).isEmpty();
	}

	@Test
	void toMeasurments() {
		final var entity = createEntity();

		final var searchParams = MeasurementParameters.create();
		searchParams.setPartyId("partyId");

		final var result = toMeasurements(List.of(entity), searchParams, AGGREGATION_MONTH, CATEGORY_ELECTRICITY);

		assertThat(result)
			.hasSize(1)
			.extracting(
				Measurement::getCategory,
				Measurement::getAggregatedOn,
				Measurement::getPartyId,
				Measurement::getFacilityId,
				Measurement::getMeasurementType,
				Measurement::getUnit,
				Measurement::getInterpolation,
				Measurement::getValue,
				Measurement::getTimestamp,
				Measurement::getMetaData)
			.containsExactly(tuple(
				CATEGORY_ELECTRICITY,
				AGGREGATION_MONTH,
				UUID,
				FACILITY_ID,
				FEED_TYPE,
				UNIT,
				INTERPOLATION,
				USAGE,
				toOffsetDateTimeWithLocalOffset(TIME_STAMP_LOCAL_DATE_TIME),
				null));
	}

	@Test
	void testToMeasurementResponse() {
		final var page = 5;
		final var limit = 10;
		final var sortBy = List.of("sortBy");
		final var sortDirection = Direction.DESC;
		final var parameters = MeasurementParameters.create();
		final var totalPages = 20;
		final var totalElements = 200;
		parameters.setPage(page);
		parameters.setLimit(limit);
		parameters.setSortBy(sortBy);
		parameters.setSortDirection(sortDirection);
		final var measurements = toMeasurements(List.of(createEntity()), parameters, AGGREGATION_MONTH, CATEGORY_ELECTRICITY);

		final var result = toMeasurementResponse(parameters, totalPages, totalElements, measurements);

		assertThat(result.getMetaData().getPage()).isEqualTo(page);
		assertThat(result.getMetaData().getLimit()).isEqualTo(limit);
		assertThat(result.getMetaData().getSortBy()).isEqualTo(sortBy);
		assertThat(result.getMetaData().getSortDirection()).isEqualTo(sortDirection);
		assertThat(result.getMetaData().getTotalPages()).isEqualTo(totalPages);
		assertThat(result.getMetaData().getTotalRecords()).isEqualTo(totalElements);
		assertThat(result.getMeasurements()).isEqualTo(measurements);
		assertThat(result.getMetaData().getCount()).isEqualTo(measurements.size());
	}

	private MeasurementElectricityMonthEntity createEntity() {
		final var entity = new MeasurementElectricityMonthEntity();
		entity.setUnit(UNIT);
		entity.setFacilityId(FACILITY_ID);
		entity.setMeasurementTimestamp(TIME_STAMP_LOCAL_DATE_TIME);
		entity.setFacilityId(FACILITY_ID);
		entity.setUnit(UNIT);
		entity.setInterpolation(INTERPOLATION);
		entity.setUsage(USAGE);
		entity.setCustomerOrgId(CUSTOMER_ORG_ID);
		entity.setFeedType(FEED_TYPE);
		entity.setUuid(UUID);
		return entity;
	}
}
