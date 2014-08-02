package com.gamecopter.wongillalib.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import com.gamecopter.wongillalib.interfaces.SceneEventListener;
import com.gamecopter.wongillalib.resources.Resources;
import com.gamecopter.wongillalib.ui.*;
import com.gamecopter.wongillalib.services.AssetService;
import com.gamecopter.wongillalib.services.ScopeService;
import com.gamecopter.wongillalib.utils.DirectiveEventListener;
import com.gamecopter.wongillalib.utils.ElementDirective;
import com.gamecopter.wongillalib.utils.TextureUtils;

import java.util.ArrayList;

/**
 * Created by Kevin Wong on 6/24/2014.
 */
public class DirectiveFactory {

    protected AssetService assetService;
    protected ScopeService scopeService;


    public DirectiveFactory(ScopeService scopeService, AssetService assetService) {
        this.assetService = assetService;
        this.scopeService = scopeService;
    }

    public ArrayList<ElementDirective> CreateList() {
        ArrayList<ElementDirective> CommonElements = new ArrayList<ElementDirective>();


        CommonElements.add(CreateLabelDirective());
        CommonElements.add(CreateTextureDirective());
        CommonElements.add(CreatePixmapDirective());
        CommonElements.add(CreateImageDirective());
        CommonElements.add(CreateTableDirective());
        CommonElements.add(CreateTileSetDirective());
        CommonElements.add(CreateActionDirective());
        CommonElements.add(CreateTextboxDirective());
        CommonElements.add(CreateTouchPadDirective());
        CommonElements.add(CreateCheckboxDirective());
        CommonElements.add(CreateTextFieldDirective());
        CommonElements.add(CreateSliderDirective());
        CommonElements.add(CreateAnimatorDirective());
        CommonElements.add(CreateTiledMapRenderDirective());
        CommonElements.add(CreateControllerLayerDirective());

        return CommonElements;
    }

    protected Actor CreateErrorLabel(Exception ex) {
        Label label = new Label(ex.getMessage(), this.assetService.getDeafaultUISkin());
        label.setWrap(true);
        label.setSize(Gdx.graphics.getWidth(), 300);
        label.setPosition(0, Gdx.graphics.getHeight() / 2);
        return label;

    }

