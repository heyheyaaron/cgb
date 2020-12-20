package com.example.controller.zuoxi;

import com.example.domain.zuoxi.bean.Roster;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.SolverManagerConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/roster")
public class RosterController {

    private SolverManager<Roster, UUID> solverManager;

    @PostMapping("/solve")
    public Roster solve(@RequestBody Roster problem) {
        UUID problemId = UUID.randomUUID();
        // Submit the problem to start solving

        SolverJob<Roster, UUID> solverJob = solverManager.solve(problemId, problem);
        Roster solution;
        try {
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
    }

    public RosterController(){
        SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml");
        SolverManager<Roster, UUID> solverManager = SolverManager.create(solverConfig, new SolverManagerConfig());
        this.solverManager=solverManager;
    }

}