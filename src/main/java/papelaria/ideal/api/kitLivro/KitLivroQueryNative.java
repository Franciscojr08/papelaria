package papelaria.ideal.api.kitLivro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import papelaria.ideal.api.kitLivro.records.DadosFiltragemKitLivro;
import papelaria.ideal.api.utils.Functions;

import java.util.List;

public class KitLivroQueryNative {

	@PersistenceContext
	private EntityManager entityManager;

	public KitLivroQueryNative(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Page<KitLivro> filtrarKits(DadosFiltragemKitLivro dadosFiltros, Pageable pageable) {
		StringBuilder sql = new StringBuilder("SELECT * FROM kit_livro WHERE ativo = true");
		StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM kit_livro WHERE ativo = true");

		if (Functions.isNotBlank(dadosFiltros.nome())) {
			sql.append(" AND nome LIKE :nome");
			countSql.append(" AND nome LIKE :nome");
		}

		if (Functions.isNotBlankFloat(dadosFiltros.valor())) {
			sql.append(" AND valor >= :valor");
			countSql.append(" AND valor >= :valor");
		}

		if (Functions.isNotBlankLong(dadosFiltros.quantidadeDisponivel())) {
			sql.append(" AND quantidade_disponivel >= :quantidadeDisponivel");
			countSql.append(" AND quantidade_disponivel >= :quantidadeDisponivel");
		}

		Query query = this.entityManager.createNativeQuery(sql.toString(), KitLivro.class);
		Query countQuery = this.entityManager.createNativeQuery(countSql.toString());

		if (Functions.isNotBlank(dadosFiltros.nome())) {
			query.setParameter("nome", "%" + dadosFiltros.nome() + "%");
			countQuery.setParameter("nome", "%" + dadosFiltros.nome() + "%");
		}

		if (Functions.isNotBlankFloat(dadosFiltros.valor())) {
			query.setParameter("valor", dadosFiltros.valor());
			countSql.append(" AND valor >= :valor");
		}

		if (Functions.isNotBlankLong(dadosFiltros.quantidadeDisponivel())) {
			query.setParameter("quantidadeDisponivel", dadosFiltros.quantidadeDisponivel());
			countQuery.setParameter("quantidadeDisponivel", dadosFiltros.quantidadeDisponivel());
		}

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<KitLivro> kits = query.getResultList();
		long totalElements = ((Number) countQuery.getSingleResult()).longValue();

		return new PageImpl<>(kits, pageable, totalElements);
	}
}
