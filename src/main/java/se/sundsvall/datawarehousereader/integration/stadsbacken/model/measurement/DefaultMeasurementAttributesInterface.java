package se.sundsvall.datawarehousereader.integration.stadsbacken.model.measurement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface DefaultMeasurementAttributesInterface {

    String getUnit();

    String getCustomerOrgId();

    String getUuid();

    String getFacilityId();

    String getFeedType();

    Integer getInterpolation();

    BigDecimal getUsage();

    LocalDateTime getMeasurementTimestamp();
}
