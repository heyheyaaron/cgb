package com.example.doc;

import com.example.domain.TimeTable;
import org.optaplanner.core.api.solver.SolverManager;

public class TimeTableService {

    private SolverManager<TimeTable, Long> solverManager;

    //To assure the user that everything is going well, show progress by displaying the best solution and best score attained so far.

    // Returns immediately
    public void solveLive(Long timeTableId) {
        solverManager.solveAndListen(timeTableId,
                // Called once, when solving starts
                this::findById,
                // Called multiple times, for every best solution change
                this::save);
    }

    public TimeTable findById(Long timeTableId) {return new TimeTable();}

    public void save(TimeTable timeTable) {}

    public void stopSolving(Long timeTableId) {
        solverManager.terminateEarly(timeTableId);
    }

}