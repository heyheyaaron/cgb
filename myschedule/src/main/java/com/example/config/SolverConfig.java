package com.example.config;

import com.example.domain.zuoxi.bean.Roster;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverManagerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolverConfig {
    @Bean
    public SolverManager<Roster, String> rosterSolverManager(){
        org.optaplanner.core.config.solver.SolverConfig solverConfig = org.optaplanner.core.config.solver.SolverConfig.createFromXmlResource("solverConfig.xml");
        SolverManager<Roster, String> solverManager = SolverManager.create(solverConfig, new SolverManagerConfig());
        return solverManager;
    }
    @Bean
    public ScoreManager rosterScoreManager(){
        ScoreManager<Roster, HardSoftScore> scoreManager = ScoreManager.create(SolverFactory.createFromXmlResource("solverConfig.xml"));
        return scoreManager;
    }
}
