package kr.server.productproject.interfaces.common;

public record ErrorResponse(
        String code,
        String message
) {
}