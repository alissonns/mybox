package br.gov.serpro.demoiselle.extra.persistence;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ContextNotActiveException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.serpro.demoiselle.extra.constant.TypeOrderEnum;
import br.gov.serpro.demoiselle.extra.domain.AbstractEntity;
import br.gov.serpro.demoiselle.extra.exception.AbstractException;
import br.gov.serpro.demoiselle.extra.exception.AmbienteException;

public abstract class AbstractJPADAO<E extends AbstractEntity<? extends Serializable>, I> extends JPACrud<E, I> {

	private static final long serialVersionUID = 1L;

	private Pagination pagination;

	@Transactional
	public void save(E entity) {
		if (entity.getId() == null) {
			insert(entity);
		} else {
			update(entity);
		}
	}

	@Transactional
	public void save(List<E> entities) {
		for (E entity : entities) {
			save(entity);
		}
	}

	@Transactional
	public void insert(List<E> entities) {
		for (E entidade : entities) {
			insert(entidade);
		}
	}

	@Transactional
	public void update(List<E> entities) {
		for (E entidade : entities) {
			update(entidade);
		}
	}

	@Transactional
	public void delete(List<I> ids) {
		for (I id : ids) {
			delete(id);
		}
	}

	@Transactional
	public void deleteAll() {
		List<E> entities = findAll();
		for (E entidade : entities) {
			getEntityManager().remove(entidade);
		}
	}

	@Transactional
	public Integer deleteBulkAll() {
		return executeUpdate("delete from " + getBeanClass().getName());
	}

	/**
	 * Acessa uma referência persistida de um objeto no banco, mas não carrega suas informações. Útil para salvar objetos complexos onde há atributos
	 * de outros objetos que precisam ser carregados antes de se persistir o objeto complexo.
	 * 
	 * @param id
	 *            Chave primária da referência.
	 * @return Uma referência persistida ao objeto desejado.
	 */
	public E getReference(I id) {
		Class<E> c = getBeanClass();
		return getEntityManager().getReference(c, id);
	}

	@Transactional
	public void refresh(E entity) {
		getEntityManager().refresh(entity);
	}

	@Transactional
	public E updateAndReturn(E entity) {
		return getEntityManager().merge(entity);
	}

	public void clearEntityManager() {
		getEntityManager().clear();
	}

	@Transactional
	public void flushEntityManager() {
		getEntityManager().flush();
	}

	public void detach(E entity) {
		getEntityManager().detach(entity);
	}

	public boolean contains(E entity) {
		return getEntityManager().contains(entity);
	}

	public Long countAll() {
		return executeSingleResultQuery("select count(this) from " + getBeanClass().getName() + " this", Long.class);
	}

	public List<E> findAllOrderBy(final String field, final TypeOrderEnum typeOrder) {
		String jpql = "select this from " + getBeanClass().getName() + " this order by this." + field + " " + typeOrder.name();
		return executeQuery(jpql);
	}

	protected Query createQuery(final String jpql, final HashMap<String, Object> params) {
		Query query = createQuery(jpql);
		if (params != null && !params.isEmpty()) {
			return fillParams(query, params);
		}
		return query;
	}

	private Query fillParams(Query query, final Map<String, Object> params) {
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		return query;
	}

	@SuppressWarnings("unchecked")
	protected List<E> executeQuery(final String jpql, final HashMap<String, Object> params) {
		return createQuery(jpql, params).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected List<E> executeQuery(final String jpql) {
		return createQuery(jpql).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected E executeSingleResultQuery(final String jpql, final HashMap<String, Object> params) {
		try {
			return (E) createQuery(jpql, params).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	protected E executeSingleResultQuery(final String jpql) {
		return executeSingleResultQuery(jpql, new HashMap<String, Object>());
	}

	@SuppressWarnings("unchecked")
	protected <T> TypedQuery<T> createQuery(final String jpql, final HashMap<String, Object> params, final Class<T> resultClass) {
		TypedQuery<T> typedQuery = getEntityManager().createQuery(jpql, resultClass);
		if (params != null && !params.isEmpty()) {
			return (TypedQuery<T>) fillParams(typedQuery, params);
		}
		return typedQuery;
	}

	protected <T> T executeFirstResultQueryFromResultList(final String jpql, final HashMap<String, Object> params, final Class<T> resultClass) {
		TypedQuery<T> createQuery = createQuery(jpql, params, resultClass);
		List<T> resultList = createQuery.getResultList();
		return !resultList.isEmpty() ? resultList.get(0) : null;
	}

	private <T> T executeSingleResultQuery(final String jpql, final HashMap<String, Object> params, final Class<T> resultClass,
			final boolean forceSingleResult) {
		try {
			TypedQuery<T> createQuery = createQuery(jpql, params, resultClass);
			if (forceSingleResult) {
				createQuery.setMaxResults(1);
			}
			return createQuery.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	protected <T> T executeSingleResultQuery(final String jpql, final HashMap<String, Object> params, final Class<T> resultClass) {
		return executeSingleResultQuery(jpql, params, resultClass, false);
	}

	protected <T> T executeSingleResultQuery(final String jpql, final Class<T> resultClass) {
		return executeSingleResultQuery(jpql, null, resultClass, false);
	}

	protected <T> T executeForceSingleResultQuery(final String jpql, final HashMap<String, Object> params, final Class<T> resultClass) {
		return executeSingleResultQuery(jpql, params, resultClass, true);
	}

	protected <T> T executeForceSingleResultQuery(final String jpql, final Class<T> resultClass) {
		return executeSingleResultQuery(jpql, null, resultClass, true);
	}

	public List<E> findByExample(final E exemplo) {
		return findByExample(exemplo);
	}

	@Transactional
	protected Integer executeUpdate(final String jpql, final HashMap<String, Object> params) {
		return createQuery(jpql, params).executeUpdate();
	}

	@Transactional
	protected Integer executeUpdate(final String jpql) {
		return createQuery(jpql).executeUpdate();
	}

	protected Pagination getPagination(Class<?> clazz) {
		if (pagination == null) {
			try {
				PaginationContext context = Beans.getReference(PaginationContext.class);
				pagination = context.getPagination(clazz);

			} catch (ContextNotActiveException cause) {
				pagination = null;
			}
		}

		return pagination;
	}

	protected void enabledPagination(Query query, Class<?> typePaginationResultQuery, int totalResults) {
		Pagination pagination = getPagination(typePaginationResultQuery);
		if (pagination != null) {
			pagination.setTotalResults(totalResults);
			query.setFirstResult(pagination.getFirstResult());
			query.setMaxResults(pagination.getPageSize());
		}
	}

	/**
	 * Método responsável pelo tratamento de exceção nos DAOs.
	 */
	@ExceptionHandler
	public void tratarExcecao(Exception ex) {

		// Caso seja um AbstractException o tratamento já foi feito então apenas relança a exceção.
		if (ex instanceof AbstractException) {
			throw (AbstractException) ex;

			// Caso contrário a mesma será relançada como uma AmbienteException.
		} else {
			throw new AmbienteException(ex);
		}
	}

}
