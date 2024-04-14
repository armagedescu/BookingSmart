package org.filipski.schema;

import java.util.List;

public class TestPlan {
    List<Schedule> plan;
    public TestPlan () {}
    public TestPlan (Schedule ... scheduleList)
    {
        plan = List.of(scheduleList);
    }

    public List<Schedule> getPlan() {
        return plan;
    }
}
