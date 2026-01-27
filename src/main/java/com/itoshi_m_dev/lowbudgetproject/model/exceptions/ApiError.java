package com.itoshi_m_dev.lowbudgetproject.model.exceptions;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
