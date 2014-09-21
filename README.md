wongillalib
===========

wongillalib provides a UI script where you can apply most Libgdx scene2D via a html-like syntax language for your game user interface. You can even implement your own HTML-like directive and render your own component.

Demo
========
Demo Page: http://nykevinwong.github.io/wongillalib/ 

Please check the demo page with Chrome Browser.
Please check Platformer Game Demo. 

Instruction:
Up,Down,Left,Right for direction.
Z = Jump

Code Example
========

Here's one example of how to set up Wongilla.

WongillalibTest.java
```java
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gamecopter.wongillalib.controllers.DefaultController;
import com.gamecopter.wongillalib.controllers.PlatformerController;
import com.gamecopter.wongillalib.controllers.TouchPadController;
import com.gamecopter.wongillalib.controllers.TouchPadOnMapController;
import com.gamecopter.wongillalib.utils.Sound;

public class WongillalibTest extends ApplicationAdapter {
    Stage stage;
    WongillaScript wongillaScript;

    public void exit() {
        Gdx.app.exit();
    }

    @Override
    public void create() {
        stage = new Stage();

        wongillaScript = new WongillaScript(stage, this);
        wongillaScript.loadLibraries();

        wongillaScript.addController("DefaultController", new DefaultController());
        wongillaScript.addController("TouchPadOnMapController", new TouchPadOnMapController());
        
        Sound.load();
        wongillaScript.LoadStage();


        // process event handler such as click event handler
        Gdx.input.setInputProcessor(stage);
    }

    public void LoadStage() {
        wongillaScript.LoadStage();
    }

    @Override
    public void render() {

        DefaultController defaultController = (DefaultController) wongillaScript.getController("DefaultController");

        if (defaultController.IsWhiteBackground())
            Gdx.gl.glClearColor(1, 1, 1, 1);
        else
            Gdx.gl.glClearColor(0, 0.5f, 0.5f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        wongillaScript.update();


        wongillaScript.draw();
    }


    @Override
    public void resize(int width, int height) {
        // this code handles collision detection for buttons or UI actors when the screen size is changed.
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void pause() {
        Gdx.app.log("ExitTest", "paused");
    }

    @Override
    public void dispose() {
        Gdx.app.log("ExitTest", "disposed");
        wongillaScript.dispose();
    }
}
```

DefaultController.java code
```java
package com.gamecopter.wongillalib.controllers;

/**
 * Created by Kevin Wong on 5/18/2014.
 */
public class DefaultController {
    protected boolean isWhiteColor = false;

    public void SwitchColor()
    {
        isWhiteColor = !isWhiteColor;
    }

    public boolean IsWhiteBackground()
    {
        return isWhiteColor;
    }

}

```

TouchPadOnMapController example of how you implement a moving map with a touchpad.

```java
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.gamecopter.wongillalib.UIScene;
import com.gamecopter.wongillalib.interfaces.SceneEventListener;
import com.gamecopter.wongillalib.services.AssetService;
import com.gamecopter.wongillalib.services.ScopeService;
import com.gamecopter.wongillalib.ui.TiledMapRender;
import com.gamecopter.wongillalib.WongillaScript;

/**
 * Created by Kevin Wong on 7/7/2014.
 */
public class TouchPadOnMapController implements SceneEventListener {
    TiledMapRender gameMap;
    Touchpad touchpad;

    private void makeTransparent(Actor a) {
        // make the touch pad transparent
        Color c = a.getColor();
        Color nc = new Color(c.r, c.g, c.b, 0.8f);
        a.setColor(nc);
    }

    @Override
    public void updateScene(UIScene scene, WongillaScript wongillaScript, ScopeService scopeService, AssetService assetService) {


            int blockSpeed = 3;
            //Move blockSprite with TouchPad
            float x = gameMap.getDrawOffsetX() + touchpad.getKnobPercentX() * 2;
            float y = gameMap.getDrawOffsetY() + touchpad.getKnobPercentY() * -2;

            gameMap.setDrawOffset(x, y);
    }

    @Override
    public void enterScene(UIScene scene) {
        gameMap = (TiledMapRender) scene.findActor("gameMap");
        touchpad = (Touchpad) scene.findActor("touchpad");

        makeTransparent(touchpad);

    }

    @Override
    public void exitScene(UIScene scene) {

    }

    @Override
    public void drawScene(Batch batch, float parentAlpha) {

    }


}
```



Script Examples
========

Start Menu Example


