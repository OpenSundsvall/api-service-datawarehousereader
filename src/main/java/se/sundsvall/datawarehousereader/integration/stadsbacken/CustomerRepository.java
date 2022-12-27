package se.sundsvall.datawarehousereader.integration.stadsbacken;

import static java.util.Optional.ofNullable;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.CustomerSpecification.withCustomerId;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.CustomerSpecification.withCustomerOrgIds;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.CustomerSpecification.withOrganizationId;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.specification.CustomerSpecification.withOrganizationName;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import se.sundsvall.datawarehousereader.api.model.customer.CustomerEngagementParameters;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.customer.CustomerEntity;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.customer.CustomerKey;
import se.sundsvall.datawarehousereader.service.util.ServiceUtil;

@Transactional
@CircuitBreaker(name = "customerRepository")
public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, CustomerKey>, JpaSpecificationExecutor<CustomerEntity> {

	default Page<CustomerEntity> findAllByParameters(CustomerEngagementParameters customerParameters, List<String> customerOrgIds, Pageable pageable) {
		CustomerEngagementParameters parameters = ofNullable(customerParameters).orElse(CustomerEngagementParameters.create());
		return this.findAll(withCustomerId(ServiceUtil.toInteger(parameters.getCustomerNumber()))
			.and(withCustomerOrgIds(customerOrgIds))
			.and(withOrganizationId(parameters.getOrganizationNumber()))
			.and(withOrganizationName(parameters.getOrganizationName())), pageable);
	}
}
