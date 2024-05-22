package com.seguranca.localyseg.repository;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.seguranca.localyseg.models.Espaco;
import com.seguranca.localyseg.models.EspacoLog;
import com.seguranca.localyseg.models.UserRole;
import com.seguranca.localyseg.models.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class EspacoLogQueryRepository {
	@PersistenceContext
	private EntityManager em;
	
	public List<EspacoLog> buscaLog(UserRole role, Usuario usuario, Date dataHoraInicio, Date dataHoraFim, Espaco espaco){
		StringBuilder query = new StringBuilder("from EspacoLog e where e.espaco = :espaco");
		
		if(role!= null) 
			query.append(" and e.usuario.role = :role");
		if(usuario != null)
			query.append(" and e.usuario = :usuario");
		if(dataHoraInicio != null)
			query.append(" and e.dataHora >= :dataHoraInicio");
		if(dataHoraFim != null)
			query.append(" and e.dataHora <= :dataHoraFim");
		
		TypedQuery<EspacoLog> typedQuery = em.createQuery(query.toString(), EspacoLog.class);
		typedQuery.setParameter("espaco", espaco);
		
		if(role!= null) 
			typedQuery.setParameter("role", role);
		if(usuario != null)
			typedQuery.setParameter("usuario", usuario);
		if(dataHoraInicio != null)
			typedQuery.setParameter("dataHoraInicio", dataHoraInicio);
		if(dataHoraFim != null)
			typedQuery.setParameter("dataHoraFim", dataHoraFim);
		
		return typedQuery.getResultList();
	}

}
