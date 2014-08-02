package com.gamecopter.wongillalib.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kevin Wong on 7/11/2014.
 */
public class Resources implements Disposable {
    public HashMap<String, Object> resourceMaps = new HashMap<String, Object>();

    public enum ResourceType {
        Texture,
        Pixmap
    }

    public Object LoadResourceFromFile(String srcPath, ResourceType type) {
        if (type == ResourceType.Pixmap) {
            return new Pixmap(Gdx.files.internal(srcPath));
        }

        if (type == ResourceType.Texture) {
            return new Texture(Gdx.files.internal(srcPath));
        }

        return null;
    }

    public Object getResourceBySource(String src, ResourceType type) {
        Object resource = this.getByName(src);

        if (resource == null) // not available for this name
        {
            resource = this.getByPath(src);

            if (resource == null) // never add this resource before.
            {
                this.addResource(src, src, type); // use the path name as key
                resource = this.getByName(src);
            }
        }

        return resource;
    }


    public Object getByName(String name) {
        return resourceMaps.get(name);
    }

    public Object getByPath(String srcPath) {
        return resourceMaps.get(srcPath);
    }

    public boolean addResource(String name, String srcPath, ResourceType type) {
        if (name == null || // must provide a name.
                resourceMaps.containsKey(name)) // this name is used before.
            return false;

        if (resourceMaps.size() > 0) {
            // perform a lookup to see if this pixmap is loaded before.
            // if it's loaded before, add another name for the same instance.

            Object resource = resourceMaps.get(srcPath);


            // save with this path before. save the same pixmap with new key
            if (resource != null) {
                resourceMaps.put(name, resource);
                return true;
            }

        }

        // if it's never loaded before from this srcPath.
        Object newResource = LoadResourceFromFile(srcPath, type);
        resourceMaps.put(name, newResource);
        resourceMaps.put(srcPath, newResource); // save with srcPath name.

        return true;
    }

    @Override
    public void dispose() {
        ArrayList<Object> disposedList = new ArrayList<Object>();
        Object[] list = resourceMaps.values().toArray();

        // dispose all resources
        for (int i = 0; i < list.length; i++) {
            Object existingResource = list[i];

            if (existingResource instanceof Disposable &&
                    !disposedList.contains(existingResource)) {
                Disposable d = (Disposable) existingResource;
                d.dispose();
                disposedList.add(d);
            }

        }

        resourceMaps.clear();
    }

}
