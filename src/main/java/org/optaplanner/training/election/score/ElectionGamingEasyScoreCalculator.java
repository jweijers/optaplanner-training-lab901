package org.optaplanner.training.election.score;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;
import org.optaplanner.training.election.domain.Election;

import java.util.concurrent.atomic.AtomicInteger;

public class ElectionGamingEasyScoreCalculator implements EasyScoreCalculator<Election> {



	@Override
	public Score calculateScore(final Election election, final int i) {
		AtomicInteger electoralVotes = new AtomicInteger(0);
		AtomicInteger votes = new AtomicInteger(0);
		election.getFederalStateList().stream()
				.filter(s -> Election.GAMER_CANDIDATE.equals(s.getWinningCandidate()))
				.forEach(s -> {
				electoralVotes.addAndGet(s.getElectoralVotes());
				votes.addAndGet(s.getMinimumMajorityPopulation());
				});

		int hardScore =  270 < electoralVotes.get() ? 0 : electoralVotes.get() -  270;
		return HardSoftScore.valueOfInitialized(hardScore,-votes.get());
	}


}
