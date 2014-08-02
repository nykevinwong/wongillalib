package com.gamecopter.wongillalib.services;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.XmlReader;
import com.gamecopter.wongillalib.UIScene;
import com.gamecopter.wongillalib.WongillaScript;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin Wong on 6/25/2014.
 */
public class ScopeService {
    protected WongillaScript wongillaScript;
    protected HashMap<String, Object> controllers = new HashMap<String, Object>();
    protected HashMap<String, Object> scopeVariables = new HashMap<String, Object>();
    private HashMap<String, ArrayList<Actor>> scopeVariableAssociatedActors = new HashMap<String, ArrayList<Actor>>();

    public ScopeService(WongillaScript script, Object rootController) {
        wongillaScript = script;
        this.setRootController(rootController);
    }

    public Stage getStage() {
        return wongillaScript.getStage();
    }

    public void putScopeVariable(String name, Object object) {
        scopeVariables.put(name, object);
    }

    public void updateScopeVariable(String name, Object object) {
        scopeVariables.put(name, object);
        raiseScopeVariableAssociatedActorEvent(name);
    }

    public void putAction(String name, Action action) {
        scopeVariables.put("ACTION_" + name, action);
    }

    public Action getAction(String name) {
        return (Action) scopeVariables.get("ACTION_" + name);
    }

    public void clearScopeVariables() {
        scopeVariables.clear();
        scopeVariableAssociatedActors.clear();
    }

    public Object getScopeVariable(String name) {
        return scopeVariables.get(name);
    }

    public void addController(String name, Object controller) {
        controllers.put(name, controller);
    }

    public void removeController(String name) {
        controllers.remove(name);
    }

    public Object getController(String name) {
        return controllers.get(name);
    }

    public Object getRootController() {
        return controllers.get("$Root");
    }

    public void setRootController(Object controller) {
        controllers.put("$Root", controller);
    }

    public UIScene getCurrentScene() {
        return wongillaScript.getCurrentScene();
    }

    public void RenderScene(String name) {
        wongillaScript.RenderScene(name);
    }

    public Actor CreateActorFromElement(XmlReader.Element e) {
        return wongillaScript.CreateActorFromElement(e);
    }

    Object currentController = null;

    public Object getCurrentController() {
        return currentController;
    }

    public void setCurrentController(String name) {
        currentController = null;

        if (name != null) {
            Object controller = this.getController(name);

            if (controller != null)
                currentController = controller;
        }

        if (currentController == null)
            currentController = this.getRootController();
    }

    public void addScopeVariableAssociatedActor(String name, Actor a) {
        ArrayList<Actor> actors = scopeVariableAssociatedActors.get(name);

        if (actors == null) {
            actors = new ArrayList<Actor>();
            scopeVariableAssociatedActors.put(name, actors);
        }

        //remove this if it's already added.
        actors.remove(a);
        actors.add(a);

    }

    protected void raiseScopeVariableAssociatedActorEvent(String name) {
        ArrayList<Actor> actors = scopeVariableAssociatedActors.get(name);
        Object value = scopeVariables.get(name);

        if (actors != null) {
            for (Actor a : actors) {
                if (a instanceof CheckBox) {
                    CheckBox box = (CheckBox) a;
                    boolean isChecked = (Boolean) value;
                    box.setChecked(isChecked);
                    continue;
                }

                if (a instanceof Label) {
                    Label label = (Label) a;
                    String text = (String) value;
                    label.setText(text);
                }

            }

        }

    }

}

