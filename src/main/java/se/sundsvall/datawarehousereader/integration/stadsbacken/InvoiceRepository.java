package se.sundsvall.datawarehousereader.integration.stadsbacken;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceEntity;

import javax.transaction.Transactional;

import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withAdministration;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withCustomerIds;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withCustomerType;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withDueDate;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withFacilityIds;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withInvoiceDate;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withInvoiceName;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withInvoiceNumber;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withInvoiceStatus;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withInvoiceType;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withOcrNumber;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withOrganizationGroup;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.InvoiceSpecification.withOrganizationId;
import static se.sundsvall.datawarehousereader.service.util.ServiceUtil.toIntegers;

@Transactional
@CircuitBreaker(name = "invoiceRepository")
public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity, Integer>, JpaSpecificationExecutor<InvoiceEntity> {

	default Page<InvoiceEntity> findAllByParameters(InvoiceParameters parameters, Pageable pageable) {
		return this.findAll(withAdministration(parameters.getAdministration()).
			and(withCustomerIds(toIntegers(parameters.getCustomerNumber())).
			and(withCustomerType(parameters.getCustomerType())).
			and(withDueDate(parameters.getDueDateFrom(), parameters.getDueDateTo())).
			and(withFacilityIds(parameters.getFacilityId()))).
			and(withInvoiceDate(parameters.getInvoiceDateFrom(), parameters.getInvoiceDateTo())).
			and(withInvoiceName(parameters.getInvoiceName())).
			and(withInvoiceNumber(parameters.getInvoiceNumber())).
			and(withInvoiceStatus(parameters.getInvoiceStatus())).
			and(withInvoiceType(parameters.getInvoiceType())).
			and(withOcrNumber(parameters.getOcrNumber())).
			and(withOrganizationGroup(parameters.getOrganizationGroup())).
			and(withOrganizationId(parameters.getOrganizationNumber())), pageable);
	}
}
