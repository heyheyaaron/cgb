package com.example.controller.zuoxi;

import com.example.domain.zuoxi.bean.Roster;
import com.example.domain.zuoxi.bean.Shift;
import org.optaplanner.core.api.score.ScoreExplanation;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.ConstraintMatchTotal;
import org.optaplanner.core.api.score.constraint.Indictment;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/roster")
public class RosterController {
    @Resource(name="rosterSolverManager")
    private SolverManager solverManager;
    @Resource(name="rosterScoreManager")
    private ScoreManager scoreManager;

    @PostMapping("/solve")
    public Roster solve(@RequestBody Roster problem) {
//        SolverConfig solverConfig = SolverConfig.createFromXmlResource("solverConfig.xml");
//        SolverManager<Roster, UUID> solverManager = SolverManager.create(solverConfig, new SolverManagerConfig());
//        ScoreManager<Roster, HardSoftScore> scoreManager = ScoreManager.create(SolverFactory.createFromXmlResource("solverConfig.xml"));
        UUID problemId = UUID.randomUUID();
        // Submit the problem to start solving
        SolverJob<Roster, UUID> solverJob = solverManager.solve(problemId, problem);
        Roster solution;
        try {
            SolverStatus solverStatus = getSolverStatus(problemId);
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
            /*Map<Long, Long> collect = solution.getShiftList().stream().collect(Collectors.groupingBy(s -> s.getEmployeeId(), Collectors.counting()));
            collect.forEach((k,v)->{
                System.out.println("k="+k+",v="+v);
            });*/
            ScoreExplanation<Roster, HardSoftScore> scoreExplanation = scoreManager.explainScore(solution);
            String summary = scoreExplanation.getSummary();
            HardSoftScore score = scoreExplanation.getScore();
            Map<String, ConstraintMatchTotal<HardSoftScore>> constraintMatchTotalMap = scoreExplanation.getConstraintMatchTotalMap();
            Map<Object, Indictment<HardSoftScore>> indictmentMap = scoreExplanation.getIndictmentMap();
            //Roster solution1 = scoreExplanation.getSolution();
            //获取违反的约束和分数
            getConstrainNameAndScore(solution, indictmentMap);
            //System.out.println(solution1);
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
    }

    private void getConstrainNameAndScore(Roster solution, Map<Object, Indictment<HardSoftScore>> indictmentMap) {
        for (Shift shift : solution.getShiftList()) {
            Indictment<HardSoftScore> indictment = indictmentMap.get(shift);
            if (indictment == null) {
                continue;
            }
            // The score impact of that planning entity
            HardSoftScore totalScore = indictment.getScore();

            for (ConstraintMatch<HardSoftScore> constraintMatch : indictment.getConstraintMatchSet()) {
                String constraintName = constraintMatch.getConstraintName();
                HardSoftScore hardSoftScore = constraintMatch.getScore();
                System.out.println(shift+constraintName+":"+hardSoftScore);
            }
        }
    }

    @PostMapping("/terminate")
    public void solve(@RequestBody int problemId) {
        solverManager.terminateEarly(problemId);
    }
    public SolverStatus getSolverStatus(UUID problemId) {
        //origin_parameter
        return solverManager.getSolverStatus(problemId);
    }


}