```xml
<wongilla>
    <scene name="startMenu" controller="DefaultController">

        <table debug="table" fillX="true">
            <tr>
                <td>
                    <textbutton OnClickTo="LoadStage()">Reload stage.xml</textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="touchpad-tests.xml">TouchPad Test</textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="load-menu.xml">Load</textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="SwitchColor()">Call
                        SwitchColor() method
                    </textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="ui-tests.xml">UI Test</textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="table-tests.xml">Table</textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="tree-tests.xml">Tree</textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="map-tests.xml">Tield Map Render</textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="animation-tests.xml">Animation</textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="nested-table-tests.xml">Nested Tables</textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="platformer.xml">Platformer Game</textbutton>
                </td>
            </tr>
            <tr>
                <td>
                    <textbutton OnClickTo="exit()">Quit</textbutton>
                </td>
            </tr>
        </table>
    </scene>
</wongilla>


```


Libgdx TextButton Example

```xml
<wongilla>
    <scene name="LoadMenu">

        <textbutton x="100" y="50" OnClickTo="index.xml">Back to Start Menu</textbutton>
        <textbutton x="100" y="100">Record 1</textbutton>
        <textbutton x="100" y="150">Record 2</textbutton>
        <textbutton x="100" y="200">Record 3</textbutton>

    </scene>
</wongilla>

```

User Interface Element Example

```xml
<wongilla>
    <scene name="UITestScene">
        <textbutton x="100" y="50" OnClickTo="index.xml">Back to Start Menu</textbutton>

        <checkbox x="100" y="100">Check me</checkbox>

        <textfield x="100" y="150" password="*"></textfield>
        <textfield x="100" y="200" watermark="Enter your name"></textfield>
        <textfield x="100" y="250">default text</textfield>

        <slider x="100" y="300"></slider>


    </scene>
</wongilla>
```

Libgdx Touchpad Example

```xml
<wongilla>
    <scene name="touchPadScene" controller="TouchPadController">
        <texture name="texture_knob" path="skin/touchpad/touchKnob.png"></texture>


        <table name="table2" debug="all">
            <tr expand="true">
                <td>Welcome to my game world</td>
                <td>testeste</td>
            </tr>
            <tr expand="true" fillX="true">
                <td>
                    <textbutton OnClickTo="index.xml">Back to Start Menu</textbutton>
                </td>
                <td>
                    <textbutton OnClickTo="$Root.LoadStage()">Reload stage.xml</textbutton>
                </td>
            </tr>
            <tr expand="true">
                <td align="left" valign="bottom">
                    <touchpad name="touchpad"></touchpad>
                </td>
                <td>
                    <image name="image1" src="texture_knob" OnClickTo="DefaultController.SwitchColor()"></image>
                </td>
            </tr>
        </table>


    </scene>
</wongilla>
```

Simple Animator/Animation Example

```xml
<wongilla>
    <scene name="animationScene">
        <texture name="superkoalioTexture" path="super-koalio/koalio.png"/>
        <texture name="kennyTexture" path="images/spritesheet.png"/>

        <tileset name="kennyTileSet" src="kennyTexture"  margin="2" cellspacing="2" col="30" row="30" tile-width="21" tile-height="21" />
        <tileset name="superkoalio" src="superkoalioTexture"  margin="0" cellspacing="0" col="7" row="1" tile-width="18" tile-height="32" />

        <action name="MoveToCenter">
            <moveby x="300" duration="1"/>
            <moveby y="100" duration="1"/>
            <moveby x="-300" duration="1"/>
        </action>

        <animator x="150" y="150" tile-set="kennyTileSet"  play="walk" display-width="32" display-height="32">
            <animation name="walk" indexes="[29,0],[28,0]" duration="0.5">
            </animation>
        </animator>


        <textbutton x="100" y="50" OnClickTo="index.xml" action="MoveToCenter">Back to Start Menu</textbutton>

        <animator x="150" y="250" tile-set="superkoalio"  duration="1.025" play="walk">
            <animation name="walk" indexes="[1,0],[2,0],[3,0],[4,0]" duration="2"/>
        </animator>


    </scene>
</wongilla>

```

The Example of a Tiled Map with a TouchPad

```xml
<wongilla>
    <scene name="mapScene" controller="TouchPadOnMapController">

        <TiledMapRender name="gameMap" file="images/test.tmx" display-width="32" display-height="32" >
        </TiledMapRender>


        <table name="mapTable" debug="all">
            <tr expand="true">
                <td align="left" valign="bottom">
                    <touchpad name="touchpad"></touchpad>
                </td>
            </tr>
        </table>

        <textbutton x="100" y="50" OnClickTo="index.xml">Back to Start Menu </textbutton>



    </scene>
</wongilla>
```



Project Setup
=======

Please install Android SDK rev 19+, IntelliJ 13.1.2+, and Java SDK 1.7 u51+.
Please follow libgdx documentation/video tutorials to see how to compile/run a libgdx project.
http://libgdx.badlogicgames.com/documentation.html

