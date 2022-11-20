export class PagingResponse<T>{
  /**
   * entity count
   */
  count: number = 0;

  /**
   * page number, 0 indicate the first page.
   */
  pageNumber: number = 0;

  /**
   * size of page, 0 indicate infinite-sized.
   */
  pageSize: number = 0;

  /**
   * Offset from the of pagination.
   */
  pageOffset: number = 0;

  /**
   * the number total of pages.
   */
  pageTotal: number = 0;

  /**
   * elements of page.
   */
  elements:Array<T> = []
}
