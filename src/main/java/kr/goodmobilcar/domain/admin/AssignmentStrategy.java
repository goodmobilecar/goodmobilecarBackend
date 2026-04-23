package kr.goodmobilcar.domain.admin;

import kr.goodmobilcar.domain.lead.Lead;
import java.util.List;

public interface AssignmentStrategy {
    Admin assignNext(List<Admin> liveAdmins, Lead lastLead);
}
