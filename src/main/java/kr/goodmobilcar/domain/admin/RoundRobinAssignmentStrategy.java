package kr.goodmobilcar.domain.admin;

import kr.goodmobilcar.domain.lead.Lead;
import java.util.Comparator;
import java.util.List;

public class RoundRobinAssignmentStrategy implements AssignmentStrategy {

    @Override
    public Admin assignNext(List<Admin> liveAdmins, Lead lastLead) {
        if (liveAdmins.isEmpty()) return null;

        List<Admin> sorted = liveAdmins.stream()
                .sorted(Comparator.comparing(a -> a.getOrderPriority() != null ? a.getOrderPriority() : Integer.MAX_VALUE))
                .toList();

        if (lastLead == null || lastLead.getAssigneeId() == null) {
            return sorted.get(0);
        }

        int lastIdx = findIndex(sorted, lastLead.getAssigneeId());
        int nextIdx = (lastIdx + 1) % sorted.size();
        return sorted.get(nextIdx);
    }

    private int findIndex(List<Admin> admins, Long assigneeId) {
        for (int i = 0; i < admins.size(); i++) {
            if (admins.get(i).getId().equals(assigneeId)) return i;
        }
        return -1;
    }
}
