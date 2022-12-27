package se.sundsvall.datawarehousereader.integration.stadsbacken;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.invoice.InvoiceDetailEntity;

@Transactional
@CircuitBreaker(name = "invoiceDetailRepository")
public interface InvoiceDetailRepository extends CrudRepository<InvoiceDetailEntity, Integer> {
	@Deprecated(since = "2022-11-04", forRemoval = true)
	List<InvoiceDetailEntity> findAllByInvoiceNumber(long invoiceNumber);
	List<InvoiceDetailEntity> findAllByOrganizationIdAndInvoiceNumber(String organizationNumber, long invoiceNumber);
}
