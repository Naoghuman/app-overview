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

import com.github.naoghuman.lib.fxml.core.FXMLController;
import com.github.naoghuman.lib.fxml.core.FXMLRegisterable;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

/**
 *
 * @since   0.1.0-PRERELEASE
 * @version 0.1.0-PRERELEASE
 * @author  Naoghuman
 */
public final class ApplicationController extends FXMLController implements FXMLRegisterable, Initializable {
    
    @FXML private TreeView<String> tvProjects;
    @FXML private TextField tfSearch;
    @FXML private VBox vbNavigation;
    @FXML private VBox vbProjects;
    
    private TreeItem<String> navigationRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "ApplicationController#initialize(URL, ResourceBundle)"); // NOI18N
        
        this.initializeSearch();
        
        this.onActionBuildNavigationTree("");
    }
    
    private void initializeSearch() {
        LoggerFacade.getDefault().info(this.getClass(), "ApplicationController#initializeSearch()"); // NOI18N
        
//        tfSearch.getStyleClass().add("search-box");
        tfSearch.textProperty().addListener((Observable observable) -> {
            this.onActionBuildNavigationTree(tfSearch.getText());
        });
    }
    
    public void onActionBuildNavigationTree(final String searchText) {
//        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionBuildNavigationTree()"); // NOI18N
        
        navigationRoot = new TreeItem<>("https://github.com/Naoghuman"); // NOI18N
        navigationRoot.setExpanded(true);
        
        // Button.Load -> GitHub infos.
        TreeItem<String> treeItem = new TreeItem("Tier 1: Basic Libraries");
        treeItem.setExpanded(true);
        TreeItem<String> treeItemChild = new TreeItem("Lib-Action");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("Lib-Logger");
        treeItem.getChildren().add(treeItemChild);
        navigationRoot.getChildren().add(treeItem);
        
        treeItem = new TreeItem("Tier 2: Extended Libraries");
        treeItem.setExpanded(true);
        treeItemChild = new TreeItem("Lib-FXML");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("Lib-I18N");
        treeItem.getChildren().add(treeItemChild);
        navigationRoot.getChildren().add(treeItem);
        
        treeItem = new TreeItem("Tier 3: Frameworks");
        treeItem.setExpanded(true);
        treeItemChild = new TreeItem("Fw-Background");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("Fw-Sampler");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("Fw-Testdata");
        treeItem.getChildren().add(treeItemChild);
        navigationRoot.getChildren().add(treeItem);
        
        treeItem = new TreeItem("Tier 4: Applications");
        treeItem.setExpanded(true);
        treeItemChild = new TreeItem("App-ABC-List");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("App-Yin-Yang");
        treeItem.getChildren().add(treeItemChild);
        navigationRoot.getChildren().add(treeItem);
        
        treeItem = new TreeItem("Additional");
        treeItem.setExpanded(true);
        treeItemChild = new TreeItem("Concentration");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("TypeWriter");
        treeItem.getChildren().add(treeItemChild);
        navigationRoot.getChildren().add(treeItem);
        
        if (searchText != null) {
            this.onActionPruneGitHubProjects(navigationRoot, searchText);
            
            tvProjects.setRoot(null);
            tvProjects.setRoot(navigationRoot);
        }
    }
    
    public void onActionLoadGitHubProjects() {
        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionLoadGitHubProjects()"); // NOI18N
        
    }
    
    private boolean onActionPruneGitHubProjects(final TreeItem<String> treeItem, final String searchText) {
        if (
                searchText == null
//                || searchText.isEmpty()
        ) {
            return true;
        }
        
        if (!treeItem.isLeaf()) {
            final List<TreeItem<String>> toRemove = new ArrayList<>();
            treeItem.getChildren().forEach((child) -> {
                final boolean keep = this.onActionPruneGitHubProjects(child, searchText);
                if (!keep) {
                    toRemove.add(child);
                }
            });
            
            treeItem.getChildren().removeAll(toRemove);
            
            return !treeItem.getChildren().isEmpty();
        }
        else {
            return treeItem.getValue().toUpperCase().contains(searchText.toUpperCase());
        }
    }
    
    @Override
    public void register() {
        LoggerFacade.getDefault().info(this.getClass(), "ApplicationController#register()"); // NOI18N
        
    }
    
}
