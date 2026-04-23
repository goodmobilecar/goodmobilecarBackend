package kr.goodmobilcar.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateLeadRequest(
        @NotBlank String customerName,
        @NotBlank String phone,
        @NotBlank String carModel,
        @NotNull Integer leasePeriod,
        @NotBlank String incomeType,
        @NotNull Boolean privacyConsent,
        @NotNull Boolean thirdPartyConsent,
        Boolean marketingConsent,
        @NotBlank String turnstileToken,
        UtmDto utm,
        TrackingDto tracking
) {
    public record UtmDto(String source, String medium, String campaign, String term, String content) {}
    public record TrackingDto(String visitorId, String sessionId, String conversionPage, String conversionPosition) {}
}
