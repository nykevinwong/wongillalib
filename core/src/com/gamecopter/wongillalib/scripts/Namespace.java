package com.gamecopter.wongillalib.scripts;


import java.util.ArrayList;

/**
 * Created by Kevin Wong on 8/6/2014.
 */
public class Namespace {
    private Namespace parent;
    private ArrayList<Namespace> childs;
    private String name;
    private ArrayList<ElementDirective> elements;
    private ArrayList<AttributeDirective> attributes;

    private Namespace(String name)
    {
        this.name = name;
    }

    public Namespace()
    {
    }

    public Namespace namespace(String name)
    {
        String[] names = name.split("\\.");

        Namespace parent = this;

            for (String tmpName : names) {
                parent = createNamespace(tmpName, parent);
            }

        return parent;
    }

    public static Namespace createRootNamespace(String singleName)
    {

        return createNamespace(singleName, null);
    }

    protected static Namespace createNamespace(String singleName, Namespace parentNamespace)
    {
        if(singleName.contains("."))
            return null;

        if(parentNamespace!=null) {
            ArrayList<Namespace> namespaces = parentNamespace.getChild();

            if(namespaces!=null) {
                for (Namespace ns : namespaces) {
                    if (ns.getName().equalsIgnoreCase(singleName))
                        return ns;
                }
            }
        }

        Namespace p = new Namespace(singleName);

        p.setParent(parentNamespace);

        if(parentNamespace!=null)
           parentNamespace.addChild(p);

        return p;
    }

    public Namespace getParent() {
        return parent;
    }

    public boolean containsChild(String namespace)
    {
        for(Namespace ns: childs)
        {
            if(ns.getName().equalsIgnoreCase(namespace))
            {
                return true;
            }
        }

        return false;
    }

    protected void setParent(Namespace parent) {
        this.parent = parent;
    }

    public ArrayList<Namespace>  getChild() {
        return childs;
    }

    public void addChild(Namespace child) {

        if(childs==null)
            childs = new ArrayList<Namespace>();

        //ToDO: no duplicate namespace is allowed.
        if(!childs.contains(child) && !this.containsChild(child.getName()))
            childs.add(child);
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        Namespace ns = this;
        String fullName = "";

        do {
            if(fullName.length() > 0 )
                fullName = "." + fullName;

            String name = ns.getName();

            if(ns.getParent()!=null && name !=null )
               fullName = name  + fullName;

            ns = ns.getParent();

        }while(ns !=null);

        return fullName;
    }

    public void add(ElementDirective d)
    {
            if (elements == null)
                elements = new ArrayList<ElementDirective>();

            elements.add(d);
    }

    public void add(AttributeDirective d)
    {
            if (attributes == null)
                attributes = new ArrayList<AttributeDirective>();

            attributes.add(d);
    }

}
