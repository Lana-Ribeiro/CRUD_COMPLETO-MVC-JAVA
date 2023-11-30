package model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

public class EmpresaDao {

    private EntityManager manager;
    
    
      //Conectando com o banco de dados
    private void conectar() {
        manager = Persistence.createEntityManagerFactory("sitePU").createEntityManager();
    }

    
        //Validando o Login
    public Usuario validarLogin(String u, String s) {
        conectar();
        try {
            TypedQuery<Usuario> query = manager.createNamedQuery("Usuario.findByEmailSenha", Usuario.class);
            query.setParameter("emailFun", u);
            query.setParameter("senhaUsu", s);
            return query.getSingleResult();
        } catch (NoResultException x) {
            return null;
        }
    }

     //Salvando no banco de dados
    public <T> int salvar(T obj) {
        conectar();
        try {
            manager.getTransaction().begin();
            manager.persist(obj);
            manager.getTransaction().commit();
            return 1;
        } catch (RollbackException erro) { // duplicação de PK   
            return 2;
        } catch (Exception erro) {
            return 0;
        }
    }

    
     //Mostrar os departamentos listados
    public <T> List listar(String queryNomeada, Class<T> classe) {
        conectar();
        try {
            return manager.createNamedQuery(queryNomeada,classe).getResultList();
        } catch (NoResultException erro) {
            return null;
        }
    }
    
    //Excluir departamentos
        public <T> int excluir(String pk, Class <T> classe){
            conectar();
            try{
          T obj = manager.find(classe, pk);
                
            // validar se o departamento é nulo
            if(obj == null){
                return 2; // não encontrou o departamento
            } else{ // se encontrou irá excluir
                manager.getTransaction().begin();
                manager.remove(obj); // exclui o departamento
                manager.getTransaction().commit();
                return 1;
            }
            
        } catch (Exception erro){
            return 0;
        }
     }
        //buscar departamento
        public <T> T buscar(Class<T> classe, String id) {
        conectar();
        try {
           return manager.find(classe, id);
        } catch (Exception x) {
            return null;
        }
    }

        
}
