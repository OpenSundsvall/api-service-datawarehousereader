package se.sundsvall.datawarehousereader.service.mapper;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

import java.util.List;

import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseItem;
import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseItemMetaData;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.installedbase.InstalledBaseItemEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.installedbase.InstalledBaseItemMetaDataEmbeddable;
import se.sundsvall.datawarehousereader.service.util.ServiceUtil;

public class InstalledBaseMapper {

	private InstalledBaseMapper() {}

	public static List<InstalledBaseItem> toInstalledBaseItems(List<InstalledBaseItemEntity> entities) {
		return ofNullable(entities).orElse(emptyList()).stream()
			.map(InstalledBaseMapper::toInstalledBaseItem)
			.toList();
	}

	private static InstalledBaseItem toInstalledBaseItem(InstalledBaseItemEntity entity) {
		return InstalledBaseItem.create()
			.withCareOf(entity.getCareOf())
			.withCity(entity.getCity())
			.withCompany(entity.getCompany())
			.withCustomerNumber(ServiceUtil.toString(entity.getCustomerId()))
			.withDateFrom(entity.getDateFrom())
			.withDateTo(entity.getDateTo())
			.withFacilityId(entity.getFacilityId())
			.withMetaData(toMetaDatas(entity.getMetaData()))
			.withPlacementId(entity.getInternalId())
			.withPostCode(entity.getPostCode())
			.withPropertyDesignation(entity.getHouseName())
			.withStreet(entity.getStreet())
			.withType(entity.getType());
	}

	private static List<InstalledBaseItemMetaData> toMetaDatas(List<InstalledBaseItemMetaDataEmbeddable> embeddables) {
		return ofNullable(embeddables).orElse(emptyList()).stream()
			.map(InstalledBaseMapper::toMetaData)
			.toList();
	}

	private static InstalledBaseItemMetaData toMetaData(InstalledBaseItemMetaDataEmbeddable embeddable) {
		return InstalledBaseItemMetaData.create()
			.withDisplayName(embeddable.getDisplayName())
			.withKey(embeddable.getKey())
			.withType(embeddable.getType())
			.withValue(embeddable.getValue());
	}
}
