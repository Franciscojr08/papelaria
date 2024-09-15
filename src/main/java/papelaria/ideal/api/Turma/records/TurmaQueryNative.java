package papelaria.ideal.api.Turma.records;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import papelaria.ideal.api.Turma.Turma;
import papelaria.ideal.api.kitLivro.KitLivro;
import papelaria.ideal.api.utils.Functions;

import java.util.List;

public class TurmaQueryNative {

	@PersistenceContext
	private EntityManager entityManager;

	public TurmaQueryNative(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Page<Turma> filtrarTurmas(DadosFiltragemTurma dadosFiltros, Pageable pageable) {
		StringBuilder sql = new StringBuilder("SELECT * FROM turma WHERE ativo = true");
		StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM turma WHERE ativo = true");

		if (Functions.isNotBlank(dadosFiltros.nome())) {
			sql.append(" AND nome LIKE :nome");
			countSql.append(" AND nome LIKE :nome");
		}

		if (Functions.isNotBlankLong(dadosFiltros.serieId())) {
			sql.append(" AND serie_id = :serieId");
			countSql.append(" AND serie_id = :serieId");
		}

		Query query = this.entityManager.createNativeQuery(sql.toString(), Turma.class);
		Query countQuery = this.entityManager.createNativeQuery(countSql.toString());

		if (Functions.isNotBlank(dadosFiltros.nome())) {
			query.setParameter("nome", "%" + dadosFiltros.nome() + "%");
			countQuery.setParameter("nome", "%" + dadosFiltros.nome() + "%");
		}

		if (Functions.isNotBlankLong(dadosFiltros.serieId())) {
			query.setParameter("serieId", dadosFiltros.serieId());
			countQuery.setParameter("serieId", dadosFiltros.serieId());
		}

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<Turma> turmas = query.getResultList();
		long totalElements = ((Number) countQuery.getSingleResult()).longValue();

		return new PageImpl<>(turmas, pageable, totalElements);
	}
}
