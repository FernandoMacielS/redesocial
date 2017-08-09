package br.com.redesocial.modelo.dao;

import java.sql.ResultSet;
import br.com.redesocial.modelo.dto.Multimidia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lara, Jeferson, Luciano
 */
public class MultimidiaDAO extends DAOCRUDBase<Multimidia> {
    @Override
    public void inserir(Multimidia m) throws Exception {
        Connection conexao = getConexao();
        
        System.out.print(m);
        
        //Arrumar as validações
        if (m.getMidia().equals("")){
            throw new Exception("A mídia não pode estar vazia!");
        }

        PreparedStatement pstmt;
        pstmt = conexao.prepareStatement("insert into multimidia(id, midia, tipoConteudo, data) values(?, ?, ?, ?)");

        pstmt.setInt(1, m.getId());
        pstmt.setString(2, m.tipoConteudo());
        pstmt.setByte(3, m.getmidia());
        pstmt.setDate(4, new java.sql.Date(m.getData().getTime()));

        pstmt.executeUpdate();
        
        m.setId(getId(pstmt));
    }
   
    @Override
    public Multimidia selecionar(int id) throws Exception{
        Connection conexao = getConexao();

        PreparedStatement pstmt;
        pstmt = conexao.prepareStatement("select * from multimidia where id = ?");
        pstmt.setInt(1, id);

        ResultSet rs;
        rs = pstmt.executeQuery();

        if (rs.next()){
            Multimidia m = new Multimidia();
            m.setId(rs.getInt("id"));
            m.setMidia(rs.getBytes("midia"));
            m.setTipoConteudo(rs.getString("tipoConteudo"));
            m.setData(rs.getDate("data"));

            return m;
        } else {
            return null;
        }
    }

    @Override
    public void excluir(int id) throws Exception {
        Connection conexao = getConexao();

        PreparedStatement pstmt;
        pstmt = conexao.prepareStatement("delete from multimidia where id = ?");

        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
    
    @Override
    public List listar() throws Exception {
        Connection conexao = getConexao();
        
        PreparedStatement pstmt;
        pstmt = conexao.prepareStatement("select * from multimidia order by id desc");
        
        ResultSet rs;
        rs = pstmt.executeQuery();
        
        List lista;
        lista = new ArrayList();
        
        while (rs.next()){
            Multimidia m = new Multimidia();
            m.setId(rs.getInt("id"));
            m.setMidia(rs.getBytes("midia"));
            m.setTipoConteudo(rs.getString("TipoConteudo"));
            m.setData(rs.getDate("data"));
            lista.add(m);
        }
        
        return lista;
    }

    @Override
    public void alterar(Multimidia dto) throws Exception {
        
    }
}