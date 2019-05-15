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

import com.github.naoghuman.app.overview.page.GitHubAccountPageController;
import com.github.naoghuman.app.overview.page.ProjectDetailsPageController;
import com.github.naoghuman.app.overview.page.TopicPageController;
import com.github.naoghuman.lib.fxml.core.FXMLController;
import com.github.naoghuman.lib.fxml.core.FXMLRegisterable;
import com.github.naoghuman.lib.fxml.core.FXMLView;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @since   0.1.0-PRERELEASE
 * @version 0.1.0-PRERELEASE
 * @author  Naoghuman
 */
public final class ApplicationController extends FXMLController implements FXMLRegisterable, Initializable {
    
    private static final String NODE__GITHUB_ACCOUNT             = "https://github.com/Naoghuman"; // NOI18N
    private static final String NODE__TIER_1__BASIC_LIBRARIES    = "Tier 1: Basic Libraries";      // NOI18N
    private static final String NODE__TIER_2__EXTENDED_LIBRARIES = "Tier 2: Extended Libraries";   // NOI18N
    private static final String NODE__TIER_3__FRAMEWORKS         = "Tier 3: Frameworks";           // NOI18N
    private static final String NODE__APPLICATIONS               = "Applications";                 // NOI18N
    private static final String NODE__ADDITIONAL                 = "Additional";                   // NOI18N
    
    @FXML private TreeView<String> tvGitHubAccount;
    @FXML private TextField tfSearch;
    @FXML private VBox vbNavigation;
    @FXML private VBox vbProjects;
    
