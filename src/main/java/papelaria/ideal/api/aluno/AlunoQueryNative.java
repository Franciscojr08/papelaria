package papelaria.ideal.api.aluno;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import papelaria.ideal.api.aluno.records.DadosFiltragemAluno;
import papelaria.ideal.api.utils.Functions;

import java.util.List;

public class AlunoQueryNative {

	@PersistenceContext
	private EntityManager entityManager;

	public AlunoQueryNative(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Page<Aluno> filtrar(DadosFiltragemAluno dadosFiltros, Pageable pageable) {
		StringBuilder sql = new StringBuilder("SELECT * FROM aluno WHERE ativo = true");
		StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM aluno WHERE ativo = true");

		if (Functions.isNotBlank(dadosFiltros.nome())) {
			sql.append(" AND nome LIKE :nome");
			countSql.append(" AND nome LIKE :nome");
		}

		if (Functions.isNotBlank(dadosFiltros.matricula())) {
			sql.append(" AND matricula LIKE :matricula");
			countSql.append(" AND matricula LIKE :matricula");
		}

		if (Functions.isNotBlank(dadosFiltros.cpf())) {
			sql.append(" AND cpf LIKE :cpf");
			countSql.append(" AND cpf LIKE :cpf");
		}

		if (Functions.isNotBlank(dadosFiltros.rg())) {
			sql.append(" AND rg LIKE :rg");
			countSql.append(" AND rg LIKE :rg");
		}

		if (Functions.isNotBlankLong(dadosFiltros.clienteId())) {
			sql.append(" AND cliente_id LIKE :clienteId");
			countSql.append(" AND cliente_id LIKE :clienteId");
		}

		if (Functions.isNotBlankLong(dadosFiltros.turmaId())) {
			sql.append(" AND turma_id LIKE :turmaId");
			countSql.append(" AND turma_id LIKE :turmaId");
		}

		Query query = this.entityManager.createNativeQuery(sql.toString(), Aluno.class);
		Query countQuery = this.entityManager.createNativeQuery(countSql.toString());

		if (Functions.isNotBlank(dadosFiltros.nome())) {
			query.setParameter("nome", "%" + dadosFiltros.nome() + "%");
			countQuery.setParameter("nome", "%" + dadosFiltros.nome() + "%");
		}

		if (Functions.isNotBlank(dadosFiltros.matricula())) {
			query.setParameter("matricula", "%" + dadosFiltros.matricula() + "%");
			countQuery.setParameter("matricula", "%" + dadosFiltros.matricula() + "%");
		}

		if (Functions.isNotBlank(dadosFiltros.cpf())) {
			query.setParameter("cpf", "%" + dadosFiltros.cpf() + "%");
			countQuery.setParameter("cpf", "%" + dadosFiltros.cpf() + "%");
		}

		if (Functions.isNotBlank(dadosFiltros.rg())) {
			query.setParameter("rg", "%" + dadosFiltros.rg() + "%");
			countQuery.setParameter("rg", "%" + dadosFiltros.rg() + "%");
		}

		if (Functions.isNotBlankLong(dadosFiltros.clienteId())) {
			query.setParameter("clienteId", "%" + dadosFiltros.clienteId() + "%");
			countQuery.setParameter("clienteId", "%" + dadosFiltros.clienteId() + "%");
		}

		if (Functions.isNotBlankLong(dadosFiltros.turmaId())) {
			query.setParameter("turmaId", "%" + dadosFiltros.turmaId() + "%");
			countQuery.setParameter("turmaId", "%" + dadosFiltros.turmaId() + "%");
		}

		query.setFirstResult((int) pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<Aluno> alunos = query.getResultList();
		long totalElements = ((Number) countQuery.getSingleResult()).longValue();

		return new PageImpl<>(alunos, pageable, totalElements);
	}
}
