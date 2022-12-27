package se.sundsvall.datawarehousereader;

public class Constants {

	private Constants() {}

	public static final String PARTY_ID_CUSTOMER_TYPE_COMBINATION = "when partyId is present as parameter, customerType must also be present";
	public static final String INVALID_PARAMETER_CATEGORY = "Invalid value for enum Category: %s";
	public static final String INVALID_PARAMETER_CUSTOMER_TYPE = "Invalid value for enum CustomerType: %s";
	public static final String INVALID_PARAMETER_AGGREGATION = "Invalid value for enum Aggregation: %s";
	public static final String UNKNOWN_CUSTOMER_TYPE = "Customer repository result contains an unknown value for enum CustomerType: %s";
}
