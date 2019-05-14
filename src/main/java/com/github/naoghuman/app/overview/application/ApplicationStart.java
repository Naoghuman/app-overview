/*
 * Copyright (C) 2019 - 2019 Naoghuman's dream
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.app.overview.application;

import com.github.naoghuman.app.overview.configuration.CSSConfiguration;
import com.github.naoghuman.lib.fxml.core.FXMLRegisterable;
import com.github.naoghuman.lib.fxml.core.FXMLView;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Naoghuman
 * @since   0.1.0-PRERELEASE
 * @version 0.1.0-PRERELEASE
 * @author  Naoghuman
 */
public final class ApplicationStart extends Application implements FXMLRegisterable {
    
    /**
     * 
     * @param   args 
     * @since   0.3.0-PRERELEASE
     * @version 0.3.0-PRERELEASE
     * @author  Naoghuman
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        
        LoggerFacade.getDefault().info(this.getClass(), "ApplicationStart#init()"); // NOI18N
        
        this.register();
    }
    
    @Override
    public void start(final Stage primaryStage) throws Exception {
        LoggerFacade.getDefault().info(this.getClass(), "ApplicationStart#start(Stage)"); // NOI18N
        
        primaryStage.setTitle("hello app-overview"); // NOI18N
        
        final FXMLView view = FXMLView.create(ApplicationController.class);
        
        final Scene scene = new Scene(view.getRoot().get(), 1280, 720);
        scene.getStylesheets().add(CSSConfiguration.CSS_PATH_WITH_FILE);
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

    @Override
    public void register() {
        LoggerFacade.getDefault().info(this.getClass(), "ApplicationStart.register()"); // NOI18N
        
    }
    
}
