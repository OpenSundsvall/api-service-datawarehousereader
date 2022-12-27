package se.sundsvall.datawarehousereader.api.model;

import static java.lang.Integer.parseInt;
import static org.springframework.data.domain.Sort.DEFAULT_DIRECTION;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import se.sundsvall.datawarehousereader.api.validation.ValidSortByProperty;

@ValidSortByProperty
public abstract class AbstractParameterBase {

	private static final String DEFAULT_PAGE = "1";
	private static final String DEFAULT_LIMIT = "100";

	@Schema(description = "Page number", example = DEFAULT_PAGE, defaultValue = DEFAULT_PAGE)
	@Min(1)
	protected int page = parseInt(DEFAULT_PAGE);

	@Schema(description = "Result size per page", example = DEFAULT_LIMIT, defaultValue = DEFAULT_LIMIT)
	@Min(1)
	@Max(1000)
	protected int limit = parseInt(DEFAULT_LIMIT);

	@ArraySchema(schema = @Schema(description = "The properties to sort on", example = "propertyName"))
	protected List<String> sortBy;

	@Schema(description = "The sort order direction", example = "ASC", enumAsRef = true)
	protected Direction sortDirection = DEFAULT_DIRECTION;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<String> getSortBy() {
		return sortBy;
	}

	public void setSortBy(List<String> sortBy) {
		this.sortBy = sortBy;
	}

	public Direction getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(Direction sortDirection) {
		this.sortDirection = sortDirection;
	}

	@JsonIgnore
	public Sort sort() {
		return Optional.ofNullable(this.sortBy)
			.map(sortby -> Sort.by(Optional.ofNullable(this.sortDirection).orElse(DEFAULT_DIRECTION), sortby.toArray(new String[0])))
			.orElseGet(Sort::unsorted);
	}

	@Override
	public int hashCode() {
		return Objects.hash(limit, page, sortBy, sortDirection);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractParameterBase other = (AbstractParameterBase) obj;
		return limit == other.limit && page == other.page && Objects.equals(sortBy, other.sortBy) && sortDirection == other.sortDirection;
	}
}
