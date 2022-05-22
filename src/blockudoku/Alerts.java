/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author PC
 */
public class Alerts {
    
    public static void display(String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error");
        window.setMinWidth(250);
        
        Label label = new Label();
        label.setText(message);
        
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> window.close());
        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, closeButton);
        vbox.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.showAndWait();
    }
    
}
