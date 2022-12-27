package se.sundsvall.datawarehousereader.service;

import static org.springframework.data.domain.PageRequest.of;
import static se.sundsvall.datawarehousereader.integration.stadsbacken.mapper.InstalledBaseMapper.toExample;
import static se.sundsvall.datawarehousereader.service.mapper.InstalledBaseMapper.toInstalledBaseItems;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.sundsvall.datawarehousereader.api.model.MetaData;
import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseItem;
import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseParameters;
import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseResponse;
import se.sundsvall.datawarehousereader.integration.stadsbacken.InstalledBaseRepository;

@Service
public class InstalledBaseService {

	@Autowired
	private InstalledBaseRepository repository;

	public InstalledBaseResponse getInstalledBase(InstalledBaseParameters parameters) {
		final var matches = repository.findAll(toExample(parameters), of(parameters.getPage() - 1, parameters.getLimit(), parameters.sort()));

		// If page larger than last page is requested, a empty list is returned otherwise the current page
		List<InstalledBaseItem> installedBase = matches.getTotalPages() < parameters.getPage() ? Collections.emptyList() : toInstalledBaseItems(matches.getContent());

		return InstalledBaseResponse.create()
			.withMetaData(MetaData.create()
				.withPage(parameters.getPage())
				.withSortBy(parameters.getSortBy())
				.withSortDirection(parameters.getSortDirection())
				.withTotalPages(matches.getTotalPages())
				.withTotalRecords(matches.getTotalElements())
				.withCount(installedBase.size())
				.withLimit(parameters.getLimit()))
			.withInstalledBase(installedBase);
	}
}
