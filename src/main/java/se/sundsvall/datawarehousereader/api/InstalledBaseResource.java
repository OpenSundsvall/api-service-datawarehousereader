package se.sundsvall.datawarehousereader.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseParameters;
import se.sundsvall.datawarehousereader.api.model.installedbase.InstalledBaseResponse;
import se.sundsvall.datawarehousereader.service.InstalledBaseService;

@RestController
@Validated
@RequestMapping("/installedbase")
@Tag(name = "Installed base", description = "Installed base operations")
public class InstalledBaseResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(InstalledBaseResource.class);
	
	@Autowired
	private InstalledBaseService service;
	
	@GetMapping(produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	@Operation(summary = "Get installed base information", description = "Resource returns all installed bases matching provided search parameters")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InstalledBaseResponse.class)))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = {Problem.class, ConstraintViolationProblem.class})))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<InstalledBaseResponse> getInstalledBase(@Valid InstalledBaseParameters searchParams) {
		LOGGER.debug("Received getInstalledBase()-request: {} ", searchParams);

		return ResponseEntity.ok(service.getInstalledBase(searchParams));
	}
}
