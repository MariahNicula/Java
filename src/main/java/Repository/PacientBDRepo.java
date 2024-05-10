package Repository;

import Domain.Pacient;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.*;

public class PacientBDRepo  extends Repository<Pacient> implements IDbRepository<Pacient> {
    private static String JDBC_URL = "jdbc:sqlite:database.db";

    private Connection connection;

    public PacientBDRepo()
    {
        openConnection();
        createTable();
        //initTable();
        //generateAndSaveEntities();
    }

    public void openConnection() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed())
            {
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection()
    {
        if (connection != null)
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTable()
    {
        try (final Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS patients(id int, nume varchar(400),prenume varchar(400), varsta int);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initTable()
    {
        List<Pacient> patients = new ArrayList<>();
        patients.add(new Pacient(1, "Ion","Pop", 23));
        patients.add(new Pacient(2, "Maria", "Ionescu", 25));
        patients.add(new Pacient(3, "IRadu",  "Verdea", 30));
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO patients values (?,?,?,?);")) {
            for (Pacient p: patients)
            {
                stmt.setInt(1,p.getId());
                stmt.setString(2,p.getNume());
                stmt.setString(3,p.getPrenume());
                stmt.setInt(4,p.getVarsta());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pacient> getAll()
    {
        ArrayList<Pacient> patients = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * from patients;")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pacient p = new Pacient(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getInt(4));
                patients.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }
    @Override
    public void add_pacient(Pacient p)
    {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO patients values (?,?,?,?);")) {
            stmt.setInt(1,p.getId());
            stmt.setString(2,p.getNume());
            stmt.setString(3,p.getPrenume());
            stmt.setInt(4,p.getVarsta());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id)
    {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM patients WHERE id=?;")) {
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Pacient p)
    {

        try (PreparedStatement stmt = connection.prepareStatement("UPDATE patients SET nume=?, prenume=?, varsta=? WHERE id=?;"))
        {
            stmt.setString(1,p.getNume());
            stmt.setString(2,p.getPrenume());
            stmt.setInt(3,p.getVarsta());
            stmt.setInt(4,p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pacient find(int id) {
        Pacient pacient = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM patients WHERE id = ?;")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pacient = new Pacient(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), rs.getInt("varsta"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacient;
    }
    private static void generateAndSaveEntities() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            // Generare și salvare pacienți
            generateAndSavePatients(connection);;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void generateAndSavePatients(Connection connection) throws SQLException {
        String insertPatientQuery = "INSERT INTO patients (id, nume, prenume, varsta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPatientQuery)) {
            Set<Integer> generatedIds = new HashSet<>();

            for (int i = 0; i < 100; i++) {
                int id;
                do {
                    id = new Random().nextInt(1000) + 1;
                } while (id == 0 || !generatedIds.add(id));

                String nume = generateRandomName();
                String prenume = generateRandomName();
                int varsta = generateRandomAge();

                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, nume);
                preparedStatement.setString(3, prenume);
                preparedStatement.setInt(4, varsta);

                preparedStatement.executeUpdate();
            }
        }
    }


    private static String generateRandomName() {
        List<String> names = List.of("John", "Jane", "Michael", "Emma", "Daniel", "Olivia", "David", "Sophia");
        Random random = new Random();
        return names.get(random.nextInt(names.size()));
    }

    private static int generateRandomAge() {
        return new Random().nextInt(40) + 18; // Age between 18 and 57
    }
}
