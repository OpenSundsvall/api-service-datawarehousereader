package se.sundsvall.datawarehousereader.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static se.sundsvall.datawarehousereader.service.mapper.InstalledBaseMapper.toInstalledBaseItems;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseItem;
import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseItemMetaData;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.installedbase.InstalledBaseItemEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.installedbase.InstalledBaseItemMetaDataEmbeddable;

class InstalledBaseMapperTest {
	private static final String CARE_OF = "careOf";
	private static final String CITY = "city";
	private static final String COMPANY = "company";
	private static final int CUSTOMER_ID = 123;
	private static final int INTERNAL_ID = 456;
	private static final String FACILITY_ID = "facilityId";
	private static final String HOUSE_NAME = "houseName";
	private static final String POST_CODE = "postCode";
	private static final String STREET = "street";
	private static final String TYPE = "type";
	private static final String DISPLAY_NAME = "displayName";
	private static final String META_KEY = "metaKey";
	private static final String META_TYPE = "metaType";
	private static final String META_VALUE = "metaValue";

	@Test
	void toInstalledBaseItemsWithNull() {
		assertThat(toInstalledBaseItems(null)).isEmpty();
	}
	
	@Test
	void toInstalledBaseItemsWithEmptyList() {
		assertThat(toInstalledBaseItems(Collections.emptyList())).isEmpty();
	}
	
	@Test 
	void toInstalledBaseItemsWithMetaDataNull() {
		final var entity = InstalledBaseItemEntity.create()
				.withCareOf(CARE_OF)
				.withCity(CITY)
				.withCompany(COMPANY)
				.withCustomerId(CUSTOMER_ID)
			.withHouseName(HOUSE_NAME)
				.withFacilityId(FACILITY_ID)
				.withInternalId(INTERNAL_ID)
				.withPostCode(POST_CODE)
				.withStreet(STREET)
				.withType(TYPE);
		
		final var result = toInstalledBaseItems(List.of(entity));
		
		assertThat(result)
		.hasSize(1)
		.extracting(
				InstalledBaseItem::getCareOf,
				InstalledBaseItem::getCity,
				InstalledBaseItem::getCompany,
				InstalledBaseItem::getCustomerNumber,
				InstalledBaseItem::getMetaData,
				InstalledBaseItem::getFacilityId,
				InstalledBaseItem::getPlacementId,
				InstalledBaseItem::getPostCode,
				InstalledBaseItem::getPropertyDesignation,
				InstalledBaseItem::getStreet,
				InstalledBaseItem::getType)
		.containsExactly(tuple(
				CARE_OF, 
				CITY,
				COMPANY,
				String.valueOf(CUSTOMER_ID),
				Collections.emptyList(),
				FACILITY_ID,
				INTERNAL_ID,
				POST_CODE,
				HOUSE_NAME,
				STREET,
				TYPE));
	}

	@Test 
	void toInstalledBaseItemsWithMetaData() {
		final var entity = InstalledBaseItemEntity.create()
				.withCareOf(CARE_OF)
				.withCity(CITY)
				.withCompany(COMPANY)
				.withCustomerId(CUSTOMER_ID)
			.withHouseName(HOUSE_NAME)
				.withMetaData(List.of(createEmbeddableMetaData()))
				.withFacilityId(FACILITY_ID)
				.withInternalId(INTERNAL_ID)
				.withPostCode(POST_CODE)
				.withStreet(STREET)
				.withType(TYPE);
		
		final var result = toInstalledBaseItems(List.of(entity));
		
		assertThat(result)
		.hasSize(1)
		.extracting(
				InstalledBaseItem::getCareOf,
				InstalledBaseItem::getCity,
				InstalledBaseItem::getCompany,
				InstalledBaseItem::getCustomerNumber,
				InstalledBaseItem::getFacilityId,
				InstalledBaseItem::getPlacementId,
				InstalledBaseItem::getPostCode,
				InstalledBaseItem::getPropertyDesignation,
				InstalledBaseItem::getStreet,
				InstalledBaseItem::getType)
		.containsExactly(tuple(
				CARE_OF, 
				CITY,
				COMPANY,
				String.valueOf(CUSTOMER_ID),
				FACILITY_ID,
				INTERNAL_ID,
				POST_CODE,
				HOUSE_NAME,
				STREET,
				TYPE));
		
		assertThat(result.get(0).getMetaData())
		.hasSize(1)
		.extracting(
				InstalledBaseItemMetaData::getDisplayName,
				InstalledBaseItemMetaData::getKey,
				InstalledBaseItemMetaData::getType,
				InstalledBaseItemMetaData::getValue)
		.containsExactly(tuple(
				DISPLAY_NAME,
				META_KEY,
				META_TYPE,
				META_VALUE));
	}
	
	private static InstalledBaseItemMetaDataEmbeddable createEmbeddableMetaData() {
		InstalledBaseItemMetaDataEmbeddable embeddable = new InstalledBaseItemMetaDataEmbeddable();
		embeddable.setCompany(COMPANY);
		embeddable.setDisplayName(DISPLAY_NAME);
		embeddable.setKey(META_KEY);
		embeddable.setType(META_TYPE);
		embeddable.setValue(META_VALUE);
		
		return embeddable;
	}
}
