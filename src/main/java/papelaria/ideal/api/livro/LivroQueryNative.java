package papelaria.ideal.api.livro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import papelaria.ideal.api.livro.records.DadosFiltragemLivro;
import papelaria.ideal.api.utils.Functions;

import java.util.List;

public class LivroQueryNative {

	@PersistenceContext
	private EntityManager entityManager;

	public LivroQueryNative(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Page<Livro> filtrarLivros(DadosFiltragemLivro dadosFiltros, Pageable pageable) {
		StringBuilder sql = new StringBuilder("SELECT * FROM livro WHERE ativo = true");
		StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM livro WHERE ativo = true");

		if (Functions.isNotBlank(dadosFiltros.identificador())) {
			sql.append(" AND identificador LIKE :identificador");
			countSql.append(" AND identificador LIKE :identificador");
		}

		if (Functions.isNotBlank(dadosFiltros.nome())) {
			sql.append(" AND nome LIKE :nome");
			countSql.append(" AND nome LIKE :nome");
		}

		if (Functions.isNotBlankLong(dadosFiltros.quantidadeDisponivel())) {
			sql.append(" AND quantidade_disponivel >= :quantidadeDisponivel");
			countSql.append(" AND quantidade_disponivel >= :quantidadeDisponivel");
		}

		if (Functions.isNotBlankLong(dadosFiltros.serieId())) {
			sql.append(" AND serie_id = :serieId");
			countSql.append(" AND serie_id = :serieId");
		}

		if (dadosFiltros.usoInterno() != null) {
			sql.append(" AND uso_interno = :usoInterno");
			countSql.append(" AND uso_interno = :usoInterno");
		}

		if (Functions.isNotBlankFloat(dadosFiltros.valor())) {
			sql.append(" AND valor >= :valor");
			countSql.append(" AND valor >= :valor");
		}

		Query query = this.entityManager.createNativeQuery(sql.toString(), Livro.class);
		Query countQuery = this.entityManager.createNativeQuery(countSql.toString());

		if (Functions.isNotBlank(dadosFiltros.identificador())) {
			query.setParameter("identificador", "%" + dadosFiltros.identificador() + "%");
			countQuery.setParameter("identificador", "%" + dadosFiltros.identificador() + "%");
		}

		if (Functions.isNotBlank(dadosFiltros.nome())) {
			query.setParameter("nome", "%" + dadosFiltros.nome() + "%");
			countQuery.setParameter("nome", "%" + dadosFiltros.nome() + "%");
		}

		if (Functions.isNotBlankLong(dadosFiltros.quantidadeDisponivel())) {
			query.setParameter("quantidadeDisponivel", dadosFiltros.quantidadeDisponivel());
			countQuery.setParameter("quantidadeDisponivel", dadosFiltros.quantidadeDisponivel());
		}

		if (Functions.isNotBlankLong(dadosFiltros.serieId())) {
			query.setParameter("serieId", dadosFiltros.serieId());
			countQuery.setParameter("serieId", dadosFiltros.serieId());
		}

		if (dadosFiltros.usoInterno() != null) {
			query.setParameter("usoInterno", dadosFiltros.usoInterno());
			countQuery.setParameter("usoInterno", dadosFiltros.usoInterno());
		}

		if (Functions.isNotBlankFloat(dadosFiltros.valor())) {
			query.setParameter("valor", dadosFiltros.valor());
			countQuery.setParameter("valor", dadosFiltros.valor());
		}

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<Livro> livros = query.getResultList();
		long totalElements = ((Number) countQuery.getSingleResult()).longValue();

		return new PageImpl<>(livros, pageable, totalElements);
	}
}
