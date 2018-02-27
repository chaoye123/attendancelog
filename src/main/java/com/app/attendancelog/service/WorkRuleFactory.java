package com.app.attendancelog.service;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import com.app.attendancelog.service.impl.WeekdayRuleImpl;
import com.app.attendancelog.service.impl.WeekendRuleImpl;
/**
 * Created by jacky on 2017/7/13.
 */
@Service
public class WorkRuleFactory {

    private WorkRuleAbstract weekdayRule = new WeekdayRuleImpl();
    private WorkRuleAbstract weekendRule = new WeekendRuleImpl();

    public WorkRuleAbstract getDateRule(DateTime dt){
        int dayOfWeek = dt.getDayOfWeek();
        if(dayOfWeek == 6 || dayOfWeek == 7){
            return weekendRule;
        }
        return weekdayRule;
    }

}
