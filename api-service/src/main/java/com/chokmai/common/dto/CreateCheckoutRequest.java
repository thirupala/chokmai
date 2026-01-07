package com.chokmai.common.dto;

public record CreateCheckoutRequest(
    String packageId,
    int credits
) {}
