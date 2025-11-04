package com.example;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL; 
public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        
        String fxmlPath = "/com/example/InterfazGrafica/PantallaRegistrarResultado.fxml";
        URL location = getClass().getResource(fxmlPath);
        
        if (location == null) {
            System.err.println("¡ERROR CRÍTICO! No se pudo encontrar el FXML en la ruta: " + fxmlPath);
            System.err.println("Por favor, verifica que la ruta y el nombre del archivo son correctos en 'src/main/resources'.");
            return; 
        }
        FXMLLoader loader = new FXMLLoader(location);
        Scene scene = new Scene(loader.load(), 900, 750);
        primaryStage.setTitle("Sistema de Registro Sismológico");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
