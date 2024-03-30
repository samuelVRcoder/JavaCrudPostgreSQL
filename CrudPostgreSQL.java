import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudPostgreSQL {
	
	private static final String url = "jdbc:postgresql://host:port/nome_banco";
	
	private static final String user = "CHANGE_ME";
	
	private static final String password = "CHANGE_ME";
	
	private static final String table = "CHANGE_ME";

    public static void main(String[] args) {        

        try {
            
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão realizada com sucesso.");
           
            insert(connection, "Samuel", 40);
           
            String query = read(connection, 3);
            
            System.out.println(query);
           
            update(connection, 1, "Jose", 25);
            
            delete(connection, 1);
            
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insert(Connection connection, String name, int idade) throws SQLException {
        String sql = "INSERT INTO "+table+" (nome, idade) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, idade);
            statement.executeUpdate();
        }
    }
   
    private static String read(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM "+table+" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return "ID: "+resultSet.getInt("id")+"\nNome: "+resultSet.getString("nome")+"\nIdade: "+resultSet.getInt("idade");
                }
            }
        }
        return "Id nao encontrado";
    }

    
    private static void update(Connection connection, int id, String name, int idade) throws SQLException {
        String sql = "UPDATE "+table+" SET nome = ?, idade = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, idade);
            statement.setInt(3, id);
            statement.executeUpdate();
        }
    }

    private static void delete(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM "+table+" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}


