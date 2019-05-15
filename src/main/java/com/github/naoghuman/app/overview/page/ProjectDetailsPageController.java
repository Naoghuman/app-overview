/*
 * Copyright (C) 2019 Naoghuman's dream
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
package com.github.naoghuman.app.overview.page;

import com.github.naoghuman.lib.fxml.core.FXMLController;
import com.github.naoghuman.lib.fxml.core.FXMLRegisterable;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * will be shown if a project treeview node is selected.
 *  - or if the link in ProjectMasterPageController is selected
 * 
 * what will be shown:
 *  - show the detail information about the project
 *     - description, how long, releases...
 *  - link to githuh project
 *
 * @since   0.1.0-PRERELEASE
 * @version 0.1.0-PRERELEASE
 * @author  Naoghuman
 */
public final class ProjectDetailsPageController extends FXMLController implements FXMLRegisterable, Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "ProjectDetailsPageController#initialize(URL, ResourceBundle)"); // NOI18N
        
        this.register();
    }

    @Override
    public void register() {
        LoggerFacade.getDefault().info(this.getClass(), "ProjectDetailsPageController#register()"); // NOI18N
        
    }
    
}
