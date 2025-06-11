package com.ureca.ufit.global.dto;

import java.util.List;

public record CursorPageResponse<T>(
	List<T> item,
	String nextCursor,
	boolean hasNext
) {
}
