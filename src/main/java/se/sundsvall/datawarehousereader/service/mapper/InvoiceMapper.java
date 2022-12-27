package se.sundsvall.datawarehousereader.service.mapper;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static org.zalando.problem.Status.INTERNAL_SERVER_ERROR;
import static se.sundsvall.datawarehousereader.Constants.UNKNOWN_CUSTOMER_TYPE;
import static se.sundsvall.datawarehousereader.api.model.CustomerType.fromValue;

import java.util.List;

import se.sundsvall.datawarehousereader.api.model.invoice.Invoice;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceDetail;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceDetailEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceEntity;
import se.sundsvall.datawarehousereader.service.util.ServiceUtil;

public class InvoiceMapper {

	private InvoiceMapper() {}

	public static List<Invoice> toInvoices(List<InvoiceEntity> entities) {
		return ofNullable(entities).orElse(emptyList()).stream()
			.map(InvoiceMapper::toInvoice)
			.toList();
	}

	private static Invoice toInvoice(InvoiceEntity entity) {
		return Invoice.create()
			.withAdministration(entity.getAdministration())
			.withAmountVatExcluded(entity.getAmountVatExcluded())
			.withAmountVatIncluded(entity.getAmountVatIncluded())
			.withCareOf(entity.getCareOf())
			.withCity(entity.getCity())
			.withCurrency(entity.getCurrency())
			.withCustomerNumber(ServiceUtil.toString(entity.getCustomerId()))
			.withCustomerType(fromValue(entity.getCustomerType(), INTERNAL_SERVER_ERROR, UNKNOWN_CUSTOMER_TYPE))
			.withDueDate(entity.getDueDate())
			.withFacilityId(entity.getFacilityId())
			.withInvoiceDate(entity.getInvoiceDate())
			.withInvoiceDescription(entity.getInvoiceDescription())
			.withInvoiceName(entity.getInvoiceName())
			.withInvoiceNumber(entity.getInvoiceNumber())
			.withInvoiceStatus(entity.getInvoiceStatus())
			.withInvoiceType(entity.getInvoiceType())
			.withOcrNumber(entity.getOcrNumber())
			.withOrganizationGroup(entity.getOrganizationGroup())
			.withOrganizationNumber(entity.getOrganizationId())
			.withPostCode(entity.getPostCode())
			.withReversedVat(entity.getReversedVat())
			.withRounding(entity.getRounding())
			.withStreet(entity.getStreet())
			.withTotalAmount(entity.getTotalAmount())
			.withVat(entity.getVat())
			.withVatEligibleAmount(entity.getVatEligibleAmount())
			.withPdfAvailable(entity.getPdfAvailable());
	}

	public static List<InvoiceDetail> toDetails(List<InvoiceDetailEntity> entities) {
		return ofNullable(entities).orElse(emptyList()).stream()
			.map(InvoiceMapper::toDetail)
			.toList();
	}

	private static InvoiceDetail toDetail(InvoiceDetailEntity entity) {
		return InvoiceDetail.create()
			.withAmount(entity.getAmount())
			.withAmountVatExcluded(entity.getAmountVatExcluded())
			.withDescription(entity.getDescription())
			.withInvoiceNumber(entity.getInvoiceNumber())
			.withOrganizationNumber(entity.getOrganizationId())
			.withPeriodFrom(entity.getPeriodFrom())
			.withPeriodTo(entity.getPeriodTo())
			.withProductCode(entity.getProductCode())
			.withProductName(entity.getProductName())
			.withQuantity(entity.getQuantity())
			.withUnit(entity.getUnit())
			.withUnitPrice(entity.getUnitPrice())
			.withVat(entity.getVat())
			.withVatRate(entity.getVatRate());
	}
}
