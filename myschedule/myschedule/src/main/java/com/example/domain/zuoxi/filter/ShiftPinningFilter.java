package com.example.domain.zuoxi.filter;

import com.example.domain.zuoxi.bean.Roster;
import com.example.domain.zuoxi.bean.Shift;
import org.optaplanner.core.api.domain.entity.PinningFilter;

public class ShiftPinningFilter implements PinningFilter<Roster, Shift> {
    @Override
    public boolean accept(Roster roster, Shift shift) {
        return false;
    }
}
