package papelaria.ideal.api.kitLivro;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import papelaria.ideal.api.kitLivro.records.DadosFiltragemKitLivro;

import java.util.List;

public class KitLivroQueryNative {

	@PersistenceContext
	private EntityManager entityManager;

	public KitLivroQueryNative(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Page<KitLivro> filtrarKits(DadosFiltragemKitLivro dadosFiltros, Pageable pageable) {
		StringBuilder sql = new StringBuilder("SELECT * FROM kit_livro WHERE ativo = true");

		if (dadosFiltros.nome() != null) {
			sql.append(" AND nome LIKE :nome");
		}

		if (dadosFiltros.valor() != null) {
			sql.append(" AND valor >= :valor");
		}

		if (dadosFiltros.quantidadeDisponivel() != null) {
			sql.append(" AND quantidade_disponivel >= :quantidadeDisponivel");
		}

		Query query = this.entityManager.createNativeQuery(sql.toString(), KitLivro.class);

		if (dadosFiltros.nome() != null) {
			query.setParameter("nome", "%" + dadosFiltros.nome() + "%");
		}

		if (dadosFiltros.valor() != null) {
			query.setParameter("valor", dadosFiltros.valor());
		}

		if (dadosFiltros.quantidadeDisponivel() != null) {
			query.setParameter("quantidadeDisponivel", dadosFiltros.quantidadeDisponivel());
		}

		// Paginação
		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<KitLivro> kits = query.getResultList();

		// Contagem total
		StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM kit_livro WHERE ativo = true");

		if (dadosFiltros.nome() != null) {
			countSql.append(" AND nome LIKE :nome");
		}

		if (dadosFiltros.valor() != null) {
			countSql.append(" AND valor >= :valor");
		}

		if (dadosFiltros.quantidadeDisponivel() != null) {
			countSql.append(" AND quantidade_disponivel >= :quantidadeDisponivel");
		}

		Query countQuery = this.entityManager.createNativeQuery(countSql.toString());

		if (dadosFiltros.nome() != null) {
			countQuery.setParameter("nome", "%" + dadosFiltros.nome() + "%");
		}

		if (dadosFiltros.valor() != null) {
			countQuery.setParameter("valor", dadosFiltros.valor());
		}

		if (dadosFiltros.quantidadeDisponivel() != null) {
			countQuery.setParameter("quantidadeDisponivel", dadosFiltros.quantidadeDisponivel());
		}

		long totalElements = ((Number) countQuery.getSingleResult()).longValue();

		return new PageImpl<>(kits, pageable, totalElements);
	}
}
