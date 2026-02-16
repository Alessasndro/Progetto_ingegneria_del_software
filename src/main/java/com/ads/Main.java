package com.ads;
import com.ads.db.DbInitializer;
import com.ads.db.DatabaseConnection;
import java.sql.Connection;
{}
public class Main {
    public static void main(String[] args) {
        System.out.println("=== TEST CONNESSIONE DATABASE ===");

        // Test 1: Vediamo se il link e le credenziali sono corretti
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connessione stabilita con successo!");
            }
        } catch (Exception e) {
            System.err.println("❌ Errore di connessione! Controlla se MySQL è acceso e se la password è giusta.");
            System.err.println("Dettaglio errore: " + e.getMessage());
            return; // Interrompiamo se non c'è connessione
        }

        // Test 2: Esecuzione dell'inizializzatore
        System.out.println("\n=== INIZIALIZZAZIONE TABELLE ===");
        try {
            DbInitializer.initialize();
            System.out.println("✅ Operazione completata senza errori.");
            System.out.println("Ora puoi controllare su MySQL Workbench se le tabelle esistono!");
        } catch (Exception e) {
            System.err.println("❌ Errore durante la creazione delle tabelle.");
            e.printStackTrace();
        }
    }
}