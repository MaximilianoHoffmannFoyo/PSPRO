/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

module sprite {
  requires javafx.swt;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;
  requires javafx.media;
  requires javafx.swing;
  requires javafx.web;
  
  opens org.ccbsoft to javafx.fxml;
  exports org.ccbsoft;
          
}