    public ElementDirective CreateControllerLayerDirective() {
        ElementDirective d = new ElementDirective("ControllerLayer");

        d.setApplyCommonAttribute(true);

        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {


                try {
                    String controllerName = iElement.get("controller", null);
                    SceneEventListener listener = null;
                    if (controllerName != null) {
                        listener = (SceneEventListener) scopeService.getController(controllerName);
                    } else {
                        listener = (SceneEventListener) scopeService.getCurrentController();
                    }

                    ControllerLayer layer = new ControllerLayer(listener);

                    return layer;
                } catch (Exception ex) {
                    return CreateErrorLabel(ex);
                }

            }


        });

        return d;
    }

    public ElementDirective CreateTiledMapRenderDirective() {
        ElementDirective d = new ElementDirective("TiledMapRender");

        d.setApplyCommonAttribute(true);

        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {
                String src = iElement.get("file", null);
                int displayWidth = iElement.getInt("display-width", 32);
                int displayHeight = iElement.getInt("display-height", 32);

                TiledMapRender render = new TiledMapRender(src);
                render.setTileSize(displayWidth, displayHeight);


                return render;
            }


        });

        return d;
    }

    public ElementDirective CreateTextureDirective() {
        ElementDirective d = new ElementDirective("texture");

        d.setApplyCommonAttribute(false);

        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {

                try {
                    String name = iElement.get("name");
                    String srcPath = iElement.get("path");

                    assetService.addResource(name, srcPath, Resources.ResourceType.Texture);
                } catch (Exception e) {
                    return CreateErrorLabel(e);
                }

                return null;
            }


        });

        return d;
    }

    public ElementDirective CreateTileSetDirective() {
        ElementDirective d = new ElementDirective("tileset");

        d.setApplyCommonAttribute(false);

        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {

                try {
                    String name = iElement.get("name");
                    String source = iElement.get("src");
                    int margin = iElement.getInt("margin", 0);
                    int cellspacing = iElement.getInt("cellspacing", 0);
                    int col = iElement.getInt("col", 0);
                    int row = iElement.getInt("row", 0);
                    int tileWidth = iElement.getInt("tile-width", 16);
                    int tileHeight = iElement.getInt("tile-height", 16);


                    // load the resource
                    Texture texture = (Texture) assetService.getResourceBySource(source, Resources.ResourceType.Texture);

                    TextureRegion[][] regions = TextureUtils.splitWithCellSpacing(texture, col, row, tileWidth, tileHeight, margin, cellspacing, false, false);

                    scopeService.putScopeVariable(name, regions);


                } catch (Exception e) {
                    return CreateErrorLabel(e);
                }

                return null;
            }


        });

        return d;
    }

    public ElementDirective CreatePixmapDirective() {
        ElementDirective d = new ElementDirective("pixmap");

        d.setApplyCommonAttribute(false);

        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {

                try {
                    String name = iElement.get("name");
                    String srcPath = iElement.get("path");

                    assetService.addResource(name, srcPath, Resources.ResourceType.Pixmap);
                } catch (Exception e) {
                    return CreateErrorLabel(e);
                }

                return null;
            }


        });

        return d;
    }

    public ElementDirective CreateImageDirective() {
        ElementDirective d = new ElementDirective("image");


        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {

                String src = iElement.get("src", null);

                Texture texture = (Texture) assetService.getResourceBySource(src, Resources.ResourceType.Texture);

                return new Image(texture);
            }


        });

        return d;
    }

    public ElementDirective CreateTouchPadDirective() {
        ElementDirective d = new ElementDirective("touchpad");


        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {
                // thanks biggz. the code and asset is from https://github.com/biggz/TouchPadTest
                Touchpad touchpad;
                Touchpad.TouchpadStyle touchpadStyle;
                String touchBackgroundSkinPath = iElement.get("background-skin", "skin/touchpad/touchBackground.png");
                String touchKnobSkinPath = iElement.get("knob-skin", "skin/touchpad/touchKnob.png");


                //Set background image
                Texture touchBackground = (Texture) assetService.getResourceBySource(touchBackgroundSkinPath, Resources.ResourceType.Texture);
                Texture touchKnob = (Texture) assetService.getResourceBySource(touchKnobSkinPath, Resources.ResourceType.Texture);

                Skin touchpadSkin = new Skin();
                touchpadSkin.add("touchBackground", touchBackground);
                //Set knob image
                touchpadSkin.add("touchKnob", touchKnob);
                //Create TouchPad Style
                touchpadStyle = new Touchpad.TouchpadStyle();
                //Create Drawable's from TouchPad skin
                touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
                touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");

                //Create new TouchPad with the created style
                touchpad = new Touchpad(10, touchpadStyle);

                float x = iElement.getFloat("x", 0);
                float y = iElement.getFloat("y", 0);
                float width = iElement.getFloat("width", 200);
                float height = iElement.getFloat("height", 200);

                if (y >= 0) {
                    // convert to screen coordination system
                    y = Gdx.graphics.getHeight() - y;

                    // Stage render an actor from its lower-left corner position. The coordination system we used on WongScript is using upper-left corner position.

                }

                //setBounds(x,y,width,height)
                touchpad.setBounds(x, y, width, height);

                return touchpad;
            }


        });

        return d;
    }

    public ElementDirective CreateTextboxDirective() {
        ElementDirective d = new ElementDirective("textbutton");


        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {
                TextButton button = new TextButton(iElement.getText(), assetService.getDeafaultUISkin());

                return button;
            }


        });

        return d;
    }

    public ElementDirective CreateCheckboxDirective() {
        ElementDirective d = new ElementDirective("checkbox");


        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {
                CheckBox button = new CheckBox(iElement.getText(), assetService.getDeafaultUISkin());

                return button;
            }


        });

        return d;
    }

    public ElementDirective CreateSliderDirective() {
        ElementDirective d = new ElementDirective("slider");


        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {
                float min = iElement.getFloatAttribute("min", 0);
                float max = iElement.getFloatAttribute("max", min + 10);
                float stepSize = iElement.getFloatAttribute("stepsize", 1);
                boolean isVertical = iElement.getBoolean("vertical", false);

                Slider slider = new Slider(min, max, stepSize, isVertical, assetService.getDeafaultUISkin());
                return slider;
            }


        });

        return d;
    }

    public ElementDirective CreateLabelDirective() {
        ElementDirective d = new ElementDirective("label");


        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {
                Label label = new Label(iElement.getText(), assetService.getDeafaultUISkin());

                return label;
            }


        });

        return d;
    }

    public ElementDirective CreateTextFieldDirective() {
        ElementDirective d = new ElementDirective("textfield");


        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {
                String defaultText = iElement.getText();
                String passwordCharacter = (iElement.get("password", null));
                String watermark = (iElement.get("watermark", null));
                Boolean isNumeric = iElement.getBoolean("numeric", false);

                if (defaultText != null)
                    defaultText = defaultText.trim();
                else
                    defaultText = "";

                TextField textfield = new TextField(defaultText, assetService.getDeafaultUISkin());

                if (watermark != null) {
                    textfield.setMessageText(watermark);
                }


                if (passwordCharacter != null) {
                    textfield.setPasswordCharacter(passwordCharacter.charAt(0));
                    textfield.setPasswordMode(true);
                }

                if (isNumeric) {
                    textfield.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
                }

                return textfield;
            }


        });

        return d;
    }

    public ElementDirective CreateTableDirective() {
        ElementDirective d = new ElementDirective("table");


        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {

                Table table = new Table(assetService.getDeafaultUISkin());

                XmlReader.Element e = iElement;

                // Normally a widget's size is set by its parent and setFillParent must not be used. setFillParent is for convenience only
                // when the widget's parent does not set the size of its children (such as the stage).

                table.setFillParent(true); // only root table need to set it true.

                String debug = e.getAttribute("debug", null);

                if (debug != null) {
                    if (debug.equalsIgnoreCase("all"))
                        table.debug();
                    else if (debug.equalsIgnoreCase("table"))
                        table.debugTable();
                    else if (debug.equalsIgnoreCase("cell"))
                        table.debugCell();
                    else if (debug.equalsIgnoreCase("widget"))
                        table.debugActor();

                }


                boolean tableExpandX = e.getBoolean("expandX", false);
                boolean tableExpandY = e.getBoolean("expandY", false);
                boolean tableExpand = e.getBoolean("expand", false);
                boolean tableFillX = e.getBoolean("fillX", false);
                boolean tableFillY = e.getBoolean("fillY", false);
                boolean tableFill = e.getBoolean("fill", false);
                boolean tableUniform = e.getBoolean("uniform", false);
                String tableAlign = e.getAttribute("align", "center");
                String tableValign = e.getAttribute("valign", "middle");

                Array<XmlReader.Element> rows = e.getChildrenByName("tr");

                for (XmlReader.Element rowElement : rows) {

                    Array<XmlReader.Element> cells = rowElement.getChildrenByName("td");

                    boolean rowExpandX = rowElement.getBoolean("expandX", tableExpandX);
                    boolean rowExpandY = rowElement.getBoolean("expandY", tableExpandY);
                    boolean rowExpand = rowElement.getBoolean("expand", tableExpand);
                    boolean rowFillX = rowElement.getBoolean("fillX", tableFillX);
                    boolean rowFillY = rowElement.getBoolean("fillY", tableFillY);
                    boolean rowFill = rowElement.getBoolean("fill", tableFill);
                    boolean rowUniform = rowElement.getBoolean("uniform", tableUniform);
                    String rowAlign = rowElement.getAttribute("align", tableAlign);
                    String rowValign = rowElement.getAttribute("valign", tableValign);


                    // no support for multiple elements.
                    for (XmlReader.Element cellElement : cells) {

                        String text = cellElement.getText();
                        Cell cell = null;

                        if (text != null) {
                            cell = table.add(text);
                        } else if (cellElement.getChildCount() > 0) {
                            XmlReader.Element child = cellElement.getChild(0);
                            Actor a = scopeService.CreateActorFromElement(child);

                            if (a != null) {

                                if (child.getName().equalsIgnoreCase("table")) {
                                    // Normally a widget's size is set by its parent and setFillParent must not be used. setFillParent is for convenience only
                                    // when the widget's parent does not set the size of its children (such as the stage).

                                    Table t = (Table) a;
                                    t.setFillParent(false);
                                }

                                cell = table.add(a);
                            }


                        }

                        if (cell != null) {
                            boolean expandX = cellElement.getBoolean("expandX", rowExpandX);
                            boolean expandY = cellElement.getBoolean("expandY", rowExpandY);
                            boolean expand = cellElement.getBoolean("expand", rowExpand);
                            boolean fillX = cellElement.getBoolean("fillX", rowFillX);
                            boolean fillY = cellElement.getBoolean("fillY", rowFillY);
                            boolean fill = cellElement.getBoolean("fill", rowFill);
                            int width = cellElement.getInt("width", -1);
                            int colspan = cellElement.getInt("colspan", 0);
                            boolean cellUniform = cellElement.getBoolean("uniform", rowUniform);
                            String align = cellElement.getAttribute("align", rowAlign);
                            String vAlign = cellElement.getAttribute("valign", rowValign);

                            if (width >= 0) {
                                cell.width(width);
                            }

                            if (expand || (expandX && expandY)) {
                                cell.expand();
                            } else {
                                if (expandX)
                                    cell.expandX();

                                if (expandY)
                                    cell.expandY();
                            }

                            if (fill || (fillX && fillY)) {
                                cell.fill();
                            } else {
                                if (fillX)
                                    cell.fillX();

                                if (fillY)
                                    cell.fillY();
                            }

                            if (colspan > 0) {
                                cell.colspan(colspan);
                            }

                            if (cellUniform) {
                                cell.uniform();
                            }

                            if (align.equalsIgnoreCase("center")) {
                                // cell default is the center.
                            } else if (align.equalsIgnoreCase("left"))
                                cell.left();
                            else if (align.equalsIgnoreCase("right"))
                                cell.right();

                            if (vAlign.equalsIgnoreCase("middle")) {
                                // cell default is the center.
                            } else if (vAlign.equalsIgnoreCase("top"))
                                cell.top();
                            else if (vAlign.equalsIgnoreCase("bottom"))
                                cell.bottom();

                        }

                    }

                    Cell row = table.row();

                    if (row != null) {
                        int height = rowElement.getInt("height", -1);

                        if (height >= 0) {
                            row.height(height);
                        }
                    }
                }


                return table;
            }


        });

        return d;
    }

    protected Animation.PlayMode getPlayMode(String mode) {
        String[] modelist = {"NORMAL", "REVERSED", "LOOP", "LOOP_REVERSED", "LOOP_PINGPONG", "LOOP_RANDOM"};
        Animation.PlayMode[] playModes = {
                Animation.PlayMode.NORMAL,
                Animation.PlayMode.REVERSED,
                Animation.PlayMode.LOOP,
                Animation.PlayMode.LOOP_REVERSED,
                Animation.PlayMode.LOOP_PINGPONG,
                Animation.PlayMode.LOOP_RANDOM};

        if (modelist != null) {


            for (int i = 0; i < modelist.length; i++) {
                if (modelist[i].equalsIgnoreCase(mode))
                    return playModes[i];
            }
        }

        return Animation.PlayMode.NORMAL;
    }

    public ElementDirective CreateAnimatorDirective() {
        ElementDirective d = new ElementDirective("animator");


        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {
                String tileSet = iElement.get("tile-set", null);
                float duration = iElement.getFloat("duration", 0.025f);
                String playName = iElement.get("play", null);
                int displayWidth = iElement.getInt("display-width", 0);
                int displayHeight = iElement.getInt("display-height", 0);

                Array<XmlReader.Element> animations = iElement.getChildrenByName("animation");
                ArrayList<UIAnimation> UIAnimations = new ArrayList<UIAnimation>();

                for (XmlReader.Element animationElement : animations) {
                    String animationName = animationElement.get("name", null);
                    String indexes = animationElement.get("indexes", "");
                    String mode = animationElement.get("mode", "normal");
                    Animation.PlayMode playMode = getPlayMode(mode);

                    float animationDuartion = animationElement.getFloat("duration", 0.025f);


                    UIAnimation animation = new UIAnimation(animationName, indexes, animationDuartion, playMode);

                    UIAnimations.add(animation);
                }


                if (tileSet != null) {
                    TextureRegion[][] tilesetRegions = (TextureRegion[][]) scopeService.getScopeVariable(tileSet);

                    Animator animator = new Animator(tilesetRegions, duration, UIAnimations);

                    if (displayWidth > 0 && displayHeight > 0)
                        animator.setTileSize(displayWidth, displayHeight);

                    if (playName != null)
                        animator.setCurrentAnimation(playName);
                    return animator;
                }

                return null;
            }


        });

        return d;
    }

    protected Action CreateActions(XmlReader.Element iElement, boolean parallel) {
        ParallelAction Actions = null;

        if (parallel)
            Actions = new ParallelAction();
        else
            Actions = new SequenceAction();

        for (int i = 0; i < iElement.getChildCount(); i++) {
            XmlReader.Element e = iElement.getChild(i);
            String childName = e.getName();
            float x = e.getFloat("x", 0);
            float y = e.getFloat("y", 0);
            float duration = e.getFloat("duration", 0);

            if (childName.equalsIgnoreCase("move")) {

                MoveToAction a = new MoveToAction();
                a.setPosition(x, y);
                a.setDuration(duration);
                Actions.addAction(a);
                continue;
            }

            if (childName.equalsIgnoreCase("scale")) {

                ScaleToAction a = new ScaleToAction();
                a.setScale(x, y);
                a.setDuration(duration);
                Actions.addAction(a);
                continue;
            }

            if (childName.equalsIgnoreCase("rotate")) {
                float degree = e.getFloat("degree", 0);
                RotateToAction a = new RotateToAction();
                a.setRotation(degree);
                a.setDuration(duration);
                Actions.addAction(a);
                continue;
            }

            if (childName.equalsIgnoreCase("moveby")) {

                MoveByAction a = new MoveByAction();
                a.setAmount(x, -y);
                a.setDuration(duration);
                Actions.addAction(a);
                continue;
            }

            if (childName.equalsIgnoreCase("scaleby")) {

                ScaleByAction a = new ScaleByAction();
                a.setAmount(x, y);
                a.setDuration(duration);
                Actions.addAction(a);
                continue;
            }

            if (childName.equalsIgnoreCase("rotateby")) {
                float degree = e.getFloat("degree", 0);
                RotateByAction a = new RotateByAction();
                a.setAmount(degree);
                a.setDuration(duration);
                Actions.addAction(a);
                continue;
            }

            if (childName.equalsIgnoreCase("alpha")) {
                float alpha = e.getFloat("alpha", 0);
                AlphaAction a = new AlphaAction();
                a.setAlpha(alpha);
                a.setDuration(duration);
                Actions.addAction(a);
                continue;
            }

            if (childName.equalsIgnoreCase("color")) {
                String endColor = e.get("endColor", null);
                String startColor = e.get("startColor", null);
                ColorAction a = new ColorAction();

                if (startColor != null)
                    a.setColor(new Color(Integer.parseInt(startColor, 16)));

                if (endColor != null)
                    a.setEndColor(new Color(Integer.parseInt(endColor, 16)));

                a.setDuration(duration);
                Actions.addAction(a);
                continue;
            }

            if (childName.equalsIgnoreCase("visible")) {
                String show = e.get("show", null);

                if (show != null) {
                    VisibleAction a = new VisibleAction();
                    a.setVisible(show.equalsIgnoreCase("true"));
                    Actions.addAction(a);
                }
                continue;
            }

            //event type actions
            if (childName.equalsIgnoreCase("touchable")) {
                boolean enable = e.getBoolean("enable", false);

                TouchableAction a = new TouchableAction();

                if (enable)
                    a.setTouchable(Touchable.enabled);
                else
                    a.setTouchable(Touchable.disabled);

                Actions.addAction(a);
                continue;
            }

        }

        return Actions;
    }

    public ElementDirective CreateActionDirective() {
        ElementDirective d = new ElementDirective("action");

        d.setApplyCommonAttribute(false);


        d.addEventListener(new DirectiveEventListener() {
            @Override
            // createInstance creates an object or actor based on XML element.
            public Actor createInstance(XmlReader.Element iElement) {

                String name = iElement.get("name", null);

                SequenceAction sequenceAction = (SequenceAction) CreateActions(iElement, false);

                if (sequenceAction.getActions().size > 0) {
                    scopeService.putAction(name, sequenceAction);
                }


                return null;
            }


        });

        return d;
    }


    public ArrayList<ElementDirective> CreateSceneActionList() {
        ArrayList<ElementDirective> ActionElements = new ArrayList<ElementDirective>();
        return ActionElements;
    }

}
