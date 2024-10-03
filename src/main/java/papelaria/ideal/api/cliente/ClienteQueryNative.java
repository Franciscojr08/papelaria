package papelaria.ideal.api.cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import papelaria.ideal.api.cliente.records.DadosFiltragemCliente;
import papelaria.ideal.api.utils.Functions;

import java.util.List;

public class ClienteQueryNative {

	@PersistenceContext
	private EntityManager entityManager;

	public ClienteQueryNative(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Page<Cliente> filtrarClientes(DadosFiltragemCliente dadosFiltros, Pageable pageable) {
		StringBuilder sql = new StringBuilder("SELECT * FROM cliente WHERE ativo = true");
		StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM cliente WHERE ativo = true");

		if (Functions.isNotBlank(dadosFiltros.nome())) {
			sql.append(" AND nome LIKE :nome");
			countSql.append(" AND nome LIKE :nome");
		}

		if (Functions.isNotBlank(dadosFiltros.email())) {
			sql.append(" AND email LIKE :email");
			countSql.append(" AND email LIKE :email");
		}

		if (Functions.isNotBlank(dadosFiltros.cpf())) {
			sql.append(" AND cpf LIKE :cpf");
			countSql.append(" AND cpf LIKE :cpf");
		}

		if (dadosFiltros.responsavel() != null) {
			sql.append(" AND responsavel_aluno LIKE :responsavelAluno");
			countSql.append(" AND responsavel_aluno LIKE :responsavelAluno");
		}

		Query query = this.entityManager.createNativeQuery(sql.toString(), Cliente.class);
		Query countQuery = this.entityManager.createNativeQuery(countSql.toString());

		if (Functions.isNotBlank(dadosFiltros.nome())) {
			query.setParameter("nome", "%" + dadosFiltros.nome() + "%");
			countQuery.setParameter("nome", "%" + dadosFiltros.nome() + "%");
		}

		if (Functions.isNotBlank(dadosFiltros.email())) {
			query.setParameter("email", "%" + dadosFiltros.email() + "%");
			countQuery.setParameter("email", "%" + dadosFiltros.email() + "%");
		}

		if (Functions.isNotBlank(dadosFiltros.cpf())) {
			query.setParameter("cpf", "%" + dadosFiltros.cpf() + "%");
			countQuery.setParameter("cpf", "%" + dadosFiltros.cpf() + "%");
		}

		if (dadosFiltros.responsavel() != null) {
			query.setParameter("responsavelAluno", dadosFiltros.responsavel());
			countQuery.setParameter("responsavelAluno", dadosFiltros.responsavel());
		}

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<Cliente> clientes = query.getResultList();
		long totalElements = ((Number) countQuery.getSingleResult()).longValue();

		return new PageImpl<>(clientes, pageable, totalElements);
	}
}