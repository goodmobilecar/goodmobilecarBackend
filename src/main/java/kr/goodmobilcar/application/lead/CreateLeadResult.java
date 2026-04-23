package kr.goodmobilcar.application.lead;

public class CreateLeadResult {
    private final long leadId;
    private final String completionToken;

    public CreateLeadResult(long leadId, String completionToken) {
        this.leadId = leadId;
        this.completionToken = completionToken;
    }

    public long getLeadId() { return leadId; }
    public String getCompletionToken() { return completionToken; }
    public String getRedirectUrl() { return "/complete?token=" + completionToken; }
}
