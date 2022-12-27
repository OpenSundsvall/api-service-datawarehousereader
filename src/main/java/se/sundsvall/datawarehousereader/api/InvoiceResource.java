package se.sundsvall.datawarehousereader.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceDetail;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceParameters;
import se.sundsvall.datawarehousereader.api.model.invoice.InvoiceResponse;
import se.sundsvall.datawarehousereader.service.InvoiceService;
import se.sundsvall.dept44.common.validators.annotation.ValidOrganizationNumber;

@RestController
@Validated
@RequestMapping("/invoices")
@Tag(name = "Invoice", description = "Invoice operations")
public class InvoiceResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceResource.class);

	@Autowired
	private InvoiceService service;
	
	@GetMapping(produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	@Operation(summary = "Get basic invoice information", description = "Resource returns all invoices matching provided search parameters")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = InvoiceResponse.class)))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(oneOf = {Problem.class, ConstraintViolationProblem.class})))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<InvoiceResponse> getInvoices(@Valid InvoiceParameters searchParams) {
		LOGGER.debug("Received getInvoices()-request: {} ", searchParams);

		return ResponseEntity.ok(service.getInvoices(searchParams));
	}

	@Hidden
	@Deprecated(since = "2022-11-04", forRemoval = true)
	@GetMapping(path = "/{invoiceNumber}/details", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	@Operation(summary = "Get invoice details for invoice matching issuer of provided organization number and having invoice number matching provided invoice number")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = InvoiceDetail.class))))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<List<InvoiceDetail>> getDeprecatedInvoiceDetails(
		@Parameter(name = "invoiceNumber", description = "Invoice number", example = "805137494", required = true) @PathVariable(name = "invoiceNumber") long invoiceNumber) {
		LOGGER.debug("Received getDeprecatedInvoiceDetails()-request: invoiceNumber='{}'", invoiceNumber);

		return ResponseEntity.ok(service.getInvoiceDetails(invoiceNumber));
	}

	@GetMapping(path = "/{organizationNumber}/{invoiceNumber}/details", produces = { APPLICATION_JSON_VALUE, APPLICATION_PROBLEM_JSON_VALUE })
	@Operation(summary = "Get invoice details for invoice matching issuer of provided organization number and having invoice number matching provided invoice number")
	@ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = InvoiceDetail.class))))
	@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server error", content = @Content(mediaType = APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = Problem.class)))
	public ResponseEntity<List<InvoiceDetail>> getInvoiceDetails(
		@Parameter(name = "organizationNumber", description = "Organization number of invoice issuer", example = "5565027223", required = true) @PathVariable(name = "organizationNumber") @ValidOrganizationNumber String organizationNumber,
		@Parameter(name = "invoiceNumber", description = "Invoice number", example = "805137494", required = true) @PathVariable(name = "invoiceNumber") long invoiceNumber) {
		LOGGER.debug("Received getInvoiceDetails()-request: organizationNumber='{}', invoiceNumber='{}'", organizationNumber, invoiceNumber);

		return ResponseEntity.ok(service.getInvoiceDetails(organizationNumber, invoiceNumber));
	}
}
