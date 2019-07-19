package it.betcalculator.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.betcalculator.domain.Match;

public interface MatchRepository extends CrudRepository<Match, Long>{

	public List<Match> findByCampionato(String campionato);
	
	public Optional<Match> findByCasaAndOspite(String casa, String ospite);
	
	public List<Match> findByDataInizioBetweenOrderByDataInizio(LocalDateTime dateDa, LocalDateTime dataA);
	
}
