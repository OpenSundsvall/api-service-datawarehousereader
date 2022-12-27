package se.sundsvall.datawarehousereader.integration.stadsbacken;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import se.sundsvall.datawarehousereader.integration.stadsbacken.model.installedbase.InstalledBaseItemEntity;

@Transactional
@CircuitBreaker(name = "installedBaseRepository")
public interface InstalledBaseRepository extends PagingAndSortingRepository<InstalledBaseItemEntity, Integer>, QueryByExampleExecutor<InstalledBaseItemEntity> {}
