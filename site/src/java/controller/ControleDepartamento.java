package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Departamento;
import model.EmpresaDao;

@WebServlet(name = "ControleDepartamento", urlPatterns = {"/ControleDepartamento"})
public class ControleDepartamento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        //Aqui Iremos fazer o Cadastro do usuário
        RequestDispatcher disp;
        String flag, mensagem, id,nome,tel;
        flag = request.getParameter("flag");
         
        
        if (flag.equals("cad_dep")) {
            
            //recebe os dados digitados no formulário
         id =  request.getParameter("id");
         nome = request.getParameter("nome");
         tel = request.getParameter("telefone");
         
         //encapsula os dados recebdos em um objeto da classe Departamento
            Departamento dep = new Departamento();
                   dep.setIdDep(id);
                        dep.setNomeDep(nome);
                        dep.setTelefoneDep(tel);
                 
            
            switch (new EmpresaDao().salvar(dep)) {
                case 1:
                    mensagem = "Departamento salvo com sucesso";
                    break;
                case 2:
                    mensagem = "O departamento " + dep.getIdDep() + " já está cadastrado";
                    break;
                default:
                    mensagem = "Erro ao tentar salvar o departamento";
                    break;
            }
            
            //mandando mensagem
            request.setAttribute("mensagem", mensagem);
            request.getRequestDispatcher("mensagens.jsp").forward(request, response);
            
        } else if (flag.equals("list_dep")) {
            List<Departamento> departamentos = new EmpresaDao().listar("Departamento.findAll", Departamento.class);
            
            
            if (departamentos == null) {
                request.setAttribute("mensagem", "Não há departamentos cadastrados");
                request.getRequestDispatcher("mensagens.jsp").forward(request, response);
                
                
            } else { //se tiver departamentos 
                request.setAttribute("departamentos", departamentos);
                request.getRequestDispatcher("mostrar_departamentos.jsp").forward(request, response);
            }
        }  
        
        else if (flag.equals("exc_dep")){ //excluir departamentos
             id = request.getParameter("id");
            int retorno = new EmpresaDao().excluir(id, Departamento.class);
            
            switch (retorno) {
                case 1:
                    mensagem = "Departamento" +id+ "excluido com sucesso";
                    break;
                case 2:
                    mensagem = "Departamento" +id+ "não cadastrado";
                    break;
                default:
                    mensagem = "Erro encontrado. Entre em contato com a Lana Linda";
                    break;
            }
            //envia mensagem para view
             request.setAttribute("mensagem", mensagem);
             request.getRequestDispatcher("mensagens.jsp").forward(request, response);   
        } 
        
        else if (flag.equals("buscar_alt_dep")) {
            Departamento dep = new EmpresaDao().buscar(Departamento.class,request.getParameter("id"));
            if (dep == null) { //se não encontrou o departamento
                request.setAttribute("mensagem", "Departamento não encontrado");
                request.getRequestDispatcher("mensagens.jsp").forward(request, response);
            } else { //se encontrou o departamento a ser alterado
                request.setAttribute("dep", dep);
                request.getRequestDispatcher("alterar_dep.jsp").forward(request, response);
            }
        }
        
        

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}