    private TreeItem<String> navigationRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "ApplicationController#initialize(URL, ResourceBundle)"); // NOI18N
        
        this.register();
        
        this.initializeSearch();
        this.initializeGitHubAccount();
        
        this.onActionBuildNavigationTree(""); // NOI18N
    }
    
    private void initializeGitHubAccount() {
        LoggerFacade.getDefault().info(this.getClass(), "ApplicationController#initializeGitHubAccount()"); // NOI18N
        
        tvGitHubAccount.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) -> {
            if (newValue == null) {
                return;
            }

            switch(newValue.getValue()) {
                case NODE__GITHUB_ACCOUNT:             { this.onActionShowPageGitHubAccount();          break; }
                case NODE__TIER_1__BASIC_LIBRARIES:    { this.onActionShowPageTier1BasicLibraries();    break; }
                case NODE__TIER_2__EXTENDED_LIBRARIES: { this.onActionShowPageTier2ExtendedLibraries(); break; }
                case NODE__TIER_3__FRAMEWORKS:         { this.onActionShowPageTier3Frameworks();        break; }
                case NODE__APPLICATIONS:               { this.onActionShowPageApplications();           break; }
                case NODE__ADDITIONAL:                 { this.onActionShowPageAdditional();             break; }
                default:                               { this.onActionShowProjectDetailsPage(newValue.getValue()); break; }
            }
        });
    }
    
    private void initializeSearch() {
        LoggerFacade.getDefault().info(this.getClass(), "ApplicationController#initializeSearch()"); // NOI18N
        
//        tfSearch.getStyleClass().add("search-box");
        tfSearch.textProperty().addListener((Observable observable) -> {
            this.onActionBuildNavigationTree(tfSearch.getText());
        });
    }
    
    private void onActionShowPage(final FXMLView view) {
        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionShowPage(FXMLView)"); // NOI18N
        
        vbProjects.getChildren().clear();
        
        final Optional<Parent> parent = view.getRoot();
        parent.ifPresent(page -> {
            vbProjects.getChildren().add(page);
            VBox.setVgrow(page, Priority.ALWAYS);
        });
    }
    
    private void onActionShowPageAdditional() {
        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionShowPageAdditional()"); // NOI18N
        
        final FXMLView view = FXMLView.create(TopicPageController.class);
        this.onActionShowPage(view);
    }
    
    private void onActionShowPageApplications() {
        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionShowPageApplications()"); // NOI18N
        
        final FXMLView view = FXMLView.create(TopicPageController.class);
        this.onActionShowPage(view);
    }
    
    /**
     * TODO#8 [gui] Implement basic project- + details-view structure.
     *  - select a treenode shows the information
     *  - root    = Welcome to Naoghuman GitHub projects
     *              Show also the tier-image
     *              overview information
     *  - topic   = show all contained projects
     *              with master-details
     *  - project = show the details-view from the project
     * 
     * do search
     *  - remove the shown information
     *  - user have to active click a node
     * 
     * @param searchText 
     */
    public void onActionBuildNavigationTree(final String searchText) {
//        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionBuildNavigationTree()"); // NOI18N
        
        navigationRoot = new TreeItem<>(NODE__GITHUB_ACCOUNT);
        navigationRoot.setExpanded(true);
        
        // Button.Load -> GitHub infos.
        TreeItem<String> treeItem = new TreeItem(NODE__TIER_1__BASIC_LIBRARIES);
        treeItem.setExpanded(true);
        TreeItem<String> treeItemChild = new TreeItem("Lib-Action");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("Lib-Logger");
        treeItem.getChildren().add(treeItemChild);
        navigationRoot.getChildren().add(treeItem);
        
        treeItem = new TreeItem(NODE__TIER_2__EXTENDED_LIBRARIES);
        treeItem.setExpanded(true);
        treeItemChild = new TreeItem("Lib-FXML");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("Lib-I18N");
        treeItem.getChildren().add(treeItemChild);
        navigationRoot.getChildren().add(treeItem);
        
        treeItem = new TreeItem(NODE__TIER_3__FRAMEWORKS);
        treeItem.setExpanded(true);
        treeItemChild = new TreeItem("Fw-Background");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("Fw-Sampler");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("Fw-Testdata");
        treeItem.getChildren().add(treeItemChild);
        navigationRoot.getChildren().add(treeItem);
        
        treeItem = new TreeItem(NODE__APPLICATIONS);
        treeItem.setExpanded(true);
        treeItemChild = new TreeItem("App-ABC-List");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("App-Yin-Yang");
        treeItem.getChildren().add(treeItemChild);
        navigationRoot.getChildren().add(treeItem);
        
        treeItem = new TreeItem(NODE__ADDITIONAL);
        treeItem.setExpanded(true);
        treeItemChild = new TreeItem("Concentration");
        treeItem.getChildren().add(treeItemChild);
        treeItemChild = new TreeItem("TypeWriter");
        treeItem.getChildren().add(treeItemChild);
        navigationRoot.getChildren().add(treeItem);
        
        if (searchText != null) {
            this.onActionPruneGitHubProjects(navigationRoot, searchText);
            
            tvGitHubAccount.setRoot(null);
            tvGitHubAccount.setRoot(navigationRoot);
        }
    }
    
    public void onActionLoadGitHubProjects() {
        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionLoadGitHubProjects()"); // NOI18N
        // load all infos from github and build tree
    }
    
    private void onActionShowPageGitHubAccount() {
        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionShowPageGitHubAccount()"); // NOI18N
        
        final FXMLView view = FXMLView.create(GitHubAccountPageController.class);
        this.onActionShowPage(view);
    }
    
    private void onActionShowProjectDetailsPage(final String project) {
        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionShowProjectDetailsPage(String)"); // NOI18N
        
        final FXMLView view = FXMLView.create(ProjectDetailsPageController.class);
        this.onActionShowPage(view);
    }
    
    private void onActionShowPageTier1BasicLibraries() {
        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionShowPageTier1BasicLibraries()"); // NOI18N
        
        final FXMLView view = FXMLView.create(TopicPageController.class);
        this.onActionShowPage(view);
    }
    
    private void onActionShowPageTier2ExtendedLibraries() {
        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionShowPageTier2ExtendedLibraries()"); // NOI18N
        
        final FXMLView view = FXMLView.create(TopicPageController.class);
        this.onActionShowPage(view);
    }
    
    private void onActionShowPageTier3Frameworks() {
        LoggerFacade.getDefault().debug(this.getClass(), "ApplicationController#onActionShowPageTier3Frameworks()"); // NOI18N
        
        final FXMLView view = FXMLView.create(TopicPageController.class);
        this.onActionShowPage(view);
    }
    
    private boolean onActionPruneGitHubProjects(final TreeItem<String> treeItem, final String searchText) {
        if (searchText == null) {
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
