package br.com.sisvendas; 

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.jboss.logging.Logger;

public  class FabricaEntityManager {
    //Sempre irá utilizar a mesma instancia(ojeto) emf -> Padrão Singleton
    private static EntityManagerFactory emf;
    
	public static EntityManagerFactory getEmf() {
		if (emf == null){
			synchronized (FabricaEntityManager.class) {
				if (emf == null)
					try {
						emf = Persistence.createEntityManagerFactory("SistemaVendas");
					} catch (RuntimeException ex) {
						Logger.getLogger(FabricaEntityManager.class).fatal("não foi possível carregar a unidade de persistencia", ex);
						throw ex;
					}
			}
		}
		return emf;
	}
        
	public static EntityManager createEm() { //método que usa uma transação pra cada parte do programa
		try {
			return getEmf().createEntityManager();
		} catch (RuntimeException ex) {
			Logger.getLogger(FabricaEntityManager.class).error("falha ao criar EntityManager", ex);
			throw ex;
		}
        }
        
}