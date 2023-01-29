package com.blacky.covid19.advice;

import lombok.Builder;

@Builder
public record ExceptionMessage(int status, String message, String timestamp) {
}