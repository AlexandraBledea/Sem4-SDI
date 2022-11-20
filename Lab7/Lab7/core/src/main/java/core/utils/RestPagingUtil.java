package core.utils;

import core.dto.PagingRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class RestPagingUtil {
    public static boolean isRequestPaged(PagingRequest pagingRequest) {
        return pagingRequest.getPageNumber() != null && pagingRequest.getPageSize() != 0;
    }

    public static Pageable buildPageRequest(PagingRequest pagingRequest) {
        return PageRequest.of(
                pagingRequest.getPageNumber(),
                pagingRequest.getPageSize());
    }
}
