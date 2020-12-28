package com.example.filter;

import com.example.domain.zuoxi.bean.Roster;
import com.example.domain.zuoxi.bean.Shift;
import org.optaplanner.core.api.domain.entity.PinningFilter;

// TODO: 2020/12/22 未研究完
public class ShiftPinningFilter implements PinningFilter<Roster, Shift> {
    @Override
    public boolean accept(Roster roster, Shift shift) {
        return false;
    }
}
