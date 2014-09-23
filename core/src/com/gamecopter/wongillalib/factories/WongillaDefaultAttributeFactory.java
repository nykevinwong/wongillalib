package com.gamecopter.wongillalib.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import com.gamecopter.wongillalib.scripts.DirectiveFactory;
import com.gamecopter.wongillalib.services.ScopeService;
import com.gamecopter.wongillalib.scripts.AttributeDirective;


import java.util.ArrayList;

/**
 * Created by Kevin Wong on 6/24/2014.
 */
public class WongillaDefaultAttributeFactory extends DirectiveFactory<AttributeDirective> {

    public WongillaDefaultAttributeFactory(ScopeService scopeService) {
        super(scopeService, null);
    }


    public AttributeDirective CreateValueAttribute() {

        AttributeDirective d = new AttributeDirective("value"){
            @Override
            public void updateInstance(Actor a, XmlReader.Element iElement) {
                String key = iElement.get("value", null);

                if (key != null && a instanceof CheckBox) {

                    if (scopeService.getScopeVariable(key) != null) {
                        if (a instanceof CheckBox) {
                            CheckBox box = (CheckBox) a;
                            boolean isChecked = (Boolean) scopeService.getScopeVariable(key);
                            box.setChecked(isChecked);
                        } else if (a instanceof Label) {
                            String text = (String) scopeService.getScopeVariable(key);
                            ((Label) a).setText(text);
                        } else if (a instanceof TextField) {
                            String text = (String) scopeService.getScopeVariable(key);
                            ((TextField) a).setText(text);
                        }
                    }


                    final String variableName = key;
                    final ScopeService scope = scopeService;

                    scopeService.addScopeVariableAssociatedActor(key, a);

                    if (a instanceof CheckBox) {


                        CheckBox box = (CheckBox) a;
                        box.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Actor listenerActor = event.getListenerActor();
                                CheckBox b = (CheckBox) listenerActor;
                                scope.putScopeVariable(variableName, b.isChecked());

                            }
                        });

                    } else if (a instanceof Label) {
                        // no input

                    } else if (a instanceof TextField) {
                        TextField field = (TextField) a;

                        field.setTextFieldListener(new TextField.TextFieldListener() {
                            @Override
                            public void keyTyped(TextField textField, char c) {
                                scope.putScopeVariable(variableName, textField.getText());
                            }
                        });
                    }


                }
            }

        };

        return d;
    }

    public AttributeDirective CreateYAttribute() {
        AttributeDirective d = new AttributeDirective("y") {
            @Override
            public void updateInstance(Actor a, XmlReader.Element iElement) {
                float y = iElement.getFloat("y", 0);

                if (y > 0) {
                    // convert to screen coordination system
                    y = Gdx.graphics.getHeight() - y;
                }

                // Stage render an actor from its lower-left corner position. The coordination system we used on WongScript is using upper-left corner position.
                y = y + a.getHeight();

                a.setY(y);
            }

        };

        return d;
    }

    public AttributeDirective CreateXAttribute() {
        AttributeDirective d = new AttributeDirective("x") {
            @Override
            public void updateInstance(Actor a, XmlReader.Element iElement) {
                float x = iElement.getFloat("x", 0);
                a.setX(x);
            }

        };

        return d;
    }

    public AttributeDirective CreateWidthAttribute() {
        AttributeDirective d = new AttributeDirective("width")
        {
            @Override
            public void updateInstance(Actor a, XmlReader.Element iElement) {
                float width = iElement.getFloat("width", 0);

                if (width > 0)
                    a.setWidth(width);
            }

        };

        return d;
    }

    public AttributeDirective CreateHeightAttribute() {
        AttributeDirective d = new AttributeDirective("height")
        {
            @Override
            public void updateInstance(Actor a, XmlReader.Element iElement) {
                float width = iElement.getFloat("height", 0);

                if (width > 0)
                    a.setWidth(width);
            }

        };

        return d;
    }

    public AttributeDirective CreateNameAttribute() {
        AttributeDirective d = new AttributeDirective("name")
        {
            @Override
            public void updateInstance(Actor a, XmlReader.Element iElement) {
                String name = iElement.get("name", null);

                if (name != null)
                    a.setName(name);
            }

        };

        return d;
    }

    public AttributeDirective CreateShowAttribute() {
        AttributeDirective d = new AttributeDirective("show"){
            @Override
            public void updateInstance(Actor a, XmlReader.Element iElement) {
                boolean show = iElement.getBoolean("show", true);

                a.setVisible(show);
            }

        };

        return d;
    }




    public AttributeDirective CreateOnClickToAttribute() {
        AttributeDirective d = new AttributeDirective("OnClickTo")
        {
            @Override
            public void updateInstance(Actor a, XmlReader.Element iElement) {
                final String OnClickTo = iElement.getAttribute("OnClickTo", null);
                // add click event
                if (OnClickTo != null && !OnClickTo.isEmpty()) {
                    final String ClickToScene = OnClickTo;
                    final ScopeService scope = scopeService;

                    a.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {

                            scope.eval(ClickToScene);

                        }
                    });

                }

            }

        };

        return d;

    }


    public AttributeDirective CreateActionAttribute() {
        AttributeDirective d = new AttributeDirective("action")
        {
            @Override
            public void updateInstance(Actor a, XmlReader.Element iElement) {
                String name = iElement.get("action", null);

                if (name != null) {
                    Action action = scopeService.getAction(name);

                    if (action != null)
                        a.addAction(action);
                }

            }

        };

        return d;
    }


    @Override
    public ArrayList<AttributeDirective> createDirectives() {
            ArrayList<AttributeDirective> CommonAttributes = new ArrayList<AttributeDirective>();

            CommonAttributes.add(CreateNameAttribute());
            CommonAttributes.add(CreateXAttribute());
            CommonAttributes.add(CreateYAttribute());
            CommonAttributes.add(CreateShowAttribute());
            CommonAttributes.add(CreateOnClickToAttribute());
            CommonAttributes.add(CreateActionAttribute());
            CommonAttributes.add(CreateValueAttribute());

            return CommonAttributes;
    }

}
